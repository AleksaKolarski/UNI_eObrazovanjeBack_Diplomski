package com.projekat.eObrazovanje.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.projekat.eObrazovanje.model.Eye;

// port 4242

@Component
public class Client {
	
	private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    @Autowired
    private SimpMessagingTemplate template;
 
    public void startConnection(String ip, int port) throws UnknownHostException, IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
 
 
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    
    
    public void startStreamParse() throws IOException {
    	
    	out.print("<SET ID=\"ENABLE_SEND_TIME\" STATE=\"1\" />\r\n");
    	out.print("<SET ID=\"ENABLE_SEND_POG_FIX\" STATE=\"1\" />\r\n");
    	out.print("<SET ID=\"ENABLE_SEND_CURSOR\" STATE=\"1\" />\r\n");
    	out.print("<SET ID=\"ENABLE_SEND_DATA\" STATE=\"1\" />\r\n");
    	
    	out.flush();
    	
    	int startindex, endindex;
    	String line;
    	
    	do {
    		
    		line = in.readLine();
    		if(line.indexOf("<REC") != -1) {
    			Double fpogx;
    			Double fpogy;
    			
    			startindex = line.indexOf("FPOGX=\"") + "FPOGX=\"".length();
    			endindex = line.indexOf("\"", startindex);
    			fpogx = Double.parseDouble(line.substring(startindex, endindex));
    			
    			startindex = line.indexOf("FPOGY=\"") + "FPOGY=\"".length();
    			endindex = line.indexOf("\"", startindex);
    			fpogy = Double.parseDouble(line.substring(startindex, endindex));
    			
    			this.template.convertAndSend("/topic/greetings", new Eye(fpogx, fpogy));
    		}
    		
    	}while(line != null);
    	System.out.println("Reached end of stream!");
    }
}
