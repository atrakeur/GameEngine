package gameoct.entity;

import java.awt.Color;

import engine.entity.Entity;
import engine.entity.components.Transform;
import engine.physics.RigidbodyComponent;
import engine.render.RenderComponent;
import engine.render.components.QuadRenderComponent;

public class RainDrop extends Entity {
	
	public RainDrop() {
		addComponent(QuadRenderComponent.class);
		addComponent(RigidbodyComponent.class);
		
		getComponent(RenderComponent.class).getColor().set(new Color(0, 0, 0.5f));
		
		getComponent(RigidbodyComponent.class).setDensity(1);
		getComponent(RigidbodyComponent.class).setFriction(0);
		getComponent(RigidbodyComponent.class).setRestitution(0.3f);
	}

}
