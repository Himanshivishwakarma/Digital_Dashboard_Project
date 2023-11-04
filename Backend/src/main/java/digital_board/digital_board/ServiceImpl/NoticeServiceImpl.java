package digital_board.digital_board.ServiceImpl;

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
    
}
