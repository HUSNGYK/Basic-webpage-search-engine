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
	{	
		//Setup webpage collection
		FileInputStream pagesIn = new FileInputStream(pagesFile);
		InputStreamReader pagesInStream = new InputStreamReader(pagesIn);
		BufferedReader pagesReader = new BufferedReader(pagesInStream);
		
		if(pagesFile.isEmpty())
			throw new IOException("File referenced is empty.");
		
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
			rank = 0;
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
		Iterator iterator = pages.iterator();
		WebPage page;
		for(int i = 0; i < pages.size(); i++)
		{
			page = (WebPage) iterator.next();
			if(page.URL().equals(url))
				throw new IllegalArgumentException("Url already exists in page");
		}
		
		pages.add(new WebPage(url, pages.size(), 0, keywords));
		
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
	throws IllegalArgumentException
	{
		//Update links
		WebPage removePage = findPage(url);		
		int sourceIndex = removePage.index();
		Collection<WebPage> links = getRefs(removePage);
		Iterator linkIterator = links.iterator();
		WebPage update;
		while(linkIterator.hasNext())
		{
			update = (WebPage) linkIterator.next();
			removeLink(update.URL(), url);
		}
		String before = "";
		String after = "";
		for(int row = 0; row < edges.length - 1; row++)
		{
			for(int col = 0; col < edges.length - 1; col++)
			{
				if(row >= sourceIndex)
					edges[row][col] = edges[row + 1][col];
				if(col >= sourceIndex)
					edges[row][col] = edges[row][col + 1];
			}
		}
		
		pages.remove(removePage);
		
		//Update index
		Iterator iterator = pages.iterator();
		WebPage page;
		while(iterator.hasNext())
		{
			page = (WebPage) iterator.next();
			if(page.index() >= sourceIndex)
				page.setIndex(page.index() - 1);
		}
		
	}
	private Collection<WebPage> getRefs(WebPage source) 
	{
		Collection<WebPage> references = new ArrayList<>();
		Iterator iterator = pages.iterator();
		WebPage add;
		for(int i = 0; i < pages.size(); i++)
		{
			add = (WebPage) iterator.next();
			if(edges[source.index()][i] == 1)
			{
				references.add(add);
			}
		}		
		return references;
	}
	public void removeLink(String source, String destination)
	throws IllegalArgumentException
	{
		WebPage removeLink = findPage(source);
		int sourceIndex = removeLink.index();
		removeLink = findPage(destination);
		int destinationIndex = removeLink.index();
		
		edges[sourceIndex][destinationIndex] = 0;
	}
	public void updatePageRanks()
	{
		Iterator iterator = pages.iterator();
		WebPage current;
		int index;
		int rank;
		for(int i = 0; i < pages.size(); i++)
		{
			rank = 0;
			current = (WebPage) iterator.next();
			index = current.index();
			for(int row = 0; row < pages.size(); row++)
				rank += edges[row][index];
			current.setRank(rank);
		}
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
		
		//Return separated by commas
		return collection.stream()
				.map(n -> String.valueOf(n))
				.collect(Collectors.joining(", "));
	}	
	public void sortByIndex()
	{
		Collections.sort((ArrayList<WebPage>) pages, new IndexComparator());
	}
	public void sortByURL()
	{		
		Collections.sort((ArrayList<WebPage>) pages, new URLComparator());
	}
	public void sortByRank()
	{
		Collections.sort((ArrayList<WebPage>) pages, new RankComparator());
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
