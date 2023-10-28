package digital_board.digital_board.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import digital_board.digital_board.Entity.Notice;
import digital_board.digital_board.ServiceImpl.NoticeServiceImpl;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeServiceImpl noticeServiceImpl;


    @PostMapping("/add")
    public Notice createNoticeByUser(Notice notice){
        Notice saveNotice = this.noticeServiceImpl.createNoticeByUser(notice);
        return saveNotice;
    }


}
