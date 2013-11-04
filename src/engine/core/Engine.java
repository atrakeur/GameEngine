package engine.core;

import engine.debug.IProfiler;
import engine.entity.GameWorld;

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
	private IPhysics physics;
	private ITime time;
	private IProfiler profiler;
	
	private ILevel level;
	private ILevel nextlevel;
	
	public Engine() throws Exception
	{
		//Check then setup the instance
		if(instance != null)
			throw new IllegalStateException("Can't instantiate Engine class twice");
		
		instance = this;
		
		Container.setInjectionPaths(this.getInjectionPaths());
		
		initEngine();
		
		//start the engine
		startEngine();
		
		time.startFrame();
		time.endFrame();
		
		while(run)
		{
			profiler.start("/");
			
			time.startFrame();
			
			//need to quit?
			if(render.isCloseRequested())
				run = false;
			
			update(time.getDelta());
			
			profiler.start("/Sleep");
			time.endFrame();
			try {Thread.sleep(time.getSleepTime());} catch(Exception e){}
			profiler.end("/Sleep");
			
			profiler.end("/");
		}
		
		level.onQuit();
		this.onQuit();
		render.destroy();
		
		System.out.println(profiler.toString());
	}
	
	/******************************************************
	 * Internal parts
	 ******************************************************/
	
	/**
	 * Init the engine
	 */
	private void initEngine() {
		render = (IRenderer) Container.inject(IRenderer.class);
		world = (GameWorld) Container.inject(GameWorld.class);
		physics = (IPhysics) Container.inject(IPhysics.class);
		time = Container.inject(ITime.class);
		profiler = Container.inject(IProfiler.class);
	}
	
	/**
	 * Start the engine
	 * @throws Exception
	 */
	private void startEngine() throws Exception {
		this.onInit();
		run = true;
		render.create();
		this.onStart();
		
		if(nextlevel == null)
			throw new IllegalStateException("A level must be set in onInit or onStart");
		
		level = nextlevel;
		level.onStart();
	}
	
	/**
	 * Called each frame to update
	 * @param delta 
	 */
	private void update(float delta) {
		//update all stuff
		profiler.start("/Level/Update");
		level.onUpdate(delta);
		this.onUpdate(delta);
		profiler.end("/Level/Update");
		
		//update build-in parts
		profiler.start("/World/");
		world.update(delta);
		profiler.end("/World/");
		
		profiler.start("/Physics/");
		physics.update(delta);
		profiler.end("/Physics/");
		
		profiler.start("/Render/");
		render.update(delta);
		profiler.end("/Render/");
		
		//switch to another level if needed
		if(nextlevel != null && nextlevel != level)
		{
			profiler.start("/Level/Change");
			
			if(level != null)
				level.onQuit();
			world.clear();
			level = nextlevel;
			level.onStart();
			
			profiler.end("/Level/Change");
		}
	}
	
	/******************************************************
	 * Public API
	 ******************************************************/
	/**
	 * Load a new level at the end of the current frame
	 * @param level the level
	 */
	public void loadLevel(ILevel level) {
		if(level == null)
			throw new IllegalArgumentException("Level to load can't be null");
		
		nextlevel = level;
	}
	
	/**
	 * @return the instance of the engine
	 */
	public static Engine getInstance()
	{
		if(instance == null)
			throw new IllegalStateException("Can't get instance of Engine before starting it");
		
		return instance;
	}
	
	/******************************************************
	 * User callbacks
	 ******************************************************/
	
	/**
	 * @return additionnal injection paths for the IoC
	 */
	public abstract String getInjectionPaths();
	
	/**
	 * Called on engine init
	 */
	public abstract void onInit();
	
	/**
	 * Called on engine start
	 */
	public void onStart() {}
	
	/**
	 * Called each frame on engine update
	 * @param delta 
	 */
	public void onUpdate(float delta) {}
	
	/**
	 * Called jst before engine exiting
	 */
	public void onQuit() {}

}
