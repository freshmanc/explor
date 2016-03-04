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

public class CategoryImplementVM extends Category{
	


	public CategoryImplementVM()
	{
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition(0,"coin",1));
		ts2.add(new Transition(1,"pepsi",2));
		ts2.add(new Transition(1,"coke",3));
		ts2.add(new Transition(1,"tea",4));
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
		Trace teaTrace=new Trace();
		teaTrace.add("coin");
		teaTrace.add("tea");
		System.out.println(teaTrace);
		
		Trace cokeTrace=new Trace();
		cokeTrace.add("coin");
		cokeTrace.add("pepsi");
		System.out.println(cokeTrace);
		
		Trace pepsiTrace=new Trace();
		pepsiTrace.add("coin");
		pepsiTrace.add("coke");
		System.out.println(pepsiTrace);
		
		
		dinkp.getFailures().add(Utilities.searchFailureByTrace(teaTrace, vmi2));
		dinkp.getFailures().add(Utilities.searchFailureByTrace(cokeTrace, vmi2));
		dinkp.getFailures().add(Utilities.searchFailureByTrace(pepsiTrace, vmi2));
		Object<Process> dinkObj=new Object<Process>(dinkp);
		coinObj.addChild(dinkObj); 
	}
	

	
	
	public static void main(String args[])
	{
		CategoryImplementVM vm=new CategoryImplementVM();
		Utilities.printCategory(vm.getInit(), 0);
	}
}
