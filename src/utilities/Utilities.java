//developer Ming Zhu
//functions used to operate on structures

package utilities;
import java.util.*;


import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import structures.Process;
import structures.*;

public class Utilities{

	public static boolean belongs(String elm, EventSet set)//check if elm inside of set
	{
		for(Iterator<String> it=set.iterator();it.hasNext();)
		{
			if(it.next().equals(elm))
				return true;
		}
		return false;
	}
	
	public static boolean belongs(EventSet elm, Refusal ref)
	{
		for(Iterator<EventSet> it=ref.iterator();it.hasNext();)
		{
			EventSet tmp=it.next();
			if(Utilities.subSet(elm, tmp)&&Utilities.subSet(tmp, elm))
				return true;
		}
		return false;
	}
	
	
	public static boolean subSet(Refusal x, Refusal y)
	{
		boolean sub=true;
		for(Iterator<EventSet> ix=x.iterator();ix.hasNext();)
		{
			if(!belongs(ix.next(),y))
			{
				sub=false;
				break;
			}
		}
		return sub;
	}
	
	public static boolean subSet(EventSet x, EventSet y)// check if x is a subset of y
	{
		boolean sub=true;
		for(Iterator<String> ix=x.iterator();ix.hasNext();)
		{
			if(!belongs(ix.next(),y))
			{
				sub=false;
				break;
			}
		}
		return sub;
	}
		
	
	public static String numberFmt(double d)
	{
	    if(d == (long) d)
	        return String.format("%d",(long)d);
	    else
	        return String.format("%s",d);
	}
	
	public static String findInitialState(TransitionSystem ts)// initial state has the smallest value in "From"
	{
		double init=Double.MAX_VALUE;
    	for(Iterator<Transition> it=ts.iterator();it.hasNext();)
    	{
    		Transition t=it.next();
    		String from=t.getFrom();
    		if(from.contains("^")!=true)
    		{
    			if(init>Double.valueOf(from))
    				init=Double.valueOf(from);
    		}
    	}
    	return numberFmt(init);
	}
	
	public static boolean subTrace(Trace a, Trace b)// check if a is a sub trace of b (a,b should have the same initial element)
	{
		Iterator<String> ia=a.iterator();
		Iterator<String> ib=b.iterator();
		if(a.size()>b.size())
		{
			return false;
		}
		for(;ia.hasNext();)
		{
			if(!ia.next().equals(ib.next()))
			{
				return false;
			}
		}
		return true;
	}
	
	
	public static  EventSet powerSetToSet(Refusal pws)// generate original events from powerset
	{	
		EventSet set=new EventSet(); 
		for(Iterator<EventSet> it=pws.iterator();it.hasNext();)
		{
			EventSet tmp=it.next();
			set.addAll(Utilities.union(set, tmp));
		}
		return set;
	}
	
	public static Refusal powerSet(EventSet originalSet) // from EventSet to Refusal
	{
		Refusal sets=new Refusal();
		if(originalSet.isEmpty())
		{
			sets.add(new EventSet());
			return sets;
		}
		
		String head=new String();
		Iterator<String> it=originalSet.iterator();
		if(it.hasNext())
			head=it.next();
		EventSet rest=new EventSet();
		while(it.hasNext())
		{
			rest.add(it.next());
		}

		for(Iterator<EventSet> rit=powerSet(rest).iterator();rit.hasNext();)
		{
			EventSet set=rit.next();
	    	EventSet newSet = new EventSet();
	    	newSet.add(head);
	    	newSet.addAll(set);
	    	sets.add(newSet);
	    	sets.add(set);
		}
		return sets;
	}
	
	
	public static Trace filterTrace(Trace t, EventSet evts) //remove events in evts from trace t
	{
		Trace nt=new Trace();
		for(Iterator<String> it=t.iterator();it.hasNext();)
		{
			String tmp=it.next();
			if(Utilities.belongs(tmp, evts))
			{
				nt.add(tmp);
			}
		}
		return nt;
	}
	
	public static Refusal filterRefursal(Refusal f, EventSet evts) //remove events from refusal
	{
		Refusal nf=new Refusal();
		for(Iterator<EventSet> it=f.iterator();it.hasNext();)
		{
			nf.add( Utilities.intersection(it.next(), evts));
		}
		nf.remove(new EventSet());
		return nf;
	}
	
	public static Failure filterFailure(Failure f,EventSet evts) //remove events from failure
	{
		Failure nf=new Failure();
		nf.setTrace(Utilities.filterTrace(f.getTrace(), evts));
		nf.setRefusal(Utilities.filterRefursal(f.getRefusal(), evts));
		return nf;
	}
	
	private static boolean addTransitionWillCreateLoop(TransitionSystem ts, Transition t) //check if there are any transitions from "state" back to "state"
	{
		TransitionSystem nts=new TransitionSystem();
		nts.addAll(ts);
		nts.add(t);
		String state=t.getFrom();
		
		LinkedList<String> queue=new LinkedList<String>(); //a set of states that have not been visited
		queue.add(state);
		TransitionSystem tmpTs;
		while(queue.isEmpty()!=true)
		{
			tmpTs=Utilities.nextImmediateTransitions(nts, queue.removeFirst());
			for(Iterator<Transition> it=tmpTs.iterator();it.hasNext();)
			{
				Transition tmpT=it.next();
				if(tmpT.getTo().equals(state)) //from state to state, a loop
				{
					return true;
				}
				else
				{
					queue.add(tmpT.getTo());
				}
			}
		}
		return false;
	}
	
	public static void printTransitionSystem(TransitionSystem ts)
	{
		for(Iterator<Transition> it=ts.iterator();it.hasNext();)
		{
			Transition t=it.next();
			System.out.println("From:"+t.getFrom()+ " Label:"+t.getLabel()+ " To:"+t.getTo());
		}
	}
	
	public static TransitionSystem loopTransitionSystem(TransitionSystem ts, int loop)//loop the acyclic transition system
	{
		if(loop<=0)
		{
			return null;
		}
		if(loop==1)
		{
			TransitionSystem nts=new TransitionSystem();
			nts.addAll(ts);
			return nts;
		}
		else
		{
			TransitionSystem nts=new TransitionSystem();
			nts.addAll(ts);
			for(int i=1;i<loop;i++)
			{
				TransitionSystem tmpTs=null;
				HashSet<String> setL=findStateWithSuccessors(nts);// find states end with ^
				for(Iterator<String> it=setL.iterator();it.hasNext();)
				{
					String tmp=it.next();
					String substitute=new String(tmp);
					tmp=tmp.substring(0, tmp.length()-1);//remove the last ^
					int lastIndex=tmp.lastIndexOf('^');// get the position of second last ^
					String prefix;
					if(lastIndex>=0)
						prefix =tmp.substring(lastIndex+1); //get the string between second last ^ and last ^
					else
						prefix=tmp;// if there is no second last ^, just take the string tmp;
					tmpTs=Utilities.findSubTransitionSystems(ts, prefix);
					tmpTs=Utilities.modifyTransitionsPrefix(tmpTs, prefix, substitute);
					nts.addAll(tmpTs);
				}		
			}
			return nts;
		}
	}
	
	private static TransitionSystem modifyTransitionsPrefix(TransitionSystem ts, String prefix, String substitute)//add substitute before states 
	{
		TransitionSystem nts=new TransitionSystem();
		Transition tmp;
		for(Iterator<Transition> it=ts.iterator();it.hasNext();)
		{
			Transition t=it.next();
			if(t.getFrom().equals(prefix))
			{
				tmp=new Transition(substitute, t.getLabel(),substitute+t.getTo());
			}
			else
			{
				tmp=new Transition(substitute+t.getFrom(), t.getLabel(),substitute+t.getTo());
			}
			nts.add(tmp);
		}
		return nts;
	}
	
	
	//this is applied to acyclic transition system only
	private static TransitionSystem findSubTransitionSystems(TransitionSystem ts, String state)// find sub transition system after the state
	{
		
		TransitionSystem nts=new TransitionSystem();
		TransitionSystem tmpTs;
		LinkedList<String> queue=new LinkedList<String>();
		queue.add(state);
		while(queue.isEmpty()!=true)
		{
			tmpTs=Utilities.nextImmediateTransitions(ts, queue.removeFirst());
			nts.addAll(tmpTs);
			for(Iterator<Transition> it=tmpTs.iterator();it.hasNext();)
				queue.add(it.next().getTo());
		}
		return nts;
	}
	
	//this is for finding state which had successors in cyclic transition system
	private static HashSet<String> findStateWithSuccessors(TransitionSystem ts) //find which states was in a loop in previous transition system, it has ^ in it
	{
		HashSet<String> set=new HashSet<String>();
		for(Iterator<Transition> it=ts.iterator();it.hasNext();)
		{
			Transition t=it.next();
			String to=t.getTo();
			if(to.lastIndexOf("^")==to.length()-1)//the state always ending with ^ was in a loop
			{
				set.add(to);
			}
		}
		return set;
	}	
	
	public static TransitionSystem acyclicTransitionSystemFromState(TransitionSystem ts, String state)// break loop after specific state
	{
		//System.out.println("acyclicTransitionSystemFromState");
		TransitionSystem nts=new TransitionSystem();
		TransitionSystem tmpTs;
		LinkedList<String> queue=new LinkedList<String>();
		queue.add(state);
		while(queue.isEmpty()!=true)
		{
			tmpTs=Utilities.nextImmediateTransitions(ts, queue.removeFirst());			
			for(Iterator<Transition> it=tmpTs.iterator();it.hasNext();)
			{
				Transition tmpT=it.next();
				//System.out.println(tmpT.getFrom()+tmpT.getLabel()+tmpT.getTo());
				Transition nt=new Transition(tmpT.getFrom(),tmpT.getLabel(),tmpT.getTo());
				if(Utilities.addTransitionWillCreateLoop(nts, nt)!=true)
				{						
					nts.add(nt);
					queue.addLast(nt.getTo());
				}
				else
				{
					nt.setTo(nt.getTo()+"^");
					nts.add(nt);
				}
			}
		}
		return nts;
	}

	
	public static TransitionSystem acyclicTransitionSystem(TransitionSystem ts)// break the loop of the transition system
	{
		String init=Utilities.findInitialState(ts);
		return Utilities.acyclicTransitionSystemFromState(ts, init);
	}
	
	public static TransitionSystem nextImmediateTransitions(TransitionSystem ts, String from)// find all immediate transitions started from "from"
	{
		//System.out.println("nextTransitions");
		TransitionSystem nextTransitions=new TransitionSystem();
		for(Iterator<Transition> it=ts.iterator();it.hasNext();)
		{
			Transition tmp=it.next();
			if(tmp.getFrom().equals(from))
			{
				//System.out.println(tmp.getFrom()+tmp.getLabel()+tmp.getTo());
				nextTransitions.add(tmp);
			}
		}
		return nextTransitions;
	}
	

	
	public static void ExtendEventsToProcess(Process p,EventSet e) //when two process deterministic select, nondeterministic, execute sequentially, loop, the alphabet and refusals need to be extended
	{
		EventSet newAlphabet= Utilities.union(p.getAlphabet(), e); //extend alphabet first
		for(Iterator<Failure> pit=p.getFailures().iterator();pit.hasNext();)  //extend each failure
		{
			Failure tmpf=pit.next();
			EventSet tmpp=Utilities.powerSetToSet(tmpf.getRefusal()); //get events in refusal
			EventSet acceptEvts=Utilities.setDiff(tmpp, p.getAlphabet()); //find  events not in refusal but in alphabet
			EventSet newRefusalEvts=Utilities.setDiff(acceptEvts, newAlphabet); //find out events in new refusal
			Refusal tmpr=Utilities.powerSet(newRefusalEvts);
			tmpr.remove(new EventSet());// remove empty set
			Refusal rf=new Refusal();
			for(Iterator<EventSet> nit=tmpr.iterator();nit.hasNext();)
			{
				rf.add(nit.next());
			}
			tmpf.setRefusal(rf);
		}
		p.setAlphabet(newAlphabet);
	}

	
	public static EventSet union(EventSet x, EventSet y) //union of EventSets
	{
		EventSet u = new EventSet();
		  for(Iterator<String> j=x.iterator();j.hasNext();)
		  {
			  u.add(j.next());
		  }
		   for (Iterator<String> i= y.iterator(); i.hasNext(); )
			   u.add(i.next());
		   return u;
	}
	
	public static Refusal union(Refusal x, Refusal y) // union of Refusals
	{
		Refusal r=new Refusal();
		for(Iterator<EventSet> xit=x.iterator();xit.hasNext();)
		{
			r.add(xit.next());
		}
		
		for(Iterator<EventSet> yit=y.iterator();yit.hasNext();)
		{
			boolean flag=false;
			EventSet yevts=yit.next();
			for(Iterator<EventSet> xit=x.iterator();xit.hasNext();)
			{
				if(Utilities.compareEventSets(xit.next(), yevts))
					flag=true;
			}
			if(flag==false)
				r.add(yevts);
		}
		return r;
	}
	
	
	public static Refusal intersection(Refusal x, Refusal y)//intersection of Refusal
	{
		Refusal its=new Refusal();
		EventSet temp;
		for(Iterator<EventSet> it=x.iterator();it.hasNext();)
		{
			temp=it.next();
			if(Utilities.belongs(temp, y))
				its.add(temp);
		}
		return its;
	}

	public static EventSet intersection(EventSet x, EventSet y) //intersection of EventSet
	{
		EventSet its=new EventSet();
		String temp;
		for(Iterator<String> it=x.iterator();it.hasNext();)
		{
			temp=it.next();
			if(Utilities.belongs(temp, y))
				its.add(temp);
		}
		return its;
	}
	


	/*compare traces*/
	public static boolean compTrace(Trace s, Trace t)
	{
		if(s.equals(t))
			return true;
		else
			return false;
	}


	public static EventSet setDiff(EventSet x, EventSet y) //find difference between EventSets
	{
		EventSet diff=new EventSet();
		EventSet insec=Utilities.intersection(x, y);
		String temp=new String();
		for(Iterator<String> itx=x.iterator();itx.hasNext();)
		{
			temp=itx.next();
			if(!Utilities.belongs(temp, insec))
				diff.add(temp);
		}
		for(Iterator<String> ity=y.iterator();ity.hasNext();)
		{
			temp=ity.next();
			if(!Utilities.belongs(temp, insec))
				diff.add(temp);
		}
		return diff;
	}
	
	
	public static Failure searchFailureByTrace(Trace trace,Process process) //find the failure that have the trace
	{
		Failure tmpf;
		for(Iterator<Failure> it=process.getFailures().iterator();it.hasNext();)
		{
			tmpf=it.next();
			if(Utilities.compTrace(trace, tmpf.getTrace()))
			{
				return tmpf;
			}
		}
		return null;
	}
	
	
	private static void printObjectState(ObjectState current, int level)
	{
		System.out.println("Level "+level);
		System.out.println("State "+ current.getState());
		System.out.println("Label "+ current.getLabelToState());
		System.out.println("-----------------");
		for(Iterator<ObjectState> xt=current.getChildren().iterator();xt.hasNext();)
		{
			ObjectState tmp=xt.next();
			printObjectState(tmp,level+1);
		}
	}
	
	public static void filterCategoryTransition(EventSet evts, CategoryTransition ct)//filter category transition by using the evts
	{
		 filterObjectState(evts,ct.getInit(),null,null);
	}
	
	private static void filterObjectState(EventSet evts, ObjectState os, ListIterator<ObjectState> lit, List<ObjectState> list)//remove events in evts from all children of os
	//because Iterator cannot be modified by using add or remove, ListIterator is used , and lit is used to track the position of ListIterator
	{
		if(list!=null)// list is used to denote the children of a objectstate which has been removed. the objectstate contains the event in evts
		{
			for(ListIterator<ObjectState> it=list.listIterator();it.hasNext();) 
			{
				ObjectState tmpOs=it.next();
				if(evts.contains(tmpOs.getLabelToState()))
				{
					it.remove();
					if(tmpOs.getChildren().isEmpty()!=false) //use the children to replace the objeststate which contains the events in evts
					{
						for(ListIterator<ObjectState> tmpIt=tmpOs.getChildren().listIterator();tmpIt.hasNext();)
							lit.add(tmpIt.next());
						filterObjectState(evts,os,lit,tmpOs.getChildren());// deep first search
					}
				}
				else
					filterObjectState(evts,tmpOs,null,null);
			}
		}
		else
		{
			for(ListIterator<ObjectState> it=os.getChildren().listIterator();it.hasNext();)
			{
				ObjectState tmpOs=it.next();
				if(evts.contains(tmpOs.getLabelToState()))
				{
					it.remove();
					if(tmpOs.getChildren().isEmpty()!=false)
					{
						for(ListIterator<ObjectState> tmpIt=tmpOs.getChildren().listIterator();tmpIt.hasNext();)
							it.add(tmpIt.next());
						filterObjectState(evts,os,it,tmpOs.getChildren());
					}
				}
				else
					filterObjectState(evts,tmpOs,null,null);
			}
		}
	}
	
	
	public static void printCategoryTransition(CategoryTransition ct)
	{
		printObjectState(ct.getInit(),0);
	}
	
	public static void printCategory(ObjectProcess current, int level)
	{
		System.out.println("Level "+level);
		System.out.println("largest trace: "+current.getTrace());
		for(Iterator<Failure> it=current.getData().getFailures().iterator();it.hasNext();)
		{
			Failure f=it.next();
			System.out.println("Trace: "+f.getTrace());
			System.out.println("Refusal: "+f.getRefusal());
		}
		System.out.println("-----------------");
		for(Iterator<ObjectProcess> xt=current.getChildren().iterator();xt.hasNext();)
		{
			ObjectProcess tmp=xt.next();
			printCategory(tmp,level+1);
		}
	}
	
	public static FailureTreeNode searchNodeInFailureTree(Failure failure,FailureTreeNode node) //find the node that has the failure in data
	{
		Failure tmpData=node.getData();
		FailureTreeNode ftn;
		if(Utilities.compTrace(failure.getTrace(), tmpData.getTrace())&&tmpData.getRefusal().equals(failure.getRefusal()))
		{
			ftn=node;
			return ftn;
		}
		else
		{
			for(Iterator<FailureTreeNode> it=node.getChildren().iterator();it.hasNext();)
			{
				ftn=searchNodeInFailureTree(failure,it.next());
				if(ftn!=null)
					return ftn;
			}
		}
		return null;
	}
	
	public static List<FailureTreeNode> findEquivalentChildren(FailureTreeNode ftn, EventSet evts) //find children whose last event in the trace is  in the evts
	{
		List<FailureTreeNode> list=new ArrayList<FailureTreeNode>();
		for(Iterator<FailureTreeNode> cit=ftn.getChildren().iterator();cit.hasNext();)
		{
			FailureTreeNode ftntmp=cit.next();
			Failure f=ftntmp.getData();
			if(Utilities.belongs(f.getTrace().get(f.getTrace().size()-1), evts))
			{
				list.add(ftntmp);
			}
		}
		return list;
	}
	
	public static Pair findEquivalentFailureTreeNode(FailureTreeNode node,EventSet evts) //find nodes which have same trace as the "node"
	//EventSet is the set that contains acceptable events
	{
		Pair pair= new Pair(new HashSet<FailureTreeNode>(),new HashSet<FailureTreeNode>());
		HashSet<FailureTreeNode> equ=new HashSet<FailureTreeNode>();// to calculate the failure
		HashSet<FailureTreeNode> cat=new HashSet<FailureTreeNode>();// to calculate the mapping between nodes
		if(node.getChildren().size()==0)
		{
			cat.add(node);
			equ.add(node);
		}
		else 
		{
			for(Iterator<FailureTreeNode> it=node.getChildren().iterator();it.hasNext();)
			{
				FailureTreeNode ftn=it.next();
				Failure f=ftn.getData();
				if(Utilities.belongs(f.getTrace().get(f.getTrace().size()-1), evts))
				{
					cat.add(node);
					equ.add(node);
				}
				else
				{
					cat.add(ftn);
					cat.add(node);
					Pair tmppair=findEquivalentFailureTreeNode(ftn,evts);
					equ.addAll(tmppair.getEqu());
					cat.addAll(tmppair.getEqu());
				}
			}
		}
		pair.setCat(cat);
		pair.setEqu(equ);
		return pair;
	}
	
	
	public static TraceSet searchSuccessfulTraces(Process p) //find successful traces
	{
		TraceSet traces=new TraceSet();
		searchLongestTraces(new Trace(),traces,p);
		return traces;
	}
	
	private static void searchLongestTraces(Trace trace,TraceSet traces, Process p) //find the longest traces
	{
		boolean flag=false;
		for(Iterator<Failure> pit=p.getFailures().iterator();pit.hasNext();)
		{
			Failure tmpfp=(Failure)pit.next();
			if(Utilities.subTrace(trace,tmpfp.getTrace()) && ((trace.size()+1)==tmpfp.getTrace().size()))
			{
				flag=true;
				searchLongestTraces(tmpfp.getTrace(),traces,p);

			}
		}
		if(flag==false)
		{
			traces.add(trace);
		}
	}
	
//	public static void extendFailures(Process p, EventSet events)// extend failures with events
//	{
//		p.setAlphabet((EventSet)Utilities.union(p.getAlphabet(),events));
//		for(Iterator<Failure> it=p.getFailures().iterator();it.hasNext();)
//		{
//			Failure f=it.next();
//			Refusal newEvents=new Refusal();
//		}
//	}
	
	public static boolean compareEventSets(EventSet evts1, EventSet evts2)//compare EventSets
	{
		return Utilities.subSet(evts1, evts2)&&Utilities.subSet(evts2, evts1);
	}

	public static void CategoryProcessToXML(Element elm, Document doc, CategoryProcess cp)
	{
		Element cprocess = doc.createElement("categoryprocess");
		elm.appendChild(cprocess);
		
		Utilities.ObjectProcessToXML(cprocess, doc, cp.getInit());
	}
	
	public static void ObjectProcessToXML(Element elm, Document doc, ObjectProcess op)
	{
		Element obj=doc.createElement("object");
		obj.setAttribute("name", op.getTrace().toString());
		elm.appendChild(obj);
		ProcessToXML(obj,doc,op.getData());
		for(Iterator<ObjectProcess> opcit=op.getChildren().iterator();opcit.hasNext();)
		{
			ObjectProcessToXML(obj,doc,opcit.next());
		}
	}
	
	public static void ProcessToXML(Element elm, Document doc, Process p)
	{
			
		Element process = doc.createElement("process");
		elm.appendChild(process);
		
		
		Element alphabet=doc.createElement("alphabet");
		alphabet.appendChild(doc.createTextNode(p.getAlphabet().toString()));
		process.appendChild(alphabet);
		
		Element failures = doc.createElement("failures");
		process.appendChild(failures);
		
		for(Iterator<Failure> fit=p.getFailures().iterator();fit.hasNext();)
		{
			Element failure = doc.createElement("failure");
			failures.appendChild(failure);
			
			Failure f=fit.next();
			Element trace = doc.createElement("trace");
			trace.appendChild(doc.createTextNode(f.getTrace().toString()));
			failure.appendChild(trace);
			
			Element refusal = doc.createElement("refusal");
			refusal.appendChild(doc.createTextNode(f.getRefusal().toString()));
			failure.appendChild(refusal);
		}
	}
	
	public static void printProcess(Process p)
	{
		Failure failure2;
		System.out.println("Alphabet: "+p.getAlphabet());
		for(Iterator<Failure> it=p.getFailures().iterator();it.hasNext();)
		{
			failure2=it.next();
			System.out.println("Trace: "+failure2.getTrace());
			System.out.println("Refusal: "+Utilities.powerSetToSet(failure2.getRefusal()));
		}
	}
	
	public static void printFailureTree(FailureTree ft)
	{
		Utilities.printFailureTreeRoot(ft, 0);
		Utilities.printFailureTreeChildren(ft.getRoot(), 1);
	}
	
	private static void printFailureTreeRoot(FailureTree ft, int level)
	{
		System.out.println("Alphabet: "+ft.getAlphabet());
		System.out.println("Level "+level);
		System.out.println("Trace: "+ ft.getRoot().getData().getTrace());
		System.out.println("Refusal: "+ft.getRoot().getData().getRefusal());//Utilities.powerSetToSet(ft.getRoot().getData().getRefusal()));
	}
	
	private static void printFailureTreeChildren(FailureTreeNode node, int level)
	{
		for(Iterator<FailureTreeNode> it=node.getChildren().iterator();it.hasNext();)
		{
			FailureTreeNode ftn=it.next();
			System.out.println("Level "+level);
			System.out.println("Trace: "+ ftn.getData().getTrace());
			System.out.println("Refusal: "+ftn.getData().getRefusal());//Utilities.powerSetToSet(ftn.getData().getRefusal()));
			Utilities.printFailureTreeChildren(ftn, level+1);
		}
	}
	
	public static void main(String args[])
	{
		EventSet set=new EventSet();
		set.add("coke");
		set.add("pepsi");
		//set.add("tea");
		set.add("coin");
		set.add("push");
		set.add("choose");
		
		EventSet set1=new EventSet();
		set1.add("coke");
		set1.add("pepsi");
		set1.add("tea");
		set1.add("coin");
		Refusal rf=Utilities.powerSet(set);
		System.out.println(Utilities.setDiff(set, set1));
		System.out.println(Utilities.intersection(set, set1));
		//System.out.println(rf);
		//HashSet<HashSet<String>> hs=Utilities.filterRefursal(rf, set1);
		//HashSet<HashSet<String>> rf1=Utilities.powerSet(set1);
//		System.out.println(hs);		
//		System.out.println(rf1);
	}

}
