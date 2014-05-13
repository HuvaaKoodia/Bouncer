package com.zyxum.bouncer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Hud{
	public static void drawinfoRect(int x,int y,int width,int height,Graphics g){
		g.setColor(new Color(0,0,255,0.5F));
		g.fillRect(x, y, width, height);
		g.setColor(new Color(0,0,255,0.8F));
		g.drawRect(x, y, width, height);
	}
	
	public static void render(Graphics g){
		
		//debug info
		if(Bouncer.debug){
			g.setColor(Color.black);
			g.drawString("Streak: "+Game.player.streak, 16, 32);
			g.drawString("FPS: "+Game.public_gc.getFPS(), 16, 48);
			g.drawString("AFK Time: "+Game.afktimer, 16, 64);
			g.drawString("Intro: "+Game.intro, 16, 80);
			g.drawString("getTick: "+Game.ticker.getTick(), 16, 96);
		}
	}
	
	public static void mouseOver(int x,int y,Graphics g){
		if(x>Bouncer.WIDTH/2-48 && x<Bouncer.WIDTH/2-16 && y>16 && y<48){
			drawinfoRect(x+16, y, 164, 66, g);
			g.setColor(Color.white);
			g.drawString("SHIELD", x+16, y);
			g.drawString("Shields the player", x+16, y+16);
			g.drawString("for one attack", x+16, y+32);
			g.drawString("every "+Ability.p_cool+" seconds", x+16, y+48);
		}
		
		if(x>Bouncer.WIDTH/2-16 && x<Bouncer.WIDTH/2+16 && y>16 && y<48){
			drawinfoRect(x+16, y, 200, 64, g);
			g.setColor(Color.white);
			g.drawString("UP - SHOCKWAVE", x+16, y);
			g.drawString("Launches a shockwave", x+16, y+16);
			g.drawString("that kills all enemies", x+16, y+32);
			g.drawString(Ability.up_cool+" second cooldown", x+16, y+48);
		}
		
		if(x>Bouncer.WIDTH/2+16 && x<Bouncer.WIDTH/2+48 && y>16 && y<48){
			drawinfoRect(x+16, y, 200, 64, g);
			g.setColor(Color.white);
			g.drawString("DOWN - TIME SLOW", x+16, y);
			g.drawString("Slows time, the next", x+16, y+16);
			g.drawString("5 seconds will be 20", x+16, y+32);
			g.drawString(Ability.down_cool+" second cooldown", x+16, y+48);
		}
	}
}