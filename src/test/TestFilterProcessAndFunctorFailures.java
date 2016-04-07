package test;

import persistence.*;
import structures.*;
import utilities.*;
import operation.*;
import structures.Process;

public class TestFilterProcessAndFunctorFailures {
	public static void main(String args[])
	{
		TestFilterProcessAndFunctorFailures.testDiffEventsImpDeterministicDesignSameImp();
		TestFilterProcessAndFunctorFailures.testDiffEventsImpDeterministicImpMore();
		TestFilterProcessAndFunctorFailures.testDiffEventsImpNonDeterministicDesignSameImp();
		
	}
	
	public static void testDiffEventsImpDeterministicDesignSameImp()
	{
		TransitionSystem ts1=new TransitionSystem();//implement
		ts1.add(new Transition("0","coin","1"));
		ts1.add(new Transition("1","pepsi'","2"));
		ts1.add(new Transition("1","coke'","3"));
		ts1.add(new Transition("1","tea'","4"));	
		ts1.add(new Transition("1","coffee'","5"));
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
		
		
		FailureTree impFt=new FailureTree(vmi1);
		EventSet evts=new EventSet();
		evts.add("coin");
		evts.add("tea");
		evts.add("coke");
		evts.add("pepsi");
		EquivalentFailureTree impEft=new EquivalentFailureTree(impFt,evts);
		vmi1=impEft.treeToProcess();
//		Utilities.printProcess(vmi1);
//		System.out.println();
		
		CategoryProcess cvm1=new CategoryProcess(vmi1);
		CategoryProcess cvm2=new CategoryProcess(vmi2);
		
		
		FunctorFailures cf=new FunctorFailures();
		System.out.println("different events in implementation deterministic implementation same as design: "+cf.compareCategories(cvm2, cvm1));		
	}
	
	public static void testDiffEventsImpDeterministicImpMore()
	{
		TransitionSystem ts1=new TransitionSystem();//implement
		ts1.add(new Transition("0","coin","1"));
		ts1.add(new Transition("1","pepsi'","2"));
		ts1.add(new Transition("1","coke'","3"));
		ts1.add(new Transition("1","tea'","4"));
		ts1.add(new Transition("2","tea'","6"));
		ts1.add(new Transition("1","coffee'","5"));
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
		
		
		FailureTree impFt=new FailureTree(vmi1);
		EventSet evts=new EventSet();
		evts.add("coin");
		evts.add("tea");
		evts.add("coke");
		evts.add("pepsi");
		EquivalentFailureTree impEft=new EquivalentFailureTree(impFt,evts);
		vmi1=impEft.treeToProcess();
//		Utilities.printProcess(vmi1);
//		System.out.println();
		
		CategoryProcess cvm1=new CategoryProcess(vmi1);
		CategoryProcess cvm2=new CategoryProcess(vmi2);
		
		
		FunctorFailures cf=new FunctorFailures();
		System.out.println("different events in implementation deterministic implementation more than design: "+cf.compareCategories(cvm2, cvm1));		
	}
	
	public static void testDiffEventsImpNonDeterministicDesignSameImp()
	{
		TransitionSystem ts1=new TransitionSystem();//implement
		ts1.add(new Transition("0","coin","1"));
		ts1.add(new Transition("1","peps'","2"));
		ts1.add(new Transition("1","coke","3"));
		ts1.add(new Transition("1","tea","4"));	
		ts1.add(new Transition("1","coffee","5"));
		Process vmi1=new Process(ts1);
		Utilities.printProcess(vmi1);
		System.out.println();
		
		TransitionSystem ts2=new TransitionSystem();//design
		ts2.add(new Transition("0","coin","1"));
		ts2.add(new Transition("1","pepsi","2"));
		ts2.add(new Transition("1","coke","3"));
		ts2.add(new Transition("1","tea","4"));

		Process vmi2=new Process(ts2);
//		Utilities.printProcess(vmi2);
//		System.out.println();
		
		
		FailureTree impFt=new FailureTree(vmi1);
		EventSet evts=new EventSet();
		evts.add("coin");
		evts.add("tea");
		evts.add("coke");
		evts.add("pepsi");
		EquivalentFailureTree impEft=new EquivalentFailureTree(impFt,evts);
		Utilities.printFailureTree(impEft);
		vmi1=impEft.treeToProcess();
		//Utilities.printProcess(vmi1);
		System.out.println();
		
		CategoryProcess cvm1=new CategoryProcess(vmi1);
		CategoryProcess cvm2=new CategoryProcess(vmi2);
		
		
		FunctorFailures cf=new FunctorFailures();
		System.out.println("different events in implementation nondeterministic  implementation same as design: "+cf.compareCategories(cvm2, cvm1));		
	}
}
