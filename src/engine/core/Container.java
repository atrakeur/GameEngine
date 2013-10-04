package engine.core;

import net.usikkert.kouinject.DefaultInjector;
import net.usikkert.kouinject.Injector;

public class Container {
	
	private static Container instance;
	
	private Injector injector;
	
	public static Container getInstance()
	{
		if(instance == null)
			instance = new Container();
		
		return instance;
	}
	
	private Container()
	{
		//Setup injector
		injector = new DefaultInjector("engine");
	}
	
	public static Injector getInjector()
	{
		return getInstance().injector;
	}
	
	public static <T> T inject(Class<T> classType)
	{
		return getInjector().getBean(classType);
	}
	
	public static void setInjectionPaths(String paths)
	{
		if(paths == null)
			throw new IllegalArgumentException("Paths can't be null");
		
		if(paths.trim().equals(""))
			return;
		
		if(!paths.startsWith(":"))
			paths = ":"+paths;
		
		getInstance().injector = new DefaultInjector("engine" + paths);
	}

}
