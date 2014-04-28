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
		//xp bar
		if(Game.intro>=2){
			g.setColor(new Color(0, 0, 255, 0.3F));
			g.fillRect(Game.xp, 0, Bouncer.WIDTH, 8);
			
			g.setColor(new Color(0, 0, 255, 0.8F));
			g.fillRect(0, 0, Game.xp, 8);
			
			g.setColor(Color.green);
			g.drawString("LVL "+Game.xplevel, Bouncer.WIDTH/2-24, 0);
		}
		
		//health bar
		if(Game.deathmsg != null && Game.deathmsg.isActive()) {
			g.setColor(new Color(255, 0, 0, 0.3F));
			g.fillRect(0, Bouncer.HEIGHT-8, Bouncer.WIDTH, 8);
			
			g.setColor(Color.green);
			g.drawString("HP 0", Bouncer.WIDTH/2-32, Bouncer.HEIGHT-16);
		}else{
			g.setColor(new Color(255, 0, 0, 0.3F));
			g.fillRect(0, Bouncer.HEIGHT-8, Bouncer.WIDTH, 8);
			
			g.setColor(new Color(255, 0, 0, 0.8F));
			g.fillRect(0, Bouncer.HEIGHT-8, (int) (Game.health*4.8), 8);
			
			g.setColor(Color.green);
			g.drawString("HP "+Game.health, Bouncer.WIDTH/2-32, Bouncer.HEIGHT-16);
		}
		
		//debug info
		if(Bouncer.debug){
			g.setColor(Color.black);
			g.drawString("Streak: "+Game.player.streak, 16, 32);
			g.drawString("FPS: "+Game.public_gc.getFPS(), 16, 48);
			g.drawString("AFK Time: "+Game.afktimer, 16, 64);
			g.drawString("Intro: "+Game.intro, 16, 80);
			g.drawString("getTick: "+Game.ticker.getTick(), 16, 96);
		}
		
		if(Game.intro>=2){
			//abilities
			g.setColor(new Color(255,255,255,0.5F));
			g.fillRect(Bouncer.WIDTH/2-48, 16, 96, 32);
			
			if(Ability.p_coolleft>0){
				g.setColor(Color.darkGray);
				g.fillRect(Bouncer.WIDTH/2-48, 16, 32, 32);
			}
			
			if(Ability.up_coolleft>0){
				g.setColor(Color.darkGray);
				g.fillRect(Bouncer.WIDTH/2-16, 16, 32, 32);
			}
			
			if(Ability.down_coolleft>0){
				g.setColor(Color.darkGray);
				g.fillRect(Bouncer.WIDTH/2+16, 16, 32, 32);
			}
			
			g.setColor(new Color(255,255,255,0.8F));
			g.drawRect(Bouncer.WIDTH/2-48, 16, 32, 32);
			g.drawRect(Bouncer.WIDTH/2-16, 16, 32, 32);
			g.drawRect(Bouncer.WIDTH/2+16, 16, 32, 32);
			
			//ability icons
			g.setColor(Color.black);
			g.drawString("Sh", Bouncer.WIDTH/2-36, 32);
			
			g.drawString("Up", Bouncer.WIDTH/2-14, 16);
			g.drawString("Sw", Bouncer.WIDTH/2-4, 32);
			
			g.drawString("Dwn", Bouncer.WIDTH/2+16, 16);
			g.drawString("Ts", Bouncer.WIDTH/2+30, 32);
			
			//ability cools
			if(Ability.p_coolleft>0){
				g.setColor(new Color(255,255,255,0.5F));
				g.fillRect(Bouncer.WIDTH/2-48, 48, 32, 16);
				g.setColor(Color.black);
				g.drawString(""+Ability.p_coolleft, Bouncer.WIDTH/2-40, 48);
			}
			
			if(Ability.up_coolleft>0){
				g.setColor(new Color(255,255,255,0.5F));
				g.fillRect(Bouncer.WIDTH/2-16, 48, 32, 16);
				g.setColor(Color.black);
				g.drawString(""+Ability.up_coolleft, Bouncer.WIDTH/2-8, 48);
			}
			
			if(Ability.down_coolleft>0){
				g.setColor(new Color(255,255,255,0.5F));
				g.fillRect(Bouncer.WIDTH/2+16, 48, 32, 16);
				if(!Game.player.usedown){
					g.setColor(Color.black);
					g.drawString(""+Ability.down_coolleft, Bouncer.WIDTH/2+24, 48);
				}else{
					g.setColor(Color.red);
					g.drawString(""+Game.player.down_time, Bouncer.WIDTH/2+24, 48);
				}
			}
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