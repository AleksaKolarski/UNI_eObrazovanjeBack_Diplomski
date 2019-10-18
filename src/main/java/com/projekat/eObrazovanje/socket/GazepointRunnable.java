package com.projekat.eObrazovanje.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.projekat.eObrazovanje.model.Eye;

@Component
public class GazepointRunnable implements Runnable {
	
	private volatile boolean isRunning;
	
	@Autowired
	@Qualifier("gazepointTaskExecutor")
	private TaskExecutor taskExecutor;
	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	
	public GazepointRunnable() {
		isRunning = false;
	}

	
	@Override
	public void run() {
		
		// init on every run
//		Display display = null;
//		int monitorWidth = 0;
//		int monitorHeight = 0;
//		try {
//			display = new Display();
//			monitorWidth = display.getPrimaryMonitor().getBounds().width;
//	    	monitorHeight = display.getPrimaryMonitor().getBounds().height;
//		}
//		catch (Exception e) {
//			System.out.println("DISPLAY ERROR, TRY AGAIN");
//			isRunning = false;
//		}
		
		
    	
    	//out.print("<SET ID=\"ENABLE_SEND_TIME\" STATE=\"1\" />\r\n");
    	out.print("<SET ID=\"ENABLE_SEND_POG_FIX\" STATE=\"1\" />\r\n");
    	out.print("<SET ID=\"ENABLE_SEND_CURSOR\" STATE=\"1\" />\r\n");
    	out.print("<SET ID=\"ENABLE_SEND_DATA\" STATE=\"1\" />\r\n");
    	out.flush();
    	
    	int startindex, endindex;
    	String line = null;
		
		
		// loop
		while(isRunning) {
			try {
				line = in.readLine();
				if(line == null) {
					System.out.println("Reached end of stream");
					isRunning = false;
					continue;
				}
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
	    			
	    			
	    			long millis=System.currentTimeMillis();
	    			template.convertAndSend("/topic/gazepoint-data", new Eye(true, ((millis/10.0) % 1920) / 1920.0, ((millis/10.0) % 1080) / 1080));
	    			
//	    			Point p = display.getCursorLocation();
//	    			template.convertAndSend("/topic/gazepoint-data", new Eye(true, (double)p.x/(monitorWidth-1), (double)p.y/(monitorHeight-1)));
	    			
	    			//System.out.println("sending " + p.x + " " + p.y);
				}
			} catch (IOException e) {
				System.out.println("Lost connection with gazepoint server.");
				e.printStackTrace();
				isRunning = false;
			}
		}
		
		
		// stopping the tread
		try {
			System.out.println("Sending END WEBSOCKET");
			template.convertAndSend("/topic/gazepoint-data", new Eye(false, 0.0, 0.0));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		closeAllStreams();
	}
	
	public void connect() throws IOException{
		try {
			clientSocket = new Socket("127.0.0.1", 4242);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			isRunning = true;
			taskExecutor.execute(this);
		} catch (IOException | TaskRejectedException e1) {
			closeAllStreams();
			isRunning = false;
			throw new IOException();
		}
	}

	public void stop() {
		isRunning = false;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	private void closeAllStreams() {
		System.out.println("Closing all streams");
		if(out != null) {
			out.close();
		}
		if(in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(clientSocket != null) {
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
