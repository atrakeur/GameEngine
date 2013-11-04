package engine.debug;

public interface IProfiler {

	public abstract void start(String point);

	public abstract void end(String point);

}