package engine.physics;

import org.jbox2d.dynamics.World;

import engine.math.Vec2;

public class Physics {
	
	public Physics()
	{
		Vec2 gravity = new Vec2(0.0f, 0.0f);
		World world = new World(gravity.toJBox2d(), true);
	}

}
