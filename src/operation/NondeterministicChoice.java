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
		
		Process pClone=new Process();
		pClone.setAlphabet(p.getAlphabet());
		pClone.setFailures(p.getFailures());
		
		Process qClone=new Process();
		qClone.setAlphabet(q.getAlphabet());
		qClone.setFailures(q.getFailures());
		
		Utilities.ExtendEventsToProcess(pClone, qClone.getAlphabet());
		Utilities.ExtendEventsToProcess(qClone, pClone.getAlphabet());

		Failure pnil=Utilities.searchFailureByTrace(new Trace(), pClone); //the failure with empty trace in pClone
		Failure qnil=Utilities.searchFailureByTrace(new Trace(), qClone); //the failure with empty trace in qClone
		 


		pClone.getFailures().remove(pnil);//remove the failure with empty trace in pClone
		qClone.getFailures().remove(qnil);//remove the failure with empty trace in qClone
		
		Failure nil=new Failure();//new failure with empty trace from failures with empty trace in pClone and qClone
		nil.setRefusal(Utilities.union(qnil.getRefusal(),pnil.getRefusal()));
		
		this.setFailures(pClone.getFailures()); //new failures combine pClone and qClone
		this.getFailures().add(nil);
		for(Iterator<Failure> qit=qClone.getFailures().iterator();qit.hasNext();)
		{
			this.getFailures().add(qit.next());
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
		//Utilities.printProcess(vmi2);
		//System.out.println();
		
		Process vmi1=new Process(ts1);
		//Utilities.printProcess(vmi1);
		//System.out.println();

		Process s=new NondeterministicChoice(vmi1,vmi2);
		Utilities.printProcess(s);
	}
}
