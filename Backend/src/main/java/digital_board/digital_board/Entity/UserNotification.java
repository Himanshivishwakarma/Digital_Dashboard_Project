package digital_board.digital_board.Entity;


import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class UserNotification {
   @Id
   private String id = UUID.randomUUID().toString();
   private String userName;
   private String userEmail;
   private String departmentName;
   private Boolean status;

}
