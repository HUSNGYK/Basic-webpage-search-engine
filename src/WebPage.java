import java.util.Collection;

//Mike Kennedy
//108715992

/* Description:
 * -represents a hyperlinked document
 */

public class WebPage 
{
	private String url;
	private int index;
	private int rank;
	private Collection<String> keywords;
	
	public WebPage() {}
	public WebPage(String url, int index, int rank, Collection<String> keywords) 
	{
		this.url = url;
		this.index = index;
		this.rank = rank;
		this.keywords = keywords;
	}
	
	public String URL()
	{
		return url;
	}
	public String toString() 
	{
		return "";
	}

}
