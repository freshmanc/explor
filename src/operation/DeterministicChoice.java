//developer ming zhu
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

public class DeterministicChoice extends Process{ //after the empty trace, the first event in p,q must be different

	
	public DeterministicChoice(Process p, Process q)  
	{
		boolean flag=false;
		this.alphabet=(EventSet)Utilities.union(p.getAlphabet(), q.getAlphabet()); //new alphabet
		Utilities.ExtendEventsToProcess(p, q.getAlphabet());
		Utilities.ExtendEventsToProcess(q, p.getAlphabet());
		
		
		Failure epf=Utilities.searchFailureByTrace(new Trace(), p);
		Failure eqf=Utilities.searchFailureByTrace(new Trace(), q);
		Refusal refusal=Utilities.intersection(epf.getRefusal(), eqf.getRefusal());//new refusal of the empty trace
		
		this.getFailures().add(new Failure(new Trace(),refusal));//generate the failure with empty trace
		p.getFailures().remove(epf);//temporarily remove the failure with empty trace from p
		q.getFailures().remove(eqf);//temporarily remove the failure with empty trace from q
		
		this.failures.addAll(p.getFailures()); //add p into new process
		
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
		
		p.getFailures().add(epf);//add the failure with empty trace back to p
		q.getFailures().add(eqf);//add the failrue with empty trace back to q
		
	}
	
	public static void main(String args[])
	{
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition(0,"pepsi",1));
		ts2.add(new Transition(1,"black",4));
		
		TransitionSystem ts1=new TransitionSystem();
		ts1.add(new Transition(0,"coke",1));
		ts1.add(new Transition(1,"red",2));

		
		Process vmi2=new Process(ts2);
		Process vmi1=new Process(ts1);

		Process s=new DeterministicChoice(vmi1,vmi2);
		Utilities.printProcess(s);
	}
}
