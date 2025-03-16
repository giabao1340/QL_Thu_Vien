/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FactoryMethod;

/**
 *
 * @author luong
 */
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    private static final String HOST = "smtp.gmail.com";
    private static final String USERNAME = "ascf5649@gmail.com";
    private static final String PASSWORD = "qyed tqed tlbe frad";
    private static final String FROM = "thuviensach123@gmail.com";

    public static void send(String to, String subject, String messageContent) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageContent);

            Transport.send(message);
            System.out.println("✅ Email đã gửi đến: " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("❌ Gửi email thất bại!");
        }
    }
}
