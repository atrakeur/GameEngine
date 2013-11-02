package gameoct;

import javax.inject.Inject;

import org.lwjgl.input.Keyboard;

import engine.core.Container;
import engine.core.Engine;
import engine.core.ILevel;
import engine.entity.Entity;
import engine.entity.GameWorld;
import engine.entity.components.Transform;
import engine.physics.RigidbodyComponent;
import engine.render.components.CameraEntity;
import engine.render.components.QuadRenderComponent;
import engine.tests.beans.DummyEntity;
import gameoct.entity.RainSpawner;

public class MainLevel implements ILevel{
	
	//private Engine engine;
	private GameWorld world;

	public void onStart() {
		//engine = Container.inject(Engine.class);
		world = Container.inject(GameWorld.class);
		
		world.addEntity(new CameraEntity());
		Entity e = new DummyEntity();
		e.addComponent(QuadRenderComponent.class);
		e.getComponent(Transform.class).getPosition().set(35, 40);
		e.getComponent(Transform.class).getScale().set(4, 4);
		e.getComponent(Transform.class).setRotation(15);
		e.addComponent(RigidbodyComponent.class);
		world.addEntity(e);
		
		e = new DummyEntity();
		e.addComponent(QuadRenderComponent.class);
		e.getComponent(Transform.class).getPosition().set(-20, 40);
		e.getComponent(Transform.class).getScale().set(4, 4);
		e.getComponent(Transform.class).setRotation(1f);
		e.addComponent(RigidbodyComponent.class);
		world.addEntity(e);
		
		e = new DummyEntity();
		e.addComponent(QuadRenderComponent.class);
		e.getComponent(Transform.class).getPosition().set(40, -30);
		e.getComponent(Transform.class).getScale().set(16, 16);
		e.getComponent(Transform.class).setRotation(15);
		e.addComponent(RigidbodyComponent.class);
		e.getComponent(RigidbodyComponent.class).setDynamic(false);
		world.addEntity(e);
		
		e = new DummyEntity();
		e.addComponent(QuadRenderComponent.class);
		e.getComponent(Transform.class).getPosition().set(-40, -20);
		e.getComponent(Transform.class).getScale().set(8, 16);
		e.getComponent(Transform.class).setRotation(10);
		e.addComponent(RigidbodyComponent.class);
		e.getComponent(RigidbodyComponent.class).setDynamic(false);
		world.addEntity(e);
		
		e = new DummyEntity();
		e.addComponent(QuadRenderComponent.class);
		e.getComponent(Transform.class).getPosition().set(-10, -30);
		e.getComponent(Transform.class).getScale().set(64, 2);
		e.getComponent(Transform.class).setRotation(-10);
		e.addComponent(RigidbodyComponent.class);
		e.getComponent(RigidbodyComponent.class).setDynamic(false);
		world.addEntity(e);
		
		e = new RainSpawner();
		e.getComponent(Transform.class).getPosition().set(0, 60);
		world.addEntity(e);
	}

	public void onUpdate(float delta) {
		//if(Keyboard.isKeyDown(Keyboard.KEY_R))
		//	engine.loadLevel(new MainLevel());
	}

	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

}
