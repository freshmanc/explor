package structures;
import java.util.*;

public class Trace extends Vector<String> {

	public Trace(Trace trace)
	{
		for(Iterator it=trace.iterator();it.hasNext();)
			this.add((String)it.next());
	}
	
	public Trace(String trace)
	{
		this.add(trace);
	}
	
	public Trace()
	{
	}
}
