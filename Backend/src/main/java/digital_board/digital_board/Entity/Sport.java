package digital_board.digital_board.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sport {
    @Id
    private String sportId = UUID.randomUUID().toString();
    private String sportName;
    private String sportDescription;
    private String sportStartDate;
    private String sportEndDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sportCreatedDate;

    private String createdBy;


    @PrePersist
    protected void onCreate() {
        this.sportCreatedDate = new Date();
    }

   

}
