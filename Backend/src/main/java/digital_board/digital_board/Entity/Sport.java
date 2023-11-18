package digital_board.digital_board.Entity;


import java.util.Date;
import java.util.UUID;
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
@NoArgsConstructor
@AllArgsConstructor
public class Sport{
    @Id
    private String sprotId = UUID.randomUUID().toString();
    private String sportName;
    private String sportDescription;
    private String sportStartDate;
    private String sportEndDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sportCreatedDate;

    private String createdBy;
    private Boolean status;


    @PrePersist
    protected void onCreate() {
        this.sportCreatedDate = new Date();
    }
}
