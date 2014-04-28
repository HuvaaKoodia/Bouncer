package com.zyxum.bouncer;

public class Ability{
	public static int p_cool,p_coolleft,up_cool,up_coolleft,down_cool,down_coolleft;
	public static double deltasec;
	
	public static void init(){
		p_coolleft=0;
		p_cool=20;
		
		up_coolleft=0;
		up_cool=30;
		
		down_coolleft=0;
		down_cool=60;
	}
	
	public static void update(int idelta){
		double delta=idelta;
		if(p_coolleft>0 || (up_coolleft>0 && !Game.player.useup) || (down_coolleft>0 && !Game.player.usedown) || Game.player.down_time>0)
			deltasec+=delta;
		
		if(deltasec>=1000){
			if(p_coolleft>0) p_coolleft--;
			if(up_coolleft>0 && !Game.player.useup) up_coolleft--;
			if(down_coolleft>0 && !Game.player.usedown) down_coolleft--;
			
			if(Game.player.down_time>0) Game.player.down_time--;
			
			deltasec=0;
		}
	}
}