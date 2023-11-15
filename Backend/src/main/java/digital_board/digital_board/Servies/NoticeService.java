package digital_board.digital_board.Servies;

import java.util.List;

import digital_board.digital_board.Entity.Notice;

public interface NoticeService {

    public Notice createNoticeByUser(Notice notice);

    public Notice getNoticeByNoticeId(String NoticeId);

    public List<Notice> getNoticeByUserId(String UserId);

    public List<Notice> getAllNotice();
    
}
