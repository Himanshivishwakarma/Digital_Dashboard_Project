package digital_board.digital_board.Servies;

import java.util.List;

import org.springframework.data.domain.Sort;

import digital_board.digital_board.Entity.Notice;

public interface NoticeService {

    public Notice createNoticeByUser(Notice notice);

    public Notice getNoticeByNoticeId(String NoticeId);

    public List<Notice> getNoticeByUserId(String UserId);

    public List<Notice> getAllNotice();

    public List<Notice> getNoticesByCategory(String category, Sort sort);   

    public List<Notice> getNoticesByDepartment(String departmentName, Sort sort);

    public List<Notice> getAllNoticesSorted(Sort sort);
    
}
