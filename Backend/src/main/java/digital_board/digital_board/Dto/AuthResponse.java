package digital_board.digital_board.Dto;

import lombok.Data;

@Data
public class AuthResponse {
    
   private String token;
   private String refreshtoken;
   private String tokenexpired;
}
