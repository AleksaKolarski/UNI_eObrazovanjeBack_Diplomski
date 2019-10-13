package com.projekat.eObrazovanje.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.projekat.eObrazovanje.socket.GazepointRunnable;

@Service
public class GazepointService {
	
	@Autowired
	private GazepointRunnable gazepointRunnable;
	
	public void start() throws IOException {
		if( !this.gazepointRunnable.isRunning() ) {
			System.out.println("Starting gazepoint thread");
			gazepointRunnable.connect();
		}
		else {
			System.out.println("Gazepoint thread is already running");
		}
	}
	
	public void stop() {
		if( this.gazepointRunnable.isRunning() ) {
			System.out.println("Stopping gazepoint thread");
			this.gazepointRunnable.stop();
		}
		else {
			System.out.println("Gazezpoint thread is NOT running");
		}
	}
}
