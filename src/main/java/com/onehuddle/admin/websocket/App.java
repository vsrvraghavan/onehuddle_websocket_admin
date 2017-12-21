package com.onehuddle.admin.websocket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Hello world!
 *
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.onehuddle.admin.websocket.model.AdminPanelMessage;
import com.onehuddle.admin.websocket.model.AdminPanelMessage.AdminPanelMessageType;


import com.onehuddle.admin.websocket.model.AdminPanelMessageData;
import com.onehuddle.admin.websocket.model.LeaderData;



@SpringBootApplication
@RestController
@EnableAutoConfiguration
@Controller
public class App {
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	private static String gameSessionLaunched = "0";	
	private static String gameSessionFinishedByPlayer = "0";	
	private static String gameSessionFinishedByManager = "0";
	private static String gameSessionFinishedByTimeout = "0";
	private static List<LeaderData> game_1_data = null;
	private static List<LeaderData> game_2_data = null;
	
	private static List<LeaderData> game_3_data = null;
	private static List<LeaderData> game_4_data = null;
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@RequestMapping(value="/adminpanel", method = RequestMethod.POST)
    public String pushPanelData(@RequestBody AdminPanelMessage data) {

		System.out.println("In adminpanel POST");
		System.out.println(data);		
		this.webSocket.convertAndSend("/channel/public", setAdminPanelMessage(data));				
		return String.valueOf("posted");
		
    }
	
	
	
	private AdminPanelMessage setAdminPanelMessage(AdminPanelMessage data) {
		

		if(data.getContent().getGameSessionsLaunched() != null) {
			gameSessionLaunched = data.getContent().getGameSessionsLaunched();
		}else {
			data.getContent().setGameSessionsLaunched(gameSessionLaunched);
		}
		
		if(data.getContent().getGameSessionsFinishedByPlayer() != null) {
			gameSessionFinishedByPlayer = data.getContent().getGameSessionsFinishedByPlayer();
		}else {
			data.getContent().setGameSessionsFinishedByPlayer(gameSessionFinishedByPlayer);
		}
		
		if(data.getContent().getGameSessionsFinishedByManager() != null) {
			gameSessionFinishedByManager = data.getContent().getGameSessionsFinishedByManager();
		}else {
			data.getContent().setGameSessionsFinishedByManager(gameSessionFinishedByManager);
		}
		
		if(data.getContent().getGameSessionsFinishedByTimeout() != null) {
			gameSessionFinishedByTimeout = data.getContent().getGameSessionsFinishedByTimeout();
		}else {
			data.getContent().setGameSessionsFinishedByTimeout(gameSessionFinishedByTimeout);
		}
		
		
		if(data.getContent().getLb1() != null) {
			game_1_data = data.getContent().getLb1();
		}else {
			data.getContent().setLb1(game_1_data);
		}
		if(data.getContent().getLb2() != null) {
			game_2_data = data.getContent().getLb2();
		}else {
			data.getContent().setLb2(game_2_data);
		}	
		
		if(data.getContent().getLbC() != null) {
			game_3_data = data.getContent().getLbC();
		}else {
			data.getContent().setLbC(game_3_data);
		}
		
		if(data.getContent().getLbD() != null) {
			game_4_data = data.getContent().getLbD();
		}else {
			data.getContent().setLbD(game_4_data);
		}
		
		
		return data;
	}
		
	@RequestMapping(value="/getpaneldata", method = RequestMethod.GET)
    public AdminPanelMessage getPanelData() {
		AdminPanelMessage data = new AdminPanelMessage();
		
		data.setMessageFor("äll");
		data.setType(AdminPanelMessageType.DATA);
		
		AdminPanelMessageData messageData = new AdminPanelMessageData();
		
		//randomNumber = random.nextInt(upperBound - lowerBound) + lowerBound;
		messageData.setGameSessionsLaunched(gameSessionLaunched);		
		messageData.setGameSessionsFinishedByPlayer(gameSessionFinishedByPlayer);		
		messageData.setGameSessionsFinishedByManager(gameSessionFinishedByManager);		
		messageData.setGameSessionsFinishedByTimeout(gameSessionFinishedByTimeout);	
		messageData.setLb1(game_1_data);
		messageData.setLb2(game_2_data);
		
		messageData.setLbC(game_3_data);
		messageData.setLbD(game_4_data);
		
		data.setContent(messageData);
		return data;
		
    }
	
}