/*
 * Kardo J�eleht 2016
 */
package xyz.kardo;

import java.util.ArrayList;
import java.util.List;

public class ResultFilter {
	private FileIO fileIO;
	
	public ResultFilter(String dataPath) {
		this.fileIO = new FileIO(dataPath);
	}
	
	public ArrayList<String> getNewResults(ArrayList<String> allResults) {
		ArrayList<String> newResults = new ArrayList<String>();
		List<String> lines = fileIO.getAllLines();
		for (String result: allResults) {
			if (!lines.contains(result)) newResults.add(result);
		}		
		return newResults;
	}
	
	public ArrayList<String> filter(ArrayList<String> allResults) {
		ArrayList<String> newResults = getNewResults(allResults);
		fileIO.appendLines(newResults);
		return newResults;
	}
}
