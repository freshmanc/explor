package structures;

import java.util.HashMap;
import java.util.HashSet;

public class Pair {


	private HashSet<FailureTreeNode> equ;
	private HashSet<FailureTreeNode> cat;
	
	public Pair(HashSet<FailureTreeNode> equ,HashSet<FailureTreeNode> cat)
	{
		this.equ=equ;
		this.cat=cat;
	}
	
	public HashSet<FailureTreeNode> getEqu() {
		return equ;
	}

	public void setEqu(HashSet<FailureTreeNode> equ) {
		this.equ = equ;
	}

	public HashSet<FailureTreeNode> getCat() {
		return cat;
	}

	public void setCat(HashSet<FailureTreeNode> cat) {
		this.cat = cat;
	}
}
