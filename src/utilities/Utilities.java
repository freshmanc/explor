package utilities;
import java.util.*;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import structures.Object;
import structures.Process;
import structures.*;

public class Utilities{

	public static <T> boolean belongs( T elm, HashSet<T> set)
	{
		for(Iterator<T> itr=set.iterator();itr.hasNext();)
		{
			if(itr.next().equals(elm))
				return true;
		}
		return false;
	}

	public static <T> boolean subSet(HashSet<T> x, HashSet<T> y)
	{
	   boolean sub = true;
	   for (Iterator ix=x.iterator(); ix.hasNext(); )
	   {   
	      if ( ! belongs((T)ix.next(), y))
	      {
	         sub = false;
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
	
	public static String findInitialState(TransitionSystem ts)
	{
		double init=Double.MAX_VALUE;
    	for(Iterator<Transition> it=ts.iterator();it.hasNext();)
    	{
    		Transition t=it.next();
    		if(init>Double.valueOf(t.getFrom()))
    			init=Double.valueOf(t.getFrom());
    	}
    	return numberFmt(init);
	}
	
	public static boolean subTrace(Trace a, Trace b)
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
	
	public static <T> HashSet<T> powerSetToSet(HashSet<HashSet<T>> pws)
	{	
		HashSet<T> set=new HashSet<T>();
		for(Iterator<HashSet<T>> it=pws.iterator();it.hasNext();)
		{
			HashSet<T> tmp=it.next();
			set.addAll(Utilities.union(set, tmp));
		}
		return set;
	}
	
	public static  HashSet<String> powerSetToSet(Refusal pws)
	{	
		HashSet<String> set=new HashSet<String>();
		for(Iterator<HashSet<String>> it=pws.iterator();it.hasNext();)
		{
			HashSet<String> tmp=it.next();
			set.addAll(Utilities.union(set, tmp));
		}
		return set;
	}
	
	public static <T> HashSet<HashSet<T>> powerSet(HashSet<T> originalSet) {
		    HashSet<HashSet<T>> sets = new HashSet<HashSet<T>>();
		    if (originalSet.isEmpty()) {
		    	sets.add(new HashSet<T>());
		    	return sets;
		    }
		    
		    List<T> list = new ArrayList<T>(originalSet);
		    T head = list.get(0);
		    HashSet<T> rest = new HashSet<T>(list.subList(1, list.size())); 
		    
		    
		    for (HashSet<T> set : powerSet(rest)) {
		    	HashSet<T> newSet = new HashSet<T>();
		    	newSet.add(head);
		    	newSet.addAll(set);
		    	sets.add(newSet);
		    	sets.add(set);
		    }		
		    return sets;
	}

	
	public static Trace filterTrace(Trace t, EventSet evts)
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
	
	public static Refusal filterRefursal(HashSet<HashSet<String>> f, EventSet evts)
	{
		Refusal nf=new Refusal();
		for(Iterator<HashSet<String>> it=f.iterator();it.hasNext();)
		{
			nf.add(Utilities.HashSetToEventSet( Utilities.intersection(it.next(), evts)));
		}
		nf.remove(new EventSet());
		return nf;
	}
	
	public static Failure filterFailure(Failure f,EventSet evts)
	{
		Failure nf=new Failure();
		nf.setTrace(Utilities.filterTrace(f.getTrace(), evts));
		nf.setRefusal(Utilities.filterRefursal(f.getRefusal(), evts));
		return nf;
	}
	
	public static boolean stateExist(TransitionSystem ts, String state)
	{
		for(Iterator<Transition> it=ts.iterator();it.hasNext();)
		{
			Transition t=it.next();
			if(t.getFrom().equals(state)||t.getTo().equals(state))
				return true;
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
	
	public static TransitionSystem loopTransitionSystem(TransitionSystem ts, int loop)
	{
		TransitionSystem nts=new TransitionSystem();
		return nts;
	}
	
	public static TransitionSystem acyclicTransitionSystemWithInitState(TransitionSystem ts, String init)
	{
		TransitionSystem nts=new TransitionSystem();
		TransitionSystem tmpTs;
		LinkedList<String> queue=new LinkedList<String>();
		queue.add(init);
		while(queue.isEmpty()!=true)
		{
			tmpTs=Utilities.nextTransitions(ts, queue.removeFirst());
			for(Iterator<Transition> it=tmpTs.iterator();it.hasNext();)
			{
				Transition tmpT=it.next();
				Transition nt=new Transition(tmpT.getFrom(),tmpT.getLabel(),tmpT.getTo());
				if(Utilities.stateExist(nts, tmpT.getTo())!=true)
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
	
	public static TransitionSystem acyclicTransitionSystem(TransitionSystem ts)
	{
		String init=Utilities.findInitialState(ts);
		return Utilities.acyclicTransitionSystemWithInitState(ts, init);
	}
	
	public static TransitionSystem nextTransitions(TransitionSystem ts, String from)
	{
		TransitionSystem nextTransitions=new TransitionSystem();
		for(Iterator<Transition> it=ts.iterator();it.hasNext();)
		{
			Transition tmp=it.next();
			if(tmp.getFrom().equals(from))
			{
				nextTransitions.add(tmp);
			}
		}
		return nextTransitions;
	}
	
	public static EventSet HashSetToEventSet(HashSet<String> hset)
	{
		EventSet eset=new EventSet();
		for(Iterator it=hset.iterator();it.hasNext();)
		{
			eset.add((String)it.next());
		}
		return eset;
	}
	
	public static void ExtendEventsToProcess(Process p,EventSet e)
	{
		p.setAlphabet((EventSet) Utilities.union(p.getAlphabet(), e));
		for(Iterator<Failure> pit=p.getFailures().iterator();pit.hasNext();) 
		{
			Failure tmpf=pit.next();
			EventSet tmpp=Utilities.HashSetToEventSet(Utilities.powerSetToSet(tmpf.getRefusal()));
			EventSet diff=Utilities.HashSetToEventSet(Utilities.setDiff(tmpp, p.getAlphabet()));
			EventSet intec=Utilities.HashSetToEventSet(Utilities.intersection(diff, e));
			EventSet tmpq=Utilities.HashSetToEventSet(Utilities.setDiff(e, intec));
			EventSet tmppq=Utilities.HashSetToEventSet(Utilities.union(tmpp,tmpq));
			HashSet<HashSet<String>> tmpr=Utilities.powerSet(tmppq);
			Refusal rf=new Refusal();
			for(Iterator nit=tmpr.iterator();nit.hasNext();)
			{
				rf.add(Utilities.HashSetToEventSet((HashSet<String>)nit.next()));
			}
			tmpf.setRefusal(rf);
		}
	}

	
	public static EventSet union(EventSet x, EventSet y)
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
	
	public static <T> HashSet<T> union(HashSet<T> x, HashSet<T> y)
	{
	  HashSet<T> u = new HashSet<T>();
	  for(Iterator<T> j=x.iterator();j.hasNext();)
	  {
		  u.add(j.next());
	  }
	   for (Iterator<T> i= y.iterator(); i.hasNext(); )
		   u.add(i.next());
	   return u;
	}
	

	public static <T> HashSet<T> intersection(HashSet<T> x, HashSet<T> y)
	{
		HashSet<T> its = new HashSet<T>();
		T temp;
		for (Iterator i = x.iterator(); i.hasNext(); )
		{
			temp=(T)i.next();
			if (belongs(temp, y))
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

	

	public static  <T> HashSet<T> setDiff(HashSet<T> x, HashSet<T> y)
	{
	   HashSet<T> diff=new HashSet<T>();
	   HashSet<T> insec=intersection(x,y);
	   T temp;
	   for(Iterator i=x.iterator();i.hasNext();)
	   {
		   temp=(T)i.next();
		   if(!belongs(temp,insec))
			   diff.add(temp);
	   }
	   for(Iterator i=y.iterator();i.hasNext();)
	   {
		   temp=(T)i.next();
		   if(!belongs(temp,insec))
			   diff.add(temp);
	   }
	   return diff;
	}
	
	public static Failure searchFailureByTrace(Trace trace,Process process)
	{
		Failure tmpf;
		for(Iterator it=process.getFailures().iterator();it.hasNext();)
		{
			tmpf=(Failure)it.next();
			if(Utilities.compTrace(trace, tmpf.getTrace()))
			{
				//System.out.println(tmpf.getTrace());
				//System.out.println(tmpf.getRefusal());
				return tmpf;
			}
		}
		return null;
	}
	
	public static void printCategory(Object<Process> current, int level)
	{
		System.out.println("Level "+level);
		System.out.println("largest trace: "+current.getTrace());
		for(Iterator it=current.getData().getFailures().iterator();it.hasNext();)
		{
			Failure f=(Failure)it.next();
			System.out.println("Trace: "+f.getTrace());
			//System.out.print("<");
			//for(Iterator tit=f.getTrace().iterator();tit.hasNext();)
			//{
			//	 System.out.print(tit.next());
			//	if(f.getTrace().size()>1&&tit.hasNext())
			//	 System.out.print(",");
			//}
			//System.out.print(">, {");
			System.out.println("Refusal: "+f.getRefusal());
			//for(Iterator rit=f.getRefusal().iterator();rit.hasNext();)
			//{
			//	HashSet<String> es=(HashSet)rit.next();
			//	if(es.size()>0)
			//	{
			//		System.out.print("{");
			//		for(Iterator eit=es.iterator();eit.hasNext();)
			//		{
			//			System.out.print(eit.next());
			//			if(es.size()>1&&eit.hasNext())
			//				System.out.print(",");
			//		}
			//		System.out.print("}");
			//	}
			//	if(es.size()>0&&rit.hasNext())
			//		 System.out.print(",");
				
			//}
			//System.out.println("}");
		}
		System.out.println("-----------------");
		for(Iterator xt=current.getChildren().iterator();xt.hasNext();)
		{
			Object<Process> tmp=(Object<Process>)xt.next();
			printCategory(tmp,level+1);
		}
	}
	
	public static FailureTreeNode searchNodeInFailureTree(Failure failure,FailureTreeNode node)
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
	
	public static List<FailureTreeNode> findEquivalentChildren(FailureTreeNode ftn, EventSet evts)
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
	
	public static Pair findEquivalentFailureTreeNode(FailureTreeNode node,EventSet evts)
	//EventSet is the set that contains acceptable events
	{
		Pair pair= new Pair(new HashSet<FailureTreeNode>(),new HashSet<FailureTreeNode>());
		HashSet<FailureTreeNode> equ=new HashSet<FailureTreeNode>();
		HashSet<FailureTreeNode> cat=new HashSet<FailureTreeNode>();
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
	
	
	public static TraceSet searchSuccessfulTraces(Process p)
	{
		TraceSet traces=new TraceSet();
		searchLongestTraces(new Trace(),traces,p);
		return traces;
	}
	
	private static void searchLongestTraces(Trace trace,TraceSet traces, Process p)
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
	
	public static void extendFailures(Process p, EventSet events)
	{
		p.setAlphabet((EventSet)Utilities.union(p.getAlphabet(),events));
		for(Iterator<Failure> it=p.getFailures().iterator();it.hasNext();)
		{
			Failure f=it.next();
			Refusal newEvents=new Refusal();
		}
	}

	public static void CategoryProcessToXML(Element elm, Document doc, CategoryProcess cp)
	{
		Element cprocess = doc.createElement("categoryprocess");
		elm.appendChild(cprocess);
		
		Utilities.ObjectProcessToXML(cprocess, doc, cp.getInit());
	}
	
	public static void ObjectProcessToXML(Element elm, Document doc, Object<Process> op)
	{
		Element obj=doc.createElement("object");
		obj.setAttribute("name", op.getTrace().toString());
		elm.appendChild(obj);
		ProcessToXML(obj,doc,op.getData());
		for(Iterator<Object<Process>> opcit=op.getChildren().iterator();opcit.hasNext();)
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
		for(Iterator it=p.getFailures().iterator();it.hasNext();)
		{
			failure2=(Failure)it.next();
			System.out.println("Trace: "+failure2.getTrace());
			System.out.println("Refusal: "+failure2.getRefusal());
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
		set.add("tea");
		set.add("coin");
		set.add("push");
		set.add("choose");
		
		EventSet set1=new EventSet();
		set1.add("coke");
		set1.add("pepsi");
		set1.add("tea");
		set1.add("coin");
		HashSet<HashSet<String>> rf=Utilities.powerSet(set);
		HashSet<HashSet<String>> hs=Utilities.filterRefursal(rf, set1);
		HashSet<HashSet<String>> rf1=Utilities.powerSet(set1);
		System.out.println(hs);		
		System.out.println(rf1);
	}

}
