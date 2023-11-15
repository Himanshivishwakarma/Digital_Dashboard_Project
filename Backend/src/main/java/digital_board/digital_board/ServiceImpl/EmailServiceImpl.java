package digital_board.digital_board.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
      String to, String subject, String text) {
      
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("sahilk.bca2021@ssismg.org");
        // message.setFrom("sultans.bca2021@ssism.org");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
      }
       
}