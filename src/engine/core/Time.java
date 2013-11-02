package engine.core;

import javax.inject.Singleton;

import net.usikkert.kouinject.annotation.Component;

import org.lwjgl.Sys;

@Component
@Singleton
public class Time implements ITime {
	
	private int targetFPS = 30;
	
	private long lastFrameStart;
	private float lastDelta;
	
	private int fps;
	private int lastFps;
	private long lastFpsTime;

	@Override
	public void startFrame() {
		lastDelta = (float)(getTime() - lastFrameStart) / 1000f;
		lastFrameStart = getTime();
		fps++;
	}

	@Override
	public void endFrame() {
		if(getTime() > lastFpsTime + 1000) {
			lastFpsTime = getTime();
			lastFps = fps;
			fps = 0;
		}
	}

	@Override
	public int getSleepTime() {
		long sleep = 1000/getTargetFps() - (getTime() - lastFrameStart);
		if(sleep < 0)
			return 0;
		
		return (int) sleep;
	}

	@Override
	public float getDelta() {
		return lastDelta;
	}

	@Override
	public int getCurrentFps() {
		return lastFps;
	}

	@Override
	public int getTargetFps() {
		return targetFPS;
	}

	@Override
	public void setTargetFps(int fps) {
		targetFPS = fps;
	}

	@Override
	public long getTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

}
