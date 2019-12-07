package org.golde.mcpexception;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) throws Exception {
		
		if(args.length == 0) {
			System.out.println("No file.");
			
		}
		else {
			//read the file in as a string
			File file = new File(args[0]);
			String s = new String(Files.readAllBytes(file.toPath()));
			
			//isolate the exception from the rest of the file
			Pattern p = Pattern.compile("(?m)^.*?Exception.*(?:\\R+^.*at .*)+");
		    Matcher m = p.matcher(s);
		    
		    if(m.find()) {
		    	for(int i = 0; i <= m.groupCount(); i++) {
		    		String theGroup = m.group(i);
			        
			    	//remove the time 12:34:56
			    	theGroup = theGroup.replaceAll("\\d{2}:\\d{2}:\\d{2}", "");
			    	
			    	//remove the  ' - '
			    	theGroup = theGroup.replaceAll(" - ", "");
			        
			        String formatted = String.format("%s\n", theGroup);
			        
			        //write the formatted exception to a file
			        FileWriter fileWriter = new FileWriter(args[0] + ".formatted");
			        
			        PrintWriter pw = new PrintWriter(fileWriter);
			        
			        pw.print(formatted);
			        
			        pw.close();
			        fileWriter.close();
			        
			        System.out.println("Finished.");
		    	}
		    	
		    }
		    else {
		    	System.out.println("Could not find the stacktrace. Quitting...");
		    }
		}
		
		Thread.sleep(3000);
		
	}

}
