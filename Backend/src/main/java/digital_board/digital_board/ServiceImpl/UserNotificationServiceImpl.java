package digital_board.digital_board.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import digital_board.digital_board.Entity.UserNotification;
import digital_board.digital_board.Repository.UserNotificationRepository;
import digital_board.digital_board.Servies.UserNotificationService;

@Service
public class UserNotificationServiceImpl implements UserNotificationService {

    @Autowired
    UserNotificationRepository userNotificationRepository;

    @Override
    public boolean createNotificationByUser(UserNotification userNotification) {
        boolean t = false;
        try {
            UserNotification user = userNotificationRepository.getbyemail(userNotification.getUserEmail());
            if (user != null) {
                this.userNotificationRepository.save(userNotification);
                t = true;
            }

        } catch (Exception e) {

        }
        return t;
    }

    @Override
    public List<UserNotification> getAllUserNotification() {
        List<UserNotification> userNotification = this.userNotificationRepository.findAll();
        return userNotification;

    }

    @Override
    public List<UserNotification> getUserNotificationByDepartment() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserNotificationByDepartment'");
    }

}
