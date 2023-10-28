package digital_board.digital_board.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.util.UUID;

@Entity
@Data
public class Sport{
    @Id
    private String sprotId = UUID.randomUUID().toString();
    private String sportName;
    private String sportDescription;
    private String sportStartDate;
    private String sportEndDate;
    private String createdBy;
}
