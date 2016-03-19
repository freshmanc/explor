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
	private Process P;
	private Process Q;

	public Communication(Process p, Process q)
	{
		this.P=p;
		this.Q=q;
		this.alphabet= Utilities.intersection(P.getAlphabet(), Q.getAlphabet());
		execute();
	}
	
	private void execute()
	{
		buildInitialCommunication();
		Trace trace=new Trace();
		buildNextCommunication(trace);
	}
	
	private void buildInitialCommunication()
	{
		for(Iterator<Failure> pit=P.getFailures().iterator();pit.hasNext();)
		{
			Failure tmpfp=pit.next();
			if(tmpfp.getTrace().size()==0)
			{
				for(Iterator<Failure> qit=Q.getFailures().iterator();qit.hasNext();)
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
	
	private void buildNextCommunication(Trace trace)
	{
		for(Iterator<Failure> pit=P.getFailures().iterator();pit.hasNext();)
		{
			Failure tmpfp=(Failure)pit.next();
			if(Utilities.subTrace(trace,tmpfp.getTrace()) && (trace.size()+1)==tmpfp.getTrace().size())
			{
				for(Iterator<Failure> qit=Q.getFailures().iterator();qit.hasNext();)
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
							buildNextCommunication(tmpfp.getTrace());
						}
					}
				}
			}
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
		ts1.add(new Transition(1,"coke1",3));
		//ts1.add(new Transition(1,"tea",4));
		
		Process vmi2=new Process(ts2);
		//Utilities.printProcess(vmi2);
		Process vmi1=new Process(ts1);
		
		Process c=new Communication(vmi1,vmi2);
		Utilities.printProcess(c);
		//CategoryProcess cp= new CategoryProcess(c);
		//Utilities.printCategory(cp.getInit(), 0);
		}
}
