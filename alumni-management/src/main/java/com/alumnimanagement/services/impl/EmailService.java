package com.alumnimanagement.services.impl;

import com.alumnimanagement.exception.MailNotSentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String password) {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        String subject = "AMS account credentials";
        String body = String.format("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Access AMS account using below credentials</title>
                </head>
                    <body style="font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f9f9f9; color: #333;">
                        <div style="max-width: 600px; margin: 30px auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); overflow: hidden;">
                            <header style="background-color: #007bff; padding: 20px; text-align: center; color: #ffffff;">
                                <h2 style="margin: 0; font-size: 24px;">Welcome to AMS</h2>
                            </header>
                            <main style="padding: 20px;">
                                <p style="font-size: 16px; line-height: 1.6;">
                                    Hello,
                                </p>
                                <p style="font-size: 16px; line-height: 1.6;">
                                    You can access your AMS account using the following credentials:
                                </p>
                                <div style="background-color: #f3f3f3; padding: 15px; border-left: 4px solid #007bff; margin: 20px 0; font-size: 16px;">
                                    <strong>Password:</strong> <span style="font-family: monospace; color: #007bff;">%s</span>
                                </div>
                                <p style="font-size: 16px; line-height: 1.6;">
                                    Please keep this information secure and do not share it with anyone. Click the button below to log in to your account.
                                </p>
                                <div style="text-align: center; margin: 20px 0;">
                                    <a href="http://localhost:5173/login" style="background-color: #007bff; color: #ffffff; text-decoration: none; padding: 10px 20px; font-size: 16px; border-radius: 5px; display: inline-block;">
                                        Login to AMS
                                    </a>
                                </div>
                            </main>
                            <footer style="background-color: #f1f1f1; padding: 15px; text-align: center; font-size: 14px; color: #666;">
                                <p style="margin: 0;">If you have any questions, please contact our support team.</p>
                                <p style="margin: 0;">&copy; 2024 AMS, Inc.</p>
                            </footer>
                        </div>
                    </body>

                </html>
                """, password);
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MailException | MessagingException e) {
            log.info(e.getMessage());
            throw new MailNotSentException("Mail Could not be sent");
        }
    }
}
