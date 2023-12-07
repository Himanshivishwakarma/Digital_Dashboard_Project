package digital_board.digital_board.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeServiceImpl noticeServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createNoticeByUser(@RequestBody Notice notice) {
        LOGGER.info("Start Notic Controller : createNoticeByUser method");
        Map<String, Object> response = new HashMap<>();
        try {
            Notice savedNotice = this.noticeServiceImpl.createNoticeByUser(notice);
            MDC.put("User", "mashid@gmail.com");
            MDC.put("path", "/public");
            LOGGER.info("createNoticeByUser method : notice created");
            MDC.clear();
            String successMessage = ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "NOTICE_CREATE_SUCCESS".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default success message if not found");

            response.put("message", successMessage);
            response.put("data", savedNotice);

            LOGGER.info("Start Notic Controller : createNoticeByUser method");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            String failureMessage = ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "NOTICE_CREATE_FAILURE".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default failure message if not found");

            response.put("message", failureMessage);
            LOGGER.info("Start Notic Controller : createNoticeByUser method");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateNoticeByNoticeId(@RequestBody Notice notice) {
        Map<String, Object> response = new HashMap<>();
        try {
            Notice updatedNotice = this.noticeServiceImpl.updateNotice(notice);
            if (updatedNotice.getStatus().startsWith("disable")) {
                String successMessage = ResponseMessagesConstants.messagelist.stream()
                        .filter(exceptionResponse -> "NOTICE_DELETE_SUCCESS"
                                .equals(exceptionResponse.getExceptonName()))
                        .map(ExceptionResponse::getMassage)
                        .findFirst()
                        .orElse("Default success message if not found");

                response.put("message", successMessage);
                // response.put("data", updatedNotice);
            } else {

                String successMessage = ResponseMessagesConstants.messagelist.stream()
                        .filter(exceptionResponse -> "NOTICE_UPDATED_SUCCESS"
                                .equals(exceptionResponse.getExceptonName()))
                        .map(ExceptionResponse::getMassage)
                        .findFirst()
                        .orElse("Default success message if not found");

                response.put("message", successMessage);
                response.put("data", updatedNotice);
            }
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
    public ResponseEntity<Map<String, Object>> getNoticeByUserEmail(@PathVariable String adminEmail,
     @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size, parseSortString(sort));
        Page<Notice> notice = noticeServiceImpl.getNoticeByUserEmail(adminEmail,pageable);
       response.put("count", notice.getTotalElements());
        response.put("data", notice.getContent());

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
    public ResponseEntity<Map<String, Object>> getNoticesByCategory(@PathVariable List<String> category,
            @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size, parseSortString(sort));
        Page<Notice> notice = noticeServiceImpl.getNoticesByCategory(category, pageable);
        response.put("count", notice.getTotalElements());
        response.put("data", notice.getContent());

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
        Page<Notice> notice = noticeServiceImpl.getNoticesByDepartment(departmentName, pageable);
        response.put("count", notice.getTotalElements());
        response.put("data", notice.getContent());

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
    public ResponseEntity<Map<String, Object>> getAllNotice(
            @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of(page, size, parseSortString(sort));
        Page<Notice> notice = noticeServiceImpl.getAllNoticesSorted(pageable);
        response.put("count", notice.getTotalElements());
        response.put("data", notice.getContent());

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
    @GetMapping("/getAll/byfilter")
    public ResponseEntity<Map<String, Object>> searchNotices(@RequestParam(required = false) List<String> department,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> admins,
            @RequestParam(required = false) String status,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> searchNotices = noticeServiceImpl.filterNotices(department, categories, admins, status,
                page, size);
        if (searchNotices.containsKey("count") && (int) searchNotices.get("count") == 0) {
            searchNotices.put("message", ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default failure message if not found"));
        }
        return ResponseEntity.ok(searchNotices);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<Map<String, Object>> searchNotices(@PathVariable String query,
            @RequestParam(required = false, defaultValue = "noticeCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size, parseSortString(sort));
        Page<Notice> notice = noticeServiceImpl.searchNotices(query, pageable);
        response.put("count", notice.getTotalElements());
        response.put("data", notice.getContent());

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
}
