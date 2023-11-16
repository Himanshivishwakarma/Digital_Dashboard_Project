package digital_board.digital_board.Entity;

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
public class Notice {

    @Id
    private String noticeId = UUID.randomUUID().toString();
    private String noticeTitle;
    private String description;
    private String category;
    private String departmentName;
    private String noticeStartDate;
    private String noticeEndDate;
    private String noticeCreatedDate;
    private String createdBy;
    private Boolean status;

    
}
