//developer ming zhu
package structures;

import java.util.*;

import utilities.Utilities;
import vm.CategoryDesignVM;
import vm.CategoryImplementVM;


public class FunctorFailures extends HashMap<Trace, Trace>{ //save two categories and a functor between them to an xml file

	
	private boolean compareObjects(Object<Process> dsgObj,Object<Process> impObj)
	{
		Process dsgData=dsgObj.getData();
		Process impData=impObj.getData();
		boolean flag;
		
		if(Utilities.subSet(dsgData.getAlphabet(), impData.getAlphabet())!=true)
		{
			//System.out.println("alphabet");
			return false;
		}
			for(Iterator<Failure> dsgit=dsgData.getFailures().iterator(); dsgit.hasNext();)
			{
				Failure dsgtmpf=(Failure)dsgit.next();
				flag=false;
				for(Iterator<Failure> impit=impData.getFailures().iterator();impit.hasNext();)
				{
					Failure imptmpf=(Failure)impit.next();
					if(Utilities.compTrace(dsgtmpf.getTrace(),imptmpf.getTrace()))
					{
						//System.out.println(dsgtmpf.getTrace() +"    "+imptmpf.getTrace());
						Refusal impRefusal=Utilities.filterRefursal(imptmpf.getRefusal(), dsgData.getAlphabet());
						//System.out.println(dsgtmpf.getRefusal().toString()+"     "+impRefusal);
						if(Utilities.subSet(impRefusal,dsgtmpf.getRefusal()))
						{
							this.put(dsgObj.getTrace(), impObj.getTrace());
							//System.out.println("same failure");
							flag= true;
							break;
						}
					}
				}
				if(flag==false)
				{
					return false;
				}
			}
			return true;
	}
	
	private boolean compareSubObjects(Object<Process> dsgObj,Object<Process> impObj)
	{
		List<Object<Process>> dsgChildren=dsgObj.getChildren();
		List<Object<Process>> impChildren=impObj.getChildren();
		boolean flag;
		Object<Process> dsgChd;
		Object<Process> impChd;
			for(Iterator<Object<Process>> dsgChdIt=dsgChildren.iterator();dsgChdIt.hasNext();)
			{
				dsgChd=dsgChdIt.next();
				flag=false;
				for(Iterator<Object<Process>> impChdIt=impChildren.iterator();impChdIt.hasNext();)
				{
					impChd=impChdIt.next();
					if(compareObjects(dsgChd, impChd))
					{
						flag=true;
						if(dsgChd.getChildren().size()!=0)
							flag=compareSubObjects(dsgChd,impChd);
						break;
					}	
				}
				if(flag==false)
				{
					return false;
				}
			}
		return true;
	}
	
	public  boolean compareCategories(Category dsgVM, Category impVM)
	{
		Object<Process> dsgInit=dsgVM.getInit();
		Object<Process> impInit=impVM.getInit();
		
		if(compareObjects(dsgInit, impInit))
		{
			
			if(compareSubObjects(dsgInit, impInit))
			{
				return true;
			}
		}
			
		return false;
	}
	
	public static void main(String args[])
	{
		TransitionSystem ts1=new TransitionSystem();
		ts1.add(new Transition("0","coin","1"));
		ts1.add(new Transition("1","pepsi","2"));
		ts1.add(new Transition("1","coke","3"));
		ts1.add(new Transition("1","tea","4"));
		ts1.add(new Transition("4","tea","6"));
		
		Process vmi1=new Process(ts1);
		
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition("0","coin","1"));
		ts2.add(new Transition("1","pepsi","2"));
		ts2.add(new Transition("1","coke","3"));
		  ts2.add(new Transition(1,"tea",4));
		
		Process vmi2=new Process(ts2);
		CategoryProcess cvmi1=new CategoryProcess(vmi1);
		//Utilities.printCategory(cvmi1.getInit(), 0);
		CategoryProcess cvmi2=new CategoryProcess(vmi2);
		
		FunctorFailures cf=new FunctorFailures();
		System.out.println(cf.compareCategories(cvmi2, cvmi1));
		for(Iterator<Map.Entry<Trace,Trace>> it=cf.entrySet().iterator();it.hasNext();)
		{
			Map.Entry<Trace,Trace> et=it.next();
			System.out.println("key "+et.getKey());
			System.out.println("value "+et.getValue());
		}
	}
}
