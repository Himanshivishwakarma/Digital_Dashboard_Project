package digital_board.digital_board.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import digital_board.digital_board.Entity.UserNotification;

@SpringBootTest
public class UserNotificationRepositoryTest {
    
    @Mock
    UserNotificationRepository userNotificationRepository;
    
    @Test
   void testGetbyemail(){
      UserNotification expected = new UserNotification("1", "Mangal Singh", "mstomar@example.com", "IT", true);

        Mockito.when(userNotificationRepository.getbyemail(anyString())).thenReturn(expected);

      UserNotification result =  userNotificationRepository.getbyemail("mstomar@example.com");
      assertEquals(expected, result);
   }
}
