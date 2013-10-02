package engine.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.entity.*;
import engine.tests.beans.DummyComponent;
import engine.tests.beans.DummyEntity;

public class GameWorldTest {
	
	@Test
	public void testComponents()
	{
		Entity e = new DummyEntity();
		assertEquals(0, e.getComponentCount());
		
		Component c = new DummyComponent();
		assertFalse(c.isAttached());
		assertEquals(null, c.getParent());
		
		e.addComponent(c);
		assertEquals(1, e.getComponentCount());
		assertTrue(c.isAttached());
		assertEquals(e, c.getParent());
		
		e.removeComponent(c);
		assertEquals(0, e.getComponentCount());
		assertFalse(c.isAttached());
		assertEquals(null, c.getParent());
	}

	@Test
	public void testEntity() 
	{
		//Test empty world
		GameWorld w = new GameWorld();
		assertEquals(0, w.getEntityCount());
		
		//Test unadded entity 
		Entity e = new DummyEntity();
		assertEquals(0, w.getEntityCount());
		assertFalse(e.hasParentWorld());
		assertFalse(e.isDestroyed());
		
		//Test with entity added
		w.addEntity(e);
		assertEquals(1, w.getEntityCount());
		assertEquals(w, e.getParentWorld());
		assertTrue(e.hasParentWorld());
		assertFalse(e.isDestroyed());
		
		//test destroying from entity
		e.destroy();
		assertEquals(0, w.getEntityCount());
		assertFalse(e.hasParentWorld());
		assertTrue(e.isDestroyed());
		
		//Test adding it back to world
		w.addEntity(e);
		assertEquals(1, w.getEntityCount());
		assertEquals(w, e.getParentWorld());
		assertTrue(e.hasParentWorld());
		assertFalse(e.isDestroyed());
		
		//test destroying from world
		w.removeEntity(e);
		assertEquals(0, w.getEntityCount());
		assertFalse(e.hasParentWorld());
		assertTrue(e.isDestroyed());
	}

}
