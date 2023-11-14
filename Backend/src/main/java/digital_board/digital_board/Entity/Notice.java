package digital_board.digital_board.Entity;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
// @Table(String stringname = "Notice")
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

    // public Notice(String string, String string2, String string3, String string4, String string5, String string6,
    //         String string7, String string8, String string9, Boolean string10) {
    //     this.noticeId = string;
    //     this.noticeTitle = string2;
    //     this.description = string3;
    //     this.category = string4;
    //     this.departmentName = string5;
    //     this.noticeStartDate = string6;
    //     this.noticeEndDate = string7;
    //     this.noticeCreatedDate = string8;
    //     this.createdBy = string9;
    //     this.status = string10;

    // }
}
