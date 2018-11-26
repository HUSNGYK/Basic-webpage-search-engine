import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

//Mike Kennedy
//108715992

public class WebGraph
{
	public static final int MAX_PAGES = 40;
	
	private static Collection<WebPage> pages;
	private static int[][] edges;
	
	public WebGraph() 
	{
		pages = new ArrayList<WebPage>();
		edges = new int[MAX_PAGES][MAX_PAGES];
	}
	public WebGraph(Collection<WebPage> pages, int[][] edges)
	{
		this.pages = pages;
		this.edges = edges;
	}
	
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
		Collection<String> keywords;
		
		while(!currentLine.equals(null))
		{
			url = currentLine[0];
			keywords = new ArrayList<String>();
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
		
		String line = linksReader.readLine();
		String source;
		String destination;
		while(line != (null))
		{
			source = line.substring(0, line.indexOf(" "));
			destination = line.substring(line.indexOf(" ") + 1, line.length());
			
			buildEdges(source, destination);
			
			try {
				line = linksReader.readLine();
			} catch (Exception e) {
				break;
			}
		}
		return new WebGraph(pages, edges);		
	}
	private static void TEST_printEdges()
	{
		for(int i = 0; i < pages.size(); i++)
		{
			for(int j = 0; j < pages.size(); j++)
			{
				System.out.print(edges[i][j] + " ");
			}
			System.out.println();
		}
	}
	private static void buildEdges(String source, String destination) 
	{
		WebPage[] all = (WebPage[]) pages.toArray(new WebPage[pages.size()]);
		for(int srcIndex = 0; srcIndex < all.length; srcIndex++) 
		{
			if(all[srcIndex].URL().equals(source))
			{
				for(int dstIndex = 0; dstIndex < all.length; dstIndex++)
				{
					if(all[dstIndex].URL().equals(destination))
						edges[srcIndex][dstIndex] = 1;
				}
			}
		}
	}

	public void addPage(String url, Collection<String> keywords)
	throws IllegalArgumentException
	{
		//Make sure page doesn't already exist in graph
		try
		{
			WebPage page = findPage(url);
			throw new IllegalArgumentException();
		} catch (IllegalArgumentException e) {}
		
		int rank = 0;	//TODO: figure out rank
		
		pages.add(new WebPage(url, pages.size(), rank, keywords));
		
	}
	public void addLink(String source, String destination)
	throws IllegalArgumentException
	{		
		WebPage page = findPage(source);
		int sourceIndex = page.index();
		page = findPage(destination);
		int destinationIndex = page.index();
		
		edges[sourceIndex][destinationIndex] = 1;
	}
	private WebPage findPage(String url) 
	throws IllegalArgumentException
	{
		Iterator iterator = pages.iterator();
		WebPage find;
		for(int i = 0;i < pages.size(); i++)
		{
			find = (WebPage) iterator.next();			
			if(find.URL().equals(url))
				return find;
		}
		throw new IllegalArgumentException();
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
		String format = "\n%1$-10s%2$-18s%3$-12s%4$-20s%5$-20s\n";
		System.out.format(format, "Index", "URL", "Page Rank", "Links", "Keywords");
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		format = "   %1$-3s| %2$-19s|    %3$-6s| %4$-18s| %5$-20s\n";
		
		ArrayList printable = (ArrayList) pages;
		
		WebPage currentPage = new WebPage();
		Iterator<WebPage> iterator = pages.iterator();		
		
		int index;
		String url;
		int rank;
		String links;
		String keywords;
		while(iterator.hasNext())
		{
			currentPage = iterator.next();
			index = currentPage.index();
			url = currentPage.URL();
			rank = currentPage.rank();
			links = linksToString(currentPage);
			keywords = keywordsToString(currentPage);
			System.out.format(format, index, url, rank, links, keywords);
		}
		
	}
	private String linksToString(WebPage current) 
	{
		int index = current.index();
		Collection<String> links = new ArrayList<String>();
		int k = 0;
		for(int i = 0; i < edges.length; i++)
		{
			if(edges[index][i] == 1)
				links.add(i + "");
		}
		//Return separated by commas
		return links.stream()
				.map(n -> String.valueOf(n))
				.collect(Collectors.joining(", "));
	}
	private String keywordsToString(WebPage page) 
	{		
		Collection<String> collection = page.keywords();
		Iterator i = collection.iterator();

		//Return separated by commas
		return collection.stream()
				.map(n -> String.valueOf(n))
				.collect(Collectors.joining(", "));
	}	
	public void sortByIndex()
	{
		Collections.sort((ArrayList) pages, new IndexComparator());
	}
	public void sortByURL()
	{		
		Collections.sort((ArrayList) pages, new URLComparator());
	}
	public void sortByRank()
	{
		Collections.sort((ArrayList) pages, new RankComparator());
	}
	public Collection<WebPage> pageContains(String keyword)
	{
		Collection<WebPage> collection = new ArrayList<WebPage>();
		
		WebPage find;
		Iterator pageIterator = pages.iterator();
		String Single;
		
		for(int i = 0; i < pages.size(); i++)
		{
			find = (WebPage) pageIterator.next();
			Single = keywordsToString(find);
			if(Single.contains(keyword))
			{
				collection.add(find);
			}
		}
		
		return collection;
	}

}
