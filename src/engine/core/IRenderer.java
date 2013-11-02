package engine.core;

public interface IRenderer {

	void create() throws Exception;

	void update(float delta);

	boolean isCloseRequested();

	void destroy();

}
