package digital_board.digital_board.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import digital_board.digital_board.Entity.Notice;
import digital_board.digital_board.ServiceImpl.NoticeServiceImpl;

@RestController
@CrossOrigin("*")
// @CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping("/notices")
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

      @GetMapping("/byCategory/{category}")
    public List<Notice> getNoticesByCategory(@PathVariable String category, @RequestParam(required = false) String sort) {
        return noticeServiceImpl.getNoticesByCategory(category, getSortObject(sort));
    }

    // http://localhost:8080/notices/byDepartment/iteg?sort=asc
    @GetMapping("/byDepartment/{departmentName}")
    public List<Notice> getNoticesByDepartment(@PathVariable String departmentName, @RequestParam(required = false) String sort) {
        return noticeServiceImpl.getNoticesByDepartment(departmentName, getSortObject(sort));
    }


     @GetMapping("/getAll")
    public List<Notice> getAllNotice() {
        List<Notice> notice= noticeServiceImpl.getAllNotice();
        return notice;
    }

    private Sort getSortObject(String sort) {
        if (sort != null && sort.equalsIgnoreCase("desc")) {
            return Sort.by(Sort.Direction.DESC, "noticeCreatedDate"); // Change the field as per your requirement
        } else {
            return Sort.by(Sort.Direction.ASC, "noticeCreatedDate"); // Change the field as per your requirement
        }
    }

}
