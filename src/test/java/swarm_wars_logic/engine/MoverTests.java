package swarm_wars_logic.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import swarm_wars_library.engine.Mover;
import swarm_wars_library.engine.Vector2D;

class MoverTests {

	@Test
	@DisplayName("Mover ConstructorTest")
	void ConstructorTest() {
		Mover m = new Mover(new Vector2D(1, 5));
		assertEquals(1, m.getLocationX(), "location x should equal 1");
		assertEquals(5, m.getLocationY(), "location y should equal 5");
		assertEquals(1, m.getScaleX(), "scale x should equal 1");
		assertEquals(1, m.getScaleY(), "scale y should equal 1");
    assertEquals(0, m.getHeading(), "heading should equal 0");
    assertEquals(1, m.getRigidBody().getMass(), "rb mass should be 1");
    assertEquals(0, m.getRigidBody().getVelocityX(), "rb x velocity should be 0");
    assertEquals(0, m.getRigidBody().getVelocityY(), "rb y velocity should be 0");
	}
}