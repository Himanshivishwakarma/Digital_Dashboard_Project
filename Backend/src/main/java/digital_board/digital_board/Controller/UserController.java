package digital_board.digital_board.Controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import digital_board.digital_board.Dto.AuthResponse;
import digital_board.digital_board.Entity.EVENT_LOGS;
import digital_board.digital_board.Entity.ExceptionResponse;
import digital_board.digital_board.Entity.User;
import digital_board.digital_board.ServiceImpl.EmailServiceImpl;
// import digital_board.digital_board.Repository.EVENT_LOGSRepository;
import digital_board.digital_board.ServiceImpl.UserServiceImpl;
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
  private EmailServiceImpl emailServices;

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

    MDC.put("User", "mashid@gmail.com");
    MDC.put("path", "/public");
    LOGGER.info("1*************getWelcomeMsg action called..");
    // MDC.remove("User");
    // MDC.remove("path");

  
      //emailServices.sendSimpleMessage("sahilkhanskkhan4@gmail.com", "email test", "Sahil");

    MDC.clear();
    return "working";
  }

  // create user
  @PostMapping("/CreatUser")
  ResponseEntity<?> CreateUser(@RequestBody User user) {

     Map<String,Object> response = new HashMap<>();
        response.put("Massage",ResponseMessagesConstants.messagelist.stream()
				.filter(exceptionResponse -> "USER_CREATE_SUCCESS".equals(exceptionResponse.getExceptonName()))
				.map(ExceptionResponse::getMassage)
				.findFirst()
				.orElse("Default message if not found"));
        response.put("User", userServiceImpl.CreateUser(user));
    return ResponseEntity.ok(response);
  }
  // UpdateUser
  @PutMapping("/UpdateUser")
  ResponseEntity<User> UpdateUser(@RequestBody User user) {
    return ResponseEntity.ok(userServiceImpl.UpdateUser(user));
  }

  // Find All User
  @GetMapping("/FindAllUser")

  ResponseEntity<List<User>> FindAllUser() {
    List<User> userDetails = userServiceImpl.FindAllUser();
    return ResponseEntity.ok(userDetails);
  }
  

}
