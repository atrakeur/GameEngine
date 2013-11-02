package engine.render.components;

import org.lwjgl.opengl.GL11;

import engine.core.Container;
import engine.render.RenderComponent;
import engine.resources.ImageResourceManager;
import engine.resources.Texture;

@net.usikkert.kouinject.annotation.Component
public class QuadRenderComponent extends RenderComponent {
	
	Texture texture;
	
	public void onAttach() {
		super.onAttach();
		
		texture = Container.inject(ImageResourceManager.class).get("./image.png");
	}
	
	public void onDetach() {
		super.onDetach();
		
		Container.inject(ImageResourceManager.class).dispose("./image.png");
	}

	public void render() {
		//GL11.glColor4f(getColor().x,getColor().y,getColor().z, getColor().w);
		
		texture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(-0.5f,-0.5f);
		
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f( 0.5f,-0.5f);
		
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f( 0.5f, 0.5f);
		
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(-0.5f, 0.5f);
		
		GL11.glEnd();
	}
	
}
