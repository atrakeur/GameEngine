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
import engine.entity.components.Transform;
import engine.gl.GLRotate;
import engine.gl.GLScale;
import engine.gl.GLTranslate;
import engine.math.Vec2;
import engine.render.components.CameraComponent;

import static org.lwjgl.opengl.GL11.*;

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
	
	private final Vec2 resolution = new Vec2(800, 600);
	
	private final GLTranslate objectPosition = new GLTranslate();
	private final GLScale objectScale = new GLScale();
	private final GLRotate objectRotation = new GLRotate();

	public Render()
	{
	}
	
	public void create() throws LWJGLException
	{
		//TODO make it cross platform
		System.setProperty("org.lwjgl.librarypath", new File("native/linux").getAbsolutePath());
		Display.setDisplayMode(new DisplayMode((int)resolution.x, (int)resolution.y));
		Display.setTitle("Test");
		Display.create();
	}
	
	public boolean isCloseRequested()
	{
		return Display.isCloseRequested();
	}
	
	public void update()
	{
		Collection<CameraComponent> cameras = world.getComponents(CameraComponent.class);
		
		if(cameras.size() == 0)
			throw new IllegalStateException("No camera found");
		
		for(CameraComponent camera : cameras)
		{
			//setup viewport for each camera
			glMatrixMode(GL11.GL_PROJECTION);
			glLoadIdentity();
			float aspect = resolution.x / resolution.y;
			glOrtho((double)-camera.getScreen().x/2 * aspect, (double)camera.getScreen().x/2 * aspect, (double)-camera.getScreen().y/2, (double)camera.getScreen().y/2, (double)camera.getNearPlane(), (double)camera.getFarPlane());
			glMatrixMode(GL11.GL_MODELVIEW);
		}
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		Collection<RenderComponent> renderables = world.getComponents(RenderComponent.class);
		
		for(RenderComponent render : renderables)
		{	
			Transform transform = render.getParent().getComponent(Transform.class);
			objectPosition.set(transform.getPosition());
			objectScale.set(transform.getScale());
			objectRotation.setRotation(transform.getRotation());
			
			objectPosition.setup();
			objectScale.setup();
			objectRotation.setup();
			
			render.render();
			
			objectRotation.teardown();
			objectScale.teardown();
			objectPosition.teardown();
		}
		
		Display.update();
	}
	
	public void destroy()
	{
		Display.destroy();
	}
	
	public Vec2 getResolution()
	{
		return resolution;
	}
	
}
