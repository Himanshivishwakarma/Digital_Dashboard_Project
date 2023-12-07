package digital_board.digital_board.ServiceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import digital_board.digital_board.Entity.UserNotification;
import digital_board.digital_board.Repository.UserNotificationRepository;
import digital_board.digital_board.Servies.UserNotificationService;
 
@Service
public class UserNotificationServiceImpl implements UserNotificationService {
     private static final Logger LOGGER = LoggerFactory.getLogger(UserNotificationServiceImpl.class);
    @Autowired
    UserNotificationRepository userNotificationRepository;

    @Override
    public boolean createNotificationByUser(UserNotification userNotification) {
        LOGGER.info("Start UserNotificationServiceImpl: createNotificationByUser method");
        boolean t = false;
        try {
            UserNotification user = userNotificationRepository.getbyemail(userNotification.getUserEmail());
            if (user == null) {
                this.userNotificationRepository.save(userNotification);
                t = true;
            }

        } catch (Exception e) {

        }
         LOGGER.info("End UserNotificationServiceImpl: createNotificationByUser method");
        return t;
    }

    @Override
    public List<UserNotification> getAllUserNotification() {
        LOGGER.info("Start UserNotificationServiceImpl: getAllUserNotification method");
        List<UserNotification> userNotification = this.userNotificationRepository.findAll();
         LOGGER.info("End UserNotificationServiceImpl: getAllUserNotification method");
        return userNotification;

    }

    @Override
    public List<UserNotification> getUserNotificationByDepartment() {
        LOGGER.info("Start UserNotificationServiceImpl: getUserNotificationByDepartment method");
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserNotificationByDepartment'");
    }

}
