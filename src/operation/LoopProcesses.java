package operation;

import java.util.HashSet;
import java.util.Iterator;

import structures.Process;
import utilities.Utilities;
import structures.*;

public class LoopProcesses extends Process{

	
	public LoopProcesses(Process p, int time)
	{
		if(time<=0)
		{
			System.out.println("the number of loops must be bigger than 0");
		}
		else if(time==1) //loop once
		{
			this.alphabet.addAll(p.getAlphabet());
			this.failures.addAll(p.getFailures());
		}
		else //loop more than once
		{
			Process s=p;
			for(int i=1;i<time;i++)
			{
				s=new SequentialProcesses(s,p);// using SequentialProcess for loop
			}
			this.alphabet.addAll(s.getAlphabet());
			this.failures.addAll(s.getFailures());
		}
	}
	
	public static void main(String args[])
	{
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition(0,"coin",1));
		ts2.add(new Transition(1,"pepsi",2));
		ts2.add(new Transition(1,"coke",3));
		
		Process vmi2=new Process(ts2);
		Process loop=new LoopProcesses(vmi2,4);
		
		System.out.println(loop.getAlphabet());
		CategoryProcess cp=new CategoryProcess(loop);
		Utilities.printCategory(cp.getInit(), 0);
		//for(Iterator fit=loop.getFailures().iterator();fit.hasNext();)
		//{
		//	Failure f=(Failure)fit.next();
		//	System.out.println("Trace "+f.getTrace());
		//	System.out.println("Refusal"+f.getRefusal());
		//}
	}
}
