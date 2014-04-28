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
			if(Game.intro>=2){
				if(key==Input.KEY_UP && !Game.player.useup && Ability.up_coolleft<=0){
					Game.abilitycounter++;
					Game.player.useup=true;
					Game.player.up_pos_x=Game.player.x-Bouncer.WIDTH;
					Game.player.up_pos_y=Game.player.y;
					Game.player.up_size=0;
					Ability.up_coolleft=Ability.up_cool;
				}
				
				if(key==Input.KEY_DOWN && !Game.player.usedown && Ability.down_coolleft<=0){
					Game.abilitycounter++;
					Game.player.usedown=true;
					Ability.down_coolleft=Ability.down_cool;
					Game.player.down_time=20;
				}
			}
			
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