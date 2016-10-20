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

public class Crawler {
	private String url;
	private String postContainerSelector;
	private String subSelector; // What will be written and sent to the specified e-mail
	
	public Crawler(Config CONFIG) {
		this.url = CONFIG.url;
		this.postContainerSelector = CONFIG.selector;
		this.subSelector = CONFIG.subSelector;
	}
	
	public ArrayList<String> crawl() {
		Document root = getUrl(url);
		Elements posts = getPosts(root, postContainerSelector);
		return getResults(posts);
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
	
	private ArrayList<String> getResults(Elements posts) {
		ArrayList<String> results = new ArrayList<String>();
		for (Element post: posts) {
			results.add(trimSearchKey(post.select(subSelector).attr("href").toString()));
		}
		return results;
	}
	
	// KV.ee adds a random search key which needs to be cut in order to save and compare links
	private String trimSearchKey(String link) {
		return link.replaceFirst("\\?nr=.*", "");
	}
}
