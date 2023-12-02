package digital_board.digital_board.ServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Autowired
    private Cloudinary cloudinary;

    // for testing purpose argument constructer
    public UserServiceImpl(UserRepository userRepo) {

        this.userRepo = userRepo;
    }

    @Override
    public User CreateUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User UpdateUser(MultipartFile file, User user) throws IOException {
        System.out.println("sultan id" + user.getId());
        userRepo.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                        .filter(exceptionResponse -> "USER_NOT_FOUND".equals(exceptionResponse.getExceptonName()))
                        .map(ExceptionResponse::getMassage)
                        .findFirst()
                        .orElse("Default message if not found")));

        try {
            if (file != null && !file.isEmpty()) {
                Map r = this.cloudinary.uploader().upload(file.getBytes(),ObjectUtils.asMap("folder", "/digital_board"));

                String secureUrl = (String) r.get("secure_url");

                user.setImage(secureUrl);
            }

            return userRepo.save(user);
        } catch (Exception e) {
               throw new ResourceNotFoundException("Image should be unber 10MB");
        }

    }

    @Override
    public List<User> FindAllUser() {
        return userRepo.findAllByRoleAndStatus("Admin", "enable");

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
