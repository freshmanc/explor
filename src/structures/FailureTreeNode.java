package structures;
import java.util.*;

public class FailureTreeNode {


	private FailureTreeNode parent;
	private List<FailureTreeNode> children;
	private Failure data;
	
	public FailureTreeNode()
	{
		this.parent=null;
		this.children=new ArrayList<FailureTreeNode>();
		this.data=new Failure();
	}
	
	public FailureTreeNode(Failure f)
	{
		this.parent=null;
		this.children=new ArrayList<FailureTreeNode>();
		Failure tmp=new Failure(f);
		this.data=tmp;
	}
	
	public FailureTreeNode(FailureTreeNode ftn)
	{
		this.parent=ftn.getParent();
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
		ftn.setParent(this);
	}
	
	public FailureTreeNode getParent() {
		return parent;
	}
	
	public void setParent(FailureTreeNode parent) {
		this.parent = parent;
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
