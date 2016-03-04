package operation;
import java.util.HashSet;
import java.util.Iterator;

import structures.EventSet;
import structures.Failure;
import structures.Object;
import structures.Process;
import structures.Refusal;
import structures.Trace;
import structures.Transition;
import structures.TransitionSystem;
import structures.CategoryProcess;
import structures.Category;
import utilities.Utilities;

public class CategoryWithHidingEvents extends Category{

	public CategoryWithHidingEvents(Category ct, EventSet evts)
	{
		Category tmpct=filterCategoryProcess(ct,evts);
		Utilities.printCategory(tmpct.getInit(), 0);
	}
	
	CategoryProcess filterCategoryProcess(Category ct, EventSet evts)
	{
		
		Object<Process> initObj=ct.getInit();
		
		CategoryProcess ctp=new CategoryProcess(new Process());
		Object<Process> initObjNew=ctp.getInit();
		filterObjects(initObjNew,initObj,evts);
		return ctp;
	}
	
	
	private void filterObjects(Object<Process> ObjNew, Object<Process> Obj, EventSet evts)
	{
		Process proc=Obj.getData();
		
		Process proctmp=new Process();
		proctmp.setAlphabet(evts);		
	
		proctmp.setFailures(proc.getFailures());
		//for(Iterator it=Proc.getFailures().iterator();it.hasNext();)
		//{
		//	Failure tmpf=(Failure)it.next();
		//	Proctmp.getFailures().add(e)
			//Proctmp.getFailures().add(Utilities.filterFailure(tmpf, evts));
		//}	
		ObjNew.setData(proctmp);
		ObjNew.setTrace(Utilities.filterTrace(Obj.getTrace(),evts));
	
		for(Iterator<Object<Process>> it=Obj.getChildren().iterator(); it.hasNext();)
		{
			Object<Process> tmpObj=new Object<Process>(new Process());
			ObjNew.addChild(tmpObj);
			filterObjects(tmpObj,it.next(),evts);
		}
	}
	
	public static void main(String args[])
	{
		TransitionSystem ts1=new TransitionSystem();
		ts1.add(new Transition(0,"coin",1));
		ts1.add(new Transition(1,"pepsi",2));
		ts1.add(new Transition(1,"coke",3));
		ts1.add(new Transition(1,"pushbutton",4));
		ts1.add(new Transition(4,"select",5));
		ts1.add(new Transition(5,"tea",6));
		ts1.add(new Transition(6,"ok",7));
		
		
		Process vmi1=new Process(ts1);
		CategoryProcess cvmi1=new CategoryProcess(vmi1);
		//Utilities.printCategory(cvmi1.getInit(), 0);
		
		System.out.println("-------------------------------");
		
		EventSet evtsNew=new EventSet();
		evtsNew.add("coin");
		evtsNew.add("pepsi");
		evtsNew.add("coke");
		evtsNew.add("tea");
		
		CategoryWithHidingEvents cth=new CategoryWithHidingEvents(cvmi1,evtsNew);
		Utilities.printCategory(cth.getInit(), 0);
	}
}
