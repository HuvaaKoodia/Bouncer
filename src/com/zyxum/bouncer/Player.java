package com.zyxum.bouncer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Player {
	public boolean useup,usedown;
	public int x,y,grav_tick,dir,streak,maxstreak,up_pos_x,up_pos_y,up_size,down_time;
	public float hspeed,gravity,speedplier;
	
	public Player(int x,int y){
		this.x=x;
		this.y=y;
		this.dir=-1;
		this.gravity=0;
		this.speedplier=1.0F;
		this.useup=false;
		this.usedown=false;
		this.streak=0;
		this.maxstreak=0;
	}
	
	private void qteHit(){
		int tempCount=0;
		for(QTEBlock qteBlock : QTEBlock.getInstances()){
			qteBlock.mones--;
			if(qteBlock.exists) tempCount++;
			if(qteBlock.mones==0) qteBlock.exists=false;
		}
		switch(tempCount){
			case 1:
				Game.qtecounter++;
				Game.healthshake=-40;
				for(Particle particle : Particle.getInstances()) if(!(particle.dir==1 && particle.x>Bouncer.WIDTH+32) && !(particle.dir==-1 && particle.x<-32)) particle.xpleft+=10;
				Game.snd_b3.playAsSoundEffect(1.0F, 0.5F, false);
				break;
			case 2:
				Game.qtefailcounter++;
				Game.snd_b2.playAsSoundEffect(1.0F, 0.5F, false);
				break;
			case 3:
				Game.qtefailcounter++;
				Game.snd_b1.playAsSoundEffect(1.0F, 0.5F, false);
				break;
		}
	}
	
	public void render(Graphics g){
		if(Game.intro>=1){
			if(Game.intro>=2){
				if(Ability.p_coolleft<=0){
					g.setColor(Color.blue);
					g.drawOval(x-Bouncer.WIDTH-16, y-16, 64, 64);
					g.setColor(new Color(0,0,255,0.2F));
					g.fillOval(x-Bouncer.WIDTH-16, y-16, 64, 64);
					
					g.setColor(Color.blue);
					g.drawOval(x-16, y-16, 64, 64);
					g.setColor(new Color(0,0,255,0.2F));
					g.fillOval(x-16, y-16, 64, 64);
					
					g.setColor(Color.blue);
					g.drawOval(x+Bouncer.WIDTH-16, y-16, 64, 64);
					g.setColor(new Color(0,0,255,0.2F));
					g.fillOval(x+Bouncer.WIDTH-16, y-16, 64, 64);
				}
			}
			
			g.setColor(Color.red);
			g.fillRect(Game.offset_x+x, Game.offset_y+y, 32, 32);
			g.fillRect(Game.offset_x+x+Bouncer.WIDTH, Game.offset_y+y, 32, 32);
			g.fillRect(Game.offset_x+x-Bouncer.WIDTH, Game.offset_y+y, 32, 32);
		}else if(Game.intro==0){
			g.setColor(Color.red);
			g.fillRect(Game.offset_x+x, Game.offset_y+y, 32, 32);
			g.fillRect(Game.offset_x+x+Bouncer.WIDTH, Game.offset_y+y, 32, 32);
			g.fillRect(Game.offset_x+x-Bouncer.WIDTH, Game.offset_y+y, 32, 32);
		}
	}
	
	public void update(int delta){
		if(streak>maxstreak) maxstreak=streak;
		
		if(useup){
			if(usedown) up_size+=(delta*2)/4;
			else up_size+=delta*2;
			
			if(up_size>Bouncer.WIDTH*1.2) useup=false;
			
			for(Particle particle : Particle.getInstances()) if(Utils.distance(up_pos_x, up_pos_y, particle.x, particle.y)<=up_size){
				particle.gravity=1.0F;
				particle.healthleft=0;
				if(Game.player.usedown) Game.snd_hit.playAsSoundEffect(0.4F/4, 0.05F, false);
				else Game.snd_hit.playAsSoundEffect(0.4F, 0.05F, false);
			}
		}
		
		if(usedown) if(down_time<=0) usedown=false;
		
		if(x>Bouncer.WIDTH+32) x-=Bouncer.WIDTH;
		if(x<Bouncer.WIDTH-32) x+=Bouncer.WIDTH;
		
		if(usedown){
			y+=(dir*(delta/2)*speedplier)/4;
			x+=((delta*hspeed)/2)/4;
		}else{
			y+=dir*(delta/2)*speedplier;
			x+=(delta*hspeed)/2;
		}
		
		if(y<=32 && dir==-1){
			dir=1;
			boolean tempBool=false;
			int tempCount=0;
			
			for(QTEBlock qteBlock : QTEBlock.getInstances()){
				if(qteBlock.exists && qteBlock.x-32<x-Bouncer.WIDTH && qteBlock.x+32>x-Bouncer.WIDTH
						&& qteBlock.y==0 && qteBlock.mones==1){
					tempBool=true;
					qteHit();
				}
			}
			
			for(QTEBlock qteBlock : QTEBlock.getInstances()) if(qteBlock.exists) tempCount++;
			if(tempCount>0 && tempCount<3 && !tempBool){
				for(QTEBlock qteBlock : QTEBlock.getInstances()) qteBlock.exists=false;
				Game.snd_fail.playAsSoundEffect(1.0F, 0.5F, false);
			}
			
		}
		if(y>=Bouncer.HEIGHT-64 && dir==1){
			dir=-1;
			boolean tempBool=false;
			int tempCount=0;

			for(QTEBlock qteBlock : QTEBlock.getInstances()){
				if(qteBlock.exists && qteBlock.x-32<x-Bouncer.WIDTH && qteBlock.x+32>x-Bouncer.WIDTH
						&& qteBlock.y==Bouncer.HEIGHT-32 && qteBlock.mones==1){
					tempBool=true;
					qteHit();
				}
			}
			
			for(QTEBlock qteBlock : QTEBlock.getInstances()) if(qteBlock.exists) tempCount++;
			if(tempCount>0 && tempCount<3 && !tempBool){
				for(QTEBlock qteBlock : QTEBlock.getInstances()) qteBlock.exists=false;
				Game.snd_fail.playAsSoundEffect(1.0F, 0.5F, false);
			}
		}
	}
}