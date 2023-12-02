package digital_board.digital_board.Entity;

import java.util.Map;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id = UUID.randomUUID().toString();
    private String userName;
    private String email;
    private String role;
    private String departmentName;
    private String category;
    private String  image;

    private String address;
    private String contact;
    private String status;
}
