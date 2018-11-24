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
		//TODO: load webgraph data
		
		
		printMenu();
		String selection;
		do
		{
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
				System.out.println("Print the graph");
				break;
			case "S":
				System.out.println("Search for pages with a keyword");
				break;
			case "Q":
				System.out.println("Quit");
				break;
			default:
				System.out.println("Invalid selection, please try again.");
				break;
			}
			
		} while (!selection.equals("Q"));
		
		System.out.println("\nGoodbye.");
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
