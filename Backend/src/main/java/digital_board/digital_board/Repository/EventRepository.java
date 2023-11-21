package digital_board.digital_board.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import digital_board.digital_board.Entity.Event;

public interface EventRepository extends JpaRepository<Event, String> {

    @Query("SELECT n FROM Event n WHERE n.createdBy = :userId")
    List<Event> findEventsByUserId(@Param("userId") String userId);

}
