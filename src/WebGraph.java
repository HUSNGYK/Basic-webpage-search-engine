import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

//Mike Kennedy
//108715992

public class WebGraph 
{
	public static final int MAX_PAGES = 40;
	
	private static Collection<WebPage> pages = new ArrayList<WebPage>();
	private int[][] edges = new int[MAX_PAGES][MAX_PAGES];
	
	public WebGraph() {}
	
	public static WebGraph buildFromFiles(String pagesFile, String linksFile)
	throws IllegalArgumentException, FileNotFoundException, IOException
	//TODO: throw exception if file does not reference valid file
	//TODO: throw fileNotFoundException if file doesn't exist
	//TODO: throw IOException if file is empty
	{	
		//Setup webpage collection
		FileInputStream pagesIn = new FileInputStream(pagesFile);
		InputStreamReader pagesInStream = new InputStreamReader(pagesIn);
		BufferedReader pagesReader = new BufferedReader(pagesInStream);
		
		String currentLine[] = pagesReader.readLine().split("\\s+");
		String url;
		int rank;
		int index = 0;
		Collection<String> keywords = new ArrayList<String>();
		
		while(!currentLine.equals(null))
		{
			url = currentLine[0];
			for(int i = 1; i < currentLine.length; i++)
				keywords.add(currentLine[i]);
			rank = 0;	//TODO: get rank from somewhere
			pages.add(new WebPage(url, index++, rank, keywords));
			try {
				currentLine = pagesReader.readLine().split("\\s+");
			} catch (Exception e){
				break;//break if there are no more lines
			}
		}
		
		//Populate graph with links
		FileInputStream linksIn = new FileInputStream(linksFile);
		InputStreamReader linksInStream = new InputStreamReader(linksIn);
		BufferedReader linksReader = new BufferedReader(linksInStream);
		
		Iterator<WebPage> iterator = pages.iterator();
		
		String line = linksReader.readLine();
		String source;
		String destination;
		while(line != (null))
		{
			source = line.substring(0, line.indexOf(" "));
			destination = line.substring(line.indexOf(" "), line.length());

			if(pages.isEmpty())
				//TODO: do something if pages didn't read anything
				return null;
			
			
			
			try {
				line = linksReader.readLine();
			} catch (Exception e) {
				break;
			}
		}
		
		
		return null;		
	}
	public void addPage(String url, Collection<String> keywords)
	throws IllegalArgumentException
	{
		
	}
	public void addLink(String source, String destination)
	throws IllegalArgumentException
	{
		
	}
	public void removePage(String url)
	{
		
	}
	public void removeLink(String source, String destination)
	{
		
	}
	public void updatePageRanks()
	{
		
	}
	public void printTable()
	{
		
	}

}
