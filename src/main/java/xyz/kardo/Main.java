/*
 * Kardo Jõeleht, 2016
 */
package xyz.kardo;

import java.util.ArrayList;

public class Main implements Runnable {
	public final Config CONFIG;
	private boolean running = true;
	private Mailer mailer;
	private ResultFilter resultFilter;
	private Crawler crawler;
	
	public Main(String dataPath, String configPath) {
		CONFIG = FileIO.readConfig(configPath);
		mailer = new Mailer(CONFIG);
		resultFilter = new ResultFilter(dataPath);
		crawler = new Crawler();
	}
	
	@Override
	public void run(){
		while (running) {
			ArrayList<String> newLinks = resultFilter.filter(crawler.crawl());
			if (CONFIG.sendMails) mailer.sendEmails(newLinks);			
			try { Thread.sleep(CONFIG.interval); }
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}

	public static void main(String[] args) {
		String dataPath = "data/kv.txt";
		String configPath = "config.json";
		new Main(dataPath, configPath).run();
	}
}
