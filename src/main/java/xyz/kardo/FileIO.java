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
	
	public void appendLinesToFile(ArrayList<String> lines) {
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
}
