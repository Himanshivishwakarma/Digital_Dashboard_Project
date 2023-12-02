package digital_board.digital_board.Entity;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Event {
   @Id
   private String eventId = UUID.randomUUID().toString();
   private String eventTitle;
   private String eventDescription;
   private String eventStartDate;
   private String eventEndDate;
 
   private String createdBy;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventCreatedDate;

    private Boolean status;


    @PrePersist
    protected void onCreate() {
        this.eventCreatedDate = new Date();
    }
}
