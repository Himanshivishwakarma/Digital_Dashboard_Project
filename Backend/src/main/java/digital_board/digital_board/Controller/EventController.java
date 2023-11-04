package digital_board.digital_board.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import digital_board.digital_board.Entity.Event;
import digital_board.digital_board.ServiceImpl.EventServiceImpl;

@RestController
    @RequestMapping("/event")
public class EventController {

    @Autowired
    EventServiceImpl eventServiceImpl;


    @PostMapping("/add")
    public Event createEventByUser(@RequestBody Event event){
        Event saveEvnet = this.eventServiceImpl.createEventByUser(event);
        return saveEvnet;
    }
}
