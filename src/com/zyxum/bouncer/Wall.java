package com.zyxum.bouncer;

import java.util.ArrayList;

public class Wall {
	public int x,y,id;
	private static int count;
	private static ArrayList<Wall> instances = new ArrayList<Wall>();
	
	public Wall(int x,int y){
		this.x=x;
		this.y=y;
		id=++count;
		
		instances.add(this);
	}
	
	public static int getCount(){
		return count;
	}
	
	public static ArrayList<Wall> getInstances(){
		return instances;
	}
}