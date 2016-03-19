//developer ming zhu
package structures;

import vm.Drink;

public class Category { // the category is represented as a tree structure

		protected Object<Process> init;  //the root of the tree
		
		public Object<Process> getInit() {
			return init;
		}

		public void setInit(Object<Process> init) {
			this.init = init;
		}
		
		public Category()
		{
			
			this.init=new Object<Process>(new Process());
		}
}
