package engine.render.components;

import org.lwjgl.opengl.GL11;

import engine.render.RenderComponent;

@net.usikkert.kouinject.annotation.Component
public class QuadRenderComponent extends RenderComponent {

	public void render() {
		GL11.glColor3f(0.5f,0.5f,1.0f);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(-0.5f,-0.5f);
		GL11.glVertex2f( 0.5f,-0.5f);
		GL11.glVertex2f( 0.5f, 0.5f);
		GL11.glVertex2f(-0.5f, 0.5f);
		GL11.glEnd();
	}
	
}
