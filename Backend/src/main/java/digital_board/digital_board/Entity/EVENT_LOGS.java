package digital_board.digital_board.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

// import javax.persistence.Entity;
// import javax.persistence.Id;

@Entity
@Getter
@Setter
public class EVENT_LOGS {
  @Id
  private String ID;
  private String USER;
  private String PATH;
  private String DATE_TIME;
  private String CLASS_INFO;
  private String LEVEL;
  private String MESSAGE;
  private String EXCEPTION_INFO;
}
