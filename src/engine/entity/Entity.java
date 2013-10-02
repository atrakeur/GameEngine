package engine.entity;

import java.util.ArrayList;

/**
 * An abstract game entity
 * 
 * @author Valentin 'Atrakeur' Letourneur <atrakeur@gmail.com>
 *
 */
public abstract class Entity {
	
	private boolean isDestroyed;
	private GameWorld parentWorld;
	private final ArrayList<Component> components;
	
	public Entity()
	{
		components = new ArrayList<Component>();
	}
	
	/**
	 * @return 
	 */
	public final boolean isDestroyed() 
	{
		return isDestroyed;
	}
	
	/**
	 * Destroy this entity
	 */
	public final void destroy()
	{	
		if(isDestroyed)
			throw new IllegalStateException("An entity can't be destroyed twice");
		
		isDestroyed = true;
		
		for(Component c : components)
		{
			c.onDetach();
			c.setParent(null);
		}
		
		this.onDestroy();
		
		if(parentWorld != null)
			parentWorld.removeEntity(this);
	}
	
	/**
	 * @return the world this entity belong to
	 */
	public final GameWorld getParentWorld() {
		return parentWorld;
	}
	
	/**
	 * @return true if this entity 
	 */
	public final boolean hasParentWorld()
	{
		return parentWorld != null;
	}

	/**
	 * Set the parent world
	 * @param parentWorld
	 */
	protected final void setParentWorld(GameWorld parentWorld) {
		if(this.hasParentWorld() && parentWorld != null)
			throw new IllegalArgumentException("Can't add an entity to a world twice.");
		
		this.parentWorld = parentWorld;
		this.isDestroyed = false;
	}

	/**
	 * Add a component to this entity
	 * @param c
	 */
	public final void addComponent(Component c)
	{
		if (c.getParent() != null)
			throw new IllegalArgumentException("A component can't be added to an entity twice");
		
		if (this.isDestroyed())
			throw new IllegalStateException("Can't add a component to a destroyed entity");
		
		components.add(c);
		c.setParent(this);
		c.onAttach();
		
	}
	
	/**
	 * Get component at the given index
	 * @param index
	 */
	public final Component getComponent(int index)
	{
		if (this.isDestroyed())
			throw new IllegalStateException("Can't add a component to a destroyed entity");
		
		return components.get(index);
	}
	
	/**
	 * @return the number of components
	 */
	public final int getComponentCount()
	{
		return components.size();
	}
	
	/**
	 * Remove a component at the given index
	 * @param index
	 */
	public final void removeComponent(Component c)
	{
		if (this.isDestroyed())
			throw new IllegalStateException("Can't add a component to a destroyed entity");
		
		c.onDetach();
		c.setParent(null);
		components.remove(c);
	}	
	
	public void onCreate()
	{
	}
	
	public void onDestroy()
	{
	}
	
}
