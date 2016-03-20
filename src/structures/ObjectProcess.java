package structures;
import java.util.*;

public class ObjectProcess{ 
	
		private Process data;  //the data of the object
		private Trace trace; //longest trace of the object

	    private List<ObjectProcess> children; //connected to next objects by morphisms

	    public ObjectProcess(Process data) {
	        this.data = data;
	        this.children = new ArrayList<ObjectProcess>();
	    }
	    
	   

		public Trace getTrace() {
			return trace;
		}



		public void setTrace(Trace trace) {
			this.trace = trace;
		}
	    

	    public ObjectProcess addChild(ObjectProcess child) {
	    	//Object<T> childNode = new Object<T>(child);
	        //child.parent = this;
	        this.children.add(child);
	        return child;
	    }
	    
		public Process getData() {
			return data;
		}

		public void setData(Process data) {
			this.data = data;
		}

		//public Object<T> getParent() {
		//	return parent;
		//}

		//public void setParent(Object<T> parent) {
		//	this.parent = parent;
		//}

		public List<ObjectProcess> getChildren() {
			return children;
		}

		public void setChildren(List<ObjectProcess> children) {
			this.children = children;
		}
}
