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
			impT1=FileLoader.fileToTransitionSystem("C:\\Users\\zhuming\\dropbox\\Transitions\\dinning_Fork.lts");
			//Utilities.printTransitionSystem(impT1);
			impT1=Utilities.acyclicTransitionSystem(impT1);
			//impT1=Utilities.loopTransitionSystem(impT1, 2);
			//Utilities.printTransitionSystem(impT1);
			Process p=new Process(impT1);
			//Utilities.printProcess(p);
			FailureTree ftp=new FailureTree(p);
			//Utilities.printFailureTree(ftp);
			EventSet newAlphabet=new EventSet();
			newAlphabet.add("pickUp");
			newAlphabet.add("stop");
			newAlphabet.add("putDown");
			ftp=new EquivalentFailureTree(ftp, newAlphabet);
			//Utilities.printFailureTree(ftp);
			Utilities.printProcess(ftp.treeToProcess());
			
			TransitionSystem impT2=new TransitionSystem();
			impT2=FileLoader.fileToTransitionSystem("C:\\Users\\zhuming\\dropbox\\Transitions\\dinning_Lefty.lts");
			//Utilities.printTransitionSystem(impT2);
			impT2=Utilities.acyclicTransitionSystem(impT2);
			//impT1=Utilities.loopTransitionSystem(impT2, 2);
			//Utilities.printTransitionSystem(impT2);
			Process p2=new Process(impT2);
			//Utilities.printProcess(p2);
			FailureTree ftp2=new FailureTree(p2);
			//Utilities.printFailureTree(ftp2);
			EventSet newAlphabet2=new EventSet();
			newAlphabet2.add("pickUp");
			newAlphabet2.add("stop");
			newAlphabet2.add("putDown");
			ftp2=new EquivalentFailureTree(ftp2, newAlphabet2);
			//Utilities.printFailureTree(ftp2);
			Utilities.printProcess(ftp2.treeToProcess());
			
			Process pp2=new Communication(ftp.treeToProcess(),ftp2.treeToProcess());
			Utilities.printProcess(pp2);
	    }
	    
}
