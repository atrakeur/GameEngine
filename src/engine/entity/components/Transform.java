package engine.entity.components;

import engine.entity.Component;
import engine.math.Vec2;

@net.usikkert.kouinject.annotation.Component
public class Transform extends Component {

	private float rotation;
	private final Vec2 position = new Vec2();
	
}
