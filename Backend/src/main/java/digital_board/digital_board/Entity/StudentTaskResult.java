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
public class StudentTaskResult {
     @Id
    private String id = UUID.randomUUID().toString();
    private String stdId;
    private String taskId;
    private String marks;
    private String feedback;
}
