import java.util.Collection;

//Mike Kennedy
//108715992

/* Description:
 * -represents a hyperlinked document
 */

public class WebPage implements Comparable
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
	
	public void setIndex(int index)
	{
		this.index = index;
	}
	public void setRank(int rank)
	{
		this.rank = rank;
	}
	public String URL()
	{
		return this.url;
	}
	public int rank()
	{
		return this.rank;
	}
	public Collection<String> keywords()
	{
		return this.keywords;
	}
	public int index()
	{
		return this.index;
	}
	public String toString() 
	{
		return "";
	}
	
	@Override
	public int compareTo(Object o) {
		WebPage other = (WebPage) o;
		if(this.rank == other.rank)
			return 0;
		else if(this.rank > other.rank)
			return 1;
		else
			return -1;
	}

}
