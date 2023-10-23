package digital_board.digital_board.Entity;

import java.util.Date;
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
public class Tasks {
    @Id
    private String id = UUID.randomUUID().toString();
    private String taskTitle;
    private String taskDes;
    private String taskGroupId;
    private String levelId;
    private Date date;     
}
