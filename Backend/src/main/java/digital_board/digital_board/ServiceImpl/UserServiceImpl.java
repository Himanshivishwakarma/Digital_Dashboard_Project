package digital_board.digital_board.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import digital_board.digital_board.Entity.User;
import digital_board.digital_board.Repository.UserRepo;
import digital_board.digital_board.Servies.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public User CreateUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User UpdateUser(User user) {
        userRepo.findByEmail(user.getEmail()).orElseThrow();
        return userRepo.save(user);

    }

    @Override
    public List<User> FindALlUser() {
        List<User> users = userRepo.findAll();
        System.out.println("=>" + users);
        if (users.isEmpty()) {
            throw new NullPointerException("Users list is null");
        } else {

            return users;
        }

    }

}
