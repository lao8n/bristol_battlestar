package swarm_wars_logic.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import swarm_wars_library.engine.RigidBody;
import swarm_wars_library.physics.Vector2D;

class RigidBodyTests {

	@Test
	@DisplayName("RigidBody ApplyForceTest")
	void ApplyForceTest() {
    RigidBody rb = new RigidBody();
    rb.setMass(1);
    rb.applyForce(new Vector2D(1, 1));
		assertEquals(1, rb.getAccelerationX(), "rb x acceleration should equal 1");
		assertEquals(1, rb.getAccelerationY(), "rb y acceleration should equal 1");
  }

  @Test
  @DisplayName("RigidBody ApplyRelativeForceTest")
  void ApplyRelativeForceTest() {
    RigidBody rb = new RigidBody();
    double angle = Math.PI / 2;
    rb.setMass(1);
    rb.applyRelativeForce(new Vector2D(1, 0), angle);
    assertEquals(true, rb.getAccelerationX() < 0.0001,
    "rb x acceleration is less than 0.0001");
    assertEquals(1, rb.getAccelerationY(), "rb y acceleration is 1");
  }

	@Test
	@DisplayName("RigidBody MassTests")
	void MassTests() {
		RigidBody rb = new RigidBody();
		rb.setMass(25);
		assertEquals(25, rb.getMass(), "rb mass should be 25");
		rb.setMass(2);
		assertEquals(2, rb.getMass(), "rb mass should be 2");
	}

	@Test
	@DisplayName("RigidBody VelocityTests")
	void VelocityTests() {
		RigidBody rb = new RigidBody();
		Vector2D v = new Vector2D(4,5);
		rb.setVelocity(v);
		assertEquals(4, rb.getVelocityX(), "rb velocity x should be 4");
		assertEquals(5, rb.getVelocityY(), "rb velocity y should be 5");
		Vector2D y = new Vector2D(3.33,2.22);
		rb.setVelocity(y);
		assertEquals(3.33, rb.getVelocityX(), "rb velocity x should be 3.33");
		assertEquals(2.22, rb.getVelocityY(), "rb velocity y should be 2.22");
	}

	@Test
	@DisplayName("RigidBody AccelerationTests")
	void AccelerationTests() {
		RigidBody rb = new RigidBody();
		assertEquals(0, rb.getAccelerationX(), "rb acceleration x should be 0");
		assertEquals(0, rb.getAccelerationY(), "rb acceleration y should be 0");
	}

	@Test
	@DisplayName("RigidBody MaxSpeedTests")
	void MaxSpeedTests() {
		RigidBody rb = new RigidBody();
		rb.setMaxSpeed(58.3);
		assertEquals(58.3, rb.getMaxSpeed(), "rb maxSpeed should be 58.3");
		rb.setMaxSpeed(0.1);
		assertEquals(0.1, rb.getMaxSpeed(), "rb maxSpeed should be 0.1");
	}
}
