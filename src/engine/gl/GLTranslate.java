package engine.gl;

import javax.vecmath.Tuple2d;
import javax.vecmath.Tuple2f;

import org.lwjgl.opengl.GL11;

import engine.math.Vec2;

public class GLTranslate implements GLAction {
	
	private boolean setup;
	
	private final Vec2 vector;
	
	public GLTranslate(){
		vector = new Vec2();
	}

	public GLTranslate(float x, float y) {
		this();
		this.vector.x = x;
		this.vector.y = y;
	}

	public void setup() {
		if(setup)
			throw new IllegalStateException("Can't setup GLAction twice");
		
		setup = true;
		GL11.glTranslatef(vector.x, vector.y, 0);
	}

	public void teardown() {
		if(!setup)
			throw new IllegalStateException("Can't teardown a GLAction that isn't setuped");
		
		setup = false;
		GL11.glTranslatef(-vector.x, -vector.y, 0);
	}

	public boolean isSetup() {
		return setup;
	}

	public final void set(float arg0, float arg1) {
		vector.set(arg0, arg1);
	}

	public final void set(float[] arg0) {
		vector.set(arg0);
	}

	public final void set(Tuple2d arg0) {
		vector.set(arg0);
	}

	public final void set(Tuple2f arg0) {
		vector.set(arg0);
	}

	public final Vec2 getVector() {
		return vector;
	}

}
