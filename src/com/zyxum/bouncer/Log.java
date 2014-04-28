package com.zyxum.bouncer;

public class Log {
	public static void debug(Object obj){
		if(Bouncer.debug) System.out.println("[DEBUG] "+obj);
	}
	
	public static void info(Object obj){
		System.out.println("[INFO] "+obj);
	}
}