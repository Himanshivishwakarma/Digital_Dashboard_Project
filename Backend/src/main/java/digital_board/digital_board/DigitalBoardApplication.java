package digital_board.digital_board;

import java.util.Optional;

import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import digital_board.digital_board.Entity.ExceptionResponse;
import digital_board.digital_board.ServiceImpl.ExceptionResponseServiceImpl;
import digital_board.digital_board.constants.ResponseMessagesConstants;

@SpringBootApplication
public class DigitalBoardApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(DigitalBoardApplication.class, args);
		System.out.println("ok");

	}

	@Autowired
	ExceptionResponseServiceImpl exceptionResponseServiceImpl;

	@Override
	public void run(String... args) throws Exception {
		ResponseMessagesConstants.messagelist = this.exceptionResponseServiceImpl.GetAllMassage();

		String specificMessage = ResponseMessagesConstants.messagelist.stream()
				.filter(exceptionResponse -> "USER_NOT_FOUND".equals(exceptionResponse.getExceptonName()))
				.map(ExceptionResponse::getMassage)
				.findFirst()
				.orElse("Default message if not found");

		System.out.println("Specific Message: " + specificMessage);

	}

}
