package digital_board.digital_board.Servies.ServicesImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import digital_board.digital_board.Entity.User;
import digital_board.digital_board.Repositories.UserRepo;
import digital_board.digital_board.Servies.UserService;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Override
    public User CreateUser(User user) 
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

}
