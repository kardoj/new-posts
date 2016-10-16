/*
 * Kardo Jõeleht, 2016
 */
package xyz.kardo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class KV implements Runnable {
	private String url = "http://kinnisvaraportaal-kv-ee.postimees.ee/" + 
						 "?act=search.simple&company_id=&page=1&orderby=pawl&page_size=50" + 
						 "&deal_type=2&dt_select=2&county=1&parish=421&price_min=100&price_max=350" + 
						 "&price_type=1&rooms_min=&rooms_max=&nr_of_people=&area_min=25&area_max=&floor_min=&floor_max=&keyword=kalamaja";
	private Document root;
	private String postContainerSelector = ".object-type-apartment";
	private String dataFile = "kv.txt";
	private String email = "kardoj@gmail.com";
	private long interval = 60 * 1000 * 5;
	private boolean running = true;
	
	public KV() {}
	
	@Override
	public void run(){
		while (running) {
			root = getUrl(url);
			Elements posts = getPosts(root, postContainerSelector);
			ArrayList<String> allLinks = getLinks(posts);
			ArrayList<String> newLinks = getNewLinks(allLinks);
			writeLinksToFile(newLinks);
			sendEmails(newLinks);
			
			try { Thread.sleep(interval); } 
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
	
	private void sendEmails(ArrayList<String> newLinks) {
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
	
	private void writeLinksToFile(ArrayList<String> newLinks) {
		try(FileWriter fw = new FileWriter(dataFile, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    for (String link: newLinks) {
			    	out.println(link);
			    }
			    out.close();
			} catch (IOException e) {
			    e.printStackTrace();
			}
	}
	
	// KV.ee adds a random search key which needs to be cut in order to save and compare links
	private String trimSearchKey(String link) {
		return link.replaceFirst("\\?nr=.*", "");
	}
	
	private ArrayList<String> getNewLinks(ArrayList<String> allLinks) {
		ArrayList<String> newLinks = new ArrayList<String>();
		
		try {
			List<String> lines = Files.readAllLines(Paths.get(dataFile), StandardCharsets.UTF_8);
			for (String link: allLinks) {
				if (!lines.contains(link)) newLinks.add(link);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newLinks;
	}
	
	private ArrayList<String> getLinks(Elements posts) {
		ArrayList<String> links = new ArrayList<String>();
		String selector = ".object-title-a";
		for (Element post: posts) {
			links.add(trimSearchKey(post.select(selector).attr("href").toString()));
		}
		return links;
	}

	private Document getUrl(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return doc;
	}	
	
	private Elements getPosts(Document doc, String selector) {
		return doc.select(selector);
	}

	public static void main(String[] args) {
		new KV().run();
	}
}
