package structures;

import java.util.*;

import structures.Process;
import utilities.*;

public class EquivalentFailureTree extends FailureTree{

	private List<FailureTreeNode> nodeList;
	private List<HashSet<FailureTreeNode>> nodeSetList;

	public EquivalentFailureTree(FailureTree ft, EventSet evts)
	{
		nodeList=new ArrayList<FailureTreeNode>();
		nodeSetList=new ArrayList<HashSet<FailureTreeNode>>();
		this.setAlphabet(evts);
		this.setRoot(buildNode(ft.getRoot(),evts));
		buildChildrenEquivalentFailureTree(this.getRoot(),ft.getRoot(),evts);
	}
	
	public void buildChildrenEquivalentFailureTree(FailureTreeNode node, FailureTreeNode ftn, EventSet evts)
	{		
		List<FailureTreeNode> list=findChildren(Utilities.findEquivalentFailureTreeNode(ftn,evts).getEqu(),evts);
		if(list.size()>0)
		{
			for(Iterator<FailureTreeNode> it=list.iterator();it.hasNext();)
			{
				FailureTreeNode ftntmp=it.next();
				FailureTreeNode child=buildNode(ftntmp,evts);
				child.setParent(node);
				node.addChild(child);
				buildChildrenEquivalentFailureTree(child,ftntmp,evts);
			}
		}
	}
	
	public List<FailureTreeNode> findChildren(HashSet<FailureTreeNode> set, EventSet evts)
	{
		List<FailureTreeNode> list=new ArrayList<FailureTreeNode>();
		for(Iterator<FailureTreeNode> it=set.iterator();it.hasNext();)
		{
			list.addAll(Utilities.findEquivalentChildren(it.next(), evts));
		}
		return list;
	}
	
	public FailureTreeNode buildNode(FailureTreeNode ftn,EventSet evts) //find equivalent nodes and nodes mapped to it
	{
		FailureTreeNode node=new FailureTreeNode();
		node.setParent(null);
		HashSet<HashSet<String>> pwtevts=Utilities.powerSet(evts);
		pwtevts.remove(new EventSet());
		for(Iterator<HashSet<String>> it=pwtevts.iterator();it.hasNext();)
		{
			node.getData().getRefusal().add(it.next());
		}
		
		Pair pair=Utilities.findEquivalentFailureTreeNode(ftn, evts);


		HashSet<FailureTreeNode> set=pair.getEqu();
		for(Iterator<FailureTreeNode> it=set.iterator();it.hasNext();)
		{
			FailureTreeNode ftntmp=it.next();
			Refusal r=ftntmp.getData().getRefusal();
			r=Utilities.filterRefursal(r, evts);
			HashSet<HashSet<String>> pwttmp=Utilities.intersection(node.getData().getRefusal(), r);
			node.getData().getRefusal().clear();
			for(Iterator<HashSet<String>> itp=pwttmp.iterator();itp.hasNext();)
			{
				node.getData().getRefusal().add(itp.next());
			}
			node.getData().setTrace(Utilities.filterTrace(ftntmp.getData().getTrace(), evts));
		}
		nodeList.add(node);
		nodeSetList.add(pair.getCat());
		return node;
	}
	
	public List<FailureTreeNode> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<FailureTreeNode> nodeList) {
		this.nodeList = nodeList;
	}

	public List<HashSet<FailureTreeNode>> getNodeSetList() {
		return nodeSetList;
	}

	public void setNodeSetList(List<HashSet<FailureTreeNode>> nodeSetList) {
		this.nodeSetList = nodeSetList;
	}

	
	public static void main(String args[])
	{
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition("0","coin","1"));
		ts2.add(new Transition("1","push","9"));
		ts2.add(new Transition("9","tea","10"));
		ts2.add(new Transition("10","pepsi","11"));
		ts2.add(new Transition("11","coke","12"));
		ts2.add(new Transition("12","button","15"));
		
		ts2.add(new Transition("1","coke","2"));
		ts2.add(new Transition("2","pepsi","5"));
		ts2.add(new Transition("5","push","6"));
		ts2.add(new Transition("6","button","7"));
		ts2.add(new Transition(7,"tea",13));
		
		ts2.add(new Transition(2,"push",3));
		ts2.add(new Transition(3,"tea",4));
		ts2.add(new Transition(4,"button",14));
		ts2.add(new Transition(14,"pepsi",8));
		
		Process vmi2=new Process(ts2);
		FailureTree ft=new FailureTree(vmi2);
		Utilities.printFailureTree(ft);
		
		EventSet evts=new EventSet();
		evts.add("coin");
		evts.add("tea");
		evts.add("coke");
		evts.add("pepsi");
		EquivalentFailureTree eft=new EquivalentFailureTree(ft,evts);
		Utilities.printFailureTree(eft);
		
		for(int i=0;i<eft.getNodeList().size();i++)
		{
			FailureTreeNode ftn=eft.getNodeList().get(i);
			HashSet<FailureTreeNode> ftnSet=eft.getNodeSetList().get(i);
			System.out.print("Original Trace: "+ftn.getData().getTrace()+"  "+"Traces Category: ");
			for(Iterator<FailureTreeNode> it=ftnSet.iterator();it.hasNext();)
			{
				System.out.print(it.next().getData().getTrace()+" ");
			}
			System.out.println();
		}
		
	}
}
