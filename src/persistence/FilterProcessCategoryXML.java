package persistence;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import operation.*;

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

import utilities.FileLoader;
import utilities.Utilities;
import structures.*;
import structures.Process;

public class FilterProcessCategoryXML {

	
	public FilterProcessCategoryXML(HashSet<ConcreteCategoryProcess> ps, EquivalentFailureTree eft)
	{
		  try {
			  	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("Category");
				doc.appendChild(rootElement);
				
				//for(int i=0;i<ps.size();i++)
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
			    
				Element mapping = doc.createElement("Mapping");
				//functor.setAttribute("exist", new Boolean(functorExist).toString());
				rootElement.appendChild(mapping);
				

				for(int i=0;i<eft.getNodeList().size();i++)
				{
					FailureTreeNode ftn=eft.getNodeList().get(i);
					HashSet<FailureTreeNode> ftnSet=eft.getNodeSetList().get(i);
					for(Iterator<FailureTreeNode> it=ftnSet.iterator();it.hasNext();)
					{
						Element entry = doc.createElement("entry");
						entry.setAttribute("from", it.next().getData().getTrace().toString());
						entry.setAttribute("to",ftn.getData().getTrace().toString());
						mapping.appendChild(entry);
					}
				}
				
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("C:\\tomcat\\webapps\\gs\\samples\\categoryMapping.xml"));
				
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

		//transition system from design
		TransitionSystem dsgT=new TransitionSystem();
		dsgT.add(new Transition(0,"coin",1));
		dsgT.add(new Transition(1,"coke",2));
		dsgT.add(new Transition(1,"pepsi",3));
		dsgT.add(new Transition(1,"tea",4));
		Process dsgP=new Process(dsgT);
	

		//transition system from implementation
		TransitionSystem impT1=new TransitionSystem();
		impT1=FileLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Desktop\\Transitions\\vm-3_VendingMachine.lts");
		Process impP1=new Process(impT1);
		//Utilities.printProcess(impP1);
		
		TransitionSystem impT2=new TransitionSystem();
		impT2=FileLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Desktop\\Transitions\\vm-3_Customer.lts");
		Process impP2=new Process(impT2);
		//Utilities.printProcess(impP2);
		
		Process comm=new Communication(impP2,impP1);
		Utilities.printProcess(comm);
		
		//generate failure tree for filtering
		FailureTree impFt=new FailureTree(comm);
		
		//events we want to analyze
		EventSet evts=new EventSet();
		evts.add("coin");
		evts.add("tea");
		evts.add("coke");
		evts.add("pepsi");

		//failure tree after filtering
		EquivalentFailureTree impEft=new EquivalentFailureTree(impFt,evts);
		
		//Utilities.printFailureTree(impEft);
		
		//mapping into a category
		CategoryProcess filteredCategory=new CategoryProcess(impEft.treeToProcess());
		CategoryProcess origCategory=new CategoryProcess(comm);
		
		//generate concrete categories with more info for xml
		ConcreteCategoryProcess filterCcp=new ConcreteCategoryProcess(filteredCategory,"filteredCategory","filter");
		ConcreteCategoryProcess origCcp=new ConcreteCategoryProcess(origCategory,"origCategory","original");
		
		//add concrete categories into a set
		ConcreteCategoryProcesses cps=new ConcreteCategoryProcesses();
		cps.add(filterCcp);
		cps.add(origCcp);
		
		//generate xml file for filtering
		FilterProcessCategoryXML fpc=new FilterProcessCategoryXML(cps,impEft);
		
		
		//functor from design to implement
		CategoryProcess dsgCp=new CategoryProcess(dsgP);
		
		//generate concrete categories
		ConcreteCategoryProcess impCcp=new ConcreteCategoryProcess(filteredCategory,"filteredCategory","implement");
		ConcreteCategoryProcess dsgCcp=new ConcreteCategoryProcess(dsgCp,"designedCategory","design");
		
		//add concrete categories into a set
		ConcreteCategoryProcesses cpfs=new ConcreteCategoryProcesses();
		cpfs.add(impCcp);
		cpfs.add(dsgCcp);
		
		//generate functor for comparing implementation and design
		FunctorFailures cf=new FunctorFailures();
		System.out.println(cf.compareCategories(dsgCp, filteredCategory));
		
		//generate xml file for functor
		ProcessCategoryXML pfxml=new ProcessCategoryXML(cpfs,cf,cf.compareCategories(dsgCp, filteredCategory));
		

	}
}
