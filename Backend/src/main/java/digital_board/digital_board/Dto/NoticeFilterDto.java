package digital_board.digital_board.Dto;






import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeFilterDto {

    private List<String>  category;
    private List<String>  departmentName;
}
