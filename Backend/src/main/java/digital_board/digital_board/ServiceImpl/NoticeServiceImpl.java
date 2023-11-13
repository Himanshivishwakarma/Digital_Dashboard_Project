package digital_board.digital_board.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import digital_board.digital_board.Entity.Notice;
import digital_board.digital_board.Repository.NoticeRepository;
import digital_board.digital_board.Servies.NoticeService;


@Service
public class NoticeServiceImpl implements NoticeService{

    @Autowired
    NoticeRepository noticeRepository;

    @Override
    public Notice createNoticeByUser(Notice notice) {
        Notice saveNotice = this.noticeRepository.save(notice);
        return saveNotice;
    }

    @Override
    public Notice getNoticeByNoticeId(String NoticeId) {
        System.out.println(NoticeId);
       Notice notice = this.noticeRepository.findById(NoticeId).orElseThrow();
       return notice;
    }

    @Override
    public List<Notice> getNoticeByUserId(String UserId) {
       List<Notice> notice = this.noticeRepository.getAllNoticeByUserId(UserId);
       return notice;
    // return null;
    }

    @Override
    public List<Notice> getAllNotice() {
       List<Notice> notice = this.noticeRepository.findAll();
       return notice;
    }
    
}
