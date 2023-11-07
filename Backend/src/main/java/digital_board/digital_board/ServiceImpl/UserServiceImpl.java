package digital_board.digital_board.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import digital_board.digital_board.Entity.ExceptionResponse;
import digital_board.digital_board.Entity.User;
import digital_board.digital_board.Exception.ResourceNotFoundException;
import digital_board.digital_board.Repository.ExceptionRepository;
import digital_board.digital_board.Repository.UserRepository;
import digital_board.digital_board.Servies.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ExceptionRepository exceptionRepository;

    @Override
    public User CreateUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User UpdateUser(User user) {
        // ExceptionResponse ex = exceptionRepository.FindbyId("404");
        userRepo.findByEmail(user.getEmail()).orElseThrow(() -> new ResourceNotFoundException(exceptionRepository.FindbyId("404")));
        return userRepo.save(user);
    }

    @Override
    public List<User> FindAllUser() {
        List<User> users = userRepo.findAll();
        if (users.isEmpty()) 
        {
            throw new ResourceNotFoundException("List is Empty....");
        } 
        else 
        {

            return users;
        }

    }

}
