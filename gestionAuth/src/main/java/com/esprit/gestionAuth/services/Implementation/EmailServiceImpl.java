package com.esprit.gestionAuth.services.Implementation;

import com.esprit.gestionAuth.persistence.entity.User;
import com.esprit.gestionAuth.persistence.entity.UserMail;
import com.esprit.gestionAuth.repositories.IUserEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements IUserEmailRepository {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private VerificationTokenService verificationTokenService;

    @Override
    public void sendCodeByMail(UserMail mail) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("aminesnoussi.contact@gmail.com");
            helper.setTo(mail.getTo());
            helper.setSubject("Code Active");

            // Corps HTML de l'e-mail, avec référence à l'image attachée via son Content-ID
                String htmlMsg = "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "    <style>\n" +
                        "        body {\n" +
                        "            font-family: Arial, sans-serif;\n" +
                        "            background-color: #f4f4f4;\n" +
                        "            margin: 0;\n" +
                        "            padding: 0;\n" +
                        "        }\n" +
                        "        .container {\n" +
                        "            max-width: 600px;\n" +
                        "            margin: 0 auto;\n" +
                        "            background-color: #ffffff;\n" +
                        "            padding: 20px;\n" +
                        "            border-radius: 8px;\n" +
                        "            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);\n" +
                        "        }\n" +
                        "        .header {\n" +
                        "            text-align: center;\n" +
                        "            margin-bottom: 20px;\n" +
                        "        }\n" +
                        "        .logo {\n" +
                        "            max-width: 100px;\n" +
                        "            height: auto;\n" +
                        "        }\n" +
                        "        h1 {\n" +
                        "            color: #333333;\n" +
                        "            font-size: 24px;\n" +
                        "            margin-bottom: 20px;\n" +
                        "            text-align: center;\n" +
                        "        }\n" +
                        "        p {\n" +
                        "            color: #666666;\n" +
                        "            font-size: 16px;\n" +
                        "            line-height: 1.5;\n" +
                        "            margin-bottom: 20px;\n" +
                        "            text-align: center;\n" +
                        "        }\n" +
                        "        .code {\n" +
                        "            background-color: #709fdc;\n" +
                        "            display: inline-block;\n" +
                        "            padding: 10px 20px;\n" +
                        "            color: #ffffff;\n" +
                        "            border-radius: 4px;\n" +
                        "            font-size: 18px;\n" +
                        "            font-weight: bold;\n" +
                        "            text-align: center;\n" +
                        "        }\n" +
                        "        .note {\n" +
                        "            margin-top: 20px;\n" +
                        "            text-align: center;\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <div class=\"container\">\n" +
                        "        <div class=\"header\">\n" +
                        "            <img class=\"logo\" src=\"https://i.postimg.cc/76KbDLSc/logobiz.png\" alt=\"logo\">\n" +
                        "            <h1>Welcome to BizMatch!</h1>\n" +
                        "        </div>\n" +
                        "        <p>Thanks for signing up. Please use the following code to complete your registration:</p>\n" +
                        "        <p class=\"code\">YOUR_VERIFICATION_CODE</p>\n" +
                        "        <p>If you didn't make this request, you can ignore this email.</p>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>\n";


            message.setContent(htmlMsg, "text/html");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public void sendVerificationEmail(User user) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(user.getUserEmail());
            user.setVerificationToken(verificationTokenService.generateVerificationToken());
            helper.setSubject("Vérification du compte");

            String htmlMsg = "<div style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 50px;'>" +
                    "<div style='background-color: #ffffff; padding: 50px; border-radius: 8px; max-width: 600px; margin: auto; box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);'>" +
                    "<div style='text-align:center; margin-bottom:20px;'>" +
                    "<img src='https://i.postimg.cc/76KbDLSc/logobiz.png' alt='logo' width='100' height='100' />" +
                    "</div>" +
                    "<h1 style='color: #333333; font-size: 24px; margin-bottom: 20px; text-align:center;'>Bonjour " + user.getUserFirstName() + ",</h1>" +
                    "<p style='color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px; text-align:center;'>Veuillez cliquer sur le bouton ci-dessous pour activer votre compte :</p>" +
                    "<div style='text-align:center; margin-top:20px;'>" +
                    "<a href='http://localhost:8083/auth/activate?token=" + user.getVerificationToken() + "' style='background-color: #709fdc; display: inline-block; padding: 10px 20px; color: #ffffff; border-radius: 4px; font-size: 18px; font-weight: bold; text-decoration: none;'>Activer le compte</a>" +
                    "</div>" +
                    "</div></div>";


            message.setContent(htmlMsg, "text/html");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

}
