package test;

import operation.Communication;
import operation.DeterministicChoice;
import operation.NondeterministicChoice;
import operation.SequentialProcesses;
import persistence.FilterProcessCategoryXML;
import persistence.ProcessCategoryXML;
import persistence.ProcessXML;
import structures.CategoryProcess;
import structures.ConcreteCategoryProcess;
import structures.ConcreteCategoryProcesses;
import structures.ConcreteProcess;
import structures.ConcreteProcesses;
import structures.EquivalentFailureTree;
import structures.EventSet;
import structures.FailureTree;
import structures.FunctorFailures;
import structures.Process;
import structures.Transition;
import structures.TransitionSystem;
import utilities.DotLoader;
import utilities.Utilities;
import persistence.ProcessCategoryXML;

public class TestOperations {

	Process coin;
	Process coke;
	Process pepsi;
	Process tea; 
	Process refund;
	Process coffee;
	Process mocha;
	Process columbia;
	Process green;
	Process red;
	
	public static void testClientServer()
	{
		TransitionSystem serviceA=new TransitionSystem();
		serviceA.add(new Transition(1,"requestA",2));
		serviceA.add(new Transition(2,"infoA",3));
		serviceA.add(new Transition(3,"resultA",4));
		Process pA=new Process(serviceA);
		
		TransitionSystem serviceB=new TransitionSystem();
		serviceB.add(new Transition(1,"requestB",2));
		serviceB.add(new Transition(2,"infoB",3));
		serviceB.add(new Transition(3,"resultB",4));
		Process pB=new Process(serviceB);
		
		TransitionSystem serviceC=new TransitionSystem();
		serviceC.add(new Transition(1,"requestC",2));
		serviceC.add(new Transition(2,"infoC",3));
		serviceC.add(new Transition(3,"resultC",4));
		Process pC=new Process(serviceC);
		
		Process serverDesign=new DeterministicChoice(pA,pB);
		Process serverScenario1=new DeterministicChoice(serverDesign,pC);
		Process ServerScenario2=new Process(serviceA);
		
		Process clientADesign=new NondeterministicChoice(pA,pB);
		Process clientAScenario1=new NondeterministicChoice(pA,pC);
		
		Process clientBDesign=new Process(serviceB);
		Process clientBScenario1=new Process(serviceB);
		
		Process clientADCserverD=new Communication(clientADesign,serverDesign);
		Utilities.printProcess(clientADCserverD);
		System.out.println();
		Process clientBDCserverD=new Communication(clientBDesign,serverDesign);
//		Utilities.printProcess(clientBDCserverD);
//		System.out.println();
		
		
    	TransitionSystem tsOddie=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\match-4_ClientB.dot");
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
//    	Utilities.printProcess(pOddie);
//    	System.out.println();
    	
    	TransitionSystem tsSteven=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\match-4-s3_Client.dot");
//    	Utilities.printTransitionSystem(tsSteven);
//    	System.out.println();
    	tsSteven=Utilities.acyclicTransitionSystem(tsSteven);
//    	Utilities.printTransitionSystem(tsSteven);
//    	System.out.println();
    	tsSteven=Utilities.filterTransitionSystem(filterEvents, tsSteven);
//    	Utilities.printTransitionSystem(tsSteven);
    	Process pSteven=new Process(tsSteven);
//    	System.out.println();
//    	Utilities.printProcess(pSteven);
//    	System.out.println();
    	
    	
    	TransitionSystem tsServer=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\match-4-s3_Server.dot");
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
//    	Utilities.printProcess(pServer);
//    	System.out.println();
//    	
    	Process ServerSteven=new Communication(pServer,pSteven);
    	Utilities.printProcess(ServerSteven);
    	System.out.println();
    	Process ServerOddie=new Communication(pServer,pOddie);
//    	Utilities.printProcess(ServerOddie);
//    	System.out.println();
    	
//    	FailureTree impFt=new FailureTree(ServerSteven);
////    	Utilities.printFailureTree(impFt);
////    	System.out.println();
//    	
//		//events we want to analyze
//		EventSet evts=new EventSet();
//		evts.add("requestA");
//		evts.add("requestB");
//		evts.add("infoA");
//		evts.add("infoB");
//		evts.add("resultA");
//		evts.add("resultB");
//
//		//failure tree after filtering
//		EquivalentFailureTree impEft=new EquivalentFailureTree(impFt,evts);
//		
//    	
//		CategoryProcess filteredCategory=new CategoryProcess(impEft.treeToProcess());
		CategoryProcess origCategory=new CategoryProcess(ServerSteven);
//		
//		//generate concrete categories with more info for xml
//		ConcreteCategoryProcess filterCcp=new ConcreteCategoryProcess(filteredCategory,"filteredCategory","design");
//		ConcreteCategoryProcess origCcp=new ConcreteCategoryProcess(origCategory,"origCategory","implementation");
//		
//		//add concrete categories into a set
//		ConcreteCategoryProcesses cps=new ConcreteCategoryProcesses();
//		cps.add(filterCcp);
//		cps.add(origCcp);
//		
//		//generate xml file for filtering
//		FilterProcessCategoryXML fpc=new FilterProcessCategoryXML(cps,impEft);
		
		
		//functor from design to implement
		CategoryProcess dsgCp=new CategoryProcess(clientADCserverD);
		
		//generate concrete categories
		ConcreteCategoryProcess impCcp=new ConcreteCategoryProcess(origCategory,"filteredCategory","abstract of implementation");
		ConcreteCategoryProcess dsgCcp=new ConcreteCategoryProcess(dsgCp,"designedCategory","design");
		
		//add concrete categories into a set
		ConcreteCategoryProcesses cpfs=new ConcreteCategoryProcesses();
		cpfs.add(impCcp);
		cpfs.add(dsgCcp);
		
		//generate functor for comparing implementation and design
		FunctorFailures cf=new FunctorFailures();
		System.out.println(cf.compareCategories(dsgCp, origCategory));
		
		//generate xml file for functor
		ProcessCategoryXML pfxml=new ProcessCategoryXML(cpfs,cf,cf.compareCategories(dsgCp, origCategory));
    
		ConcreteProcess cp1=new ConcreteProcess(ServerSteven,"vmi1","design");
		ConcreteProcess cp2=new ConcreteProcess(clientADCserverD,"vmi2","design");
		ConcreteProcesses cps=new ConcreteProcesses();
		cps.add(cp1);
		cps.add(cp2);
		
		ProcessXML pxml=new ProcessXML(cps);
	}
	
	public static void main(String[] args)
	{
		testClientServer();
		//TestOperations tester=new TestOperations();
		//tester.testSeqDeterminNonDetermin();
		//tester.testSeqDeterminDetermin();
		//tester.testSeqNonDeterminNonDetermin();
		//tester.testSeqNonDeterminDetermin();
		//tester.testNonDeterminSeqNonDeterminDetermin();
	}
	
	public void testNonDeterminSeqNonDeterminDetermin()
	{
		Process drinks=new DeterministicChoice(coke, pepsi);
		Process teaTypes=new DeterministicChoice(red,green);
		Process coffeeTypes=new DeterministicChoice(mocha,columbia);
		tea=new SequentialProcesses(tea,teaTypes);
		coffee=new SequentialProcesses(coffee,coffeeTypes);
		Process teaCoffee=new NondeterministicChoice(tea,coffee);

		drinks=new NondeterministicChoice(drinks,refund);
		drinks=new SequentialProcesses(drinks,coin);
		drinks=new SequentialProcesses(drinks,teaCoffee);
		Utilities.printProcess(drinks);
	}
	
	public void testSeqNonDeterminDetermin()
	{
		Process drinks=new DeterministicChoice(coke, pepsi);
		Process teaTypes=new DeterministicChoice(red,green);
		Process coffeeTypes=new DeterministicChoice(mocha,columbia);
		tea=new SequentialProcesses(tea,teaTypes);
		coffee=new SequentialProcesses(coffee,coffeeTypes);
		drinks=new NondeterministicChoice(drinks,tea);
		drinks=new NondeterministicChoice(coffee,drinks);
		drinks=new NondeterministicChoice(drinks,refund);
		drinks=new SequentialProcesses(coin,drinks);
		Utilities.printProcess(drinks);
	}
	
	public void testSeqNonDeterminNonDetermin()
	{
		Process drinks=new DeterministicChoice(coke, pepsi);
		Process teaTypes=new NondeterministicChoice(red,green);
		Process coffeeTypes=new NondeterministicChoice(mocha,columbia);
		tea=new SequentialProcesses(tea,teaTypes);
		coffee=new SequentialProcesses(coffee,coffeeTypes);
		drinks=new NondeterministicChoice(drinks,tea);
		drinks=new NondeterministicChoice(coffee,drinks);
		drinks=new NondeterministicChoice(drinks,refund);
		drinks=new SequentialProcesses(coin,drinks);
		Utilities.printProcess(drinks);
	}
	
	public void testSeqDeterminNonDetermin()
	{
		Process drinks=new DeterministicChoice(coke, pepsi);
		Process teaTypes=new NondeterministicChoice(red,green);
		Process coffeeTypes=new NondeterministicChoice(mocha,columbia);
		tea=new SequentialProcesses(tea,teaTypes);
		coffee=new SequentialProcesses(coffee,coffeeTypes);
		drinks=new DeterministicChoice(drinks,tea);
		drinks=new DeterministicChoice(coffee,drinks);
		drinks=new DeterministicChoice(drinks,refund);
		drinks=new SequentialProcesses(coin,drinks);
		Utilities.printProcess(drinks);
	}
	
	public void testSeqDeterminDetermin()
	{
		Process drinks=new DeterministicChoice(coke, pepsi);
		Process teaTypes=new DeterministicChoice(red,green);
		Process coffeeTypes=new DeterministicChoice(mocha,columbia);
		tea=new SequentialProcesses(tea,teaTypes);
		coffee=new SequentialProcesses(coffee,coffeeTypes);
		drinks=new DeterministicChoice(drinks,tea);
		drinks=new DeterministicChoice(coffee,drinks);
		drinks=new DeterministicChoice(drinks,refund);
		drinks=new SequentialProcesses(coin,drinks);
		Utilities.printProcess(drinks);
	}
	
	public TestOperations()
	{
		 TransitionSystem coinT=new TransitionSystem();
		 coinT.add(new Transition(1,"coin",2));
		 this.coin=new Process(coinT);
		
		 TransitionSystem cokeT=new TransitionSystem();
		 cokeT.add(new Transition(1,"coke",2));
		 this.coke=new Process(cokeT);
		 
		 TransitionSystem pepsiT=new TransitionSystem();
		 pepsiT.add(new Transition(1,"pepsi",2));
		 this.pepsi=new Process(pepsiT);
		 
		 TransitionSystem teaT=new TransitionSystem();
		 teaT.add(new Transition(1,"tea",2));
		 this.tea=new Process(teaT);
		 
		 TransitionSystem refundT=new TransitionSystem();
		 refundT.add(new Transition(1,"refund",2));
		 this.refund=new Process(refundT);
		 
		 TransitionSystem coffeeT=new TransitionSystem();
		 coffeeT.add(new Transition(1,"coffee",2));
		 this.coffee=new Process(coffeeT);
		 
		 TransitionSystem mochaT=new TransitionSystem();
		 mochaT.add(new Transition(1,"mocha",2));
		 this.mocha=new Process(mochaT);
		 
		 TransitionSystem columbiaT=new TransitionSystem();
		 columbiaT.add(new Transition(1,"columbia",2));
		 this.columbia=new Process(columbiaT);
		 
		 TransitionSystem greenT=new TransitionSystem();
		 greenT.add(new Transition(1,"green",2));
		 this.green=new Process(greenT);
		 
		 TransitionSystem redT=new TransitionSystem();
		 redT.add(new Transition(1,"red",2));
		 this.red=new Process(redT);
	}
}
