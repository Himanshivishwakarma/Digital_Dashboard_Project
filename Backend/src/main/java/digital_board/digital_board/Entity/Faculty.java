package digital_board.digital_board.Entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Entity
public class Faculty {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private String groupId;
    private String email;
    private String gender;
    private String status;
    private String enable;
}
