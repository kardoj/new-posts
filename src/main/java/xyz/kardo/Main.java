/*
 * Kardo Jõeleht, 2016
 */
package xyz.kardo;

import java.util.ArrayList;

public class Main implements Runnable {
	public final ArrayList<Site> sites;
	private int timeout = 300000;
	
	public Main(String configsPath) {
		SiteConfig[] configs = FileIO.readSiteConfigs(configsPath);
		sites = new ArrayList<Site>();
		for (SiteConfig config : configs) {
			sites.add(new Site(config));
		}
	}

	public static void main(String[] args) {
		String configsPath = "configs.json";
		new Main(configsPath).run();
	}

	@Override
	public void run() {
		while(true) {
			for (Site site : sites) {
				site.check();
				waitRandomTime(1000, 10000);
			}
			try { Thread.sleep(timeout); }
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
	
	private void waitRandomTime(int min, int max) {
		int random = (int) (Math.random() * max) + min;
		try { Thread.sleep(random); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}
}
