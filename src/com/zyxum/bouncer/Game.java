package com.zyxum.bouncer;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

public class Game implements GameState{
	public static File save;
	public static Audio snd_hit,snd_lvlup,snd_death,snd_b1,snd_b2,snd_b3,snd_fail;
	public static boolean afk,pause,msg1,deathsound,first=true;
	public static GameContainer public_gc;
	public static int[] shakeArray={-5,-4,-3,-2,-1,0,1,2,3,4,5};
	public static int public_delta,xp,xplevel,offset_y,offset_x,shake,afktimer,health,healthshake,intro,
						qtecounter,abilitycounter,qtefailcounter;
	public static Player player;
	public static Image back,wall,rock,wall_green,wall_red;
	public static Random random;
	public static Ticker ticker;
	public static AppGameContainer app;
	public static Message deathmsg;
	
	@Override public void init(GameContainer gc, StateBasedGame game) throws SlickException{
		public_gc=gc;
		gc.getGraphics().setBackground(Color.white);
		app=(AppGameContainer) (gc);
		
		abilitycounter=0;
		qtecounter=0;
		qtefailcounter=0;
		deathmsg=null;
		deathsound=false;
		msg1=false;
		pause=false;
		health=150;
		healthshake=0;
		xp=1;
		xplevel=1;
		shake=0;
		offset_x=0;
		offset_y=0;
		
		save=new File("save");
		if(save.exists()) intro=3;
		else intro=0; //TODO debug
		
		random=new Random();
		player=new Player(Bouncer.WIDTH/2-16,Bouncer.HEIGHT/2-16);
		ticker=new Ticker();
		
		rock=new Image("res/rock.png").getScaledCopy(0.5F);
		back=new Image("res/back_wall.png");
		wall=new Image("res/wall.png");
		wall_green=new Image("res/wall_green.png");
		wall_red=new Image("res/wall_red.png");
		
		try {
			snd_hit=AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/hit.ogg"));
			snd_lvlup=AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/lvlup.ogg"));
			snd_death=AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/death.ogg"));
			snd_b1=AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/blip1.ogg"));
			snd_b2=AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/blip2.ogg"));
			snd_b3=AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/blip3.ogg"));
			snd_fail=AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/fail.ogg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i=-32;i<Bouncer.WIDTH+32;i+=32){
			new Wall(i, 0);
			new Wall(i, Bouncer.HEIGHT-32);
		}
		
		Ability.init();
		
		if(intro==0){
			new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-32, 256, 64, "This is the final version,", "so enjoy Bouncer!");
			new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-32, 256, 64, "Move with arrow keys and", "avoid rocks (blue things)!");
			new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-32, 256, 64, "In bouncer,", "you bounce constantly.");
			new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-32, 256, 64, "Hello, and welcome", "to Bouncer!");
		}else if(intro==3 && first){
			new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-32, 256, 64, "", "Welcome back!");
			first=false;
		}
	}

	@Override public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException{		
		for(int i=-32;i<Bouncer.WIDTH+32;i+=32) for(int j=-32;j<Bouncer.HEIGHT+32;j+=32)
			if((intro==1 && !Particle.msg.isActive()) || intro>=2) g.drawImage(back, offset_x+i, offset_y+j);
		
		player.render(g);
		
		g.setColor(Color.blue);
		for(Particle particle : Particle.getInstances()){
			if(particle.x>-32 && particle.x<Bouncer.WIDTH+32 && particle.deadtick<5000) if((intro==1 && !Particle.msg.isActive()) || intro>=2)
				g.drawImage(rock, offset_x+particle.x, offset_y+particle.y);
			else g.fillOval(offset_x+particle.x, offset_y+particle.y, 16, 16);
		}
		
		for(Wall wall : Wall.getInstances()){
			if((intro==1 && !Particle.msg.isActive()) || intro>=2) g.drawImage(back, offset_x+wall.x, offset_y+wall.y);
			else{
				g.setColor(Color.black);
				g.fillRect(offset_x+wall.x, offset_y+wall.y, 32, 32);
			}
		}
		
		g.setColor(Color.white);
		for(QTEBlock qteBlock : QTEBlock.getInstances()){
			if(qteBlock.exists){
				g.drawImage((qteBlock.mones==1)? wall_green : wall_red, offset_x+qteBlock.x, offset_y+qteBlock.y);
				g.drawString(""+qteBlock.mones, offset_x+qteBlock.x+8, offset_y+qteBlock.y+8);
			}
		}
		
		if(intro>1 || (intro==1 && !Particle.msg.isActive())) Hud.render(g);
		if(intro>=2) Hud.mouseOver(gc.getInput().getMouseX(), gc.getInput().getMouseY(), g);
		
		if(player.useup){
			g.setColor(new Color(255,0,0,0.5F));
			g.fillOval(player.up_pos_x-player.up_size/2, player.up_pos_y-player.up_size/2, player.up_size, player.up_size);
			g.setColor(Color.red);
			g.drawOval(player.up_pos_x-player.up_size/2, player.up_pos_y-player.up_size/2, player.up_size, player.up_size);
		}
		
		for(Message message : Message.getInstances()) message.render(g);
		
		if(deathmsg != null && deathmsg.isActive()){
			g.setColor(Color.black);
			g.drawString("XP: "+(((xplevel-1)*Bouncer.WIDTH)+xp), Bouncer.WIDTH/2-120, Bouncer.HEIGHT/2-80);
			g.drawString("Longest Streak: "+player.maxstreak, Bouncer.WIDTH/2-120, Bouncer.HEIGHT/2-64);
			g.drawString("Abilities Used: "+abilitycounter, Bouncer.WIDTH/2-120, Bouncer.HEIGHT/2-48);
			g.drawString("QTEs Done: "+qtecounter, Bouncer.WIDTH/2-120, Bouncer.HEIGHT/2-32);
			g.drawString("QTEs Failed: "+qtefailcounter, Bouncer.WIDTH/2-120, Bouncer.HEIGHT/2-16);
		}
	}

	@Override public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException{
		boolean tempMessageActive=false;
		for(Message message : Message.getInstances()) if(message.isActive()) tempMessageActive=true;
		if(!tempMessageActive) pause=false;
		
		if(intro==3 && !save.exists()) try{save.createNewFile();}catch (IOException e){e.printStackTrace();}
		
		if(!pause){
			public_delta=delta;
			
			if(intro==0) app.setShowFPS(true);
			else app.setShowFPS(false);
			
			if(intro>3) intro=3;
			
			if(!gc.getInput().isKeyDown(Input.KEY_LEFT) && !gc.getInput().isKeyDown(Input.KEY_RIGHT)
					&& !gc.getInput().isKeyDown(Input.KEY_UP) && !gc.getInput().isKeyDown(Input.KEY_DOWN)) afk=true;
			else afk=false;
			
			if(!player.usedown && intro>=1){
				if(afk==true) afktimer+=.1*delta;
				else afktimer=0;
			}else afktimer=0;
			
			if(healthshake>0){
				if(healthshake%2==0 && health>0) health--;
				healthshake--;
			}if(healthshake<0){
				if(healthshake%2==0 && health<150) health++;
				healthshake++;
			}
			
			if(deathmsg!= null && !deathmsg.isActive()){
				health=150;
				deathmsg=null;
				gc.reinit();
			}
			
			if(health<=0 && intro==3){
				health=0;
				if(!deathsound && !snd_death.isPlaying()){
					if(player.usedown) snd_death.playAsSoundEffect(1.0F, 0.7F, false);
					else snd_death.playAsSoundEffect(1.0F/4, 0.7F, false);
					deathsound=true;
				}else{
					if(deathmsg==null){
						deathmsg=new Message(Bouncer.WIDTH/2-128, Bouncer.HEIGHT/2-128, 256, 256, "You died!", "------");
						for(Particle particle : Particle.getInstances()){
							if(particle.dir==1) particle.x=Bouncer.WIDTH+64;
							else particle.x=-64;
						}
						health=150;
					}
				}
			}
			
			if(shake>0 && intro>=2){
				offset_x=shakeArray[random.nextInt(shakeArray.length)];
				offset_y=shakeArray[random.nextInt(shakeArray.length)];
				shake--;
			}else{
				offset_x=0;
				offset_y=0;
			}
			
			if(xp>Bouncer.WIDTH && intro>=2){
				xp=1;
				xplevel++;
				if(Game.player.usedown) snd_lvlup.playAsSoundEffect(1.0F/4, 1.0F, false);
				else snd_lvlup.playAsSoundEffect(1.0F, 1.0F, false);
			}
			
			player.update(delta);
			ticker.update(delta);
			Ability.update(delta);
			
			for(Particle particle : Particle.getInstances()) if((!(particle.dir==1 && particle.x>Bouncer.WIDTH+32)
					&& !(particle.dir==-1 && particle.x<-32)) || particle.xpleft>0) particle.update(delta);
		}
	}
	
	//mouse events
	@Override public void mouseClicked(int button, int x, int y, int count) {}
	@Override public void mouseDragged(int oldx, int oldy, int newx, int newy) {	}
	@Override public void mouseMoved(int oldx, int oldy, int newx, int newy) {}
	@Override public void mousePressed(int button, int x, int y) {}
	@Override public void mouseReleased(int button, int x, int y) {}
	@Override public void mouseWheelMoved(int change) {}
	
	//key events
	@Override public void keyPressed(int key, char c) {
		GameInput.keyPress(key, c);
	}
	@Override public void keyReleased(int key, char c) {
		GameInput.keyRelease(key, c);
	}
	
	//controller events
	@Override public void controllerButtonPressed(int controller, int button) {}
	@Override public void controllerButtonReleased(int controller, int button) {}
	
	@Override public void controllerDownPressed(int controller) {}
	@Override public void controllerLeftPressed(int controller) {}
	@Override public void controllerRightPressed(int controller) {}
	@Override public void controllerUpPressed(int controller) {}
	
	@Override public void controllerDownReleased(int controller) {}
	@Override public void controllerLeftReleased(int controller) {}
	@Override public void controllerRightReleased(int controller) {}
	@Override public void controllerUpReleased(int controller) {}
	
	//input events
	@Override public void inputEnded() {}
	@Override public void inputStarted() {}
	@Override public boolean isAcceptingInput() {
		return true;
	}
	@Override public void setInput(Input input) {}
	
	//state events
	@Override public void enter(GameContainer gc, StateBasedGame game) throws SlickException {}
	@Override public void leave(GameContainer gc, StateBasedGame game) throws SlickException {}
	
	@Override public int getID() {
		return Bouncer.game;
	}
}