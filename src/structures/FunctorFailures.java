//developer ming zhu
package structures;

import java.util.*;

import utilities.Utilities;
import vm.CategoryDesignVM;
import vm.CategoryImplementVM;


public class FunctorFailures extends HashMap<Trace, Trace>{ //save two categories and a functor between them to an xml file

	
	private boolean compareObjects(ObjectProcess dsgObj,ObjectProcess impObj)
	{
		Process dsgData=dsgObj.getData();
		Process impData=impObj.getData();
		boolean flag;
		
		if(Utilities.subSet(dsgData.getAlphabet(), impData.getAlphabet())!=true)
		{
			//System.out.println("alphabet");
			return false;
		}
			for(Iterator<Failure> dsgit=dsgData.getFailures().iterator(); dsgit.hasNext();)
			{
				Failure dsgtmpf=(Failure)dsgit.next();
				flag=false;
				for(Iterator<Failure> impit=impData.getFailures().iterator();impit.hasNext();)
				{
					Failure imptmpf=(Failure)impit.next();
					if(Utilities.compTrace(dsgtmpf.getTrace(),imptmpf.getTrace()))
					{
						//System.out.println(dsgtmpf.getTrace() +"    "+imptmpf.getTrace());
						Refusal impRefusal=Utilities.filterRefursal(imptmpf.getRefusal(), dsgData.getAlphabet());
						//System.out.println(dsgtmpf.getRefusal().toString()+"     "+impRefusal);
						if(Utilities.subSet(impRefusal,dsgtmpf.getRefusal()))
						{
							this.put(dsgObj.getTrace(), impObj.getTrace());
							//System.out.println("same failure");
							flag= true;
							break;
						}
					}
				}
				if(flag==false)
				{
					return false;
				}
			}
			return true;
	}
	
	private boolean compareSubObjects(ObjectProcess dsgObj,ObjectProcess impObj)
	{
		List<ObjectProcess> dsgChildren=dsgObj.getChildren();
		List<ObjectProcess> impChildren=impObj.getChildren();
		boolean flag;
		ObjectProcess dsgChd;
		ObjectProcess impChd;
			for(Iterator<ObjectProcess> dsgChdIt=dsgChildren.iterator();dsgChdIt.hasNext();)
			{
				dsgChd=dsgChdIt.next();
				flag=false;
				for(Iterator<ObjectProcess> impChdIt=impChildren.iterator();impChdIt.hasNext();)
				{
					impChd=impChdIt.next();
					if(compareObjects(dsgChd, impChd))
					{
						flag=true;
						if(dsgChd.getChildren().size()!=0)
							flag=compareSubObjects(dsgChd,impChd);
						break;
					}	
				}
				if(flag==false)
				{
					return false;
				}
			}
		return true;
	}
	
	public  boolean compareCategories(CategoryProcess dsgVM, CategoryProcess impVM)
	{
		ObjectProcess dsgInit=dsgVM.getInit();
		ObjectProcess impInit=impVM.getInit();
		
		if(compareObjects(dsgInit, impInit))
		{
			
			if(compareSubObjects(dsgInit, impInit))
			{
				return true;
			}
		}
			
		return false;
	}
	

}
