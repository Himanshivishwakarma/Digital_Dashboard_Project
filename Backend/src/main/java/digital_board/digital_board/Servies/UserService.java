package digital_board.digital_board.Servies;


import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import digital_board.digital_board.Entity.User;

public interface UserService {
    
    // create user
    User CreateUser(User user);

    // Update User
    User UpdateUser(User user) throws IOException;
    
    // Find All User
    List<User> FindAllUser();

    User getUserByEmail(String email);
}
