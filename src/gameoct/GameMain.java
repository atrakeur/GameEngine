package gameoct;

import engine.core.Engine;

public class GameMain extends Engine{

	public GameMain() throws Exception {
		super();
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new GameMain();
	}

	public String getInjectionPaths() {
		return "";
	}

	public void onInit() {
		
	}

}
