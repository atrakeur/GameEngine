package engine.entity;

/**
 * An abstract component attached to an entity
 * @author Valentin 'Atrakeur' Letourneur <atrakeur@gmail.com>
 *
 */
public abstract class Component {
	
	private Entity parent;
	
	public final boolean isAttached()
	{
		return parent != null;
	}

	public final Entity getParent() {
		return parent;
	}

	protected final void setParent(Entity parent) {
		if(isAttached() && parent != null)
			throw new IllegalArgumentException("A component can't be added to an entity twice");
		
		if (parent != null && parent.isDestroyed())
			throw new IllegalStateException("Can't add a component to a destroyed entity");
		
		this.parent = parent;
	}
	
	public void onAttach()
	{
	}
	
	public void onDetach()
	{		
	}

}
