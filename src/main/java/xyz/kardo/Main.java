/*
 * Kardo Jõeleht, 2016
 */
package xyz.kardo;

import java.util.ArrayList;

public class Main implements Runnable {
	private long interval = 60 * 1000 * 5;
	private boolean running = true;
	private boolean sendMails;
	private Mailer mailer;
	private ResultFilter resultFilter;
	private Crawler crawler;
	
	public Main(boolean sendMails, String dataFolder, String dataFile) {
		this.sendMails = sendMails;
		mailer = new Mailer("kardoj@gmail.com");
		resultFilter = new ResultFilter(dataFolder + "/" + dataFile);
		crawler = new Crawler();
	}
	
	@Override
	public void run(){
		while (running) {
			ArrayList<String> allLinks = crawler.crawl();
			ArrayList<String> newLinks = resultFilter.filter(allLinks);
			if (sendMails) mailer.sendEmails(newLinks);
			
			try { Thread.sleep(interval); } 
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}

	public static void main(String[] args) {
		String dataFolder = "data";
		String dataFile = "kv.txt";
		new Main(true, dataFolder, dataFile).run();
	}
}
