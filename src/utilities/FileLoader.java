package utilities;
import java.io.*;
import java.util.Iterator;

import operation.Communication;
import operation.LoopProcesses;
import structures.*;
import structures.Process;

public class FileLoader {

 
	    public static TransitionSystem fileToTransitionSystem(String path) 
	    {
	    	TransitionSystem ts=new TransitionSystem();
	    	File file = null;
	        String line = null;

	        String delimiters = "\\s+|,\\s*";
	        try {
	        	file=new File(path);
		        System.out.println(file.getAbsolutePath());
		        
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = 
	                new FileReader(file);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) {
	            	line=line.trim();
	            	String[] tmp =line.split(delimiters);
	            	if(tmp[0].equals("0")!=true)
	            	{
	            		Transition t=new Transition(tmp[0],tmp[2],tmp[1]);
	            		ts.add(t);
	            	}
	            	
	            }   

	            // Always close files.
	            bufferedReader.close();  
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println(
	                "Unable to open file '" + 
	                file + "'");                
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error reading file '" 
	                + file + "'");                  
	            // Or we could just do this: 
	            // ex.printStackTrace();
	        }
	    	return ts;
	    }
	    
	    public static void main(String args[])
	    {
			TransitionSystem impT1=new TransitionSystem();
			impT1=FileLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Desktop\\Transitions\\vm-1_VendingMachine.lts");
			//Utilities.printTransitionSystem(impT1);
			impT1=Utilities.acyclicTransitionSystem(impT1);
			//impT1=Utilities.loopTransitionSystem(impT1, 3);
			//Utilities.printTransitionSystem(impT1);
			Process p=new Process(impT1);
			FailureTree ftp=new FailureTree(p);
			EventSet newAlphabet=new EventSet();
			newAlphabet.add("coke");
			newAlphabet.add("tea");
			newAlphabet.add("pepsi");

			ftp=new EquivalentFailureTree(ftp, newAlphabet);
			Utilities.printProcess(ftp.treeToProcess());
			
			TransitionSystem impT2=new TransitionSystem();
			impT2=FileLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Desktop\\Transitions\\vm-1_customer.lts");
			//Utilities.printTransitionSystem(impT1);
			impT2=Utilities.acyclicTransitionSystem(impT2);
			//Utilities.printTransitionSystem(impT2);
			Process q=new Process(impT2);
			//q=new LoopProcesses(q,2);
			Utilities.printProcess(q);
			
			//Process pq=new Communication(p,q);
			//Utilities.printProcess(pq);
	    }
	    
}
