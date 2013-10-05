package engine.physics;

import javax.inject.Inject;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import engine.entity.Component;
import engine.entity.components.Transform;
import engine.math.Vec2;

@net.usikkert.kouinject.annotation.Component
public class RigidbodyComponent extends Component {
	
	@Inject
	Physics physics;
	
	Body body;
	
	public void onAttach() {
		super.onAttach();
		
		BodyDef def = new BodyDef();
		def.position = getPosition().toJBox2d();
		def.type = BodyType.DYNAMIC;
		def.angle = getRotation();
		body = physics.addBody(def);
	}

	public void onDetach() {
		super.onDetach();
		
		physics.removeBody(body);
	}
	
	public void setupToPhysics()
	{
		body.getTransform().set(getPosition().toJBox2d(), getRotation());
	}
	
	public void setupFromPhysics()
	{
		getPosition().fromJBox2d(body.getPosition());
		setRotation(body.getAngle());
	}

	public void setRotation(float r)
	{
		getParent().getComponent(Transform.class).setRotation(r);
	}
	
	public float getRotation()
	{
		return getParent().getComponent(Transform.class).getRotation();
	}
	
	public Vec2 getScale()
	{
		return getParent().getComponent(Transform.class).getScale();
	}

	public Vec2 getPosition()
	{
		return getParent().getComponent(Transform.class).getPosition();
	}

}
