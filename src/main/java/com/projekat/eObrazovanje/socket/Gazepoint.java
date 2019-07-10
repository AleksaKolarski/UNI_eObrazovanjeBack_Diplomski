package com.projekat.eObrazovanje.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.projekat.eObrazovanje.model.Eye;

// port 4242

@Component
public class Gazepoint {
	
	private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    private ClientThread thread = new ClientThread();
    
    @Autowired
    private SimpMessagingTemplate template;
 
    public void startConnection(String ip, int port) throws UnknownHostException, IOException {
    	if(!thread.isAlive()) {
    		clientSocket = new Socket(ip, port);
    		out = new PrintWriter(clientSocket.getOutputStream(), true);
    		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
    		thread = new ClientThread();
        	thread.start();
        
        	System.out.println("gazepoint connection started");
    	}
    }
 
 
    @SuppressWarnings("deprecation")
	public void stopConnection() {
    	
    	template.convertAndSend("/topic/gazepoint-data", new Eye(false, 0.0, 0.0));
    	
    	if(thread != null) {	
        	thread.stop();
        }
    	
    	out.close();
    	
    	try {
    		in.close();
    	}catch (IOException e) {} 
    	
    	try {
			clientSocket.close();
		} catch (IOException e) {}
    	
        System.out.println("gazepoint connection dropped");
    }
    
    
    public void startStreamParse() {
    	Display d = new Display();
    	int monitorWidth = d.getPrimaryMonitor().getBounds().width;
    	int monitorHeight = d.getPrimaryMonitor().getBounds().height;
    	
    	//out.print("<SET ID=\"ENABLE_SEND_TIME\" STATE=\"1\" />\r\n");
    	out.print("<SET ID=\"ENABLE_SEND_POG_FIX\" STATE=\"1\" />\r\n");
    	out.print("<SET ID=\"ENABLE_SEND_CURSOR\" STATE=\"1\" />\r\n");
    	out.print("<SET ID=\"ENABLE_SEND_DATA\" STATE=\"1\" />\r\n");
    	
    	out.flush();
    	
    	int startindex, endindex;
    	String line = null;
    	do {
    		try {
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
	    			
	    			//template.convertAndSend("/topic/gazepoint-data", new Eye(true, fpogx, fpogy));
	    			
	    			
	    			//long millis=System.currentTimeMillis();
	    			//template.convertAndSend("/topic/gazepoint-data", new Eye(true, (millis/10.0) % 1920, (millis/10.0) % 1080));
	    			
	    			Point p = d.getCursorLocation();
	    			template.convertAndSend("/topic/gazepoint-data", new Eye(true, (double)p.x/(monitorWidth-1), (double)p.y/(monitorHeight-1)));
	    			
	    			//System.out.println("sending " + p.x + " " + p.y);
	    		}
			} catch (Exception e) {
				System.out.println("Lost connection with gazepoint server.");
				d.dispose();
				stopConnection();
				break;
			}
    	}while(line != null);
    	System.out.println("Reached end of stream!");
    	stopConnection();
    }
    
    
    private class ClientThread extends Thread{
    	public void run(){
			startStreamParse();
    	}
    }
}
