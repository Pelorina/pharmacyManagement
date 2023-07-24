package pharmacy.pharmacyrecordsmadeeasy.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleEmail(EmailDetails emailDetails) {
        try {
            // Check if the recipient email is valid


            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setText(emailDetails.getMessage());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setTo(emailDetails.getRecipient());
            javaMailSender.send(simpleMailMessage);
            return "Mail successfully sent";
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }


}
