package structures;

import vm.Drink;

public class Category {

		protected Object<Process> init;
		
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
