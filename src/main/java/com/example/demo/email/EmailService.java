package com.example.demo.email;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService implements EmailPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Value("${sendgrid.api.key}")
    private String sendgridApiKey;

    @Override
    public boolean sendEmail(EmailBody emailBody) {
        LOGGER.info("EmailBody: {}", emailBody.toString());
        return sendEmailTool(emailBody.getContent(), emailBody.getEmail(), emailBody.getSubject());
    }

    public boolean sendEmailTool(String textMessage, String email, String subject) {
        Email from = new Email("eltitomosca3@gmail.com");
        Email to = new Email(email);
        Content content = new Content("text/html", textMessage);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendgridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
            int statusCode = response.getStatusCode();

            if (statusCode == 202) {
                LOGGER.info("Mail enviado! Status: {}", statusCode);
                return true;
            } else {
                LOGGER.error("SendGrid respondió con status {}: {}", statusCode, response.getBody());
                return false;
            }

        } catch (IOException e) {
            LOGGER.error("Hubo un error al enviar el mail: {}", e);
            return false;
        }
    }
}