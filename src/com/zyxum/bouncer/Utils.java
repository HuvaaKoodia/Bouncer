package com.zyxum.bouncer;

public class Utils{
	public static double distance(int x1, int y1, int x2, int y2){
		int valix,valiy;
		
		if(x1>x2) valix=x1-x2;
		else if(x2>x1) valix=x2-x1;
		else valix=0;

		if(y1>y2) valiy=y1-y2;
		else if(y2>y1) valiy=y2-y1;
		else valiy=0;
		
		return Math.sqrt(Math.pow(valix, 2)+Math.pow(valiy, 2));
	}
}