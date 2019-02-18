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
		assertEquals(1, m.location.x, "location x should equal 1");
		assertEquals(5, m.location.y, "location y should equal 5");
		assertEquals(1, m.scale.x, "scale x should equal 1");
		assertEquals(1, m.scale.y, "scale y should equal 1");
    assertEquals(0, m.heading, "heading should equal 0");
    assertEquals(1, m.rb.mass, "rb mass should be 1");
    assertEquals(0, m.rb.velocity.x, "rb x velocity should be 0");
    assertEquals(0, m.rb.velocity.y, "rb y velocity should be 0");

	}
}