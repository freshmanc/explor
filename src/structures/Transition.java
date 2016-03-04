package structures;
import java.util.*;

public class Transition {
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public Transition(String from, String label, String to)
	{
		this.from=from;
		this.to=to;
		this.label=label;
	}
	
	public Transition(int from, String label, int to)
	{
		this.from=Integer.toString(from);
		this.to=Integer.toString(to);
		this.label=label;
	}
	
	private String from;
	private String to;
	private String label;
	
}
