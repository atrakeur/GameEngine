package engine.resources;

public interface IResourceManager<T extends IResource> {
	
	public T load(String path);
	
	public T get(String path);
	
	public void dispose(String path);

}
