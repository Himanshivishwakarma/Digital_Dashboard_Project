package digital_board.digital_board.Servies;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import digital_board.digital_board.Dto.CreateUserRequestDto;
import digital_board.digital_board.Dto.SignupRequestDto;
import digital_board.digital_board.Dto.SignupResponseDto;
import digital_board.digital_board.Entity.ExceptionResponse;
import digital_board.digital_board.Entity.User;
import digital_board.digital_board.Exception.ResourceNotFoundException;
import digital_board.digital_board.Repository.UserRepository;
import digital_board.digital_board.ServiceImpl.EmailServiceImpl;
import digital_board.digital_board.constants.ResponseMessagesConstants;
@Service
public class Auth0Service {
    @Autowired
    private EmailServiceImpl emailServices;
    @Autowired
    private UserRepository userRepo;
    private String auth0Domain = "dev-2v6nqrql62h5dwnv.us.auth0.com";
    private String clientId = "UkrepWEIKkn2CIYLmGIiuU2fdwU34WdH";
    private String connection = "Username-Password-Authentication";
    private RestTemplate restTemplate = new RestTemplate();
    public SignupResponseDto signUp(SignupRequestDto signupRequestDto) {
        System.out.println("signUp service");
        String apiUrl = "https://" + auth0Domain + "/dbconnections/signup";
        // SignupRequestDto.getEmail(), SignupRequestDto.getPassword()
        User userAvailable = userRepo.getbyemail(signupRequestDto.getEmail());
        User superAdminAvailable = userRepo.getbyemail(signupRequestDto.getCreatedBy());
        if (superAdminAvailable != null && "SuperAdmin".equals(superAdminAvailable.getRole())) {
            if (userAvailable == null) {
                System.out.println("signUp if block");
                String randomPasswrod = RandomStringUtils.random(12, true, true);
                System.out.println(randomPasswrod);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                CreateUserRequestDto request = new CreateUserRequestDto(clientId, signupRequestDto.getEmail(),
                        randomPasswrod,
                        connection);
                HttpEntity<CreateUserRequestDto> requestEntity = new HttpEntity<>(request, headers);
                // restTemplate.postForLocation(apiUrl, requestEntity);
                ResponseEntity<SignupResponseDto> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity,
                        SignupResponseDto.class);
                SignupResponseDto signupResponseDto = responseEntity.getBody();

                try {
                    if (signupResponseDto != null && signupResponseDto.getEmail() != null) {
                        User user = new User();
                        user.setUserName(signupRequestDto.getUserName());
                        user.setEmail(signupResponseDto.getEmail());
                        user.setRole("Admin");
                        user.setDepartmentName(signupRequestDto.getDepartmentName());
                        user.setCategory(signupRequestDto.getCategory());
                        user.setStatus("enable");
                        user.setAddress(signupRequestDto.getAddress());
                        user.setContact(signupRequestDto.getContact());
                        userRepo.save(user);
                        emailServices.sendSimpleMessageForPassword(signupResponseDto.getEmail(),
                                signupRequestDto.getUserName(),
                                randomPasswrod);
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }

                return signupResponseDto;

            } else {
                throw new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                        .filter(exceptionResponse -> "MESSAGE_REGISTER_ERRROR"
                                .equals(exceptionResponse.getExceptonName()))
                        .map(ExceptionResponse::getMassage)
                        .findFirst()
                        .orElse("Default message if not found"));
            }
        }else{
            throw new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                        .filter(exceptionResponse -> "NOT_SUPERADMIN"
                                .equals(exceptionResponse.getExceptonName()))
                        .map(ExceptionResponse::getMassage)
                        .findFirst()
                        .orElse("Default message if not found"));
        }
    }
}