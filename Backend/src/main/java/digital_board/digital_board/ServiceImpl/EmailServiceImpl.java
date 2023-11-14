package digital_board.digital_board.ServiceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {

  @Autowired
  private JavaMailSender emailSender;

  // public void sendSimpleMessage(
  //     String to, String subject, String text) {

  //   SimpleMailMessage message = new SimpleMailMessage();
  //   message.setFrom("sahilk.bca2021@ssismg.org");
  //   // message.setFrom("sultans.bca2021@ssism.org");
  //   message.setTo(to);
  //   message.setSubject(subject);
  //   message.setText("<html><body><h1>Hello, this is HTML content!</h1></body></html>");
  //   emailSender.send(message);
  // }

  public void sendSimpleMessage(String to, String subject, String userName) {
    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper;

    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom("sahilk.bca2021@ssismg.org");
      // helper.setFrom("sultans.bca2021@ssism.org");
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText("<html><body><h1>Hello, " + userName + "!</h1></body></html>", true); // true indicates HTML content

      emailSender.send(message);
    } catch (MessagingException e) {
      // Handle the exception appropriately
      e.printStackTrace();
    }
  }

}