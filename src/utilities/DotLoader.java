package utilities;
import java.io.*;
import java.util.Iterator;

import operation.Communication;
import operation.LoopProcesses;
import structures.*;
import structures.Process;
import java.util.regex.*;


public class DotLoader {
	
    public static TransitionSystem fileToTransitionSystem(String path) 
    {
    	String REGEX="->|\\s\\[label=\"|\"\\];";
    	String REPLACE="-";
    	TransitionSystem ts=new TransitionSystem();
    	File file = null;
        String line = null;
        Pattern p=Pattern.compile(REGEX);

        String delimiters = "-";
        try {
        	file=new File(path);
	        //System.out.println(file.getAbsolutePath());
	        
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(file);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	 
            	 if(line.contains("->"))
            	 {
            		 //System.out.println(line); 
            		 line=line.trim();
            		 //System.out.println(line);
            		 Matcher m =p.matcher(line);
            		 line=m.replaceAll(REPLACE);
            		 //System.out.println(line);
                 	 String[] tmp =line.split(delimiters);
                 	if(tmp[0].equals("0")!=true)
                	{
                		Transition t=new Transition(tmp[0],tmp[2],tmp[1]);
                		ts.add(t);
                	}
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
        }
		return ts;
    }
    
    public static void main(String[] args)
    {		
    	TransitionSystem tsv=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\test-1_VendingMachine.dot");
    	//Utilities.printTransitionSystem(ts);
    	tsv=Utilities.acyclicTransitionSystem(tsv);
    	//Utilities.printTransitionSystem(ts);
    	Process pv=new Process(tsv);
    	Utilities.printProcess(pv);
    	System.out.println();
    	
    	TransitionSystem tsc=DotLoader.fileToTransitionSystem("C:\\Users\\zhuming\\Dropbox\\SDK\\latest\\vm-4_Customer.dot");
    	//Utilities.printTransitionSystem(ts);
    	tsc=Utilities.acyclicTransitionSystem(tsc);
    	//Utilities.printTransitionSystem(ts);
    	Process pc=new Process(tsc);
    	Utilities.printProcess(pc);
    	System.out.println();
    	
    	Process CcV=new Communication(pv,pc);
    	Utilities.printProcess(CcV);
    }
}
