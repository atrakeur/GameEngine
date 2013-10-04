package engine.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.core.Container;
import engine.entity.*;
import engine.tests.beans.DummyComponent;
import engine.tests.beans.DummyEntity;

public class GameWorldTest {
	
	public void setUpBeforeClass()
	{
		Container.getInstance();
	}
	
	@Test
	public void testComponents()
	{
		Entity e = new DummyEntity();
		assertEquals(1, e.getComponentCount());
		
		Component c = new DummyComponent();
		assertFalse(c.isAttached());
		assertEquals(null, c.getParent());
		
		e.addComponent(c);
		assertEquals(2, e.getComponentCount());
		assertTrue(c.isAttached());
		assertEquals(e, c.getParent());
		
		e.removeComponent(c);
		assertEquals(1, e.getComponentCount());
		assertFalse(c.isAttached());
		assertEquals(null, c.getParent());
	}
	
	@Test
	public void testComponentUnicity()
	{
		//Check that adding two component of the same type fail
		Entity e = new DummyEntity();
		e.addComponent(new DummyComponent());
		try {
			e.addComponent(new DummyComponent());
			fail();
		}
		catch(IllegalArgumentException ex)
		{
		}
	}
	
	@Test
	public void testComponentGet()
	{
		//test empty world
		GameWorld w = new GameWorld();
		assertEquals(0, w.getComponents(DummyComponent.class).size());
		
		//test with one entity
		Entity e = new DummyEntity();
		e.addComponent(new DummyComponent());
		w.addEntity(e);
		assertEquals(1, w.getComponents(DummyComponent.class).size());
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
		w.clean();
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
		w.clean();
		assertEquals(0, w.getEntityCount());
		assertFalse(e.hasParentWorld());
		assertTrue(e.isDestroyed());
		
		//Test removing multiple entity while looping
		w.addEntity(new DummyEntity());
		w.addEntity(new DummyEntity());
		w.addEntity(new DummyEntity());
		for(int i = 0; i < w.getEntityCount(); i++)
		{
			assertFalse(w.getEntity(w.getEntityCount() - i - 1).isDestroyed());
			w.getEntity(w.getEntityCount() - i - 1).destroy();
			assertTrue(w.getEntity(w.getEntityCount() - i - 1).isDestroyed());
		}
		assertEquals(3, w.getEntityCount());
		w.clean();
		assertEquals(0, w.getEntityCount());
	}

}
