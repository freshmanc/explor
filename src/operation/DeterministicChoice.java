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
		Process pClone=new Process();
		pClone.setAlphabet(p.getAlphabet());
		pClone.setFailures(p.getFailures());
		
		Process qClone=new Process();
		qClone.setAlphabet(q.getAlphabet());
		qClone.setFailures(q.getFailures());

		Utilities.ExtendEventsToProcess(pClone, qClone.getAlphabet());
		Utilities.ExtendEventsToProcess(qClone, pClone.getAlphabet());
		
		Failure epf=Utilities.searchFailureByTrace(new Trace(), pClone);
		Failure eqf=Utilities.searchFailureByTrace(new Trace(), qClone);
		Refusal refusal=Utilities.intersection(epf.getRefusal(), eqf.getRefusal());//new refusal of the empty trace
		
		Failure nil=new Failure(new Trace(),refusal);
		
		
		pClone.getFailures().remove(epf);//temporarily remove the failure with empty trace from p
		qClone.getFailures().remove(eqf);//temporarily remove the failure with empty trace from q
		
		
		this.setFailures(pClone.getFailures()); //add failures of pClone without epf
		this.getFailures().add(nil);//add the failure with empty trace
		for(Iterator<Failure> qit=qClone.getFailures().iterator();qit.hasNext();)//add failures of qClone without eqf
		{
			this.getFailures().add(qit.next());
		}
		
//		for(Iterator<Failure> qit=qClone.getFailures().iterator();qit.hasNext();)//according to the definition of deterministic choice
//		{
//			Failure fq=qit.next();
//			flag=false;
//			for(Iterator<Failure> pit=pClone.getFailures().iterator();pit.hasNext();)//check if there is a failure with the same trace
//			{
//				Failure fp=pit.next();
//				if(fq.getTrace().equals(fp.getTrace()))
//				{
//					for(Iterator<Failure> tit=this.getFailures().iterator();tit.hasNext();)// update the trace in the new failure as the union
//					{
//						Failure ft=tit.next();
//						if(ft.getTrace().equals(fq.getTrace()))
//						{
//							ft.setRefusal(Utilities.union(fp.getRefusal(), fq.getRefusal()));
//							flag=true;
//						}
//					}
//				}
//			}
//			if(flag==false)
//			this.failures.add(fq);
//		}		
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
