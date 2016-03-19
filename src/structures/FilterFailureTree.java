//developer ming zhu
package structures;
import java.util.*;

import utilities.Utilities;

public class FilterFailureTree extends FailureTree{ // simply remove events from trace and refusal

	//private EventSet alphabet;
	//private FailureTreeNode root;

	public FilterFailureTree(FailureTree ft, EventSet evts) { 
		
		this.setAlphabet(new EventSet(evts));
		filterInit(ft.getRoot(),evts);
		filterSubTree(this.getRoot(),ft.getRoot(),evts);
	}
	private void filterInit(FailureTreeNode node,EventSet evts)
	{
		Failure f=node.getData();
		f=Utilities.filterFailure(f, evts);
		FailureTreeNode init=new FailureTreeNode(f);
		this.setRoot(init);
	}
	
	private void filterSubTree(FailureTreeNode filterNode, FailureTreeNode node,  EventSet evts)
	{
		for(Iterator<FailureTreeNode> it=node.getChildren().iterator();it.hasNext();)
		{
			FailureTreeNode ftn=it.next();
			Failure f=ftn.getData();
			f=Utilities.filterFailure(f, evts);
			FailureTreeNode child=new FailureTreeNode(f);
			filterNode.addChild(child);
			filterSubTree(child,ftn,evts);
		}
	}
	
	public static void main(String args[])
	{
//		TransitionSystem ts2=new TransitionSystem();
//		ts2.add(new Transition(0,"coin",1));
//		ts2.add(new Transition(1,"push",9));
//		ts2.add(new Transition(9,"tea",10));
//		ts2.add(new Transition(10,"pepsi",11));
//		ts2.add(new Transition(11,"coke",12));
//		ts2.add(new Transition(12,"button",15));
//		
//		ts2.add(new Transition(1,"coke",2));
//		ts2.add(new Transition(2,"pepsi",5));
//		ts2.add(new Transition(5,"push",6));
//		ts2.add(new Transition(6,"button",7));
//		ts2.add(new Transition(7,"tea",13));
//		
//		ts2.add(new Transition(2,"push",3));
//		ts2.add(new Transition(3,"tea",4));
//		ts2.add(new Transition(4,"button",14));
//		ts2.add(new Transition(14,"pepsi",8));
//		
//		EventSet evts=new EventSet();
//		evts.add("coin");
//		evts.add("coke");
//		evts.add("pepsi");
//		evts.add("tea");
		
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition("0","coin","1"));
		ts2.add(new Transition("1","tea","10"));
		ts2.add(new Transition("10","pepsi","11"));
		ts2.add(new Transition("11","coke","12"));
		
		ts2.add(new Transition("1","coke","2"));
		ts2.add(new Transition("2","pepsi","5"));
		ts2.add(new Transition("5","tea","13"));
		
		ts2.add(new Transition("2","tea","4"));
		ts2.add(new Transition("4","pepsi","8"));
		
		Process vmi2=new Process(ts2);
		FailureTree ft=new FailureTree(vmi2);
		//FilterFailureTree fft=new FilterFailureTree(ft,evts);
		Utilities.printFailureTree(ft);
	}

}
