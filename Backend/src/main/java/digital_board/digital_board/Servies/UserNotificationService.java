package digital_board.digital_board.Servies;


import java.util.List;

import digital_board.digital_board.Entity.UserNotification;

public interface UserNotificationService {

    public boolean createNotificationByUser(UserNotification userNotification);

    public  List<UserNotification> getAllUserNotification();

    public  List<UserNotification> getUserNotificationByDepartment();
}
