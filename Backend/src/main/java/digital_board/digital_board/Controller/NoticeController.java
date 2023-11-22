package digital_board.digital_board.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import digital_board.digital_board.Dto.NoticeFilterDto;
import digital_board.digital_board.Entity.ExceptionResponse;
import digital_board.digital_board.Entity.Notice;
import digital_board.digital_board.Exception.ResourceNotFoundException;
import digital_board.digital_board.ServiceImpl.NoticeServiceImpl;
import digital_board.digital_board.constants.ResponseMessagesConstants;

@RestController
@CrossOrigin("*")
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
        List<Notice> notice = noticeServiceImpl.getNoticeByUserId(UserName);
        return notice;
    }

    @GetMapping("/byCategory/{category}")
    public List<Notice> getNoticesByCategory(@PathVariable List<String> category,
            @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, parseSortString(sort));
        return noticeServiceImpl.getNoticesByCategory(category, pageable);
    }

    // http://localhost:8080/notices/byDepartment/iteg?sort=asc
    @GetMapping("/byDepartment/{departmentName}")
    public List<Notice> getNoticesByDepartment(@PathVariable List<String> departmentName,
            @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return noticeServiceImpl.getNoticesByDepartment(departmentName, pageable);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllNotice(
            @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, parseSortString(sort));
        List<Notice> notice = noticeServiceImpl.getAllNoticesSorted(pageable);
        if (notice.isEmpty()) {
            throw new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default message if not found"));
        }

        return ResponseEntity.ok(notice);
    }
    // getAllNoticesSorted

    @PostMapping("/getAll/byfilter")
    public ResponseEntity<?> getAllNoticeByDepartmentAndCategory(@RequestBody NoticeFilterDto noticeFilterDto,
            @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, parseSortString(sort));
        // Pageable pageable = PageRequest.of(page, size);

        List<Notice> notice = noticeServiceImpl.filterNotices(noticeFilterDto, pageable);

        if (notice.isEmpty()) {
            throw new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default message if not found"));
        }

        return ResponseEntity.ok(notice);

    }

    private Sort getSortObject(String sort) {
        if (sort != null && sort.equalsIgnoreCase("desc")) {
            return Sort.by(Sort.Direction.DESC, "noticeCreatedDate"); // Change the field as per your requirement
        } else {
            return Sort.by(Sort.Direction.ASC, "noticeCreatedDate"); // Change the field as per your requirement
        }
    }

    private Sort parseSortString(String sort) {
        String[] sortParams = sort.split(",");
        if (sortParams.length == 2) {
            Sort.Direction direction = sortParams[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            return Sort.by(new Sort.Order(direction, sortParams[0]));
        } else {
            return Sort.by(Sort.Order.asc("noticeCreatedDate")); // Default sorting by noticeCreatedDate in ascending
                                                                 // order
        }
    }
}
