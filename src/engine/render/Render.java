package engine.render;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import engine.core.IRenderer;
import engine.entity.GameWorld;
import engine.render.components.Camera;

@net.usikkert.kouinject.annotation.Component
@Singleton
/**
 * Manages the rendering in game
 * 
 * @author Valentin 'Atrakeur' Letourneur <atrakeur@gmail.com>
 *
 */
class Render implements IRenderer {
	
	@Inject
	private GameWorld world;
	
	private ArrayList<engine.entity.Component> cameras;
	private ArrayList<engine.entity.Component> renderers;

	public Render()
	{
		cameras = new ArrayList<>();
		renderers = new ArrayList<>();
	}
	
	public void create() throws LWJGLException
	{
		//TODO make it cross platform
		System.setProperty("org.lwjgl.librarypath", new File("native/linux").getAbsolutePath());
		Display.setDisplayMode(new DisplayMode(800, 600));
		Display.create();
	}
	
	public boolean isCloseRequested()
	{
		return Display.isCloseRequested();
	}
	
	public void update()
	{
		Collection<Camera> cameras = world.getComponents(Camera.class);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 1, 0, 1, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glColor3f(10, 10, 10);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(100, 100);
			GL11.glVertex2f(200, 100);
			GL11.glVertex2f(200, 200);
			GL11.glVertex2f(100, 200);
		GL11.glEnd();
		
		Display.update();
	}
	
	public void destroy()
	{
		Display.destroy();
	}
	
}
