package com.zyxum.bouncer;

import java.util.ArrayList;

public class QTEBlock {
	public boolean exists;
	public int x,y,id,mones;
	public static int count;
	private static ArrayList<QTEBlock> instances = new ArrayList<QTEBlock>();
	
	public QTEBlock(int x,int y,int mones){
		this.exists=true;
		this.x=x;
		this.y=y;
		this.mones=mones;
		this.id=++count;
		
		instances.add(this);
	}
	
	public static ArrayList<QTEBlock> getInstances(){
		return instances;
	}
}