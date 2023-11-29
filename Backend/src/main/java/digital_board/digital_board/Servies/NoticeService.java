package digital_board.digital_board.Servies;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import digital_board.digital_board.Dto.NoticeFilterDto;
import digital_board.digital_board.Entity.Notice;

public interface NoticeService {

    public Notice createNoticeByUser(Notice notice);

    public Notice getNoticeByNoticeId(String noticeId);

    public List<Notice> getNoticeByUserId(String userId);

    public List<Notice> getAllNotice();

    public List<Notice> getNoticesByCategory(List<String> category, Pageable pageable);

    public List<Notice> getNoticesByDepartment(List<String> departmentName, Pageable pageable);

    public List<Notice> getAllNoticesSorted(Pageable pageable);

    public List<Notice> filterNotices(NoticeFilterDto noticeFilterDto, Pageable pageable);

    // Get ALl important notice
    public List<Notice> getAllImportantNotice(int limit);
    
    // update notice
    public Notice updateNotice(Notice notice);
   
    // searching filter

}
