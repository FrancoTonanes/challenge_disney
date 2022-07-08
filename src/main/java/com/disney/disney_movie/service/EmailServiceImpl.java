package com.disney.disney_movie.service;

import com.disney.disney_movie.service.interfaces.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class EmailServiceImpl implements EmailService {
    @Value("${key-email}")
    private String API_KEY;
    @Override
    public String sendEmail(String email, String user) throws IOException {


        Email from = new Email("francotonanes@hotmail.com");
        String subject = "¡Welcome to the Disney Challenge!";
        Email to = new Email(email);
        Content content = new Content("text/html",
                "<h1 style: color><strong>Congratulation!</strong> We hope you enjoy the web</h1> " +
                        "<h1>"+user+"</h1>" + ".\n <h5>ATTE: FRANCO TOÑANES</h5>");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            return response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }

}
