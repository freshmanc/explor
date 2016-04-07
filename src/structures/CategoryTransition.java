package structures;

import java.util.Iterator;

import operation.LoopProcesses;
import utilities.DotLoader;
import utilities.Utilities;


public class CategoryTransition {
	ObjectState init;

	public ObjectState getInit() {
		return init;
	}

	public void setInit(ObjectState init) {
		this.init = init;
	}
	
	CategoryTransition()
	{
		this.init=new ObjectState();
	}
	
	public CategoryTransition(TransitionSystem ts)
	{
		this.init=new ObjectState();
		buildInitialState(ts);
		buildNextState(init,ts);
	}
	
	private void buildInitialState(TransitionSystem ts)
	{
		init.setState(Utilities.findInitialState(ts));
		init.setLabelToState(null);
	}
	
	private void buildNextState(ObjectState from, TransitionSystem ts)
	{
		TransitionSystem nextTransitions=Utilities.nextImmediateTransitions(ts, from.getState());
		for(Iterator<Transition> it=nextTransitions.iterator();it.hasNext();)
		{
			Transition t=it.next();
			ObjectState tmpObjState=new ObjectState();
			tmpObjState.setState(t.getTo());
			tmpObjState.setLabelToState(t.getLabel());
			from.addChild(tmpObjState);
			buildNextState(tmpObjState,ts);
		}
	}
	
	public TransitionSystem toTransitionSystem()
	{
		TransitionSystem ts=new TransitionSystem();
		buildTransitionSystem(this.init,ts);
		return ts;
	}
	
	 private void buildTransitionSystem(ObjectState state, TransitionSystem ts)
	{
		for(Iterator<ObjectState> it=state.getChildren().iterator();it.hasNext();)
		{
			ObjectState os=it.next();
			Transition t=new Transition(state.getState(),os.getLabelToState(),os.getState());
			ts.add(t);
			buildTransitionSystem(os,ts);
		}
	}
	
	public static void main(String[] args)
	{
	   	TransitionSystem tsv=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\test-1_VendingMachine.dot");
    	Utilities.printTransitionSystem(tsv);
    	tsv=Utilities.acyclicTransitionSystem(tsv);
    	System.out.println();
    	CategoryTransition ctv=new CategoryTransition(tsv);
    	//Utilities.printCategoryTransition(ctv);
    	//System.out.println();
    	//Utilities.printTransitionSystem(ctv.toTransitionSystem());
    	EventSet evts=new EventSet();
    	evts.add("#");
    	Utilities.filterCategoryTransition(evts, ctv);
    	//Utilities.printCategoryTransition(ctv);
    	Utilities.printTransitionSystem(ctv.toTransitionSystem());
    	Process p=new Process(ctv.toTransitionSystem());
    	System.out.println();
    	Utilities.printProcess(p);
    	System.out.println();
    	p=new LoopProcesses(p,2);
    	Utilities.printProcess(p);
    	System.out.println();
	}
}
