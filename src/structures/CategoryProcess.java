package structures;
import java.util.*;

import utilities.Utilities;


public class CategoryProcess extends Category{


	public CategoryProcess(Process orig)
	{
		init=new Object<Process>(new Process());
		Trace trace=new Trace();
		buildInitialObject(orig);
		buildNextObjects(trace,init,orig);
	}
	
	
	private void buildInitialObject(Process orig)
	{
		for(Iterator it=orig.getFailures().iterator();it.hasNext();)
		{
			Failure tmpf=(Failure)it.next();
			if(tmpf.getTrace().size()==0)
			{
				Process tmpp=new Process();
				tmpp.setAlphabet(orig.getAlphabet());
				tmpp.getFailures().add(tmpf);
				init.setData(tmpp);
				//init.setParent(null);
				init.setTrace(tmpf.getTrace());
				//Process p=(Process)init.getData();
			}
		}		
	}
	
	private void buildNextObjects(Trace trace, Object<Process> parent, Process orig)
	{
		for(Iterator it=orig.getFailures().iterator();it.hasNext();)
		{
			Failure tmpf=(Failure)it.next();
			
			if(Utilities.subTrace(trace,tmpf.getTrace()) && (trace.size()+1)==tmpf.getTrace().size())
			{
				Process tmpp=new Process();
				tmpp.setAlphabet(orig.getAlphabet());
				
				tmpp.setFailures(parent.getData().getFailures());
				tmpp.getFailures().add(tmpf);
				Object<Process> child=new Object<Process>(tmpp);
				//child.setParent(parent);
				parent.addChild(child);
				child.setTrace(tmpf.getTrace());
				buildNextObjects(tmpf.getTrace(),child, orig);
			}
		}
	}
	

	
	
	public static void main(String args[])
	{
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition(0,"coin",1));
		ts2.add(new Transition(1,"pepsi",2));
		ts2.add(new Transition(1,"coke",3));
		ts2.add(new Transition(1,"tea",4));
		
		Process vmi2=new Process(ts2);
		
		CategoryProcess ct2=new CategoryProcess(vmi2);
		Utilities.printCategory(ct2.getInit(), 0);
		
	}
}
