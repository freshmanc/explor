package test;

import operation.Communication;
import operation.LoopProcesses;
import persistence.ProcessCategoryXML;
import structures.CategoryProcess;
import structures.CategoryTransition;
import structures.ConcreteCategoryProcess;
import structures.ConcreteCategoryProcesses;
import structures.EventSet;
import structures.FunctorFailures;
import structures.Process;
import structures.Transition;
import structures.TransitionSystem;
import utilities.DotLoader;
import utilities.Utilities;

public class TestServicesInThesis {
    public static void main(String[] args)
    {	
    	//match-4.tex 3 services
    	//match-4_Client.dot 3 services
    	//match-4_Server.dot 3 services
    	
    	//match-4-s2.tex 1 service
    	//match-4-s2_Client.dot 1 service
    	//match-4-s2_Server.dot 1 service
    	
    	//match-4-s3.tex 2 service
    	//match-4-s3_Client.dot 2 service
    	//match-4-s3_Server.dot 2 service
    	
    	
    	//scenario 1 with 3 services
    	TransitionSystem clientA=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\match-4_Client.dot");
//    	Utilities.printTransitionSystem(clientA);
//    	System.out.println();
    	clientA=Utilities.acyclicTransitionSystem(clientA);
//    	Utilities.printTransitionSystem(clientA);
//    	System.out.println();
    	EventSet filterEvents=new EventSet();
    	filterEvents.add("#");
    	clientA=Utilities.filterTransitionSystem(filterEvents, clientA);
//    	Utilities.printTransitionSystem(clientA);
    	Process pClientA=new Process(clientA);
//    	System.out.println();
    	//Utilities.printProcess(pClientA);
    	//System.out.println();
    	
    	
    	TransitionSystem serverA=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\match-4_Server.dot");
//    	Utilities.printTransitionSystem(serverA);
//    	System.out.println();
    	serverA=Utilities.acyclicTransitionSystem(serverA);
//    	Utilities.printTransitionSystem(serverA);
//    	System.out.println();
    	serverA=Utilities.filterTransitionSystem(filterEvents, serverA);
//    	Utilities.printTransitionSystem(serverA);
    	Process pServerA=new Process(serverA);
    	//pServerA = new LoopProcesses(pServerA,2);
//    	System.out.println();
    	//Utilities.printProcess(pServerA);
    	//System.out.println();    	
    	
    	
    	
    	//scenario 2 with 1 service
    	TransitionSystem clientB=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\match-4-s2_Client.dot");
//    	Utilities.printTransitionSystem(clientB);
//    	System.out.println();
    	clientB=Utilities.acyclicTransitionSystem(clientB);
//    	Utilities.printTransitionSystem(clientB);
//    	System.out.println();
    	clientB=Utilities.filterTransitionSystem(filterEvents, clientB);
//    	Utilities.printTransitionSystem(clientB);
    	Process pClientB=new Process(clientB);
//    	System.out.println();
    	//Utilities.printProcess(pClientB);
    	//System.out.println();
    	
    	TransitionSystem serverB=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\match-4-s2_Server.dot");
//    	Utilities.printTransitionSystem(serverA);
//    	System.out.println();
    	serverB=Utilities.acyclicTransitionSystem(serverB);
//    	Utilities.printTransitionSystem(serverA);
//    	System.out.println();
    	serverB=Utilities.filterTransitionSystem(filterEvents, serverB);
//    	Utilities.printTransitionSystem(serverA);
    	Process pServerB=new Process(serverB);
    	//pServerA = new LoopProcesses(pServerA,2);
//    	System.out.println();
    	//Utilities.printProcess(pServerB);
    	//System.out.println();    	    	
    	
    	
    	//scenario 3 with 2 services
    	TransitionSystem clientC=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\match-4-s3_Client.dot");
//    	Utilities.printTransitionSystem(clientB);
//    	System.out.println();
    	clientC=Utilities.acyclicTransitionSystem(clientC);
//    	Utilities.printTransitionSystem(clientB);
//    	System.out.println();
    	clientC=Utilities.filterTransitionSystem(filterEvents, clientC);
//    	Utilities.printTransitionSystem(clientB);
    	Process pClientC=new Process(clientC);
//    	System.out.println();
    	//Utilities.printProcess(pClientB);
    	//System.out.println();
    	
    	TransitionSystem serverC=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\match-4-s3_Server.dot");
//    	Utilities.printTransitionSystem(serverA);
//    	System.out.println();
    	serverC=Utilities.acyclicTransitionSystem(serverC);
//    	Utilities.printTransitionSystem(serverA);
//    	System.out.println();
    	serverB=Utilities.filterTransitionSystem(filterEvents, serverC);
//    	Utilities.printTransitionSystem(serverA);
    	Process pServerC=new Process(serverC);
    	//pServerA = new LoopProcesses(pServerA,2);
//    	System.out.println();
    	//Utilities.printProcess(pServerB);
    	//System.out.println();    	

//    	

    	//3 services
    	Process scenario1=new Communication(pServerA,pClientA);
    	Utilities.printProcess(scenario1);
    	System.out.println();
    	
    	
    	//1 service
    	Process scenario2=new Communication(pServerB,pClientB);
    	Utilities.printProcess(scenario2);
    	System.out.println();
    	
    	
    	//2 services
    	Process scenario3=new Communication(pServerC,pClientC);
    	Utilities.printProcess(scenario3);
    	System.out.println();
    	
    	//construct categories
		CategoryProcess cscenario1=new CategoryProcess(scenario1);
		CategoryProcess cscenario2=new CategoryProcess(scenario2);
		CategoryProcess cscenario3=new CategoryProcess(scenario3);
		CategoryProcess cdesign=new CategoryProcess(scenario3);
	
		//construct functor for scenario 1
		FunctorFailures cf1=new FunctorFailures();
		boolean result1=cf1.compareCategories(cdesign, cscenario1);
		System.out.println("scenario 1 matches design? "+result1);		
		
		//construct functor for scenario 2
		FunctorFailures cf2=new FunctorFailures();
		boolean result2=cf2.compareCategories(cdesign, cscenario2);
		System.out.println("scenario 2 matches design? "+result2);	
		
		//construct functor for scenario 3
		FunctorFailures cf3=new FunctorFailures();
		boolean result3=cf3.compareCategories(cdesign, cscenario3);
		System.out.println("scenario 3 matches design? "+result3);	
		
		//construct categories for graphical representation
		ConcreteCategoryProcess scenario=new ConcreteCategoryProcess(cscenario1,"scenario","implementation");
		ConcreteCategoryProcess design=new ConcreteCategoryProcess(cdesign,"original","design");
		ConcreteCategoryProcesses cps=new ConcreteCategoryProcesses();
		cps.add(scenario);
		cps.add(design);
		
		
		//construct a functor for graphical representation
		ProcessCategoryXML pfxml=new ProcessCategoryXML(cps,cf1,result1);
    }
}
