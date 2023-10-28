package digital_board.digital_board.Entity;


import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User 
{
    @Id
    private String id=UUID.randomUUID().toString();
    private String user_name;
    private String email;
    private String role;
    private String ssismGroup;
    private String prifilePhoto;

}
