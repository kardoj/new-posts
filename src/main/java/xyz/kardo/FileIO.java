/*
 * Kardo Jõeleht, 2016
 */
package xyz.kardo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
	private String dataPath;
	
	public FileIO(String dataPath) {
		this.dataPath = dataPath;
	}
	
	public void writeLinksToFile(ArrayList<String> newLinks) {
		try(FileWriter fw = new FileWriter(dataPath, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    for (String link: newLinks) {
			    	out.println(link);
			    }
			    out.close();
			} catch (IOException e) {
			    e.printStackTrace();
			}
	}
	
	public ArrayList<String> getNewLinks(ArrayList<String> allLinks) {
		ArrayList<String> newLinks = new ArrayList<String>();
		
		try {
			List<String> lines = Files.readAllLines(Paths.get(dataPath), StandardCharsets.UTF_8);
			for (String link: allLinks) {
				if (!lines.contains(link)) newLinks.add(link);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newLinks;
	}
}
