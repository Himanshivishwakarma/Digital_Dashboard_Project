package digital_board.digital_board.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import digital_board.digital_board.Dto.AuthResponse;
import digital_board.digital_board.Dto.SignupRequestDto;
import digital_board.digital_board.Dto.SignupResponseDto;
import digital_board.digital_board.Entity.User;
import digital_board.digital_board.ServiceImpl.EmailServiceImpl;
import digital_board.digital_board.ServiceImpl.UserServiceImpl;
import digital_board.digital_board.Servies.Auth0Service;
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

    // emailServices.sendSimpleMessage("sahilkhanskkhan4@gmail.com", "email test",
    // "Sahil");

    MDC.clear();
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
  public ResponseEntity<?> signUp(@RequestBody SignupRequestDto signupRequestDto) {
    System.out.println("signUp controller");
    SignupResponseDto signupResponseDto = auth0Service.signUp(signupRequestDto);
    return ResponseEntity.ok(signupResponseDto);
  }

  // UpdateUser
  @PutMapping("/update")
  public ResponseEntity<User> updateUser(@RequestBody User user) {
    return ResponseEntity.ok(userServiceImpl.UpdateUser(user));
  }

  // Find All User
  @GetMapping("/FindAllUser")

  public ResponseEntity<List<User>> findAllUser() {
    List<User> userDetails = userServiceImpl.FindAllUser();
    return ResponseEntity.ok(userDetails);
  }

  @GetMapping("/getByEmail/{email}")
  public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
    return ResponseEntity.ok(userServiceImpl.getUserByEmail(email));
  }

}
