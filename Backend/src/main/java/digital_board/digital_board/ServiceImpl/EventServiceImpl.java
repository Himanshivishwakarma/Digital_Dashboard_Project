package digital_board.digital_board.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import digital_board.digital_board.Entity.Event;
import digital_board.digital_board.Repository.EventRepository;
import digital_board.digital_board.Servies.EventService;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    EventRepository eventRepository;

    @Override
    public Event createEventByUser(Event event) {
           Event saveEvent = this.eventRepository.save(event);
        return saveEvent;
    }
    
}
