package digital_board.digital_board.Servies;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import digital_board.digital_board.Dto.NoticeFilterDto;
import digital_board.digital_board.Entity.Notice;

public interface NoticeService {

    public Notice createNoticeByUser(Notice notice);

    public Notice getNoticeByNoticeId(String noticeId);

    public Page<Notice> getNoticeByUserEmail(String email,Pageable pageable);

    public List<Notice> getAllNotice();

    public Page<Notice> getNoticesByCategory(List<String> category, Pageable pageable);

    public Page<Notice> getNoticesByDepartment(List<String> departmentName, Pageable pageable);

    public Page<Notice> getAllNoticesSorted(Pageable pageable);

    public List<Notice> filterNotices(NoticeFilterDto noticeFilterDto, Pageable pageable);

    // Get ALl important notice
    public List<Notice> getAllImportantNotice(int limit);

    public Long getTotalNoticeCount();

    public Page<Notice> searchNotices(String query, Pageable pageable);

    // update notice
    public Notice updateNotice(Notice notice);

    // searching filter
    
    // public List<Notice> filterNotices(List<String> department, List<String> categories, List<String> admins,
    // String status, int page, int size);

    public Long countByCategory(String category);

    public Long countByDepartmentName(String departmentName);
}
