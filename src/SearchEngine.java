import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//Mike Kennedy
//108715992

public class SearchEngine 
{
	static Scanner s = new Scanner(System.in);
	
	public static final String PAGES_FILE = "pages.txt";
	public static final String LINKS_FILE = "links.txt";
	
	private WebGraph web;
	
	public SearchEngine() {}
	
	public static void main(String[] args)
	{
		System.out.println("Loading WebGraph data...");
		
		WebGraph graph = new WebGraph();
		try 
		{
			graph.buildFromFiles("pages.txt", "links.txt");
			System.out.println("Success!");
		} 
		catch (IOException e) 
		{
			//TODO: implement all relevent catches
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
				System.out.println("Add a new page to the graph.");
				break;
			case "RP":
				System.out.println("Remove a page from the graph.");
				break;
			case "AL":
				System.out.println("Add a link between pages");
				break;
			case "RL":
				System.out.println("Remove a link");
				break;
			case "P":
				printTheGraph();
				break;
			case "S":
				System.out.println("Search for pages with a keyword");
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
			System.out.println("Sort based on index");
			break;
		case "U":
			System.out.println("Based on url");
			break;
		case "R":
			System.out.println("Based on rank");
			break;
		default:
			System.out.println("Selection not recognized.");
			break;
		}
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
