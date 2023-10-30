package digital_board.digital_board.Controller;



import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import digital_board.digital_board.Dto.AuthResponse;

@RestController
public class UserController {


    // @GetMapping("/test")
    // public ResponseEntity<AuthResponse> home(@AuthenticationPrincipal OidcUser principal) {
    //     AuthResponse authResponse = new AuthResponse();
    //      authResponse.setName(principal.getEmail());
    //     authResponse.setToken(principal.getIdToken().getTokenValue());
    //     return ResponseEntity.ok(authResponse);
    // }

      @GetMapping("/public")
    public String publicTest() {
        return "working";
    }



 
 
    
}

