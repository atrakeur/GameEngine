package engine.core;

import engine.entity.GameWorld;
import net.usikkert.kouinject.DefaultInjector;
import net.usikkert.kouinject.Injector;

/**
 * Main class of the engine
 * 
 * Used as a starting point of the engine, take care of the main loop,
 * an host the dependency injection container
 * 
 * @author Valentin 'Atrakeur' Letourneur <atrakeur@gmail.com>
 *
 */
public abstract class Engine {
	
	private static Engine instance;
	
	private boolean run;
	
	private IRenderer render;
	private GameWorld world;
	
	public Engine() throws Exception
	{
		//Check then setup the instance
		if(instance != null)
			throw new IllegalStateException("Can't instantiate Engine class twice");
		
		instance = this;
		
		Container.setInjectionPaths(this.getInjectionPaths());
		
		render = (IRenderer) Container.inject(IRenderer.class);
		world = (GameWorld) Container.inject(GameWorld.class);
		
		//start the engine
		this.onInit();
		run = true;
		render.create();
		this.onStart();
		
		while(run)
		{
			if(render.isCloseRequested())
				run = false;
			
			this.onUpdate();
			
			render.update();
			
			try {Thread.sleep(10);} catch(Exception e){}
		}
		
		this.onQuit();
		render.destroy();
	}
	
	public static Engine getInstance()
	{
		if(instance == null)
			throw new IllegalStateException("Can't get instance of Engine before starting it");
		
		return instance;
	}
	
	public abstract String getInjectionPaths();
	
	public abstract void onInit();
	
	public void onStart() {}
	
	public void onUpdate() {}
	
	public void onQuit() {}

}
