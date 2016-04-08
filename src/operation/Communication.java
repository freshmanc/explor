package operation;

import java.util.*;
import java.util.Iterator;

import structures.EventSet;
import structures.Failure;
import structures.Process;
import structures.Refusal;
import structures.Trace;
import structures.Transition;
import structures.TransitionSystem;
import structures.CategoryProcess;
import utilities.Utilities;

public class Communication extends Process{


	public Communication(Process p, Process q)
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
		
		execute(pClone, qClone);
	}
	
	private void execute(Process p, Process q)
	{
		buildInitialCommunication(p,q);
		Trace trace=new Trace();
		buildNextCommunication(trace,p,q);
	}
	
	private void buildInitialCommunication(Process p, Process q)
	{
		for(Iterator<Failure> pit=p.getFailures().iterator();pit.hasNext();)
		{
			Failure tmpfp=pit.next();
			if(tmpfp.getTrace().size()==0)
			{
				for(Iterator<Failure> qit=q.getFailures().iterator();qit.hasNext();)
				{
					Failure tmpfq=qit.next();
					if(tmpfq.getTrace().size()==0)
					{
						Failure tmp=new Failure();
						tmp.setTrace(tmpfp.getTrace());
						EventSet evtsRP=Utilities.powerSetToSet(tmpfp.getRefusal());
						EventSet evtsRQ=Utilities.powerSetToSet(tmpfq.getRefusal());
						EventSet union=Utilities.union(evtsRP, evtsRQ);
						Refusal newPws=Utilities.powerSet(union);
						newPws.remove(new EventSet());
						tmp.setRefusal(newPws);
						this.failures.add(tmp);
					}	
				}	
			}
		}		
	}
	
	private void buildNextCommunication(Trace trace, Process p, Process q)
	{
		for(Iterator<Failure> pit=p.getFailures().iterator();pit.hasNext();)
		{
			Failure tmpfp=(Failure)pit.next();
			if(Utilities.subTrace(trace,tmpfp.getTrace()) && (trace.size()+1)==tmpfp.getTrace().size())
			{
				for(Iterator<Failure> qit=q.getFailures().iterator();qit.hasNext();)
				{
					Failure tmpfq=(Failure)qit.next();
					if(Utilities.subTrace(trace,tmpfq.getTrace()) && (trace.size()+1)==tmpfq.getTrace().size())
					{
						if(tmpfp.getTrace().equals(tmpfq.getTrace()))
						{
							Failure tmp=new Failure();
							tmp.setTrace(tmpfp.getTrace());
							EventSet evtsRP=Utilities.powerSetToSet(tmpfp.getRefusal());
							EventSet evtsRQ=Utilities.powerSetToSet(tmpfq.getRefusal());
							EventSet union=Utilities.union(evtsRP, evtsRQ);
							Refusal newPws=Utilities.powerSet(union);
							newPws.remove(new EventSet());
							tmp.setRefusal(newPws);
							this.failures.add(tmp);
							buildNextCommunication(tmpfp.getTrace(),p,q);
						}
					}
				}
			}
		}
	}
	

}
