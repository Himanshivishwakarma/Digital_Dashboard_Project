package digital_board.digital_board.Services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import digital_board.digital_board.Dto.UserDTO;
import digital_board.digital_board.Entity.User;
import digital_board.digital_board.Repository.UserRepository;
import digital_board.digital_board.ServiceImpl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Test
    void testCreateUser() {
        User user = new User("1", "Mangal Banna", "mstomar038@gmail.com@.com", "student", "iteg", "image", null,
                "piplya", "1233", "enable");
        when(userRepository.save(any(User.class))).thenReturn(user);
        User result = userServiceImpl.CreateUser(user);
        assertEquals(user, result);
    }

    @Test
    void testFindAllUser() {
        List<User> userList = Arrays.asList(
                new User("1", "Mangal Banna", "mstomar038@gmail.com@.com", "student", "iteg", "image", null, "piplya",
                        "1233", "enable"),
                new User("1", "Mangal Banna", "mstomar038@gmail.com@.com", "student", "iteg", "image", null, "piplya",
                        "1233", "enable"));
        Page<User> pageList = new PageImpl<>(userList);

        when(userRepository.findAllByRoleAndStatus(anyString(), anyString(), any(Pageable.class))).thenReturn(pageList);
        Page<User> result = userServiceImpl.FindAllUser(PageRequest.of(0, 10));
        assertEquals(pageList, result);
    }

    @Test
    void testGetUserByEmail() {
        User user = new User("1", "Mangal Banna", "mstomar038@gmail.com@.com", "student", "iteg", "image", null,
                "piplya", "1233", "enable");
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);
        User result = userServiceImpl.getUserByEmail("mstomar038@gmail.com@.com");
        assertEquals(optionalUser.get(), result);
    }

    @Test
    void testGetInfoOfAdmins() {
        List<UserDTO> userDTO = Arrays.asList(new UserDTO("1", "mstomar@gmail.com", "Mangal Banna"),
                new UserDTO("2", "mstomar@gmail.com", "Mangal Banna"));
        when(userRepository.findUserNames()).thenReturn(userDTO);
        List<UserDTO> result = userServiceImpl.getInfoOfAdmins();
        assertEquals(userDTO, result);

    }

}
