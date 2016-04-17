package test;

import operation.Communication;
import operation.LoopProcesses;
import structures.CategoryTransition;
import structures.EventSet;
import structures.Process;
import structures.Transition;
import structures.TransitionSystem;
import utilities.DotLoader;
import utilities.Utilities;

public class TestDotLoaderAndFilterTransition {
    public static void main(String[] args)
    {		
    	
    	TransitionSystem tsOddie=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\match-4_Oddie.dot");
//    	Utilities.printTransitionSystem(tsOddie);
//    	System.out.println();
    	tsOddie=Utilities.acyclicTransitionSystem(tsOddie);
//    	Utilities.printTransitionSystem(tsOddie);
//    	System.out.println();
    	EventSet filterEvents=new EventSet();
    	filterEvents.add("#");
    	tsOddie=Utilities.filterTransitionSystem(filterEvents, tsOddie);
//    	Utilities.printTransitionSystem(tsOddie);
    	Process pOddie=new Process(tsOddie);
//    	System.out.println();
    	Utilities.printProcess(pOddie);
    	System.out.println();
    	
    	TransitionSystem tsSteven=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\match-4_Steven.dot");
//    	Utilities.printTransitionSystem(tsSteven);
//    	System.out.println();
    	tsSteven=Utilities.acyclicTransitionSystem(tsSteven);
//    	Utilities.printTransitionSystem(tsSteven);
//    	System.out.println();
    	tsSteven=Utilities.filterTransitionSystem(filterEvents, tsSteven);
//    	Utilities.printTransitionSystem(tsSteven);
    	Process pSteven=new Process(tsSteven);
//    	System.out.println();
    	Utilities.printProcess(pSteven);
    	System.out.println();
    	
    	
    	TransitionSystem tsServer=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\match-4_Server.dot");
//    	Utilities.printTransitionSystem(tsServer);
//    	System.out.println();
    	tsServer=Utilities.acyclicTransitionSystem(tsServer);
//    	Utilities.printTransitionSystem(tsServer);
//    	System.out.println();
    	tsServer=Utilities.filterTransitionSystem(filterEvents, tsServer);
//    	Utilities.printTransitionSystem(tsServer);
    	Process pServer=new Process(tsServer);
    	//pServer = new LoopProcesses(pServer,2);
//    	System.out.println();
    	Utilities.printProcess(pServer);
    	System.out.println();
//    	
    	Process ServerSteven=new Communication(pServer,pSteven);
    	Utilities.printProcess(ServerSteven);
    	System.out.println();
    	Process ServerOddie=new Communication(pServer,pOddie);
    	Utilities.printProcess(ServerOddie);
    }
}
