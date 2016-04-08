package test;

import operation.Communication;
import operation.LoopProcesses;
import structures.CategoryTransition;
import structures.EventSet;
import structures.Process;
import structures.TransitionSystem;
import utilities.DotLoader;
import utilities.Utilities;

public class TestDotLoaderAndFilterTransition {
    public static void main(String[] args)
    {		
    	TransitionSystem tsv=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\test-1_VendingMachine.dot");
    	//Utilities.printTransitionSystem(ts);
    	tsv=Utilities.acyclicTransitionSystem(tsv);
    	//Utilities.printTransitionSystem(ts);
    	Process pv=new Process(tsv);
    	pv=new LoopProcesses(pv,2);
    	Utilities.printProcess(pv);
    	System.out.println();
    	
    	TransitionSystem tsc=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\vm-4_Customer.dot");
    	//Utilities.printTransitionSystem(ts);
    	tsc=Utilities.acyclicTransitionSystem(tsc);
    	CategoryTransition ct=new CategoryTransition(tsc);
    	EventSet filterEvents=new EventSet();
    	filterEvents.add("#");
    	Utilities.filterCategoryTransition(filterEvents, ct);
    	//Utilities.printTransitionSystem(ts);
    	Process pc=new Process(ct.toTransitionSystem());
    	pc=new LoopProcesses(pc,2);
    	Utilities.printProcess(pc);
    	System.out.println();
    	
    	Process CcV=new Communication(pv,pc);
    	Utilities.printProcess(CcV);
    }
}
