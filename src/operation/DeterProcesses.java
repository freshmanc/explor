package operation;

import java.util.HashSet;
import java.util.Iterator;

import structures.EventSet;
import structures.Failure;
import structures.Process;
import structures.Refusal;
import structures.Trace;
import structures.TraceSet;
import structures.Transition;
import structures.TransitionSystem;
import utilities.Utilities;

public class DeterProcesses extends Process{

	
	public DeterProcesses(Process p, Process q)
	{
		boolean flag=false;
		this.alphabet=(EventSet)Utilities.union(p.getAlphabet(), q.getAlphabet());
		this.failures=p.getFailures();
		
		for(Iterator<Failure> qit=q.getFailures().iterator();qit.hasNext();)
		{
			Failure fq=(Failure)qit.next();
			flag=false;
			for(Iterator<Failure> tit=this.failures.iterator();tit.hasNext();)
			{
				Failure ft=(Failure)tit.next();
				if(fq.getTrace().equals(ft.getTrace()))
				{
					ft.getRefusal().addAll(fq.getRefusal());
					flag=true;
				}
			}
			if(flag==false)
			this.failures.add(fq);
		}
		Failure epf=Utilities.searchFailureByTrace(new Trace(), p);
		Failure eqf=Utilities.searchFailureByTrace(new Trace(), q);
		HashSet<HashSet<String>> events=Utilities.intersection(epf.getRefusal(), eqf.getRefusal());
		Refusal r=new Refusal();
		for(Iterator<HashSet<String>> eit=events.iterator();eit.hasNext();)
		{
			r.add((HashSet<String>) eit.next());
		}
		Failure etf=Utilities.searchFailureByTrace(new Trace(), this);
		etf.setRefusal(r);
	}
	
	public static void main(String args[])
	{
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition(0,"coin",1));
		ts2.add(new Transition(1,"pepsi",2));
		ts2.add(new Transition(1,"coke",3));
		//ts2.add(new Transition(1,"tea",4));
		
		TransitionSystem ts1=new TransitionSystem();
		ts1.add(new Transition(0,"coin1",1));
		ts1.add(new Transition(1,"pepsi1",2));
		ts1.add(new Transition(1,"coke1",3));
		ts1.add(new Transition(1,"tea1",4));
		
		Process vmi2=new Process(ts2);
		System.out.println(vmi2.getAlphabet());
		Process vmi1=new Process(ts1);

		Process s=new DeterProcesses(vmi1,vmi2);
		Utilities.printProcess(s);
	}
}
