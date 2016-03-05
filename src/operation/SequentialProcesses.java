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
		HashSet sucSet=Utilities.searchSuccessfulTraces(p);
		Failure qnil=null;
		Failures exp=new Failures();
		for(Iterator qit=q.getFailures().iterator();qit.hasNext();)
		{
			Failure tmpfq=(Failure)qit.next();
			if(tmpfq.getTrace().equals(new Trace()))
			{
				qnil=tmpfq;
				//System.out.println("-------------");
				//System.out.println(qnil.getTrace());
				//System.out.println(qnil.getRefusal());
				//System.out.println("-------------");
			}
		}
		for(Iterator it=sucSet.iterator();it.hasNext();)
		{
			Trace tmpt=(Trace)it.next();
			for(Iterator pit=p.getFailures().iterator();pit.hasNext();)
			{
				Failure tmpfp=(Failure)pit.next();
				if(tmpfp.getTrace().equals(tmpt))
				{
					tmpfp.setRefusal(qnil.getRefusal());
				}
			}
		}
		q.getFailures().remove(qnil);
		
		
		for(Iterator it=sucSet.iterator();it.hasNext();)
		{
			Trace tmpts=(Trace)it.next();
			for(Iterator qit=q.getFailures().iterator();qit.hasNext();)
			{
				Failure tmpfq=(Failure)qit.next();
				Failure nf=new Failure();
				for(Iterator sit=tmpts.iterator();sit.hasNext();)
				{
					nf.getTrace().add((String)sit.next());
				}
				for(Iterator tit=tmpfq.getTrace().iterator();tit.hasNext();)
				{
					nf.getTrace().add((String)tit.next());
				}
				for(Iterator rit=tmpfq.getRefusal().iterator();rit.hasNext();)
				{
					nf.getRefusal().add((EventSet)rit.next());
				}
				exp.add(nf);
			}
		}
		
		this.failures=p.getFailures();
		for(Iterator qit=exp.iterator();qit.hasNext();)
		{
			Failure tmpfq=(Failure)qit.next();
			this.failures.add(tmpfq);
		}
		q.getFailures().add(qnil);//for loopprocesses
	}
	
	public static void main(String args[])
	{
		
		TransitionSystem ts1=new TransitionSystem();
		ts1.add(new Transition(0,"coin1",1));
		ts1.add(new Transition(1,"pepsi1",2));
		ts1.add(new Transition(1,"coke1",3));
		ts1.add(new Transition(1,"tea1",4));
		
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition(0,"coin",1));
		ts2.add(new Transition(1,"pepsi",2));
		ts2.add(new Transition(1,"coke",3));
		//ts2.add(new Transition(1,"tea",4));
		

		
		Process vmi2=new Process(ts2);
		Process vmi1=new Process(ts1);
		//TraceSet set=Utilities.searchSuccessfulTraces(vmi1);
		//for(Iterator it=set.iterator();it.hasNext();)
		//{
		//	System.out.println(it.next());
		//}
		
		Process s=new SequentialProcesses(vmi1,vmi2);
		
		System.out.println(s.getAlphabet());
		for(Iterator fit=s.getFailures().iterator();fit.hasNext();)
		{
			Failure f=(Failure)fit.next();
			System.out.println("Trace "+f.getTrace());
			System.out.println("Refusal"+f.getRefusal());
			System.out.println();
		}
	}
}
