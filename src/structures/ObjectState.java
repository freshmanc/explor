package structures;

import java.util.ArrayList;
import java.util.List;

public class ObjectState {
	
	private String labelToState;
	private String state;
	private List<ObjectState> children;
	
	public String getLabelToState() {
		return labelToState;
	}
	public void setLabelToState(String labelToState) {
		this.labelToState = labelToState;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<ObjectState> getChildren() {
		return children;
	}
	public void setChildren(List<ObjectState> children) {
		this.children = children;
	}
	
	ObjectState(String state, String labelToState)
	{
		this.state=state;
		this.labelToState=labelToState;
		this.children=new ArrayList<ObjectState>();
	}
	
	ObjectState()
	{
		this.state=new String();
		this.labelToState=new String();
		this.children=new ArrayList<ObjectState>();
	}
	
    ObjectState addChild(ObjectState child) {
        this.children.add(child);
        return child;
    }
}
