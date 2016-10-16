/*
 * Kardo Jõeleht, 2016
 */
package xyz.kardo;

import java.io.IOException;
import java.util.ArrayList;

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
	private long interval = 60 * 1000 * 5;
	private boolean running = true;
	private boolean sendMails;
	private Mailer mailer;
	private FileIO fileIO;
	
	public Main(boolean sendMails, String dataFolder, String dataFile) {
		this.sendMails = sendMails;
		mailer = new Mailer("kardoj@gmail.com");
		fileIO = new FileIO(dataFolder + "/" + dataFile);
	}
	
	@Override
	public void run(){
		while (running) {
			root = getUrl(url);
			Elements posts = getPosts(root, postContainerSelector);
			ArrayList<String> allLinks = getLinks(posts);
			ArrayList<String> newLinks = fileIO.getNewLinks(allLinks);
			fileIO.writeLinksToFile(newLinks);
			if (sendMails) mailer.sendEmails(newLinks);
			
			try { Thread.sleep(interval); } 
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
	
	// KV.ee adds a random search key which needs to be cut in order to save and compare links
	private String trimSearchKey(String link) {
		return link.replaceFirst("\\?nr=.*", "");
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
		String dataFolder = "data";
		String dataFile = "kv.txt";
		new Main(true, dataFolder, dataFile).run();
	}
}
