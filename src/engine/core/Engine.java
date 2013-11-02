package engine.core;

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
	
	private ILevel level;
	private ILevel nextlevel;
	
	public Engine() throws Exception
	{
		System.out.println(System.getProperty("java.version"));
		
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
			time.startFrame();
			
			//need to quit?
			if(render.isCloseRequested())
				run = false;
			
			update(time.getDelta());
			
			time.endFrame();
			
			System.out.println(time.getCurrentFps());
			
			//wait for the next pass
			try {Thread.sleep(time.getSleepTime());} catch(Exception e){}
		}
		
		level.onQuit();
		this.onQuit();
		render.destroy();
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
		level.onUpdate(delta);
		this.onUpdate(delta);
		
		//update build-in parts
		world.update(delta);
		
		physics.update(delta);
		
		render.update(delta);
		
		//switch to another level if needed
		if(nextlevel != null && nextlevel != level)
		{
			if(level != null)
				level.onQuit();
			world.clear();
			level = nextlevel;
			level.onStart();
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
