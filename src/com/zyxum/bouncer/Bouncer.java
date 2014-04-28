package com.zyxum.bouncer;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Bouncer extends StateBasedGame{
	public static boolean debug=false;
	public static final int WIDTH=640,HEIGHT=480;
	
	public static final int game=1;
	
	public Bouncer() {
		super("Bouncer");
		this.addState(new Game());
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException{
		this.enterState(game);
	}
	
	public static void main(String[] args) throws SlickException {
		if(args!=null && !debug) if(args.toString().contains("debug")) debug=true;
        AppGameContainer app = new AppGameContainer(new Bouncer());
        app.setShowFPS(false);
        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.setAlwaysRender(true);
        app.setUpdateOnlyWhenVisible(true);
        app.setVSync(true);
        app.start();
   }
}