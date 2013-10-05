package gameoct;

import engine.core.Container;
import engine.core.ILevel;
import engine.entity.Entity;
import engine.entity.GameWorld;
import engine.entity.components.Transform;
import engine.physics.RigidbodyComponent;
import engine.render.components.CameraEntity;
import engine.render.components.QuadRenderComponent;
import engine.tests.beans.DummyEntity;

public class MainLevel implements ILevel{
	
	private GameWorld world;

	public void onStart() {
		world = Container.inject(GameWorld.class);
		
		world.addEntity(new CameraEntity());
		Entity e = new DummyEntity();
		e.addComponent(QuadRenderComponent.class);
		e.getComponent(Transform.class).getPosition().set(40, 40);
		e.getComponent(Transform.class).getScale().set(4, 4);
		e.addComponent(RigidbodyComponent.class);
		world.addEntity(e);
		
		e = new DummyEntity();
		e.addComponent(QuadRenderComponent.class);
		e.getComponent(Transform.class).getPosition().set(-40, 40);
		e.getComponent(Transform.class).getScale().set(4, 4);
		e.getComponent(Transform.class).setRotation(45);
		e.addComponent(RigidbodyComponent.class);
		world.addEntity(e);
		
		e = new DummyEntity();
		e.addComponent(QuadRenderComponent.class);
		e.getComponent(Transform.class).getPosition().set(40, -40);
		e.getComponent(Transform.class).getScale().set(8, 8);
		world.addEntity(e);
		
		e = new DummyEntity();
		e.addComponent(QuadRenderComponent.class);
		e.getComponent(Transform.class).getPosition().set(-40, -40);
		e.getComponent(Transform.class).getScale().set(4, 4);
		world.addEntity(e);
	}

	public void onUpdate() {
		// TODO Auto-generated method stub
		
	}

	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
