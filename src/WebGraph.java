import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

//Mike Kennedy
//108715992

public class WebGraph 
{
	public static final int MAX_PAGES = 40;
	
	private Collection<WebPage> pages;
	private int[][] edges = new int[MAX_PAGES][MAX_PAGES];
	
	public WebGraph() 
	{
		
	}
	
	public static WebGraph buildFromFiles(String pagesFile, String linksFile)
	throws IllegalArgumentException, FileNotFoundException, IOException
	//TODO: throw exception if file does not reference valid file
	//TODO: throw fileNotFoundException if file doesn't exist
	//TODO: throw IOException if file is empty
	{		
		FileInputStream pagesIn = new FileInputStream(pagesFile);
		InputStreamReader pagesInStream = new InputStreamReader(pagesIn);
		BufferedReader pagesReader = new BufferedReader(pagesInStream);
		
		String currentLine = pagesReader.readLine();
		while(currentLine != null)
		{
			System.out.println(currentLine);
			currentLine = pagesReader.readLine();
		}
		
		
		
		FileInputStream linksIn = new FileInputStream(linksFile);
		InputStreamReader linksInStream = new InputStreamReader(linksIn);
		BufferedReader linksReader = new BufferedReader(linksInStream);
		
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
