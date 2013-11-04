package engine.physics;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.usikkert.kouinject.annotation.Component;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import engine.core.IPhysics;
import engine.debug.IProfiler;
import engine.entity.GameWorld;
import engine.math.Vec2;

@Component
@Singleton
public class Physics implements IPhysics {
	
	@Inject
	GameWorld world;
	
	@Inject
	IProfiler profiler;
	
	World physicsworld;
	
	public Physics()
	{
		Vec2 gravity = new Vec2(0.0f, -9.1f);
		physicsworld = new World(gravity.toJBox2d(), true);
	}
	
	protected Body addBody(BodyDef def)
	{
		return physicsworld.createBody(def);
	}
	
	protected void removeBody(Body body)
	{
		physicsworld.destroyBody(body);
	}
	
	public void update(float delta)
	{
		final int iterations = 5;
		
		profiler.start("/Physics/SetupObjects");
		//setup physics according to game world
		Collection<RigidbodyComponent> rigidbodys = world.getComponents(RigidbodyComponent.class);
		for(RigidbodyComponent rigidbody : rigidbodys)
			rigidbody.setupToPhysics();
		profiler.end("/Physics/SetupObjects");
		
		profiler.start("/Physics/Simulate");
		//simulate world
		float timeStep = delta / iterations;
		int velocityIterations = 6;
		int positionIterations = 2;
		 
		for (int i = 0; i < iterations; ++i) {
			physicsworld.step(timeStep, velocityIterations, positionIterations);
		}
		profiler.end("/Physics/Simulate");
		
		profiler.start("/Physics/TearDown");
		//setup world from physics
		rigidbodys = world.getComponents(RigidbodyComponent.class);
		for(RigidbodyComponent rigidbody : rigidbodys)
			rigidbody.setupFromPhysics();
		profiler.end("/Physics/TearDown");
	}

}
