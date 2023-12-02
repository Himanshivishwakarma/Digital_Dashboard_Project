package digital_board.digital_board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.boot.CommandLineRunner;
import digital_board.digital_board.Entity.ExceptionResponse;
import digital_board.digital_board.ServiceImpl.ExceptionResponseServiceImpl;
import digital_board.digital_board.constants.ResponseMessagesBackup;
import digital_board.digital_board.constants.ResponseMessagesConstants;
@SpringBootApplication
public class DigitalBoardApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(DigitalBoardApplication.class, args);
        System.out.println("ok");
    }
    @Autowired
    ExceptionResponseServiceImpl exceptionResponseServiceImpl;
    @Autowired
    ResponseMessagesBackup responseMessagesBackup;
    @Override
    public void run(String... args) throws Exception {
        ResponseMessagesConstants.messagelist = this.exceptionResponseServiceImpl.GetAllMassage();
        String specificMessage = ResponseMessagesConstants.messagelist.stream()
                .filter(exceptionResponse -> "USER_NOT_FOUND".equals(exceptionResponse.getExceptonName()))
                .map(ExceptionResponse::getMassage)
                .findFirst()
                .orElse("Default message if not found");
        System.out.println("Specific Message: " + specificMessage);
        if (ResponseMessagesConstants.messagelist.isEmpty()) {
            responseMessagesBackup.smsResponseService();
        }
    }
}