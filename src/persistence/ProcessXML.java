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

import java.util.*;

public class ProcessXML {
	
	  ProcessXML(HashSet<ConcreteProcess> ps)
	  {
	  try {
		  	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("ConcreteProcesses");
			doc.appendChild(rootElement);
			
			Integer processid=1;
		    for(Iterator<ConcreteProcess> psit=ps.iterator();psit.hasNext();)
		    {
		    	ConcreteProcess cp=psit.next();
				Element cprocess = doc.createElement("ConcreteProcess");
				rootElement.appendChild(cprocess);
				
				Attr name = doc.createAttribute("name");
				name.setValue(cp.getName());
				cprocess.setAttributeNode(name);
				
				Attr type=doc.createAttribute("type");
				type.setValue(cp.getType());
				cprocess.setAttributeNode(type);
							
				Utilities.ProcessToXML(cprocess, doc, cp.getProcess());	
		    }
		
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\tomcat\\webapps\\gs\\samples\\processes.xml"));
			
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
			
			ConcreteProcess cp1=new ConcreteProcess(vmi1,"vmi1","design");
			ConcreteProcess cp2=new ConcreteProcess(vmi2,"vmi2","design");
			ConcreteProcesses cps=new ConcreteProcesses();
			cps.add(cp1);
			cps.add(cp2);
			
			ProcessXML pxml=new ProcessXML(cps);
	  }
}
