package digital_board.digital_board.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.util.UUID;

@Entity
@Data
public class ResultAnnouncment {
    @Id
    private String resultAnnouncmentId = UUID.randomUUID().toString();
    private String resultData;
    private String resultStartDate;
    private String resultEndDate;
    private String resultCreatedDate;
    private String createdBy;
}
