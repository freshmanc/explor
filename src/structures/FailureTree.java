package structures;
import java.util.*;

import structures.Object;
import structures.Process;
import utilities.Utilities;

public class FailureTree {
	private EventSet alphabet;
	private FailureTreeNode root;
	
	public Process treeToProcess()
	{
		Process p=new Process();
		p.setAlphabet(this.alphabet);
		Failure init=new Failure();
		init.setTrace(root.getData().getTrace());
		init.setRefusal(root.getData().getRefusal());
		p.getFailures().add(init);
		travesTree(p, root);
		return p;
	}

	public FailureTree()
	{
		this.alphabet=new EventSet();
		this.root=new FailureTreeNode();
	}
	
	
	public FailureTree(Process p)
	{
		this.alphabet=p.getAlphabet();
		buildInit(p);
		buildSubTree(new Trace(),root,p);
	}
	
	private void buildInit(Process p)
	{
		for(Iterator<Failure> it=p.getFailures().iterator();it.hasNext();)
		{
			Failure tmp=it.next();
			if(tmp.getTrace().equals(new Trace()))
			{
				FailureTreeNode init=new FailureTreeNode(tmp);
				this.root=init;
			}
		}
	}
	
	private void travesTree(Process p, FailureTreeNode node)
	{
		for(Iterator<FailureTreeNode> it=node.getChildren().iterator();it.hasNext();)
		{
			Failure f=new Failure();
			FailureTreeNode tmp=it.next();

			f.setRefusal(tmp.getData().getRefusal());
			f.setTrace(tmp.getData().getTrace());
			p.getFailures().add(f);
			travesTree(p, tmp);
		}
	}
	
	private void buildSubTree(Trace trace, FailureTreeNode node, Process p)
	{
		for(Iterator<Failure> it=p.getFailures().iterator();it.hasNext();)
		{
			Failure tmpf=(Failure)it.next();
			
			if(Utilities.subTrace(trace,tmpf.getTrace()) && (trace.size()+1)==tmpf.getTrace().size())
			{
				FailureTreeNode child=new FailureTreeNode(tmpf);
				child.setParent(node);
				node.addChild(child);
				buildSubTree(tmpf.getTrace(),child, p);
			}
		}
	}
	
	public EventSet getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(EventSet alphabet) {
		this.alphabet = alphabet;
	}

	public FailureTreeNode getRoot() {
		return root;
	}

	public void setRoot(FailureTreeNode root) {
		this.root = root;
	}
	
	public static void main(String args[])
	{
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition("0","coin","1"));
		//ts2.add(new Transition(1,"push",9));
		ts2.add(new Transition("1","tea","10"));
		ts2.add(new Transition("10","pepsi","11"));
		ts2.add(new Transition("11","coke","12"));
		
		ts2.add(new Transition("1","coke","2"));
		ts2.add(new Transition("2","pepsi","5"));
		//ts2.add(new Transition(5,"push",6));
		//ts2.add(new Transition(6,"button",7));
		ts2.add(new Transition("5","tea","13"));
		
		//ts2.add(new Transition(2,"push",3));
		ts2.add(new Transition("2","tea","4"));
		//ts2.add(new Transition(4,"button",14));
		ts2.add(new Transition("4","pepsi","8"));
		
		
		Process vmi2=new Process(ts2);
		Utilities.printProcess(vmi2);
		FailureTree ft=new FailureTree(vmi2);
		Utilities.printFailureTree(ft);
		//Utilities.printProcess(ft.treeToProcess());
	}
}
