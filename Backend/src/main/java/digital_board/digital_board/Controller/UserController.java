package digital_board.digital_board.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import digital_board.digital_board.Entity.User;
import digital_board.digital_board.ServiceImpl.UserServiceImpl;

@RestController
public class UserController {

  @Autowired
  private UserServiceImpl userServiceImpl;

  // @GetMapping("/test")
  // public ResponseEntity<AuthResponse> home(@AuthenticationPrincipal OidcUser
  // principal) {
  // AuthResponse authResponse = new AuthResponse();
  // authResponse.setName(principal.getEmail());
  // authResponse.setToken(principal.getIdToken().getTokenValue());
  // return ResponseEntity.ok(authResponse);
  // }

  @GetMapping("/public")
  public String publicTest() {
    return "working";
  }

  // create user
  @PostMapping("/CreatUser")
  ResponseEntity<User> CreateUser(@RequestBody User user) {
    return ResponseEntity.ok(userServiceImpl.CreateUser(user));
  }

  // UpdateUser
  @PutMapping("/UpdateUser")
  ResponseEntity<User> UpdateUser(@RequestBody User user) {
    return ResponseEntity.ok(userServiceImpl.UpdateUser(user));
  }

  // Find All User
  @GetMapping("/FindAllUser")
  ResponseEntity<List> FindAllUser() {
    List<User> userDetails = userServiceImpl.FindAllUser();
    return ResponseEntity.ok(userDetails);
  }
}
