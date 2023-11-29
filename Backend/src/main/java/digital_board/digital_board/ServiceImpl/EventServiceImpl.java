package digital_board.digital_board.ServiceImpl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import digital_board.digital_board.Entity.Event;
import digital_board.digital_board.Entity.ExceptionResponse;
import digital_board.digital_board.Entity.Notice;
import digital_board.digital_board.Entity.User;
import digital_board.digital_board.Exception.ResourceNotFoundException;
import digital_board.digital_board.Repository.EventRepository;
import digital_board.digital_board.Servies.EventService;
import digital_board.digital_board.constants.ResponseMessagesConstants;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Override
    public Event createEventByUser(Event event) {
        Event saveEvent = this.eventRepository.save(event);
        return saveEvent;
    }

    @Override
    public Event getEventByEventId(String EventId) {

        return this.eventRepository.findById(EventId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                        .filter(exceptionResponse -> "EVENT_NOT_FOUND".equals(exceptionResponse.getExceptonName()))
                        .map(ExceptionResponse::getMassage)
                        .findFirst()
                        .orElse("Default message if not found")));
    }

    @Override
    public List<Event> getEventByUserId(String UserId) {
       
        List<Event> Events = eventRepository.findEventsByUserId(UserId);

        if (Events.isEmpty()) {
            throw new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default message if not found"));
        } else {

            return Events;
        }

    }

    @Override
    public List<Event> getAllEvent() {

        List<Event> event = eventRepository.findAll();
        if (event.isEmpty()) {
            throw new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default message if not found"));
        } else {

            return event;
        }
    }

    @Override
    public Event EventUpdate(Event event) {

        Event findEvent = eventRepository.findById(event.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                        .filter(exceptionResponse -> "EVENT_NOT_FOUND".equals(exceptionResponse.getExceptonName()))
                        .map(ExceptionResponse::getMassage)
                        .findFirst()
                        .orElse("Default message if not found")));

        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEventSorted(Pageable pageable)
     {
        Page<Event> event = eventRepository.findAll(pageable);
        if (event.isEmpty()) {
            throw new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default message if not found"));
        } else {

            return event.getContent();
        }
    }

    // get all important notice
    @Override
    public List<Event> getAllImportantNotice() 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetAllImportantNotice'");
    }

}
