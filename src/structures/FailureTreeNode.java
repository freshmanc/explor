//developer ming zhu
package structures;
import java.util.*;

public class FailureTreeNode { //each node represents a failure

	private List<FailureTreeNode> children; // next failures
	private Failure data; // a failure
	
	public FailureTreeNode()
	{
		this.children=new ArrayList<FailureTreeNode>();
		this.data=new Failure();
	}
	
	public FailureTreeNode(Failure f)
	{
		this.children=new ArrayList<FailureTreeNode>();
		Failure tmp=new Failure(f);
		this.data=tmp;
	}
	
	public FailureTreeNode(FailureTreeNode ftn)
	{
		this.children=ftn.getChildren();
		this.data=ftn.getData();
	}
	
	public void addChild(Failure f)
	{
		FailureTreeNode ftn=new FailureTreeNode(f);
		this.children.add(ftn);
	}
	
	public void addChild(FailureTreeNode ftn)
	{
		this.children.add(ftn);
	}

	
	public List<FailureTreeNode> getChildren() {
		return children;
	}
	
	public void setChildren(List<FailureTreeNode> children) {
		this.children = children;
	}
	
	public Failure getData() {
		return data;
	}
	
	public void setData(Failure data) {
		this.data = data;
	}	
}
