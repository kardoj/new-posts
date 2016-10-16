package xyz.kardo;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	private String url = "http://kinnisvaraportaal-kv-ee.postimees.ee/" + 
			 "?act=search.simple&company_id=&page=1&orderby=pawl&page_size=50" + 
			 "&deal_type=2&dt_select=2&county=1&parish=421&price_min=100&price_max=350" + 
			 "&price_type=1&rooms_min=&rooms_max=&nr_of_people=&area_min=25&area_max=&floor_min=&floor_max=&keyword=kalamaja";
	private Document root;
	private String postContainerSelector = ".object-type-apartment";
	
	public Crawler() {
		
	}
	
	public ArrayList<String> crawl() {
		root = getUrl(url);
		Elements posts = getPosts(root, postContainerSelector);
		return getLinks(posts);
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
	
	private ArrayList<String> getLinks(Elements posts) {
		ArrayList<String> links = new ArrayList<String>();
		String selector = ".object-title-a";
		for (Element post: posts) {
			links.add(trimSearchKey(post.select(selector).attr("href").toString()));
		}
		return links;
	}
	
	// KV.ee adds a random search key which needs to be cut in order to save and compare links
	private String trimSearchKey(String link) {
		return link.replaceFirst("\\?nr=.*", "");
	}
}
