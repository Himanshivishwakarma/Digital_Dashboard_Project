package digital_board.digital_board.Servies;


import java.util.List;

import digital_board.digital_board.Entity.User;

public interface UserService {
    
    // create user
    User CreateUser(User user);

    // Update User
    User UpdateUser(User user);
    
    // Find All User
    List<User> FindAllUser();
}
