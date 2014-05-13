package com.zyxum.bouncer;

public class Ticker {
	public int ptick,ptickmax,qtick,qtickmax;
	
	public Ticker(){
		this.ptick=0;
		this.ptickmax=getTick();
		
		this.qtick=0;
		this.qtickmax=Game.random.nextInt(10000)+10000;
	}
	
	public void update(int delta){
		this.ptickmax=getTick();
		
		if(Game.player.usedown) ptick+=delta/4;
		else ptick+=delta;
		
		if(Game.player.usedown) qtick+=delta/4;
		else qtick+=delta;
		
		if(ptick>ptickmax || (ptick>ptickmax/4 && Game.afktimer>500)){
			if(Particle.getCount()%2==0) new Particle((Bouncer.HEIGHT/2)+Game.random.nextInt(Bouncer.HEIGHT/2-80), (Game.random.nextBoolean()==true? 1 : -1), 4,10,20);
			else new Particle(32+Game.random.nextInt(Bouncer.HEIGHT/2-32), (Game.random.nextBoolean()==true? 1 : -1), 4,15+Game.random.nextInt(20),20);
			
			ptick=0;
		}
		
		if(qtick>qtickmax && Game.intro==3){
			int tempCount=0;
			for(QTEBlock qteBlock : QTEBlock.getInstances()) if(qteBlock.exists) tempCount++;
			
			if(tempCount==0 && Game.random.nextBoolean()){
				if(Game.random.nextBoolean()){
					new QTEBlock(Game.random.nextInt(Bouncer.WIDTH/2), 0, 1);
					new QTEBlock(Bouncer.WIDTH/4+Game.random.nextInt(Bouncer.WIDTH/2), Bouncer.HEIGHT-32, 2);
					new QTEBlock(Bouncer.WIDTH/2+Game.random.nextInt(Bouncer.WIDTH/2-64), 0, 3);
				}else{
					new QTEBlock(Game.random.nextInt(Bouncer.WIDTH/2), 0, 3);
					new QTEBlock(Bouncer.WIDTH/4+Game.random.nextInt(Bouncer.WIDTH/2), Bouncer.HEIGHT-32, 2);
					new QTEBlock(Bouncer.WIDTH/2+Game.random.nextInt(Bouncer.WIDTH/2-64), 0, 1);
				}
			}else if(tempCount==0){
				if(Game.random.nextBoolean()){
					new QTEBlock(Game.random.nextInt(Bouncer.WIDTH/2), Bouncer.HEIGHT-32, 1);
					new QTEBlock(Bouncer.WIDTH/4+Game.random.nextInt(Bouncer.WIDTH/2), 0, 2);
					new QTEBlock(Bouncer.WIDTH/2+Game.random.nextInt(Bouncer.WIDTH/2-64), Bouncer.HEIGHT-32, 3);
				}else{
					new QTEBlock(Game.random.nextInt(Bouncer.WIDTH/2), Bouncer.HEIGHT-32, 3);
					new QTEBlock(Bouncer.WIDTH/4+Game.random.nextInt(Bouncer.WIDTH/2), 0, 2);
					new QTEBlock(Bouncer.WIDTH/2+Game.random.nextInt(Bouncer.WIDTH/2-64), Bouncer.HEIGHT-32, 1);
				}
			}
			
			qtick=0;
		}else if(Game.intro<3) qtick=0;
	}
	
	public int getTick(){
		return 450;
	}
}