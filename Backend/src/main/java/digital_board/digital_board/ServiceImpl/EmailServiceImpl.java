package digital_board.digital_board.ServiceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;

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
      helper.setSubject("New Notifiaction");
      helper.setText("<html><head><style>"
      + "body { font-family: 'Arial', sans-serif; background-color: #f4f4f4; text-align: center; }"
      + "a {color: white}"
      + ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
      + ".header { background-color: #4B49AC; color: #fff; padding: 10px; display: flex; justify-content: center; align-items: center; }"
      + ".content { background-color: #fff; padding: 20px; }"
      + ".button { display: inline-block; padding: 10px 20px; background-color: #4B49AC; color: #fff; text-decoration: none; border-radius: 3px;}"
      + "</style></head><body>"
      + "<center>"
      + "<div class='container'>"
      + "<div class='header'><center><h1>Digital Board</h1></center></div>"
      + "<div class='content'>"
      + "<p>Hello, " + userName + " !</p>"
      + "<p>We wanted to inform you about a new notice that has been posted. Please click the button below to view the details:</p>"
      + "<a class='button' href='noticeLink'>View Notification</a>"
      + "<p>Thank you for being a valued member of our community.</p>"
      + "<p>Best regards,</p>"
      + "<p>Digital Dashboard</p>"
      + "</div>"
      + "</div>"
      + "</center>"
      + "</body>"
      + "</html>", true); // true indicates HTML content

      emailSender.send(message);
    } catch (MessagingException e) {
      // Handle the exception appropriately
      e.printStackTrace();
    }
  }

   public void sendSimpleMessageForPassword(String to, String name, String Password) {
    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper;

    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom("sahilk.bca2021@ssismg.org");
      // helper.setFrom("sultans.bca2021@ssism.org");
      helper.setTo(to);
      helper.setSubject("Welcome Aboard - Your Login Details");
      helper.setText("<html><head><style>"
      + "body { font-family: 'Arial', sans-serif; background-color: #f4f4f4; text-align: center; }"
      + "a {color: white}"
      + ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
      + ".header { background-color: #4B49AC; color: #fff; padding: 10px; display: flex; justify-content: center; align-items: center; }"
      + ".content { background-color: #fff; padding: 20px; }"
      + "img {padding-left: 12px;}"
      + ".button { display: inline-block; padding: 10px 20px; background-color: #4B49AC; color: #fff; text-decoration: none; border-radius: 3px;}"
      + "</style></head><body>"
      + "<center>"
      + "<div class='container'>"
      + "<div class='header'><h1>Digital Board</h1></div>"
      + "<div class='content'>"
      + "<p>Hello, " + name + "!</p>"
      + "<p>Your login username is: <strong>" + to + "</strong></p>"
      + "<p>Your temporary password is: <strong>" + Password + "</strong></p>"
      + "<p>Thank you for being a valued member of our community.</p>"
      + "<p>Best regards,</p>"
      + "<p>Digital Dashboard</p>"
      + "</div>"
      + "</div>"
      + "</center>"
      + "</body></html>", true); // true indicates HTML content

      emailSender.send(message);
    } catch (MessagingException e) {
      // Handle the exception appropriately
      e.printStackTrace();
    }
  }


}