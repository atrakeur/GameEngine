package engine.physics;

import javax.inject.Inject;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import engine.entity.Component;
import engine.entity.components.Transform;
import engine.math.Vec2;

@net.usikkert.kouinject.annotation.Component
public class RigidbodyComponent extends Component {
	
	@Inject
	private Physics physics;
	
	private Body body;
	
	public void onAttach() {
		super.onAttach();
		
		BodyDef def = new BodyDef();
		def.position = getPosition().toJBox2d();
		def.type = BodyType.DYNAMIC;
		def.angle = -getRotation();
		body = physics.addBody(def);
		
		PolygonShape rect = new PolygonShape();
		rect.setAsBox(getScale().x/2, getScale().y/2);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = rect;
		fd.density = 0.8f;
		fd.friction = 0.5f;       
		fd.restitution = 0.01f;
		
		body.createFixture(fd);
	}

	public void onDetach() {
		super.onDetach();
		
		physics.removeBody(body);
	}
	
	public void setStatic(boolean isStatic)
	{
		if(isStatic)
			body.setType(BodyType.STATIC);
		else
			body.setType(BodyType.DYNAMIC);
	}
	
	public void setupToPhysics()
	{
		body.setTransform(getPosition().toJBox2d(), (float)Math.toRadians(getRotation()));
	}
	
	public void setupFromPhysics()
	{
		getPosition().fromJBox2d(body.getPosition());
		setRotation((float)Math.toDegrees(body.getAngle()));
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
