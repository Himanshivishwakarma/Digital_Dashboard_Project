package digital_board.digital_board.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    private NoticeServiceImpl noticeServiceImpl;

    @PostMapping("/add")
    public  ResponseEntity<Notice> createNoticeByUser(@RequestBody Notice notice) {
        Notice saveNotice = this.noticeServiceImpl.createNoticeByUser(notice);
        return ResponseEntity.ok(saveNotice);
    }


    @GetMapping("/get/byNoticeId/{NoticeId}")
    public ResponseEntity<Notice> getNoticeByNoticeId(@PathVariable String noticeId) {
        Notice notice = noticeServiceImpl.getNoticeByNoticeId(noticeId);
        return ResponseEntity.ok(notice);
    }


    @GetMapping("/getAll/byAdminEmail/{adminEmail}")
    public ResponseEntity<List<Notice>> getNoticeByUserId(@PathVariable String adminEmail) {
        List<Notice> notice = noticeServiceImpl.getNoticeByUserId(adminEmail);
        return  ResponseEntity.ok(notice);
    }

    @GetMapping("/byCategory/{category}")
    public ResponseEntity<List<Notice>> getNoticesByCategory(@PathVariable List<String> category,
            @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, parseSortString(sort));
        return   ResponseEntity.ok(noticeServiceImpl.getNoticesByCategory(category, pageable));
    }


    // http://localhost:8080/notices/byDepartment/iteg?sort=asc
    @GetMapping("/byDepartment/{departmentName}")
    public ResponseEntity<List<Notice>> getNoticesByDepartment(@PathVariable List<String> departmentName,
            @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(noticeServiceImpl.getNoticesByDepartment(departmentName, pageable));
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

    
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalNoticeCount() {
        return ResponseEntity.ok(noticeServiceImpl.getTotalNoticeCount());
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


    // serching filter
    @GetMapping("/getAll/byfilters")
    public List<Notice> searchNotices(@RequestParam(required = false)List<String> department,
    @RequestParam(required = false) List<String> categories,
    @RequestParam(required = false) List<String> createdByList,
    @RequestParam(required = false) String status)
    {
        // Pageable pageable = PageRequest.of(page, size, parseSortString(sort));

        return noticeServiceImpl.searchNotices(department,categories,createdByList,status);
    }
    
    @GetMapping("/search/{query}")
    public ResponseEntity<Object> searchNotices(@PathVariable String query) {
        List<Notice> result = noticeServiceImpl.searchNotices(query);

        if (result.isEmpty()) {
            // Return a JSON response with a message for data not found
            return new ResponseEntity<>(ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default message if not found"), HttpStatus.NOT_FOUND);
        }

        // Return the list of notices if data is found
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
