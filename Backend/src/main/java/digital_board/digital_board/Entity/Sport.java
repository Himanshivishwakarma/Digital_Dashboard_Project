package digital_board.digital_board.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sport{
    @Id
    private String sportId = UUID.randomUUID().toString();
    private String sportName;
    private String sportDescription;
    private String sportStartDate;
    private String sportEndDate;
    private String createdBy;
}
