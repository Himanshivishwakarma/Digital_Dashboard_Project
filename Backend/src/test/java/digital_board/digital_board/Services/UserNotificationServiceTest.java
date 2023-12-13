package digital_board.digital_board.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import digital_board.digital_board.Entity.UserNotification;
import digital_board.digital_board.Repository.UserNotificationRepository;
import digital_board.digital_board.ServiceImpl.UserNotificationServiceImpl;

@SpringBootTest
public class UserNotificationServiceTest {

    @Mock
    UserNotificationRepository userNotificationRepository;

    @InjectMocks
    UserNotificationServiceImpl userNotificationServiceImpl;

    String userid = "testUser";

    @Test
     void testCreateNotificationByUser() {
        // Arrange
        UserNotification userNotification = new UserNotification(userid, "Mangal Singh", "mstomar@example.com", "IT", true);

        Mockito.when(userNotificationRepository.getbyemail(userNotification.getUserEmail())).thenReturn(null);
        Mockito.when(userNotificationRepository.save(Mockito.any(UserNotification.class))).thenReturn(userNotification);

        // Act
        Map<String, Object> response = userNotificationServiceImpl.createNotificationByUser(userNotification);

        // Assert
        assertEquals("Email Notification Added Successfully", response.get("message"));
        assertTrue(response.containsKey("data"));

    }
  
    @Test
    void testGetAllUserNotification(){
        List<UserNotification> expected = Arrays.asList(new UserNotification(userid, "Mangal Singh", "mstomar@example.com", "IT", true),
        new UserNotification(userid, "Mangal Singh", "mstomar@example.com", "IT", true));
        
        when(userNotificationRepository.findAll()).thenReturn(expected);

       List<UserNotification> result = userNotificationServiceImpl.getAllUserNotification();
       assertEquals(expected, result);
    }

    
    

}

