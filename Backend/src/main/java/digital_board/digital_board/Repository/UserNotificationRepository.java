package digital_board.digital_board.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import digital_board.digital_board.Entity.UserNotification;

public interface UserNotificationRepository extends JpaRepository<UserNotification, String> {

    @Query("Select u from UserNotification u Where u.userEmail =:userEmail")
    UserNotification getbyemail(@Param("userEmail") String userEmail);

}
