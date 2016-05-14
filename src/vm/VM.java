package vm;

import structures.FunctorFailures;

public class VM {

	public static void main(String args[])
	{
		CategoryDesignVM dsgVM=new CategoryDesignVM();
		CategoryImplementVM impVM=new CategoryImplementVM();
		if(FunctorFailures.compareCategories(dsgVM, impVM))
		{
			System.out.println("The implementation match the design");
		}
		else
		{
			System.out.println("The implementation doesn't match the design");
		}
	}
	public test main()
	{}
}
