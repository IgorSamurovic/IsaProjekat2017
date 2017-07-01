package utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Mailer {

	private static String USER_NAME = "isamrsprojekatloznica";
    private static String PASSWORD = "qweqweqweqweqweqwe";

    public void sendFromGMail(String to, String subject, String body) {
    	sendFromGMail(new String[] {to}, subject, body);
    }
    
    public void sendFromGMail(String[] to, String subject, String body) {
        String from = USER_NAME;
        String pass = PASSWORD;
		Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        
        try {
            message.setFrom(new InternetAddress(from));
            
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setText(body, "utf-8", "html");
            message.setSubject(subject);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            
        
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
