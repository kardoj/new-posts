/*
 * Kardo Jõeleht 2016
 */
package xyz.kardo;

import java.util.ArrayList;

public class Site {
	private boolean sendMails;
	private String to;
	private String from;
	private String subject;
	private String url;
	private String selector;
	private String subSelector;
	private String dataFile;
	
	private Mailer mailer;
	private ResultFilter resultFilter;
	private Crawler crawler;
	
	public Site(SiteConfig config) {
		setAttributes(config);
		
		mailer = new Mailer(from, to, subject);
		resultFilter = new ResultFilter(dataFile);
		crawler = new Crawler(url, selector, subSelector);
	}
	
	private void setAttributes(SiteConfig config) {
		this.sendMails = config.sendMails;
		this.to = config.to;
		this.from = config.from;
		this.subject = config.subject;
		this.url = config.url;
		this.selector = config.selector;
		this.subSelector = config.subSelector;
		this.dataFile = config.dataFile;		
	}
	
	public void check() {
		ArrayList<String> newLinks = resultFilter.filter(crawler.crawl());
		if (sendMails) mailer.sendEmails(newLinks);		
	}
}
