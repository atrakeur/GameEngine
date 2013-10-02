package engine.entity;

import java.util.ArrayList;

public class GameWorld {
	
	private ArrayList<Entity> entities;
	
	public GameWorld()
	{
		entities = new ArrayList<Entity>();
	}
	
	public void addEntity(Entity e)
	{
		if(e.hasParentWorld())
			throw new IllegalArgumentException("Can't add an entity to a world twice.");
		
		entities.add(e);
		e.setParentWorld(this);
		e.onCreate();
	}
	
	public int getEntityCount()
	{
		return entities.size();
	}
	
	public void removeEntity(Entity e)
	{
		if(e.getParentWorld() != this)
			throw new IllegalArgumentException("Can't remove an entity that don't exist in the world");
		
		entities.remove(e);
		e.setParentWorld(null);
		
		if(!e.isDestroyed())
			e.destroy();
	}
	
}
