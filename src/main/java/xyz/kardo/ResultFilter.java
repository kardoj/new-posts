/*
 * Kardo Jõeleht 2016
 */
package xyz.kardo;

import java.util.ArrayList;
import java.util.List;

public class ResultFilter {
	private FileIO fileIO;
	
	public ResultFilter(FileIO fileIO) {
		this.fileIO = fileIO;
	}
	
	public ArrayList<String> getNewResults(ArrayList<String> allResults) {
		ArrayList<String> newResults = new ArrayList<String>();
		List<String> lines = fileIO.getAllLines();
		for (String result: allResults) {
			if (!lines.contains(result)) newResults.add(result);
		}		
		return newResults;
	}
}
