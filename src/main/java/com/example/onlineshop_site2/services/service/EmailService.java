package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.exceptions.MailNotFoundException;
import com.example.onlineshop_site2.exceptions.WrongCodeException;
import com.example.onlineshop_site2.models.entities.Mail;
import com.example.onlineshop_site2.repositories.MailRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final MailRepo mailRepo;


    public boolean createCodeDB(String email){
        Mail mail = mailRepo.findByEmail(email).orElse(new Mail());
        mail.setEmail(email);
        mail.setCode(generateCode());
        mail.setTimeToEnd(LocalDateTime.now().plusMinutes(5));
        mailRepo.save(mail);
        return true;
    }

    public boolean checkCode(String email, String code){
        Mail mail = mailRepo.findByEmailAndCode(email, code)
                .orElseThrow(WrongCodeException::new);
        mailRepo.delete(mail);
        return true;
    }

    public boolean sendRegCode(String email){
        Mail mail = mailRepo.findByEmail(email).orElse(null);
        if(mail == null){
            return false;
        }else {
            sendRegCodeEmail(email, mail.getCode());
            return true;
        }
    }

    public boolean sendPasCode(String email){
        Mail mail = mailRepo.findByEmail(email).orElse(null);
        if(mail == null){
            return false;
        }else {
            sendPasCodeEmail(email, mail.getCode());
            return true;
        }
    }

    public boolean changePassword(String email){
        createCodeDB(email);
        return true;
    }

    private void sendPasCodeEmail(String to, String code){
        String htmlMsg = "<html>"
                + "<head>"
                + "<style>"
                + "body {font-family: 'Arial', sans-serif;}"
                + "h2 {color: #3498db;}"
                + "p {color: #555;}"
                + "strong {color: #3498db;}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div style='padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
                + "<h2>Здравствуйте!</h2>"
                + "<p>Для смены пароля, пожалуйста, введите следующий код подтверждения:</p>"
                + "<p style='font-size: 24px;'><strong>" + code + "</strong></p>"
                + "</div>"
                + "</body>"
                + "</html>";
        sendEmail(to, "Регистрация на Aetherium", htmlMsg);
    }

    private void sendRegCodeEmail(String to, String code){
        String htmlMsg = "<html>"
                + "<head>"
                + "<style>"
                + "body {font-family: 'Arial', sans-serif;}"
                + "h2 {color: #3498db;}"
                + "p {color: #555;}"
                + "strong {color: #3498db;}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div style='padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
                + "<h2>Здравствуйте!</h2>"
                + "<p>Благодарим вас за регистрацию.</p>"
                + "<p>Для завершения регистрации, пожалуйста, введите следующий код подтверждения:</p>"
                + "<p style='font-size: 24px;'><strong>" + code + "</strong></p>"
                + "</div>"
                + "</body>"
                + "</html>";
        sendEmail(to, "Регистрация на Aetherium", htmlMsg);
    }

    private void sendEmail(String to, String subject, String text) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            // Задаем адрес отправителя, получателя и тему письма
            helper.setFrom("aetherium70@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);

            // Устанавливаем HTML-контент в теле письма
            helper.setText(text, true);

            // Отправляем письмо
            javaMailSender.send(mimeMessage);

            System.out.println("Письмо с кодом подтверждения отправлено успешно.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String generateCode() {
        // Генерируем случайное шестизначное число
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
