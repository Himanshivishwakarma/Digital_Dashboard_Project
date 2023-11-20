package digital_board.digital_board.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import digital_board.digital_board.Entity.UserNotification;
import digital_board.digital_board.ServiceImpl.UserNotificationServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/notification")
public class UserNotificationController {

    @Autowired
    UserNotificationServiceImpl notificationServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<UserNotification> createNotificationByUser(@RequestBody UserNotification userNotification) {
        System.out.println("createNotificationByUser");
        boolean t = this.notificationServiceImpl.createNotificationByUser(userNotification);
        return ResponseEntity.ok(userNotification);
    }

    @GetMapping("/getAll")
    public List<UserNotification> getAllUserNotification() {
        List<UserNotification> userNotification = this.notificationServiceImpl.getAllUserNotification();
        return userNotification;

    }

}
