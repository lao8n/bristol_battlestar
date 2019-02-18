package swarm_wars_logic.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.beans.Transient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import swarm_wars_library.engine.RigidBody;
import swarm_wars_library.engine.Vector2D;

class RigidBodyTests {

	@Test
	@DisplayName("RigidBody ApplyForceTest")
	void ApplyForceTest() {
    RigidBody rb = new RigidBody();
    rb.mass = 1;
    rb.applyForce(new Vector2D(1, 1));
		assertEquals(1, rb.acceleration.x, "rb x acceleration should equal 1");
		assertEquals(1, rb.acceleration.y, "rb y acceleration should equal 1");
  }
  
  @Test 
  @DisplayName("RigidBody ApplyRelativeForceTest")
  void ApplyRelativeForceTest(){
    RigidBody rb = new RigidBody();
    double angle = Math.PI / 2;
    rb.mass = 1;
    rb.applyRelativeForce(new Vector2D(1, 0), angle);
    assertEquals(true, rb.acceleration.x < 0.0001, 
    "rb x acceleration is less than 0.0001");
    assertEquals(1, rb.acceleration.y, "rb y acceleration is 1");

  }
}