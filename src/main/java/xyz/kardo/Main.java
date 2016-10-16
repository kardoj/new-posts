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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main implements Runnable {
	private String url = "http://kinnisvaraportaal-kv-ee.postimees.ee/" + 
						 "?act=search.simple&company_id=&page=1&orderby=pawl&page_size=50" + 
						 "&deal_type=2&dt_select=2&county=1&parish=421&price_min=100&price_max=350" + 
						 "&price_type=1&rooms_min=&rooms_max=&nr_of_people=&area_min=25&area_max=&floor_min=&floor_max=&keyword=kalamaja";
	private Document root;
	private String postContainerSelector = ".object-type-apartment";
	private String dataFile = "kv.txt";
	private long interval = 60 * 1000 * 5;
	private boolean running = true;
	private boolean sendMails;
	private Mailer mailer;
	
	public Main(boolean sendMails) {
		this.sendMails = sendMails;
		mailer = new Mailer("kardoj@gmail.com");
	}
	
	@Override
	public void run(){
		while (running) {
			root = getUrl(url);
			Elements posts = getPosts(root, postContainerSelector);
			ArrayList<String> allLinks = getLinks(posts);
			ArrayList<String> newLinks = getNewLinks(allLinks);
			writeLinksToFile(newLinks);
			if (sendMails) mailer.sendEmails(newLinks);
			
			try { Thread.sleep(interval); } 
			catch (InterruptedException e) { e.printStackTrace(); }
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
		new Main(true).run();
	}
}
