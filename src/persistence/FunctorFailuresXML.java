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
import structures.Trace;

import java.util.*;

public class FunctorFailuresXML {

	public FunctorFailuresXML(FunctorFailures cf)
	{
		  try {
			  	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("functor");
				doc.appendChild(rootElement);
				

			    for(Iterator<Map.Entry<Trace, Trace>> cfit=cf.entrySet().iterator();cfit.hasNext();)
			    {
			    	Map.Entry<Trace, Trace> et=cfit.next();
					Element entry = doc.createElement("entry");
					entry.setAttribute("key", et.getKey().toString());
					entry.setAttribute("value", et.getKey().toString());
					rootElement.appendChild(entry);
			    }
			
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("C:\\go\\functor.xml"));
				
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
		ts1.add(new Transition(1,"pepsi",2));
		ts1.add(new Transition(1,"coke",3));
		ts1.add(new Transition(1,"tea",4));
		
		Process vmi1=new Process(ts1);
		
		TransitionSystem ts2=new TransitionSystem();
		ts2.add(new Transition(0,"coin",1));
		ts2.add(new Transition(1,"pepsi",2));
		ts2.add(new Transition(1,"coke",3));
		//ts2.add(new Transition(1,"tea",4));
		
		Process vmi2=new Process(ts2);
		CategoryProcess cvmi1=new CategoryProcess(vmi1);
		CategoryProcess cvmi2=new CategoryProcess(vmi2);
		
		FunctorFailures cf=new FunctorFailures();
		cf.compareCategories(cvmi2, cvmi1);
		for(Iterator<Map.Entry<Trace,Trace>> it=cf.entrySet().iterator();it.hasNext();)
		{
			Map.Entry<Trace,Trace> et=it.next();
			System.out.println("key "+et.getKey());
			System.out.println("value "+et.getValue());
		}
		FunctorFailuresXML ff=new FunctorFailuresXML(cf);

	}
}
