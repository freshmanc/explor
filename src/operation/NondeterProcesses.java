package operation;

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

public class NondeterProcesses extends Process{

	public NondeterProcesses(Process p, Process q)
	{
		boolean flag=false;
		this.alphabet=(EventSet)Utilities.union(p.getAlphabet(), q.getAlphabet());
		this.failures=p.getFailures();
		for(Iterator qit=q.getFailures().iterator();qit.hasNext();)
		{
			Failure fq=(Failure)qit.next();
			flag=false;
			for(Iterator tit=this.failures.iterator();tit.hasNext();)
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
	}
	
	public static void main(String args[])
	{
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition(0,"coin",1));
		ts2.add(new Transition(1,"pepsi",2));
		ts2.add(new Transition(1,"coke",3));
		//ts2.add(new Transition(1,"tea",4));
		
		TransitionSystem ts1=new TransitionSystem();
		ts1.add(new Transition(0,"coin",1));
		ts1.add(new Transition(1,"pepsi",2));
		ts1.add(new Transition(1,"coke",3));
		ts1.add(new Transition(1,"tea",4));
		
		Process vmi2=new Process(ts2);
		System.out.println(vmi2.getAlphabet());
		for(Iterator fit=vmi2.getFailures().iterator();fit.hasNext();)
		{
			Failure f=(Failure)fit.next();
			System.out.println("Trace "+f.getTrace());
			System.out.println("Refusal"+f.getRefusal());
		}
		System.out.println("-------------------------");
		Process vmi1=new Process(ts1);

		Process s=new NondeterProcesses(vmi1,vmi2);
		System.out.println(s.getAlphabet());
		for(Iterator fit=s.getFailures().iterator();fit.hasNext();)
		{
			Failure f=(Failure)fit.next();
			System.out.println("Trace "+f.getTrace());
			System.out.println("Refusal"+f.getRefusal());
		}
	}
}
