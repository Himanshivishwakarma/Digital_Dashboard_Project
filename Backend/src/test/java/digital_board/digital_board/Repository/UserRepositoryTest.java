package digital_board.digital_board.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import digital_board.digital_board.Dto.UserDTO;
import digital_board.digital_board.Entity.User;

@SpringBootTest
public class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;

    @Test
    void testFindByEmail() {

        User user = new User("1", "Mangal Banna", "mstomar038@gmail@.com", "student", "iteg", "image", null,
                "piplya", "1233", "enable");
        Optional<User> excepted = Optional.of(user);
        when(userRepository.findByEmail(anyString())).thenReturn(excepted);

        Optional<User> result = userRepository.findByEmail("mstomar038@gmail@.com");
        assertEquals(excepted, result);

    }

    @Test
    void testGetbyemail() {

        User excepted = new User("1", "Mangal Banna", "mstomar038@gmail@.com", "student", "iteg", "image", null,
                "piplya", "1233", "enable");

        when(userRepository.getbyemail(anyString())).thenReturn(excepted);

        User result = userRepository.getbyemail("mstomar038@gmail@.com");
        assertEquals(excepted, result);

    }

    @Test
    void testFindByStatusIgnoreCase() {

        List<User> excepted = Arrays.asList(
                new User("1", "Mangal Banna", "mstomar038@gmail.com@.com", "student", "iteg", "image", null, "piplya",
                        "1233", "enable"),
                new User("1", "Mangal Banna", "mstomar038@gmail.com@.com", "student", "iteg", "image", null, "piplya",
                        "1233", "enable"));

        when(userRepository.findByStatusIgnoreCase(anyString())).thenReturn(excepted);

        List<User> result = userRepository.findByStatusIgnoreCase("enable");
        assertEquals(excepted, result);

    }

    @Test
    void testFindAllByRoleAndStatus() {

        List<User> userList = Arrays.asList(
                new User("1", "Mangal Banna", "mstomar038@gmail.com@.com", "student", "iteg", "image", null, "piplya",
                        "1233", "enable"),
                new User("1", "Mangal Banna", "mstomar038@gmail.com@.com", "student", "iteg", "image", null, "piplya",
                        "1233", "enable"));
        Page<User> excepted = new PageImpl<>(userList);

        when(userRepository.findAllByRoleAndStatus(anyString(), anyString(), any(Pageable.class))).thenReturn(excepted);

        Page<User> result = userRepository.findAllByRoleAndStatus("student", "enable", PageRequest.of(0, 10));
        assertEquals(excepted, result);

    }

    @Test
    void testFindUserNames() {
        List<UserDTO> expected = Arrays.asList(new UserDTO("1", "mstomar@gmail.com", "Mangal Banna"),
                new UserDTO("2", "mstomar@gmail.com", "Mangal Banna"));
        when(userRepository.findUserNames()).thenReturn(expected);
        List<UserDTO> result = userRepository.findUserNames();
        assertEquals(expected, result);

    }

}