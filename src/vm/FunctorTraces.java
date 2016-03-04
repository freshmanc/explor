package vm;

import java.util.Iterator;
import java.util.List;

import structures.Category;
import structures.Failure;
import structures.Object;
import structures.Process;
import structures.Trace;
import structures.Transition;
import structures.TransitionSystem;
import utilities.Utilities;

public class FunctorTraces {
	
	private static int compareObjects(Object<Process> dsgObj,Object<Process> impObj, Drink drink)
	{
		Process dsgData=dsgObj.getData();
		Process impData=impObj.getData();
		boolean sametrace=false;
		boolean samecategory=false;
		
		if(Utilities.subSet(dsgData.getAlphabet(), impData.getAlphabet()))
		{
			for(Iterator dsgit=dsgData.getFailures().iterator(); dsgit.hasNext();)
			{
				Failure dsgtmpf=(Failure)dsgit.next();
				sametrace=false;
				samecategory=false;
				for(Iterator impit=impData.getFailures().iterator();impit.hasNext();)
				{
					Failure imptmpf=(Failure)impit.next();

					Trace dsgtmpt=dsgtmpf.getTrace();
					Trace imptmpt=imptmpf.getTrace();
					//System.out.println("Design"+dsgtmpt);
					//System.out.println("Implement"+imptmpt);
					//System.out.println(Utilities.compTrace(dsgtmpt,dsgtmpt));
					if(Utilities.compTrace(dsgtmpt,imptmpt)==true)
					{
						sametrace= true;
						System.out.println("Design same trace"+dsgtmpt);
						System.out.println("Implement same trace"+imptmpt);
						//System.out.println("------------");
					}
					else if(dsgtmpt.size()==imptmpt.size()&&dsgtmpt.size()>=2)
					{
						//System.out.println(dsgtmpt.size());
						//System.out.println(drink.contains(dsgtmpt.get(1)));
						//System.out.println(drink.contains(imptmpt.get(1)));
						if(drink.contains(dsgtmpt.get(1))&&drink.contains(imptmpt.get(1)))
						{
							String imptmps=imptmpt.set(1, dsgtmpt.get(1));
							if(Utilities.compTrace(dsgtmpt,imptmpt))
							{
								samecategory=true;
								imptmpt.set(1, imptmps);
								System.out.println("Design same category"+dsgtmpt);
								System.out.println("Implement same category"+imptmpt);
							}						
						}
					}
				}
				if(sametrace==false&&samecategory==false)
				{
					return 0;
				}
			}
			System.out.println("------------");
			if(sametrace==true)
				return 1;
			else if(samecategory==true)
				return 2;
		}
		return 0;
	}
	
	private static boolean compareSubObjects(Object<Process> dsgObj,Object<Process> impObj, Drink drink)
	{
		List<Object<Process>> dsgChildren=dsgObj.getChildren();
		List<Object<Process>> impChildren=impObj.getChildren();
		boolean flag;
		
		if(!dsgChildren.isEmpty())
		{
			for(Iterator dsgChdIt=dsgChildren.iterator();dsgChdIt.hasNext();)
			{
				Object<Process> dsgChd=(Object<Process>)dsgChdIt.next();
				flag=false;
				if(!impChildren.isEmpty())
				{
					for(Iterator impChdIt=impChildren.iterator();impChdIt.hasNext();)
					{
						Object<Process> impChd=(Object<Process>)impChdIt.next();
						if(compareObjects(dsgChd, impChd,drink)!=0)
						{
							flag=true;
							return compareSubObjects(dsgChd,impChd,drink);
						}	
					
					}
				}
				if(flag==false)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean compareCategories(Category dsgVM, Category impVM,Drink drink)
	{
		Object<Process> dsgInit=dsgVM.getInit();
		Object<Process> impInit=impVM.getInit();
		
		if(compareObjects(dsgInit, impInit,drink)!=0)
		{
			if(compareSubObjects(dsgInit, impInit,drink))
			{
				return true;
			}
		}
			
		return false;
	}
	
	public static void main(String args[])
	{
		Drink drink;
		drink=new Drink();
		drink.add("coke");
		drink.add("pepsi");
		drink.add("tea");
		
		CategoryDesignDrink dd=new CategoryDesignDrink("coke");
		//Utilities.printCategory(dd.getInit(), 0);
		
		CategoryImplementDrink id=new CategoryImplementDrink("tea");
		//Utilities.printCategory(id.getInit(), 0);
		
		System.out.println(FunctorTraces.compareCategories(dd, id,drink));
	}
}
