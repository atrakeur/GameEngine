package engine.core;

public interface IRenderer {

	void create() throws Exception;

	void update();

	boolean isCloseRequested();

	void destroy();

}
