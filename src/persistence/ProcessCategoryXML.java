//developer ming zhu
package persistence;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import structures.Process;
import structures.Trace;
import structures.Transition;
import structures.TransitionSystem;
import utilities.Utilities;
import structures.ConcreteProcesses;
import structures.ConcreteProcess;
import structures.Failure;
import structures.Failures;
import structures.Category;
import structures.CategoryProcess;
import structures.Object;
import structures.ConcreteCategoryProcess;
import structures.ConcreteCategoryProcesses;
import structures.FunctorFailures;
import java.util.*;

import java.awt.Desktop;
import java.net.URI;

public class ProcessCategoryXML {//create an xml file for a category of process
	
	public ProcessCategoryXML(HashSet<ConcreteCategoryProcess> ps)
	{ 
		try {
			  	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("categoryfunctor");
				doc.appendChild(rootElement);
				
			    for(Iterator<ConcreteCategoryProcess> csit=ps.iterator();csit.hasNext();)
			    {
			    	ConcreteCategoryProcess ccp=csit.next();
					Element ccprocess = doc.createElement("ConcreteCategoryProcess");
					rootElement.appendChild(ccprocess);
					
					Attr name = doc.createAttribute("name");
					name.setValue(ccp.getName());
					ccprocess.setAttributeNode(name);
					
					Attr type=doc.createAttribute("type");
					type.setValue(ccp.getType());
					ccprocess.setAttributeNode(type);
								
					Utilities.CategoryProcessToXML(ccprocess, doc, ccp.getCategoryProcess());	
			    }
		
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("C:\\tomcat\\webapps\\gs\\samples\\categoryprocesses.xml"));
				
				transformer.transform(source, result);
		
				System.out.println("File saved!");
			  } 
		  catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			  } 
		  catch (TransformerException tfe) {
				tfe.printStackTrace();
			  }
  }
	
	
	public ProcessCategoryXML(HashSet<ConcreteCategoryProcess> ps, FunctorFailures cf, boolean functorExist)
	  {
	  try {
		  	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("categoryfunctor");
			doc.appendChild(rootElement);
			
		    for(Iterator<ConcreteCategoryProcess> csit=ps.iterator();csit.hasNext();)
		    {
		    	ConcreteCategoryProcess ccp=csit.next();
				Element ccprocess = doc.createElement("ConcreteCategoryProcess");
				rootElement.appendChild(ccprocess);
				
				Attr name = doc.createAttribute("name");
				name.setValue(ccp.getName());
				ccprocess.setAttributeNode(name);
				
				Attr type=doc.createAttribute("type");
				type.setValue(ccp.getType());
				ccprocess.setAttributeNode(type);
							
				Utilities.CategoryProcessToXML(ccprocess, doc, ccp.getCategoryProcess());	
		    }
		    
			Element functor = doc.createElement("functor");
			functor.setAttribute("exist", new Boolean(functorExist).toString());
			rootElement.appendChild(functor);
			
			if(functorExist==true)
			{
			    for(Iterator<Map.Entry<Trace, Trace>> cfit=cf.entrySet().iterator();cfit.hasNext();)
			    {
			    	Map.Entry<Trace, Trace> et=cfit.next();
					Element entry = doc.createElement("entry");
					entry.setAttribute("from", et.getKey().toString());
					entry.setAttribute("to", et.getKey().toString());
					functor.appendChild(entry);
			    }
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\tomcat\\webapps\\gs\\samples\\categoryfunctor.xml"));
			
			transformer.transform(source, result);

			System.out.println("File saved!");
		  } 
	  catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } 
	  catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	  }
	
	  public static void main(String args[])
	  {
			TransitionSystem ts1=new TransitionSystem();
			ts1.add(new Transition(0,"coin",1));
			ts1.add(new Transition(1,"coke",2));
			ts1.add(new Transition(1,"pepsi",3));
			ts1.add(new Transition(1,"tea",4));
			//ts1.add(new Transition(3,"movie",5));
			//ts1.add(new Transition(5,"beat",6));
			
			Process vmi1=new Process(ts1);
			
			TransitionSystem ts2=new TransitionSystem();
			ts2.add(new Transition(0,"coin",1));
			ts1.add(new Transition(1,"coke",2));
			ts2.add(new Transition(1,"pepsi",3));
			//ts2.add(new Transition(1,"tea",4));
			//ts2.add(new Transition(4,"movie",5));

			
			
			
			Process vmi2=new Process(ts2);
			
			CategoryProcess cvmi1=new CategoryProcess(vmi1);
			Utilities.printCategory(cvmi1.getInit(), 0);
			
			CategoryProcess cvmi2=new CategoryProcess(vmi2);
			Utilities.printCategory(cvmi2.getInit(), 0);
			
			ConcreteCategoryProcess cp1=new ConcreteCategoryProcess(cvmi1,"vmi1","implement");
			ConcreteCategoryProcess cp2=new ConcreteCategoryProcess(cvmi2,"vmi2","design");
			ConcreteCategoryProcesses cps=new ConcreteCategoryProcesses();
			cps.add(cp1);
			cps.add(cp2);
			
			FunctorFailures cf=new FunctorFailures();
			System.out.println(cf.compareCategories(cvmi2, cvmi1));
			
			//ProcessCategoryXML pxml=new ProcessCategoryXML(cps);
			ProcessCategoryXML pfxml=new ProcessCategoryXML(cps,cf,cf.compareCategories(cvmi2, cvmi1));
			


			try
			{
				if(Desktop.isDesktopSupported())
				{
					Desktop.getDesktop().browse(new URI("http://localhost:9001/gs/samples/category.html"));
				}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
	  }
}
