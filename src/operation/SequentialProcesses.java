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
		Utilities.ExtendEventsToProcess(p, q.getAlphabet());
		Utilities.ExtendEventsToProcess(q, p.getAlphabet());
		HashSet<Trace> sucSet=Utilities.searchSuccessfulTraces(p); //find a set of largest traces
		Failure qnil=null; //failure with empty trace
		Failures exp=new Failures(); //failures of extended q 
		for(Iterator<Failure> qit=q.getFailures().iterator();qit.hasNext();) // find the empty trace
		{
			Failure tmpfq=qit.next();
			if(tmpfq.getTrace().equals(new Trace()))
			{
				qnil=tmpfq;
			}
		}
		
		for(Iterator<Trace> it=sucSet.iterator();it.hasNext();) // replace the refusal of each largest trace by using  the empty trace
		{
			Trace tmpt=it.next();
			for(Iterator<Failure> pit=p.getFailures().iterator();pit.hasNext();) //find the failures of the largest trace
			{
				Failure tmpfp=(Failure)pit.next();
				if(tmpfp.getTrace().equals(tmpt))
				{
					tmpfp.setRefusal(qnil.getRefusal());
				}
			}
		}
		q.getFailures().remove(qnil); //remove the empty trace from failure temporarily
		
		
		for(Iterator<Trace> it=sucSet.iterator();it.hasNext();) //extend the each trace of q by useing each largest trace of p
		{
			Trace tmpts=(Trace)it.next();
			for(Iterator<Failure> qit=q.getFailures().iterator();qit.hasNext();)
			{
				Failure tmpfq=qit.next();
				Failure nf=new Failure();//new failure to copy the failure in q
				for(Iterator<String> sit=tmpts.iterator();sit.hasNext();) //events from the largest trace 
				{
					nf.getTrace().add((String)sit.next());
				}
				for(Iterator<String> tit=tmpfq.getTrace().iterator();tit.hasNext();)// events from the trace of q
				{
					nf.getTrace().add((String)tit.next());
				}
				for(Iterator<EventSet> rit=tmpfq.getRefusal().iterator();rit.hasNext();)//refusal is from the refusal of q
				{
					nf.getRefusal().add((EventSet)rit.next());
				}
				exp.add(nf);
			}
		}
		
		this.failures=p.getFailures();//add failures of p
		for(Iterator<Failure> qit=exp.iterator();qit.hasNext();) //add failures of q
		{
			Failure tmpfq=qit.next();
			this.failures.add(tmpfq);
		}
		q.getFailures().add(qnil);//add the previously removed empty trace
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
