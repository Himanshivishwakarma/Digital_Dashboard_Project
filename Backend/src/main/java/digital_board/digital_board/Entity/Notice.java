package digital_board.digital_board.Entity;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date noticeCreatedDate;

    private String createdBy;
    private String status;

    @PrePersist
    protected void onCreate() {
        this.noticeCreatedDate = new Date();
    }

}
