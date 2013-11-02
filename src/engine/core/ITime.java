package engine.core;

public interface ITime {
	
	public long getTime();
	
	public void startFrame();
	
	public void endFrame();
	
	public int getSleepTime();
	
	public float getDelta();
	
	public int getCurrentFps();
	
	public int getTargetFps();
	
	public void setTargetFps(int fps);

}
