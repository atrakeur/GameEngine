package gameoct.entity;

import engine.core.Container;
import engine.entity.Entity;
import engine.entity.GameWorld;
import engine.entity.components.Transform;
import engine.math.Vec2;
import engine.physics.RigidbodyComponent;

public class RainSpawner extends Entity {
	
	public final int RAINDROP_NUMBER = 10;
	
	private GameWorld world;
	
	private int lastResetRain = 0;
	private RainDrop[] raindrops;
	private Vec2 spawnSize;
	
	public RainSpawner() {
		world = Container.inject(GameWorld.class);
		
		raindrops = new RainDrop[RAINDROP_NUMBER];
		spawnSize = new Vec2(120, 1);
	}
	
	public void update(float delta) {
		for(int i = 0; i < 2; i++){
			lastResetRain = (lastResetRain + 1) % raindrops.length;
			
			if(raindrops[lastResetRain] == null) {
				raindrops[lastResetRain] = new RainDrop();
				world.addEntity(raindrops[lastResetRain]);
			}
			
			raindrops[lastResetRain].getComponent(Transform.class).getPosition().set(
					getComponent(Transform.class).getPosition().x + (float)Math.random() * spawnSize.x - spawnSize.x/2,
					getComponent(Transform.class).getPosition().y + (float)Math.random() * spawnSize.y - spawnSize.y/2
			);
			raindrops[lastResetRain].getComponent(RigidbodyComponent.class).getVelocity().set(0, 0);
		}
	}

}
