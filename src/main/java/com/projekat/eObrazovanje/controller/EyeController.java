package com.projekat.eObrazovanje.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.projekat.eObrazovanje.model.Eye;

@EnableScheduling
@Controller
public class EyeController {

	
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Eye eyeMethod() throws InterruptedException {
		//System.out.println(eye.getFpogx() + " " + eye.getFpogy());
		//Thread.sleep(1000);
		
		greeting();
		
		return new Eye(1.1, 2.2);
	}
	
	
	@Autowired
    private SimpMessagingTemplate template;

    //@Scheduled(fixedRate = 5000)
    public void greeting() {
        System.out.println("called");
        this.template.convertAndSend("/topic/greetings", new Eye(3.3, 4.4));
    }
	
}
