/*
 * Kardo Jõeleht, 2016
 */
package xyz.kardo;

import java.util.ArrayList;

public class Main implements Runnable {
	private long interval = 60 * 1000 * 5;
	private boolean running = true;
	private boolean sendMails;
	private Config config;
	private Mailer mailer;
	private ResultFilter resultFilter;
	private Crawler crawler;
	
	public Main(boolean sendMails, String dataPath, String configPath) {
		config = new ConfigReader(configPath).read();
		this.sendMails = sendMails;
		mailer = new Mailer("kardoj@gmail.com");
		resultFilter = new ResultFilter(dataPath);
		crawler = new Crawler();
	}
	
	@Override
	public void run(){
		while (running) {
			ArrayList<String> newLinks = resultFilter.filter(crawler.crawl());
			if (sendMails) mailer.sendEmails(newLinks);			
			try { Thread.sleep(interval); }
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}

	public static void main(String[] args) {
		String dataPath = "data/kv.txt";
		String configPath = "config.json";
		new Main(false, dataPath, configPath).run();
	}
}
