package structures;
import java.util.*;

public class EventSet extends HashSet<String>{
	public EventSet(EventSet evts)
	{
		for(Iterator<String> it=evts.iterator();it.hasNext();)
		{
			this.add(it.next());
		}
	}
	public EventSet()
	{
		this.addAll(new HashSet<String>());
	}
}
