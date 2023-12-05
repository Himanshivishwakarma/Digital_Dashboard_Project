package digital_board.digital_board.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import digital_board.digital_board.Dto.AuthResponse;
import digital_board.digital_board.Dto.SignupRequestDto;
import digital_board.digital_board.Dto.SignupResponseDto;
import digital_board.digital_board.Entity.ExceptionResponse;
import digital_board.digital_board.Entity.Notice;
import digital_board.digital_board.Entity.User;
import digital_board.digital_board.Repository.UserRepository;
import digital_board.digital_board.ServiceImpl.EmailServiceImpl;
import digital_board.digital_board.ServiceImpl.UserServiceImpl;
import digital_board.digital_board.Servies.Auth0Service;
import digital_board.digital_board.constants.ResponseMessagesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/user")
public class UserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserServiceImpl userServiceImpl;

  @Autowired
  private Auth0Service auth0Service;

  @Autowired
  EmailServiceImpl emailServices;

  @Autowired
  private Cloudinary cloudinary;

  @Autowired
  private UserRepository userRepo;

  // @Autowired
  // private EVENT_LOGSRepository eVENT_LOGSRepository;

  @GetMapping("/test")
  public ResponseEntity<AuthResponse> home(@AuthenticationPrincipal OidcUser principal) {
    AuthResponse authResponse = new AuthResponse();
    authResponse.setName(principal.getEmail());
    authResponse.setToken(principal.getIdToken().getTokenValue());
    return ResponseEntity.ok(authResponse);
  }

  @GetMapping("/public")
  public String publicTest() {
    LOGGER.info("Start User Controller : public method");

    MDC.put("User", "mashid@gmail.com");
    MDC.put("path", "/public");
    LOGGER.info("WelcomeMsg action called..");
    // MDC.remove("User");
    // MDC.remove("path");

    // emailServices.sendSimpleMessage("sahilkhanskkhan4@gmail.com", "email test",
    // "Sahil");

    MDC.clear();
    LOGGER.info("End User Controller : public method");

    return "working";
  }

  // create user
  // @PostMapping("/CreatUser")
  // ResponseEntity<?> CreateUser(@RequestBody User user) {

  // Map<String,Object> response = new HashMap<>();
  // response.put("Massage",ResponseMessagesConstants.messagelist.stream()
  // .filter(exceptionResponse ->
  // "USER_CREATE_SUCCESS".equals(exceptionResponse.getExceptonName()))
  // .map(ExceptionResponse::getMassage)
  // .findFirst()
  // .orElse("Default message if not found"));
  // response.put("User", userServiceImpl.CreateUser(user));
  // return ResponseEntity.ok(response);
  // }

  @PostMapping("/signup")
  public ResponseEntity<Map<String, Object>> signUp(@RequestBody SignupRequestDto signupRequestDto) {
    LOGGER.info("Start User Controller : signUp method");
    Map<String, Object> response = new HashMap<>();
    try {
      SignupResponseDto signupResponseDto = auth0Service.signUp(signupRequestDto);

      String successMessage = ResponseMessagesConstants.messagelist.stream()
          .filter(exceptionResponse -> "USER_CREATE_SUCCESS".equals(exceptionResponse.getExceptonName()))
          .map(ExceptionResponse::getMassage)
          .findFirst()
          .orElse("Default success message if not found");

      response.put("message", successMessage);
      response.put("data", signupResponseDto);
      MDC.put("User", signupRequestDto.getCreatedBy());
      MDC.put("path", "/user/signup");
      LOGGER.info("User Controller : signUp method");
      MDC.clear();
      LOGGER.info("End User Controller : signUp method");
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      String failureMessage = ResponseMessagesConstants.messagelist.stream()
          .filter(exceptionResponse -> "NOTICE_CREATE_FAILURE".equals(exceptionResponse.getExceptonName()))
          .map(ExceptionResponse::getMassage)
          .findFirst()
          .orElse("Default failure message if not found");

      response.put("message", failureMessage);
      LOGGER.info("End User Controller : signUp method");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

  }

  // UpdateUser
  @PutMapping("/update")
  public ResponseEntity<Map<String, Object>> updateUser(@RequestBody User user)
      throws IOException {
    if (user.getStatus().startsWith("disable")) {

      Map<String, Object> response = new HashMap<>();
      String successMessage = ResponseMessagesConstants.messagelist.stream()
          .filter(exceptionResponse -> "USER_DELETE_SUCCESS".equals(exceptionResponse.getExceptonName()))
          .map(ExceptionResponse::getMassage)
          .findFirst()
          .orElse("Default success message if not found");

      response.put("message", successMessage);
      response.put("user", userServiceImpl.UpdateUser(user));

      return ResponseEntity.ok(response);
    } else {

      Map<String, Object> response = new HashMap<>();
      response.put("message", ResponseMessagesConstants.messagelist.stream()
          .filter(exceptionResponse -> "USER_UPDATED_SUCCESS".equals(exceptionResponse.getExceptonName()))
          .map(ExceptionResponse::getMassage)
          .findFirst()
          .orElse("Default message if not found"));
      response.put("user", userServiceImpl.UpdateUser(user));
      return ResponseEntity.ok(response);
    }

  }

  // Find All User
  @GetMapping("/FindAllUser")

  public ResponseEntity<Map<String, Object>> findAllUser(
      @RequestParam(required = false, defaultValue = "userName,asc") String sort,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Map<String, Object> response = new HashMap<>();
    Pageable pageable = PageRequest.of(page, size, parseSortString(sort));

    Page<User> userDetails = userServiceImpl.FindAllUser(pageable);

    response.put("count", userDetails.getTotalElements());
    response.put("data", userDetails.getContent());
    if (userDetails.isEmpty()) {
      // Return a JSON response with a message for data not found
      String emptyMessage = ResponseMessagesConstants.messagelist.stream()
          .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
          .map(ExceptionResponse::getMassage)
          .findFirst()
          .orElse("Default failure message if not found");

      response.put("message", emptyMessage);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    // Return the list of notices if data is found
    return ResponseEntity.ok(response);
  }

  @GetMapping("/getByEmail/{email}")
  public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
    User user = userServiceImpl.getUserByEmail(email);

    if (user == null) {
      return new ResponseEntity<>(ResponseMessagesConstants.messagelist.stream()
          .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
          .map(ExceptionResponse::getMassage)
          .findFirst()
          .orElse("Default message if not found"), HttpStatus.OK);
    }
    return ResponseEntity.ok(user);
  }

  private Sort parseSortString(String sort) {
    String[] sortParams = sort.split(",");
    if (sortParams.length == 2) {
      Sort.Direction direction = sortParams[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC
          : Sort.Direction.ASC;
      return Sort.by(new Sort.Order(direction, sortParams[0]));
    } else {
      return Sort.by(Sort.Order.asc("userName")); // Default sorting by noticeCreatedDate in ascending
                                                  // order
    }
  }

}
