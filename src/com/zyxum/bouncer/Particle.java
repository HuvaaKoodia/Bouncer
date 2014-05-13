package com.zyxum.bouncer;

import java.util.ArrayList;

public class Particle{
	public int x,y,dir,speed,id,xpleft,healthleft,deadtick;
	public float gravity;
	public static Message msg;
	private static int count;
	private static ArrayList<Particle> instances = new ArrayList<Particle>();
	
	public Particle(int y,int dir,int speed,int xpAmount,int healthAmount){
		this.id=++count;
		this.y=y;
		this.dir=dir;
		this.speed=speed;
		this.xpleft=xpAmount;
		this.healthleft=100;
		this.gravity=0.0F;
		this.deadtick=0;
		this.x=((dir==1)? -32 : Bouncer.WIDTH+32);
		
		instances.add(this);
	}
	
	public void update(int delta){
		if(Game.player.usedown) x+=(dir*speed*(delta/10))/4;
		else{
			if(delta/10>0) x+=dir*speed*(delta/10);
			else x+=(dir*speed)/10;
		}
		
		if(gravity>0.0F && gravity<12.0F) gravity*=delta/8.5;
		
		if(y>=Bouncer.HEIGHT-40 && deadtick<5000){
			deadtick+=delta;
			gravity=0.0F;
			y=Bouncer.HEIGHT-40;
			speed=0;
		}
		
		if(Game.player.usedown) y+=gravity/4;
		else y+=gravity;
		
		if(dir==1 && x>Bouncer.WIDTH && xpleft==2) Game.player.streak++;
		if(dir==-1 && x<-16 && xpleft==2) Game.player.streak++;
		
		if(dir==1 && x>Bouncer.WIDTH && xpleft>0){
			Game.xp++;
			xpleft--;
		}
		
		
		if(dir==-1 && x<-16 && xpleft>0){
			Game.xp++;
			xpleft--;
		}
		
		if(x>Game.player.x-Bouncer.WIDTH-8 && x<Game.player.x-Bouncer.WIDTH+32
				&& y>Game.player.y-8 && y<Game.player.y+32 && !Game.player.useup){
			
				
				Game.health=0;
				Game.intro=1;
			
			gravity=1.0F;
		}
	}
	
	public static int getCount(){
		return count;
	}
	
	public static ArrayList<Particle> getInstances(){
		return instances;
	}
}