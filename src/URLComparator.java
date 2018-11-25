import java.util.Comparator;

//Mike Kennedy
//108715992

public class URLComparator implements Comparator
{
	@Override
	public int compare(Object o1, Object o2) {
		WebPage w1 = (WebPage) o1;
		WebPage w2 = (WebPage) o2;
		return w1.URL().compareTo(w2.URL());
	}

}
