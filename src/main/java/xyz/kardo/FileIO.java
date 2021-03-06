/*
 * Kardo J�eleht, 2016
 */
package xyz.kardo;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class FileIO {
	private String dataFolder = "data";
	private String dataPath;
	
	public FileIO(String dataFile) {
		this.dataPath = dataFolder + "/" + dataFile;
	}
	
	public void appendLines(ArrayList<String> lines) {
		try(FileWriter fw = new FileWriter(dataPath, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    for (String line: lines) {
			    	out.println(line);
			    }
			    out.close();
			} catch (IOException e) {
			    e.printStackTrace();
			}		
	}
	
	public List<String> getAllLines() {
		List<String> lines = new ArrayList<String>();
		
		try {
			lines = Files.readAllLines(Paths.get(dataPath), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		return lines;
	}
	
	public static SiteConfig[] readSiteConfigs(String configsPath) {
		JsonReader reader;
		SiteConfig[] siteConfigs = null;
		try {
			reader = new JsonReader(new FileReader(configsPath));
			Gson gson = new Gson();
			siteConfigs = gson.fromJson(reader, SiteConfig[].class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return siteConfigs;
	}
}
