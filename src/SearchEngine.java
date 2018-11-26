import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//Mike Kennedy
//108715992

public class SearchEngine 
{
	static Scanner s = new Scanner(System.in);
	
	public static final String PAGES_FILE = "pages.txt";
	public static final String LINKS_FILE = "links.txt";
	
	public static WebGraph web;
	
	public SearchEngine() 
	{
		web = new WebGraph();
	}
	
	public static void main(String[] args)
	{
		System.out.println("Loading WebGraph data...");
		try 
		{
			WebGraph newGraph = new WebGraph();
			newGraph.buildFromFiles("pages.txt", "links.txt");
			web = newGraph;
			System.out.println("Success!");
		} 
		catch (IOException e) 
		{
			System.out.println("Unable to load WebGraph.  Terminating.");
			return;
		}		
		
		String selection;
		do
		{
			printMenu();
			System.out.print("\nPlease select an option: ");
			selection = getSelection();
			
			switch(selection)
			{
			case "AP":
				addPage();
				break;
			case "RP":
				removePage();
				break;
			case "AL":
				addLink();
				break;
			case "RL":
				removeLink();
				break;
			case "P":
				printTheGraph();
				break;
			case "S":
				System.out.print("Search keyword: ");
				String key = s.nextLine();
				Search(key);
				break;
			case "Q":
				System.out.println("\nGoodbye.");
				return;
			default:
				System.out.println("Invalid selection, please try again.");
				break;
			}
			
		} while (true);
	}

	private static void removeLink() 
	{
		System.out.print("Enter a source URL: ");
		String source = s.nextLine();
		System.out.print("Enter a destination URL: ");
		String destination = s.nextLine();
		try
		{
			web.removeLink(source, destination);
			System.out.println("\nLink removed from " + source + " to " + destination + "!");
		} catch (IllegalArgumentException e) {
			System.out.println("Error encountered.  Link was not removed.");
		}
		
	}

	private static void removePage() 
	{
		System.out.print("Enter a URL: ");
		String url = s.next();
		
		try
		{
			web.removePage(url);
			System.out.println("\n" + url + " has been removed from the graph!\n");
		} catch (IllegalArgumentException e) {
			System.out.println("Error encountered. \"" + url + "\" not removed.");
		}
	}

	private static void addPage() 
	{
		System.out.print("Enter a URL: ");
		String url = s.nextLine();
		System.out.print("Enter keywords (space - separated): ");
		String keys = s.nextLine();
		String[] keysArray = keys.split(" ");
		Collection<String> keywords = new ArrayList<String>();
		for(int i = 0; i < keysArray.length; i++)
		{
			keywords.add(keysArray[i]);
		} 
		
		try
		{
			web.addPage(url, keywords);
			System.out.println("\n" + url + " successfully added to the WebGraph!");
		} catch (IllegalArgumentException e){
			System.out.println("Unable to add page to graph");
		}
	}

	private static void addLink() 
	{
		System.out.print("Enter a source URL: ");
		String source = s.nextLine();	//TODO: detect bad/not links
		System.out.print("Enter a destination URL: ");
		String destination = s.nextLine();
		try
		{
			web.addLink(source, destination);
			System.out.println("\nLink successfully added from " + source + " to " + destination + "!\n");
		} catch (IllegalArgumentException illex)
		{
			System.out.println("Link addition failed.");
		}
	}

	private static void Search(String key) 
	{
		Collection<WebPage> hasKeyword = web.pageContains(key);
		if(hasKeyword.isEmpty())
			System.out.println("No pages found containing keyword: \"" + key + "\"");
		else
		{
			printSearchTable(hasKeyword, key);
		}
	}

	private static void printSearchTable(Collection<WebPage> hasKeyword, String key) 
	{
		String format = "\n%1$-8s%2$-12s%3$-12s\n";
		System.out.format(format, "Rank", "Page Rank", "URL");
		System.out.println("---------------------------------------------");
		format = "  %1$-4s|    %2$-7s| %3$-20s\n";
		
		ArrayList printable = (ArrayList) hasKeyword;
		
		WebPage currentPage = new WebPage();
		Iterator<WebPage> iterator = hasKeyword.iterator();		
		
		String url;
		int rank;
		int list = 1;
		while(iterator.hasNext())
		{
			currentPage = iterator.next();
			url = currentPage.URL();
			rank = currentPage.rank();
			System.out.format(format, list++, rank, url);
		}
	}

	private static void printTheGraph() 
	{
		System.out.println();
		System.out.println("    (I) Sort based on index (ASC)");
		System.out.println("    (U) Sort based on URL (ASC)");
		System.out.println("    (R) Sort based on rank (DSC)");
		System.out.println();
		System.out.print("Please select an option: ");
		
		String option = getSelection();
		
		switch(option)
		{
		case "I":
			web.sortByIndex();
			break;
		case "U":
			web.sortByURL();
			break;
		case "R":
			web.sortByRank();
			break;
		default:
			System.out.println("Selection not recognized.");
			return;
		}
		web.printTable();
	}

	private static String getSelection() 
	{
		String option;
		option = s.nextLine();
		//TODO: catch dumb inputs
		return option;
	}

	private static void printMenu() 
	{
		System.out.println();
		System.out.println("Menu:");
		System.out.println("    (AP) - Add new page to the graph.");
		System.out.println("    (RP) - Remove a page from the graph.");
		System.out.println("    (AL) - Add a link between pages in the graph.");
		System.out.println("    (RL) - Remove a link between pages in the graph.");
		System.out.println("    (P)  - Print the graph.");
		System.out.println("    (S)  - Search for pages with a keyboard.");
		System.out.println("    (Q)  - Quit.");
	}

}
