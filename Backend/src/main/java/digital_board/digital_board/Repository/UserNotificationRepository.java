package digital_board.digital_board.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import digital_board.digital_board.Entity.UserNotification;

public interface UserNotificationRepository extends JpaRepository<UserNotification, String>{

    
}
