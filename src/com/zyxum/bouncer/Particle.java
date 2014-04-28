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
		if(Game.intro==3) this.healthleft=healthAmount;
		else this.healthleft=100;
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
			if(Game.intro==0){
				msg=new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-32, 300, 64, "Like 'Graphics' 'Health' 'Sound'", " and 'Animations'. They'll do!");
				new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-32, 300, 64, "Let's add some 'Game Mechanics'", "into play!");
				new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-32, 300, 64, "Just kidding!", "This version sucks...");
				
				healthleft=0;
				Game.intro++;
				Game.health=150;
				Game.player.hspeed=0;
			}else if(Game.intro==1 && Game.health<=1){
				new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-32, 300, 64, "Also! Quick time events! Get 3", "blocks in order and earn health!");
				new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-32, 300, 64, "You can move your mouse over the", "icons at the top of the screen");
				new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-32, 300, 64, "Also some abilities: one passive,", "one for UP and DOWN keys each");
				new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-32, 300, 64, "Everyone loves XP", "and leveling up...right?");
				new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-32, 300, 64, "Still not good enough?", "Then let's make it better!");
				
				Game.healthshake=-300;
				healthleft=0;
				Game.intro=3;
				Game.player.useup=true;
				Game.player.up_pos_x=Bouncer.WIDTH/2;
				Game.player.up_pos_y=Bouncer.HEIGHT/2;
				Game.player.hspeed=0;
			}
			
			xpleft=0;
			gravity=1.0F;
			
			if(!Game.player.useup && Ability.p_coolleft<=0 && deadtick<=0 && Game.intro>=2){
				Ability.p_coolleft=Ability.p_cool;
				if(healthleft>0){
					healthleft=0;
					if(Game.player.usedown) Game.snd_hit.playAsSoundEffect(0.7F/4, 0.2F, false);
					else Game.snd_hit.playAsSoundEffect(0.7F, 0.2F, false);
				}
			}else if(deadtick<=0){
				Game.shake=20;
				if(healthleft>0){
					if(Game.player.usedown) Game.snd_hit.playAsSoundEffect(1.0F/4, 0.2F, false);
					else Game.snd_hit.playAsSoundEffect(1.0F, 0.2F, false);
					Game.healthshake=healthleft*2;
					healthleft=0;
				}
			}
			
			Game.player.streak=0;
		}
	}
	
	public static int getCount(){
		return count;
	}
	
	public static ArrayList<Particle> getInstances(){
		return instances;
	}
}