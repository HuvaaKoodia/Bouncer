package com.zyxum.bouncer;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Input;

public class GameInput{
	@SuppressWarnings("unchecked")
	public static void keyPress(int key, char c){
		if(key==Input.KEY_ESCAPE) Game.public_gc.exit();
		if(key==Input.KEY_SPACE){
			ArrayList<Message> tempArrayList = (ArrayList<Message>) Message.getInstances().clone();
			Collections.reverse(tempArrayList);
			for(Message message : tempArrayList){
				if(!message.isActive()) continue;
				
				message.disable();
				break;
			}
		}
		
		if(!Game.pause){
			
			if(key==Input.KEY_LEFT) Game.player.hspeed=-1;
			if(key==Input.KEY_RIGHT) Game.player.hspeed=1;
		}
	}

	public static void keyRelease(int key, char c){
		if(!Game.pause){
			if(key==Input.KEY_RIGHT && Game.player.hspeed==1) Game.player.hspeed=0;
			if(key==Input.KEY_LEFT && Game.player.hspeed==-1) Game.player.hspeed=0;
		}
	}
}