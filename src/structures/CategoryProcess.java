//developer ming zhu

package structures;
import java.util.*;

import utilities.Utilities;


public class CategoryProcess extends Category{ 
	// a specific category, 
	//each object represents a process
	//each morphism represents the evolution from a process to another process


	public CategoryProcess(Process orig) //construct category from process
	{
		init=new Object<Process>(new Process());// create the root object
		Trace trace=new Trace(); //start from empty trace
		buildInitialObject(orig); //build the initial object
		buildNextObjects(trace,init,orig); //build rest of objects from the initial object
	}
	
	
	private void buildInitialObject(Process orig)
	{
		for(Iterator<Failure> it=orig.getFailures().iterator();it.hasNext();)
		{
			Failure tmpf=it.next();
			if(tmpf.getTrace().size()==0)
			{
				Process tmpp=new Process(); 
				tmpp.setAlphabet(orig.getAlphabet());
				tmpp.getFailures().add(tmpf);
				init.setData(tmpp);
				init.setTrace(tmpf.getTrace());
			}
		}		
	}
	
	private void buildNextObjects(Trace trace, Object<Process> parent, Process orig)
	{
		for(Iterator<Failure> it=orig.getFailures().iterator();it.hasNext();)
		{
			Failure tmpf=it.next();
			
			if(Utilities.subTrace(trace,tmpf.getTrace()) && (trace.size()+1)==tmpf.getTrace().size()) //find next traces
			{
				Process tmpp=new Process();
				tmpp.setAlphabet(orig.getAlphabet());
				
				tmpp.setFailures(parent.getData().getFailures());// failures of the previous process
				tmpp.getFailures().add(tmpf);//new failure
				Object<Process> child=new Object<Process>(tmpp);
			
				parent.addChild(child);
				child.setTrace(tmpf.getTrace());
				buildNextObjects(tmpf.getTrace(),child, orig); //build next objects of child
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
