import java.util.Collection;

//Mike Kennedy
//108715992

public class WebGraph 
{
	public static final int MAX_PAGES = 40;
	
	private Collection<WebPage> pages;
	private int[][] edges = new int[MAX_PAGES][MAX_PAGES];
	
	public WebGraph() {}
	
	public static WebGraph buildFromFiles(String pagesFile, String linksFile)
	throws IllegalArgumentException	//TODO: throw exception if file does not reference valid file
	{
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
