package engine.gl;

import org.lwjgl.opengl.GL11;

public class GLTranslate implements GLAction {
	
	private boolean setup;
	
	private float x;
	private float y;
	
	public GLTranslate(){
	}

	public GLTranslate(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public void setup() {
		if(setup)
			throw new IllegalStateException("Can't setup GLAction twice");
		
		setup = true;
		GL11.glTranslatef(x, y, 0);
	}

	public void teardown() {
		if(!setup)
			throw new IllegalStateException("Can't teardown a GLAction that isn't setuped");
		
		setup = false;
		GL11.glTranslatef(x, y, 0);
	}

	public boolean isSetup() {
		return setup;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
