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
import engine.debug.IProfiler;
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
	@Inject
	private IProfiler profiler;
	
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
	
	public void update(float delta)
	{
		profiler.start("/Render/SetCameras");
		
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
		
		profiler.end("/Render/SetCameras");
		
		profiler.start("/Render/Clear");
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glEnable(GL_TEXTURE_2D);
		profiler.end("/Render/Clear");
		
		profiler.start("/Render/GetRenderables");
		Collection<RenderComponent> renderables = world.getComponents(RenderComponent.class);
		profiler.end("/Render/GetRenderables");
		
		profiler.start("/Render/DisplayRenderables");
		for(RenderComponent render : renderables)
		{	
			Transform transform = render.getParent().getComponent(Transform.class);
			objectPosition.set(transform.getPosition());
			objectScale.set(transform.getScale());
			objectRotation.setRotation(transform.getRotation());
			
			objectPosition.setup();
			objectRotation.setup();
			objectScale.setup();
			
			render.render();
			
			objectScale.teardown();
			objectRotation.teardown();
			objectPosition.teardown();
		}
		profiler.end("/Render/DisplayRenderables");
		
		profiler.start("/Render/Swap");
		Display.update();
		profiler.end("/Render/Swap");
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
