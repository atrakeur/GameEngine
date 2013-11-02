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
	
	protected Vec2 velocity;
	protected boolean dynamic = true;
	protected float density = 1;
	protected float friction = 0.5f;
	protected float restitution = 0.01f;
	
	@Inject
	private Physics physics;
	
	private Body body;
	
	public RigidbodyComponent() {
		velocity = new Vec2();
	}
	
	public void onAttach() {
		super.onAttach();
		
		BodyDef def = new BodyDef();
		def.position = getPosition().toJBox2d();
		def.type = BodyType.DYNAMIC;
		def.angle = -(float)Math.toRadians(getRotation());
		body = physics.addBody(def);
		
		resetFixture();
	}
	
	public void resetFixture() {
		while(body.m_fixtureCount > 0)
			body.destroyFixture(body.getFixtureList());
		
		PolygonShape rect = new PolygonShape();
		rect.setAsBox(getScale().x/2, getScale().y/2);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = rect;
		fd.density = density;
		fd.friction = friction;       
		fd.restitution = restitution;
		
		body.createFixture(fd);
	}

	public void onDetach() {
		super.onDetach();
		
		physics.removeBody(body);
	}
	
	public void setDynamic(boolean dynamic)
	{
		this.dynamic = dynamic;
		if(!dynamic)
			body.setType(BodyType.STATIC);
		else
			body.setType(BodyType.DYNAMIC);
	}
	
	public void setupToPhysics()
	{	
		body.setTransform(getPosition().toJBox2d(), (float)Math.toRadians(getRotation()));
		body.m_linearVelocity.set(velocity.toJBox2d());
	}
	
	public void setupFromPhysics()
	{
		getPosition().fromJBox2d(body.getPosition());
		setRotation((float)Math.toDegrees(body.getAngle()));
		velocity.fromJBox2d(body.m_linearVelocity);
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
	
	public Vec2 getVelocity()
	{
		return velocity;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
		
		resetFixture();
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
		
		resetFixture();
	}

	public float getRestitution() {
		return restitution;
	}

	public void setRestitution(float restitution) {
		this.restitution = restitution;
		
		resetFixture();
	}

}
