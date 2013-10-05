package engine.render.components;

import org.lwjgl.opengl.GL11;

import engine.render.RenderComponent;

@net.usikkert.kouinject.annotation.Component
public class QuadRenderComponent extends RenderComponent {

	public void render() {
		GL11.glColor4f(getColor().x,getColor().y,getColor().z, getColor().w);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(-0.5f,-0.5f);
		GL11.glVertex2f( 0.5f,-0.5f);
		GL11.glVertex2f( 0.5f, 0.5f);
		GL11.glVertex2f(-0.5f, 0.5f);
		GL11.glEnd();
	}
	
}
