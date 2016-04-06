//developer ming zhu

package operation;

import java.util.HashSet;
import java.util.Iterator;

import structures.Process;
import utilities.Utilities;
import structures.*;

public class SequentialProcesses extends Process{ //build sequential processes

	public SequentialProcesses(Process p,Process q)
	{
		this.alphabet=(EventSet) Utilities.union(p.getAlphabet(), q.getAlphabet());
		
		Process qClone=new Process();
		qClone.setAlphabet(q.getAlphabet());
		qClone.setFailures(q.getFailures());
		
		Process pClone=new Process();
		pClone.setAlphabet(p.getAlphabet());
		pClone.setFailures(p.getFailures());
		
		Utilities.ExtendEventsToProcess(pClone, qClone.getAlphabet());
		Utilities.ExtendEventsToProcess(qClone, pClone.getAlphabet());

		this.setFailures(pClone.getFailures());;//add failures of pClone
		HashSet<Trace> sucSet=Utilities.searchSuccessfulTraces(this); //find a set of largest traces
		
		Failure qnil=null; //failure with empty trace in qClone
		Failures exp=new Failures(); //failures of extended qClone 
		
		for(Iterator<Failure> qit=qClone.getFailures().iterator();qit.hasNext();) // find the empty trace
		{
			Failure tmpfq=qit.next();
			if(tmpfq.getTrace().equals(new Trace()))
			{
				qnil=tmpfq;
			}
		}
		
		for(Iterator<Trace> traceIt=sucSet.iterator();traceIt.hasNext();) // replace the refusal of each largest trace of pClone by using  refusal of the empty trace of q
		{
			Trace tmpt=traceIt.next();
			for(Iterator<Failure> thisIt=this.getFailures().iterator();thisIt.hasNext();) //find the failures of the largest trace
			{
				Failure tmpfp=(Failure)thisIt.next();
				if(tmpfp.getTrace().equals(tmpt))
				{
					tmpfp.setRefusal(qnil.getRefusal());
				}
			}
		}
		
		qClone.getFailures().remove(qnil);//remove the empty trace from failure temporarily

		for(Iterator<Trace> traceIt=sucSet.iterator();traceIt.hasNext();) //extend the each trace of q by useing each largest trace of p
		{
			Trace tmpts=traceIt.next();
			for(Iterator<Failure> qit=qClone.getFailures().iterator();qit.hasNext();)
			{
				Failure tmpfq=qit.next();
				Failure nf=new Failure();//new failure to copy the failure in qCline
				for(Iterator<String> sit=tmpts.iterator();sit.hasNext();) //events from the largest trace of pClone
				{
					nf.getTrace().add(sit.next());
				}
				for(Iterator<String> tit=tmpfq.getTrace().iterator();tit.hasNext();)// events from the trace of qClone
				{
					nf.getTrace().add(tit.next());
				}
				for(Iterator<EventSet> rit=tmpfq.getRefusal().iterator();rit.hasNext();)//refusal is from the refusal of qClone
				{
					nf.getRefusal().add(rit.next());
				}
				exp.add(nf);
			}
		}
	
			
		for(Iterator<Failure> qit=exp.iterator();qit.hasNext();) //add extended failures of qClone
		{
			Failure tmpfq=qit.next();
			this.failures.add(tmpfq);
		}

	}
	
	public static void main(String args[])
	{
		
		TransitionSystem ts1=new TransitionSystem();
		ts1.add(new Transition(0,"coin",1));

		
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition(0,"pepsi",1));
		
		TransitionSystem ts3=new TransitionSystem();
		ts3.add(new Transition(0,"hotpepsi",1));
		
		Process vmi2=new Process(ts2);
		Process vmi1=new Process(ts1);
		Process vmi3=new Process(ts3);

		Process s=new SequentialProcesses(vmi1,vmi2);
		s=new SequentialProcesses(s,vmi3);
		Utilities.printProcess(s);
	}
}
