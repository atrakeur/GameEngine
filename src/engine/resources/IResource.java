package engine.resources;

import java.io.IOException;

public interface IResource {
	
	public String getPath();
	
	public boolean isReady();
	
	public boolean isLoading();
	
	public void dispose();
	
	public void load() throws IOException;

}
