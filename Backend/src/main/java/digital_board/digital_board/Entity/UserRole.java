package digital_board.digital_board.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.Entity;

@Entity
@Data
public class UserRole 
{
   @Id
   String Id;
   String RoleName;
}
