package digital_board.digital_board.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import digital_board.digital_board.Entity.Notice;
import digital_board.digital_board.ServiceImpl.NoticeServiceImpl;

@RestController
@CrossOrigin("*")
// @CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping("/api/v1/notice")
public class NoticeController {

    @Autowired
    NoticeServiceImpl noticeServiceImpl;

    @PostMapping("/add")
    public Notice createNoticeByUser(@RequestBody Notice notice) {
        Notice saveNotice = this.noticeServiceImpl.createNoticeByUser(notice);
        return saveNotice;
    }

    @GetMapping("/get/byNoticeId/{NoticeId}")
    public Notice getNoticeByNoticeId(@PathVariable String NoticeId) {
        Notice notice = noticeServiceImpl.getNoticeByNoticeId(NoticeId);
        return notice;
    }

     @GetMapping("/getAll/byUserName/{UserName}")
    public List<Notice> getNoticeByUserId(@PathVariable String UserName) {
         List<Notice> notice= noticeServiceImpl.getNoticeByUserId(UserName);
        return notice;
    }

     @GetMapping("/getAll")
    public List<Notice> getAllNotice() {
        List<Notice> notice= noticeServiceImpl.getAllNotice();
        return notice;
    }

}
