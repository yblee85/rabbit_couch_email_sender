package com.yblee.mqcouch.email;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import com.yblee.mqcouch.model.AppModel;

public class SendEmailAWS {
    // The name of the Configuration Set to use for this message.
    // If you comment out or remove this variable, you will also need to
    // comment out or remove the header below.
    static final String CONFIGSET = "ConfigSet";
    
    // The port you will connect to on the Amazon SES SMTP endpoint. 
    static final int PORT = 587;
	
	public SendEmailAWS() {
	}
	
	public static boolean sendEmailSSL(String from, String to, String title, String content) {
		// Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.ssl.trust", AppModel.AWS_SMTP_HOST);

      // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

    	String strFrom = ((from!=null && !from.isEmpty())?from:AppModel.SENDER_EMAIL);
    	
        try {
        	// Create a message with the specified information. 
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(strFrom));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(title);
            msg.setContent(content,"text/html");
            
            // Create a transport.
            Transport transport = session.getTransport();
            
            boolean isSent = false;
            // Send the message.
            try
            {
                System.out.println("Sending...");
                
                // Connect to Amazon SES using the SMTP username and password you specified above.
                transport.connect(AppModel.AWS_SMTP_HOST, AppModel.AWS_SMTP_USER, AppModel.AWS_SMTP_PASS);
            	
                // Send the email.
                transport.sendMessage(msg, msg.getAllRecipients());
                System.out.println("Email sent!");
                isSent = true;
            }
            catch (Exception ex) {
                System.out.println("The email was not sent.");
                System.out.println("Error message: " + ex.getMessage());
                ex.printStackTrace();
            }
            finally
            {
                // Close and terminate the connection.
                transport.close();
            }
            return isSent;
        } catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
	}
}
