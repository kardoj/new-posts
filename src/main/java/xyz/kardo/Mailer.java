/*
 * Kardo Jõeleht, 2016
 */
package xyz.kardo;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {
	private String email;
	
	public Mailer(String email) {
		this.email = email;
	}	
	
	public void sendEmails(ArrayList<String> newLinks) {
		for (String link: newLinks) {
			sendLinkToEmail(link);
		}
	}
	
	private void sendLinkToEmail(String link) {
		// Recipient's email ID needs to be mentioned.
		String to = email;
		
		// Sender's email ID needs to be mentioned
		String from = "KV-mailer@kardo.xyz";
		
		// Assuming you are sending email from localhost
		String host = "localhost";
		
		// Get system properties
		Properties properties = System.getProperties();
		
		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		
		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
	        // Create a default MimeMessage object.
	        MimeMessage message = new MimeMessage(session);
	
	        // Set From: header field of the header.
	        message.setFrom(new InternetAddress(from));
	
	        // Set To: header field of the header.
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	
	        // Set Subject: header field
	        message.setSubject("Uus üüripind Kalamajas");
	
	        // Now set the actual message
	        message.setText(link);
	
	        // Send message
	        Transport.send(message);
		}catch (MessagingException mex) {
			mex.printStackTrace();
		}		
	}
}
