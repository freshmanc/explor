package vm;

import java.util.Iterator;

import structures.Failure;
import structures.Failures;
import structures.Object;
import structures.Process;
import structures.Trace;
import structures.TraceSet;
import structures.Transition;
import structures.TransitionSystem;
import utilities.Utilities;

public class CategoryVM {
	private Object<Process> init;


	
	public Object<Process> getInit() {
		return init;
	}

	public void setInit(Object<Process> init) {
		this.init = init;
	}

	CategoryVM(Process orig)
	{
		init=new Object<Process>(new Process());
		Trace trace=new Trace();
		buildInitialObject(orig);
		buildNextObjects(init,orig);
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
				init.setParent(null);
			}
		}		
	}
	
	private Object<Process>  searchObjectByTrace(Trace trace,Object<Process> obj)
	{
		Process tmpp=obj.getData();
		Failure tmpf;
		for(Iterator it=tmpp.getFailures().iterator();it.hasNext();)
		{
			tmpf=(Failure)it.next();
			if(Utilities.compTrace(trace, tmpf.getTrace()))
			{
				return obj;
			}
		}
		for(Iterator ct=obj.getChildren().iterator();ct.hasNext();)
		{
			searchObjectByTrace(trace, (Object<Process>)ct.next());
		}
		return null;
	}
	
	private TraceSet getLongestTrace(Object<Process> obj)
	{
		Process tmpp=obj.getData();
		for(Iterator it=obj.getData().getFailures().iterator();it.hasNext();)
		{
			
		}
		Failure tmpf;
		Failure crtf;
		Iterator it=tmpp.getFailures().iterator();
		TraceSet longestTraceSet=new TraceSet();
		tmpf=(Failure)it.next();
		for(it=tmpp.getFailures().iterator();it.hasNext();)
		{
			crtf=(Failure)it.next();
			if(crtf.getTrace().size()>tmpf.getTrace().size())
				tmpf=crtf;
		}	
		for(it=tmpp.getFailures().iterator();it.hasNext();)
		{
			crtf=(Failure)it.next();
			
			if(crtf.getTrace().size()==tmpf.getTrace().size())
			{
				longestTraceSet.add(tmpf.getTrace());
				System.out.println(crtf.getTrace());
			}
		}	
		return longestTraceSet;
	}
	
	private void buildNextObjects( Object<Process> parent, Process orig)
	{
		
		Failure tmpf;
		TraceSet traceSet=getLongestTrace(parent);
		Trace trace=new Trace();
		Process tmppd;
		tmppd=new Process();	
		tmppd.setAlphabet(orig.getAlphabet());				
		tmppd.setFailures(parent.getData().getFailures());
		
		for(Iterator tsit=traceSet.iterator();tsit.hasNext();)
		{
			trace=(Trace)tsit.next();	
		}
		
		if (traceSet.size()>1)
		{
			for(Iterator tsit=traceSet.iterator();tsit.hasNext();)
			{
				for(Iterator it=orig.getFailures().iterator();it.hasNext();)
				{
					tmpf=(Failure)it.next();
					if(Utilities.subTrace(trace,tmpf.getTrace()) && (trace.size()+1)==tmpf.getTrace().size())
					{
						tmppd.getFailures().add(tmpf);
					}
				}
			}
			Object<Process> child=new Object<Process>(tmppd);
			child.setParent(parent);
			parent.addChild(child);
			buildNextObjects(child, orig);

		}
		else
		{
			boolean flag=false;
			for(Iterator it=orig.getFailures().iterator();it.hasNext();)
			{
				tmpf=(Failure)it.next();
				if(Utilities.subTrace(trace,tmpf.getTrace()) && (trace.size()+1)==tmpf.getTrace().size())
				{

					if(drinks.contains(tmpf.getTrace().get(trace.size()))&&tmpf.getTrace().get(trace.size()-1).equals("coin"))
					{
						flag=true;
						tmppd.getFailures().add(tmpf);
					}
					else
					{	
						Process tmpp;
						tmpp=new Process();
						tmpp.setAlphabet(orig.getAlphabet());				
						tmpp.setFailures(parent.getData().getFailures());
						tmppd.getFailures().add(tmpf);
						Object<Process> child=new Object<Process>(tmpp);
						child.setParent(parent);
						parent.addChild(child);
						buildNextObjects(child, orig);
					}
				}
			}
			if(flag==true)
			{
				Object<Process> child=new Object<Process>(tmppd);
				child.setParent(parent);
				parent.addChild(child);
				buildNextObjects(child, orig);
			}
		}

	}

	

	
	public void printCategory(Object<Process> current, int level)
	{
		System.out.println("Level "+level);
		for(Iterator it=current.getData().getFailures().iterator();it.hasNext();)
		{
			Failure f=(Failure)it.next();
			System.out.println(f.getTrace());
			System.out.println(f.getRefusal());
		}
		System.out.println("-----------------");
		for(Iterator xt=current.getChildren().iterator();xt.hasNext();)
		{
			Object<Process> tmp=(Object<Process>)xt.next();
			printCategory(tmp,level+1);
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
		
		CategoryVM ct2=new CategoryVM(vmi2);
		ct2.printCategory(ct2.getInit(), 0);
		
	}
}
