package engine.render.components;

import engine.math.Vec2;
import net.usikkert.kouinject.annotation.Component;

@Component
public class Camera extends engine.entity.Component {
	
	private final Vec2 screen = new Vec2(100, 100);
	private float nearPlane = 1;
	private float farPlane = -1;
	
	public Vec2 getScreen() {
		return screen;
	}
	
	public float getNearPlane() {
		return nearPlane;
	}
	
	public void setNearPlane(float nearPlane) {
		this.nearPlane = nearPlane;
	}
	
	public float getFarPlane() {
		return farPlane;
	}
	
	public void setFarPlane(float farPlane) {
		this.farPlane = farPlane;
	}

}
