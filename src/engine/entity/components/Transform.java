package engine.entity.components;

import engine.entity.Component;
import engine.math.Vec2;

/**
 * A transform component represents a position in 2D space
 * 
 * @author Valentin 'Atrakeur' Letourneur <atrakeur@gmail.com>
 *
 */
@net.usikkert.kouinject.annotation.Component
public class Transform extends Component {

	private float rotation;
	private final Vec2 position = new Vec2();
	private final Vec2 scale = new Vec2(1, 1);
	
	public float getRotation() 
	{
		return rotation;
	}
	
	public void setRotation(float rotation) 
	{
		this.rotation = rotation;
	}
	
	public Vec2 getPosition() 
	{
		return position;
	}
	
	public Vec2 getScale() 
	{
		return scale;
	}
	
	
	
}
