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

public class NondeterministicChoice extends Process{

	public NondeterministicChoice(Process p, Process q) //after the empty trace, the first event must be equal
	{
		boolean flag=false;
		this.alphabet=(EventSet)Utilities.union(p.getAlphabet(), q.getAlphabet());
		Utilities.ExtendEventsToProcess(p, q.getAlphabet());
		Utilities.ExtendEventsToProcess(q, p.getAlphabet());
		
		this.failures=p.getFailures();
		for(Iterator<Failure> qit=q.getFailures().iterator();qit.hasNext();)//according to the definition of deterministic choice
		{
			Failure fq=qit.next();
			flag=false;
			for(Iterator<Failure> pit=p.getFailures().iterator();pit.hasNext();)//check if there is a failure with the same trace
			{
				Failure fp=pit.next();
				if(fq.getTrace().equals(fp.getTrace()))
				{
					for(Iterator<Failure> tit=this.getFailures().iterator();tit.hasNext();)// update the trace in the new failure as the union
					{
						Failure ft=tit.next();
						if(ft.getTrace().equals(fq.getTrace()))
						{
							ft.setRefusal(Utilities.union(fp.getRefusal(), fq.getRefusal()));
							flag=true;
						}
					}
				}
			}
			if(flag==false)
			this.failures.add(fq);
		}
	}
	
	public static void main(String args[])
	{
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition(1,"coke",3));
		ts2.add(new Transition(3,"red",4));
		
		TransitionSystem ts1=new TransitionSystem();
		ts1.add(new Transition(1,"pepsi",2));
		ts1.add(new Transition(2,"black",3));

		
		Process vmi2=new Process(ts2);
		Process vmi1=new Process(ts1);

		Process s=new NondeterministicChoice(vmi1,vmi2);
		Utilities.printProcess(s);
	}
}
