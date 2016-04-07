package test;

import persistence.*;
import structures.*;
import utilities.*;
import operation.*;
import structures.Process;


public class TestFunctorFailures {
	public static void main(String args[])
	{
		TestFunctorFailures.testSameEventsDeterministicDesignSameImp();
		TestFunctorFailures.testSameEventsNonDeterministicDesignSameImp();
		TestFunctorFailures.testSameEventsDeterministicDesignMore();
		TestFunctorFailures.testSameEventsDeterministicImpMore();
		TestFunctorFailures.testSameEventsNonDeterministicDesignMore();
		TestFunctorFailures.testSameEventsNonDeterministicImpMore();
	}
	
	public static void testSameEventsDeterministicDesignSameImp()
	{
		TransitionSystem ts1=new TransitionSystem(); //implement
		ts1.add(new Transition("0","coin","1"));
		ts1.add(new Transition("1","pepsi'","2"));
		ts1.add(new Transition("1","coke'","3"));
		ts1.add(new Transition("1","tea'","4"));
		Process vmi1=new Process(ts1);
		//Utilities.printProcess(vmi1);
		//System.out.println();
		
		TransitionSystem ts2=new TransitionSystem();//design
		ts2.add(new Transition("0","coin","1"));
		ts2.add(new Transition("1","pepsi'","2"));
		ts2.add(new Transition("1","coke'","3"));
		ts2.add(new Transition("1","tea'","4"));
		Process vmi2=new Process(ts2);
		//Utilities.printProcess(vmi2);
		//System.out.println();
		
		CategoryProcess cvm1=new CategoryProcess(vmi1);
		CategoryProcess cvm2=new CategoryProcess(vmi2);
		
		FunctorFailures cf=new FunctorFailures();
		System.out.println("same events deterministic design same as implementation: "+cf.compareCategories(cvm2, cvm1));
	}
	
	public static void testSameEventsNonDeterministicDesignSameImp()
	{
		TransitionSystem ts1=new TransitionSystem();//implement
		ts1.add(new Transition("0","coin","1"));
		ts1.add(new Transition("1","pepsi","2"));
		ts1.add(new Transition("1","coke","3"));
		ts1.add(new Transition("1","tea","4"));
		Process vmi1=new Process(ts1);
		//Utilities.printProcess(vmi1);
		//System.out.println();
		
		TransitionSystem ts2=new TransitionSystem();//design
		ts2.add(new Transition("0","coin","1"));
		ts2.add(new Transition("1","pepsi","2"));
		ts2.add(new Transition("1","coke","3"));
		ts2.add(new Transition("1","tea","4"));
		Process vmi2=new Process(ts2);
		//Utilities.printProcess(vmi2);
		//System.out.println();
		
		CategoryProcess cvm1=new CategoryProcess(vmi1);
		CategoryProcess cvm2=new CategoryProcess(vmi2);
		
		FunctorFailures cf=new FunctorFailures();
		System.out.println("same events nondeterministic design same as implementation: "+cf.compareCategories(cvm2, cvm1));
	}
	
	public static void testSameEventsNonDeterministicImpMore()
	{
		TransitionSystem ts1=new TransitionSystem();//implement
		ts1.add(new Transition("0","coin","1"));
		ts1.add(new Transition("1","pepsi","2"));
		ts1.add(new Transition("1","coke","3"));
		ts1.add(new Transition("1","tea","4"));
		ts1.add(new Transition("1","coin","5"));
		Process vmi1=new Process(ts1);
		//Utilities.printProcess(vmi1);
		//System.out.println();
		
		TransitionSystem ts2=new TransitionSystem();//design
		ts2.add(new Transition("0","coin","1"));
		ts2.add(new Transition("1","pepsi","2"));
		ts2.add(new Transition("1","coke","3"));
		ts2.add(new Transition("1","tea","4"));
		
		Process vmi2=new Process(ts2);
		//Utilities.printProcess(vmi2);
		//System.out.println();
		
		CategoryProcess cvm1=new CategoryProcess(vmi1);
		CategoryProcess cvm2=new CategoryProcess(vmi2);
		
		FunctorFailures cf=new FunctorFailures();
		System.out.println("same events nondeterministic implementation more than design: "+cf.compareCategories(cvm2, cvm1));		
	}
	
	public static void testSameEventsNonDeterministicDesignMore()
	{
		TransitionSystem ts1=new TransitionSystem();//implement
		ts1.add(new Transition("0","coin","1"));
		ts1.add(new Transition("1","pepsi","2"));
		ts1.add(new Transition("1","coke","3"));
		ts1.add(new Transition("1","tea","4"));
		Process vmi1=new Process(ts1);
//		Utilities.printProcess(vmi1);
//		System.out.println();
		
		TransitionSystem ts2=new TransitionSystem();//design
		ts2.add(new Transition("0","coin","1"));
		ts2.add(new Transition("1","pepsi","2"));
		ts2.add(new Transition("1","coke","3"));
		ts2.add(new Transition("1","tea","4"));
		ts2.add(new Transition("1","coin","5"));
		Process vmi2=new Process(ts2);
//		Utilities.printProcess(vmi2);
//		System.out.println();
		
		CategoryProcess cvm1=new CategoryProcess(vmi1);
		CategoryProcess cvm2=new CategoryProcess(vmi2);
		
		FunctorFailures cf=new FunctorFailures();
		System.out.println("same events nondeterministic design more than implementation: "+cf.compareCategories(cvm2, cvm1));		
	}
	
	public static void testSameEventsDeterministicImpMore()
	{
		TransitionSystem ts1=new TransitionSystem();//implement
		ts1.add(new Transition("0","coin","1"));
		ts1.add(new Transition("1","pepsi'","2"));
		ts1.add(new Transition("1","coke'","3"));
		ts1.add(new Transition("1","tea'","4"));
		ts1.add(new Transition("1","coin'","5"));
		Process vmi1=new Process(ts1);
//		Utilities.printProcess(vmi1);
//		System.out.println();
		
		TransitionSystem ts2=new TransitionSystem();//design
		ts2.add(new Transition("0","coin","1"));
		ts2.add(new Transition("1","pepsi'","2"));
		ts2.add(new Transition("1","coke'","3"));
		ts2.add(new Transition("1","tea'","4"));
		Process vmi2=new Process(ts2);
//		Utilities.printProcess(vmi2);
//		System.out.println();
		
		CategoryProcess cvm1=new CategoryProcess(vmi1);
		CategoryProcess cvm2=new CategoryProcess(vmi2);
		
		FunctorFailures cf=new FunctorFailures();
		System.out.println("same events deterministic implementation more than design: "+cf.compareCategories(cvm2, cvm1));		
	}
	
	public static void testSameEventsDeterministicDesignMore()
	{
		TransitionSystem ts1=new TransitionSystem();//implement
		ts1.add(new Transition("0","coin","1"));
		ts1.add(new Transition("1","pepsi'","2"));
		ts1.add(new Transition("1","coke'","3"));
		ts1.add(new Transition("1","tea'","4"));	
		Process vmi1=new Process(ts1);
//		Utilities.printProcess(vmi1);
//		System.out.println();
		
		TransitionSystem ts2=new TransitionSystem();//design
		ts2.add(new Transition("0","coin","1"));
		ts2.add(new Transition("1","pepsi'","2"));
		ts2.add(new Transition("1","coke'","3"));
		ts2.add(new Transition("1","tea'","4"));
		ts2.add(new Transition("1","coin'","5"));
		Process vmi2=new Process(ts2);
//		Utilities.printProcess(vmi2);
//		System.out.println();
		
		CategoryProcess cvm1=new CategoryProcess(vmi1);
		CategoryProcess cvm2=new CategoryProcess(vmi2);
		
		FunctorFailures cf=new FunctorFailures();
		System.out.println("same events deterministic design more than implementation: "+cf.compareCategories(cvm2, cvm1));		
	}
	

	
}
