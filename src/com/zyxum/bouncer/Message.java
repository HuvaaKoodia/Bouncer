package com.zyxum.bouncer;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Message {
	public String line1,line2;
	public int x,y,width,height,id,lenght1,lenght2;
	private boolean active;
	private static int count;
	private static ArrayList<Message> instances = new ArrayList<Message>();
	
	public Message(int x,int y,int width,int height,String line1,String line2){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.id=++count;
		this.active=true;
		this.line1=line1;
		this.line2=line2;
		this.lenght1=line1.toCharArray().length;
		this.lenght2=line2.toCharArray().length;
		
		instances.add(this);
	}
	
	public void render(Graphics g){
		if(active){
			Game.pause=true;
			
			g.setColor(Color.lightGray);
			g.fillRect(x, y, width, height);
			g.setColor(Color.darkGray);
			g.drawRect(x, y, width, height);
			
			g.setColor(Color.black);
			g.drawString(line1, (float) (x+(width/2)-(lenght1*9)/2), y);
			g.drawString(line2, (float) (x+(width/2)-(lenght2*9)/2), y+16);
			
			g.drawString("(PRESS SPACE)", x+width-116, y+height-16);
		}
	}
	
	public static int getCount(){
		return count;
	}
	
	public static ArrayList<Message> getInstances(){
		return instances;
	}
	
	public void enable(){
		active=true;
	}
	
	public void disable(){
		active=false;
	}
	
	public boolean isActive(){
		return active;
	}
}