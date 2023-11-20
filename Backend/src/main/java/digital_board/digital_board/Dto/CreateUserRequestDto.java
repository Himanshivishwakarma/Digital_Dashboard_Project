package digital_board.digital_board.Dto;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {
  
        private String client_id;
        private String email;
        private String password;
        private String connection;
    
        public CreateUserRequestDto(String client_id, String email, String password, String connection) {
            this.client_id = client_id;
            this.email = email;
            this.password = password;
            this.connection = connection;
        }
    
      
}
