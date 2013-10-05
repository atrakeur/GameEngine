package engine.math;

import javax.vecmath.Tuple2d;
import javax.vecmath.Tuple2f;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;

public class Vec2 extends Vector2f {

	public Vec2() {
		super();
	}

	public Vec2(float arg0, float arg1) {
		super(arg0, arg1);
	}

	public Vec2(float[] arg0) {
		super(arg0);
	}

	public Vec2(Tuple2d arg0) {
		super(arg0);
	}

	public Vec2(Tuple2f arg0) {
		super(arg0);
	}

	public Vec2(Vector2d arg0) {
		super(arg0);
	}

	public Vec2(Vector2f arg0) {
		super(arg0);
	}

	public org.jbox2d.common.Vec2 toJBox2d() {
		return new org.jbox2d.common.Vec2(this.x, this.y);
	}

}
