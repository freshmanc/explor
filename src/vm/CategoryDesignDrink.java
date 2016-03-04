package vm;

import java.util.Iterator;

import structures.Category;
import structures.Failure;
import structures.Object;
import structures.Process;
import structures.Trace;
import structures.Transition;
import structures.TransitionSystem;
import utilities.Utilities;

public class CategoryDesignDrink extends Category{
	Drink drink;


	public CategoryDesignDrink(String dk)
	{
		drink=new Drink();
		drink.add("coke");
		drink.add("pepsi");
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition(0,"coin",1));
		int nextst=2;
		for(Iterator it=drink.iterator();it.hasNext();)
		{
			ts2.add(new Transition(1,(String)it.next(),nextst++));
		}

		
		Process vmi2=new Process(ts2);
		
		
		
		
		Process initp=new Process();
		initp.setAlphabet(vmi2.getAlphabet());
		//System.out.println(initp.getAlphabet());
		Trace initTrace=new Trace();
		initp.getFailures().add(Utilities.searchFailureByTrace(initTrace, vmi2));
		//System.out.println(((Failure)(initp.getFailures().iterator().next())).getRefusal());
		init=new Object<Process>(initp);
		
		
		Process coinp=new Process();
		coinp.setAlphabet(vmi2.getAlphabet());
		coinp.setFailures(initp.getFailures());
		Trace coinTrace=new Trace();
		coinTrace.add("coin");
		coinp.getFailures().add(Utilities.searchFailureByTrace(coinTrace, vmi2));
		Object<Process> coinObj=new Object<Process>(coinp);
		init.addChild(coinObj);
		
		Process dinkp=new Process();
		dinkp.setAlphabet(vmi2.getAlphabet());
		dinkp.setFailures(coinp.getFailures());

		
		Trace drinkTrace=new Trace();
		drinkTrace.add("coin");
		drinkTrace.add(dk);
		System.out.println(drinkTrace);
		
		
		
		dinkp.getFailures().add(Utilities.searchFailureByTrace(drinkTrace, vmi2));
		Object<Process> dinkObj=new Object<Process>(dinkp);
		coinObj.addChild(dinkObj); 
	}
	
	public static void main(String args[])
	{
		CategoryDesignDrink vm=new CategoryDesignDrink("coke");
		Utilities.printCategory(vm.getInit(), 0);
	}
}
