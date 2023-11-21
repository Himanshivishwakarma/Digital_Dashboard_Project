package digital_board.digital_board.Servies;

import java.util.List;

import org.springframework.data.domain.Sort;

import digital_board.digital_board.Entity.Event;

public interface EventService {

    public Event createEventByUser(Event event);

    public Event getEventByEventId(String EventId);

    public List<Event> getEventByUserId(String UserId);
    
    public List<Event> getAllEvent();

    public List<Event> getAllEventSorted(Sort sort);

    public Event EventUpdate(String EventId);



}
