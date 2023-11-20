package digital_board.digital_board.Servies;

import java.util.List;

import org.springframework.data.domain.Sort;

import digital_board.digital_board.Dto.NoticeFilterDto;
import digital_board.digital_board.Entity.Notice;

public interface NoticeService {

    public Notice createNoticeByUser(Notice notice);

    public Notice getNoticeByNoticeId(String noticeId);

    public List<Notice> getNoticeByUserId(String userId);

    public List<Notice> getAllNotice();

    public List<Notice> getNoticesByCategory(String category, Sort sort);   

    public List<Notice> getNoticesByDepartment(String departmentName, Sort sort);

    public List<Notice> getAllNoticesSorted(Sort sort);

    public List<Notice> filterNotices(NoticeFilterDto noticeFilterDto, Sort sort);
    
}
