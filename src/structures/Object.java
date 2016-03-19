package structures;
import java.util.*;

public class Object<T>{ 
	
		private T data;  //the data of the object
		private Trace trace; //longest trace of the object

	    private List<Object<T>> children; //connected to next objects by morphisms

	    public Object(T process) {
	        this.data = process;
	        this.children = new ArrayList<Object<T>>();
	    }
	    
	    
		public Trace getTrace() {
			return trace;
		}



		public void setTrace(Trace trace) {
			this.trace = trace;
		}
	    

	    public Object<T> addChild(Object<T> child) {
	    	//Object<T> childNode = new Object<T>(child);
	        //child.parent = this;
	        this.children.add(child);
	        return child;
	    }
	    
		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		//public Object<T> getParent() {
		//	return parent;
		//}

		//public void setParent(Object<T> parent) {
		//	this.parent = parent;
		//}

		public List<Object<T>> getChildren() {
			return children;
		}

		public void setChildren(List<Object<T>> children) {
			this.children = children;
		}
}
