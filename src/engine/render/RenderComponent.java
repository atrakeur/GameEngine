package engine.render;

import javax.vecmath.Color4f;

import engine.entity.Component;

public abstract class RenderComponent extends Component{
	
	private final Color4f color;
	
	public RenderComponent() {
		color = new Color4f(0.5f, 0.5f, 0f, 1f);
	}
	
	public Color4f getColor()
	{
		return color;
	}
	
	public abstract void render();

}
