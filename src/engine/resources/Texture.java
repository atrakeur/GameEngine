package engine.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class Texture implements IResource {
	
	String path;
	boolean ready;
	boolean loading;
	
	int textureID;
	BufferedImage image;
	
	protected Texture(String path) {
		this(path, null);
	}
	
	protected Texture(String path, BufferedImage image) {
		this.path = path;
		this.image = image;
		ready = false;
		loading = false;
	}

	@Override
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
		
		final int BYTES_PER_PIXELS = 4; //4 = RGBA, 3 = RGB
		
		if(image == null) {
			image = ImageIO.read(new File(path));
		}
		
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXELS);
		
		for(int i = 0; i < image.getHeight(); i++) {
			for(int j = 0; j < image.getWidth(); j++) {
				int pixel = pixels[i * image.getWidth() + j];
				
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) ((pixel) & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		
		buffer.flip();
		
		textureID = GL11.glGenTextures();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		
		loading = false;
		ready = true;
	}

	@Override
	public void dispose() {
		GL11.glDeleteTextures(textureID);
		ready = false;
	}
	
	
}
