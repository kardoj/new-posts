/*
 * Kardo Jõeleht 2016
 */
package xyz.kardo;

public class Config {
	private String configPath;
	private FileIO fileIO;
	public Config(String configPath) {
		this.configPath = configPath;
		fileIO = new FileIO(this.configPath);
		String text = fileIO.getFileAsString();
		System.out.println(text);
	}
}
