package test;

import operation.Communication;
import operation.DeterministicChoice;
import operation.NondeterministicChoice;
import operation.SequentialProcesses;
import structures.Process;
import structures.Transition;
import structures.TransitionSystem;
import utilities.Utilities;

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
	
	public static void main(String[] args)
	{
		TestOperations tester=new TestOperations();
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
