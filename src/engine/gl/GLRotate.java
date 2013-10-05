package engine.gl;

import org.lwjgl.opengl.GL11;

public class GLRotate implements GLAction {
	
	private boolean setup;
	private float rotation;
	
	public GLRotate() {
		this(0);
	}

	public GLRotate(float rotation) {
		super();
		this.rotation = rotation;
	}
	
	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void setup() {
		if(setup)
			throw new IllegalStateException("Can't setup GLAction twice");
		
		setup = true;
		GL11.glRotatef(rotation, 0, 0, 1);
	}

	public void teardown() {
		if(!setup)
			throw new IllegalStateException("Can't teardown a GLAction that isn't setuped");
		
		setup = false;
		GL11.glRotatef(-rotation, 0, 0, 1);
	}

	public boolean isSetup() {
		return setup;
	}
	
	

}
