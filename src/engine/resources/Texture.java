package engine.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import engine.gl.GLUtility;

public class Texture implements IResource {
	
	boolean ready;
	boolean loading;
	
	int textureID = -1;
	
	private String path;
	
	private BufferedImage image;
	
	private int[] imageData;
	private int width = -1;
	private int height = -1;
	
	protected Texture(String path) {
		this.path = path;
	}
	
	protected Texture(String path, BufferedImage image) {
		this(path);
		this.image = image;
	}
	
	protected Texture(String path, int[] imageData, int width, int height) {
		this(path);
		this.imageData = imageData;
		this.width = width;
		this.height = height;
	}
	
	public String getPath() {
		return path;
	}

	@Override
	public boolean isReady() {
		return ready;
	}

	@Override
	public boolean isLoading() {
		return loading;
	}
	
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}
	
	public void load() throws IOException {
		loading = true;
		ready = false;
		
		if(image != null) {
			textureID = GLUtility.loadImage(image);
		}
		else if(imageData != null) {
			textureID = GLUtility.loadImage(imageData, width, height);
		}
		else {
			image = ImageIO.read(new File(path));
			textureID = GLUtility.loadImage(image);
		}
		
		loading = false;
		ready = true;
	}

	@Override
	public void dispose() {
		GL11.glDeleteTextures(textureID);
		ready = false;
	}
	
	
}
