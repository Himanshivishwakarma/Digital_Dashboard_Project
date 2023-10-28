package digital_board.digital_board.Entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
   private String eventCreatedDate;
   private String createdBy;
}
