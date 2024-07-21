package com.example.demo.emails;

import com.example.demo.dto.response.GenericResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }




    @Async
    public void sendConfirmationEmail(User user, String confirmationLink) {

        String subject = "Email Verification";

        String senderName = "LogiTracker";

        String mailContent = "Hello, " +" \n"+ user.getFullName() + "\n" +
                "Thank you for choosing LogiTrack! We're excited to have you on board. To complete your registration, please use the one-time password (OTP) provided below:" +
                "\n" +
                "OTP: " + confirmationLink + " \nIf you didn't request this OTP, please disregard this message. Your security is important to us at:" +
                "\nAdminOne@gmail.com" + "\nThank you for trusting us" +
                "\nLogiTrack Team.";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setFrom("logitrackapplication@gmail.com" + senderName);
        mailMessage.setText(mailContent);
        sendEmail(mailMessage);
    }

}