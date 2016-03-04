package structures;
import java.util.*;

public class Failure {
	private Trace trace;
	private Refusal refusal;
	
	public Trace getTrace() {
		return trace;
	}
	
	public void setTrace(Trace trace) {
		
		Trace t=new Trace();
		for(Iterator<String> it=trace.iterator();it.hasNext();)
		{
			t.add(it.next());
		}
		this.trace = t;
	}
	
	public Refusal getRefusal() {
		return refusal;
	}
	
	public void setRefusal(Refusal refusal) {
		Refusal r=new Refusal();
		for(Iterator<HashSet<String>> it=refusal.iterator();it.hasNext();)
		{
			r.add(it.next());
		}
		this.refusal = r;
	}
	
	public Failure()
	{
		this.trace=new Trace();
		this.refusal=new Refusal();
	}
	
	public Failure(Trace trace, Refusal refusal)
	{
		this.setTrace(trace);
		this.setRefusal(refusal);
	}
	
	public Failure(Failure f)
	{
		this.setTrace(f.getTrace());
		this.setRefusal(f.getRefusal());
	}
	
}
