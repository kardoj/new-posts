/*
 * Kardo Jõeleht 2016
 */
package xyz.kardo;

import com.google.gson.Gson;

public class ConfigReader {
	private String configPath;
	private FileIO fileIO;
	public ConfigReader(String configPath) {
		this.configPath = configPath;
		fileIO = new FileIO(this.configPath);
	}
	
	public Config read() {
		String text = fileIO.getFileAsString();
		Gson g = new Gson();
		return g.fromJson(text, Config.class);
	}
}
