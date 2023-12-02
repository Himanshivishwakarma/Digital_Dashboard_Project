package digital_board.digital_board.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Map<String, Object>> createNoticeByUser(@RequestBody Notice notice) {
        Map<String, Object> response = new HashMap<>();
        try {
            Notice savedNotice = this.noticeServiceImpl.createNoticeByUser(notice);

            String successMessage = ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "NOTICE_CREATE_SUCCESS".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default success message if not found");

            response.put("message", successMessage);
            response.put("data", savedNotice);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            String failureMessage = ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "NOTICE_CREATE_FAILURE".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default failure message if not found");

            response.put("message", failureMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateNoticeByNoticeId(@RequestBody Notice notice) {
        Map<String, Object> response = new HashMap<>();
        try {
            Notice updatedNotice = this.noticeServiceImpl.updateNotice(notice);

            String successMessage = ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "NOTICE_UPDATED_SUCCESS".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default success message if not found");

            response.put("message", successMessage);
            response.put("data", updatedNotice);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            String failureMessage = ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "NOTICE_UPDATE_FAILURE".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default failure message if not found");

            response.put("message", failureMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/get/byNoticeId/{noticeId}")
    public ResponseEntity<Notice> getNoticeByNoticeId(@PathVariable String noticeId) {
        Notice notice = noticeServiceImpl.getNoticeByNoticeId(noticeId);
        return ResponseEntity.ok(notice);
    }

    @GetMapping("/getAll/byAdminEmail/{adminEmail}")
    public ResponseEntity<Map<String, Object>> getNoticeByUserEmail(@PathVariable String adminEmail) {
        Map<String, Object> response = new HashMap<>();
        List<Notice> notice = noticeServiceImpl.getNoticeByUserEmail(adminEmail);
        response.put("count", notice.size());
        response.put("data", notice);

        if (notice.isEmpty()) {
            String emptyMessage = ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default failure message if not found");

            response.put("message", emptyMessage);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/byCategory/{category}")
    public ResponseEntity<?> getNoticesByCategory(@PathVariable List<String> category,
            @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size, parseSortString(sort));
        List<Notice> notice = noticeServiceImpl.getNoticesByCategory(category, pageable);
        response.put("count", notice.size());
        response.put("data", notice);

        if (notice.isEmpty()) {
            // Return a JSON response with a message for data not found
            String emptyMessage = ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default failure message if not found");

            response.put("message", emptyMessage);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        // Return the list of notices if data is found
        return ResponseEntity.ok(response);
    }

    // http://localhost:8080/notices/byDepartment/iteg?sort=asc
    @GetMapping("/byDepartment/{departmentName}")
    public ResponseEntity<Map<String, Object>> getNoticesByDepartment(@PathVariable List<String> departmentName,
            @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size);
        List<Notice> notice = noticeServiceImpl.getNoticesByDepartment(departmentName, pageable);
        response.put("count", notice.size());
        response.put("data", notice);
        if (notice.isEmpty()) {
            // Return a JSON response with a message for data not found
            String emptyMessage = ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default failure message if not found");

            response.put("message", emptyMessage);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        // Return the list of notices if data is found
        return ResponseEntity.ok(response);
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
    // @PostMapping("/getAll/byfilter")
    // public ResponseEntity<?> getAllNoticeByDepartmentAndCategory(@RequestBody
    // NoticeFilterDto noticeFilterDto,
    // @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc")
    // String sort,
    // @RequestParam(defaultValue = "0") int page,
    // @RequestParam(defaultValue = "10") int size) {

    // Pageable pageable = PageRequest.of(page, size, parseSortString(sort));
    // List<Notice> notice = noticeServiceImpl.filterNotices(noticeFilterDto,
    // pageable);

    // if (notice.isEmpty()) {
    // throw new
    // ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
    // .filter(exceptionResponse ->
    // "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
    // .map(ExceptionResponse::getMassage)
    // .findFirst()
    // .orElse("Default message if not found"));
    // }
    // return ResponseEntity.ok(notice);
    // }

    @GetMapping("/count")
    public ResponseEntity<Long> countNoticesByCriteria(@RequestParam(required = false) String category,
            @RequestParam(required = false) String departmentName) {
        if (category != null) {
            // Count notices by category
            return ResponseEntity.ok(noticeServiceImpl.countByCategory(category));
        } else if (departmentName != null) {
            // Count notices by departmentName
            return ResponseEntity.ok(noticeServiceImpl.countByDepartmentName(departmentName));
        } else {
            // No criteria provided, return total count
            return ResponseEntity.ok(noticeServiceImpl.getTotalNoticeCount());
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

    // serching filter
    @GetMapping("/getAll/byfilters")
    public ResponseEntity<Map<String, Object>> searchNotices(@RequestParam(required = false)List<String> department,
    @RequestParam(required = false) List<String> categories,
    @RequestParam(required = false) List<String> admins,
    @RequestParam(required = false) String status,
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "5") int size)
    {
       Map<String,Object> response=new HashMap<>();
       List<Notice> searchNotices = noticeServiceImpl.filterNotices(department,categories,admins,status,page,size);
          response.put("data", searchNotices);
          response.put("count",searchNotices.size());
        if(searchNotices.isEmpty()) 
        {
            String emptyMessage = ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default failure message if not found");
            response.put("message", emptyMessage);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<?> searchNotices(@PathVariable String query,
            @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size, parseSortString(sort));
        List<Notice> notice = noticeServiceImpl.searchNotices(query, pageable);
        response.put("count", notice.size());
        response.put("data", notice);

        if (notice.isEmpty()) {
            // Return a JSON response with a message for data not found
            String emptyMessage = ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default failure message if not found");

            response.put("message", emptyMessage);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        // Return the list of notices if data is found
        return ResponseEntity.ok(response);

    }
}
