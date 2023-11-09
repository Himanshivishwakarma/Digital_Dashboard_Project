package digital_board.digital_board;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ={"digital_board.digital_board.*"})
public class DigitalBoardApplication {


	public static void main(String[] args) {
		SpringApplication.run(DigitalBoardApplication.class,args);
		System.out.println("ok");
	}
}