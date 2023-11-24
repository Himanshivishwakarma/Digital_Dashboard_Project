package digital_board.digital_board.ServiceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import digital_board.digital_board.Entity.ExceptionResponse;
import digital_board.digital_board.Entity.User;
import digital_board.digital_board.Exception.ResourceNotFoundException;
import digital_board.digital_board.Repository.UserRepository;
import digital_board.digital_board.Servies.UserService;
import digital_board.digital_board.constants.ResponseMessagesConstants;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    // for testing purpose argument constructer
    public UserServiceImpl(UserRepository userRepo) {

        this.userRepo = userRepo;
    }

    @Override
    public User CreateUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User UpdateUser(User user) {
        // ExceptionResponse ex = exceptionRepository.FindbyId("404");
        userRepo.findByEmail(user.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                        .filter(exceptionResponse -> "USER_NOT_FOUND".equals(exceptionResponse.getExceptonName()))
                        .map(ExceptionResponse::getMassage)
                        .findFirst()
                        .orElse("Default message if not found")));

        return userRepo.save(user);
    }

    @Override
    public List<User> FindAllUser() {
        List<User> users = userRepo.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default message if not found"));
        } else {
            return users;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                        .filter(exceptionResponse -> "USER_NOT_FOUND".equals(exceptionResponse.getExceptonName()))
                        .map(ExceptionResponse::getMassage)
                        .findFirst()
                        .orElse("Default message if not found")));

    }

}
