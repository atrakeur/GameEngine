package engine.render.components;

import engine.entity.Entity;

public class CameraEntity extends Entity {

	public CameraEntity()
	{
		addComponent(CameraComponent.class);
	}

}
