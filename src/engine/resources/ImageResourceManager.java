package engine.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.inject.Singleton;

import net.usikkert.kouinject.annotation.Component;

@Component
@Singleton
public class ImageResourceManager implements IResourceManager<Texture> {
	
	public static final int[] DEFAULT_IMAGE_DATA = {
                  0xFF00FF, 0xFF00FF, 0xFF00FF, 0xAA22AA, 0xAA22AA, 0xAA22AA,
                  0xFF00FF, 0xFF00FF, 0xFF00FF, 0xAA22AA, 0xAA22AA, 0xAA22AA,
                  0xFF00FF, 0xFF00FF, 0xFF00FF, 0xAA22AA, 0xAA22AA, 0xAA22AA,
                  0xAA22AA, 0xAA22AA, 0xAA22AA, 0xFF00FF, 0xFF00FF, 0xFF00FF,
                  0xAA22AA, 0xAA22AA, 0xAA22AA, 0xFF00FF, 0xFF00FF, 0xFF00FF,
                  0xAA22AA, 0xAA22AA, 0xAA22AA, 0xFF00FF, 0xFF00FF, 0xFF00FF,
	};
	public static final int DEFAULT_IMAGE_SIZE = 6;
	
	private Texture defaultTexture;
	private HashMap<String, Texture> textures;
	private HashMap<String, Integer> refCount;
	
	public ImageResourceManager() {
		textures = new HashMap<String, Texture>();
		refCount = new HashMap<String, Integer>();
		
		//load default image
		defaultTexture = new Texture("default", DEFAULT_IMAGE_DATA, DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE);
		try {
			defaultTexture.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Texture load(String path) {
		if(textures.containsKey(path)) {
			refCount.put(path, refCount.get(path) + 1);
			return textures.get(path);
		}
		
		Texture tex = new Texture(path);
		
		textures.put(path, tex);
		refCount.put(path, 1);
		
		try {
			tex.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tex;
	}

	@Override
	public Texture get(String path) {
		if(!textures.containsKey(path))
			return defaultTexture;
		
		return textures.get(path);
	}

	@Override
	public void dispose(String path) {
		if(textures.containsKey(path)) {
			refCount.put(path, refCount.get(path) - 1);
			
			if(refCount.get(path) == 0) {
				textures.get(path).dispose();
				textures.remove(path);
				refCount.remove(path);
			}
		}
	}

}