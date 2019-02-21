package swarm_wars_logic.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import swarm_wars_library.engine.GameObject;
import swarm_wars_library.engine.Vector2D;

class GameObjectTests {

	@Test
	@DisplayName("GameObject ConstructorTest")
	void ConstructorTest() {
		GameObject go = new GameObject(new Vector2D(1, 5));
		assertEquals(1, go.getLocationX(), "location x should equal 1");
		assertEquals(5, go.getLocationY(), "location y should equal 5");
		assertEquals(1, go.getScaleX(), "scale x should equal 1");
		assertEquals(1, go.getScaleY(), "scale y should equal 1");
		assertEquals(0, go.getHeading(), "heading should equal 0");
	}
}