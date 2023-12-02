package digital_board.digital_board.Services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// import org.mockito.junit.jupiter.MockitoExtension;
import digital_board.digital_board.Repository.UserRepository;
import digital_board.digital_board.ServiceImpl.UserServiceImpl;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    

    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() 
    {
        this.userServiceImpl = new UserServiceImpl(this.userRepository);

    }

    @Test
    void getAllUser() 
    {
        this.userServiceImpl.FindAllUser();
        verify(userRepository).findAll();
    }

    
}