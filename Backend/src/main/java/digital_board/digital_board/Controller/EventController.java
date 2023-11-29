package digital_board.digital_board.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import digital_board.digital_board.Entity.Event;
import digital_board.digital_board.Entity.ExceptionResponse;
import digital_board.digital_board.Entity.Notice;
import digital_board.digital_board.ServiceImpl.EventServiceImpl;
import digital_board.digital_board.constants.ResponseMessagesConstants;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<?> createEventByUser(@RequestBody Event event) {
        Map response = new HashMap<>();
        response.put("Message", ResponseMessagesConstants.messagelist.stream()
                .filter(exceptionResponse -> "EVENT_CREATE_SUCCESS".equals(exceptionResponse.getExceptonName()))
                .map(ExceptionResponse::getMassage)
                .findFirst()
                .orElse("Default message if not found"));
        response.put("Event", this.eventServiceImpl.createEventByUser(event));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/byEventId/{EventId}")
    public ResponseEntity<Event> getEventByEventId(@PathVariable String EventId) {

        return ResponseEntity.ok(eventServiceImpl.getEventByEventId(EventId));
    }

    @GetMapping("/getAll/byUserName/{UserName}")
    public ResponseEntity<List<Event>> getEventByUserId(@PathVariable String UserName) {
        List<Event> event = eventServiceImpl.getEventByUserId(UserName);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Event>> getAllEvent() {

        return ResponseEntity.ok(eventServiceImpl.getAllEvent());
    }

    // sorted
    @GetMapping("/getAll/sortedby")
    public ResponseEntity<List<Event>> getAllEventSorted(
            @RequestParam(required = false, defaultValue = "eventCreatedDate,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, parseSortString(sort));

        return ResponseEntity.ok(eventServiceImpl.getAllEventSorted(pageable));
    }

    @PutMapping("/update")
    public ResponseEntity<?> EventUpdate(@RequestBody Event event) {

        Map response = new HashMap<>();
        response.put("Message", ResponseMessagesConstants.messagelist.stream()
                .filter(exceptionResponse -> "EVENT_UPDATED_SUCCESS".equals(exceptionResponse.getExceptonName()))
                .map(ExceptionResponse::getMassage)
                .findFirst()
                .orElse("Default message if not found"));
        response.put("Event", eventServiceImpl.EventUpdate(event));

        return ResponseEntity.ok(response);
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
