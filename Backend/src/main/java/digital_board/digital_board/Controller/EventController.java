package digital_board.digital_board.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    EventServiceImpl eventServiceImpl;

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
    public ResponseEntity<Event> getNoticeByNoticeId(@PathVariable String EventId) {

        return ResponseEntity.ok(eventServiceImpl.getEventByEventId(EventId));
    }

     @GetMapping("/getAll/byUserName/{UserName}")
     public ResponseEntity<List<Event>>getEventByUserId(@PathVariable String UserName) 
     {
        List<Event> event=eventServiceImpl.getEventByUserId(UserName);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Event>>getAllNotice() {
      
        return ResponseEntity.ok(eventServiceImpl.getAllEvent());
    }

    // sorted
    @GetMapping("/getAll/sortedby")
    public ResponseEntity<List<Event>>getAllNoticeSorted(@RequestParam(required = false) String sort)
    {
      
        return ResponseEntity.ok(eventServiceImpl.getAllEventSorted(getSortObject(sort)));
    }

     private Sort getSortObject(String sort) 
     {
        if (sort != null && sort.equalsIgnoreCase("desc")) {
            return Sort.by(Sort.Direction.DESC, "eventCreatedDate"); // Change the field as per your requirement
        } 
        else 
        {
            return Sort.by(Sort.Direction.ASC, "eventCreatedDate"); // Change the field as per your requirement
        }
    }
}
