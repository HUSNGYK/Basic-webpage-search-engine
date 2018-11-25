import java.util.Comparator;

//Mike Kennedy
//108715992

public class RankComparator implements Comparator 
{
	@Override
	public int compare(Object o1, Object o2) {
		WebPage w1 = (WebPage) o1;
		WebPage w2 = (WebPage) o2;
		return (w1.rank() + "".compareTo(w2.rank() + ""));
	}

}
