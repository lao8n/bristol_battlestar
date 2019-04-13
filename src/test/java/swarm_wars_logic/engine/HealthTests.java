package swarm_wars_logic.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.beans.Transient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import swarm_wars_library.engine.Health;
import swarm_wars_library.engine.Tag;

class HealthTests {

  @Test
  @DisplayName("Health HealthTests")
  void HealthTests(){
    Health h = new Health(Tag.PLAYER);
    assertEquals(100, h.getMaxHealth(), "max health for a player should be 100");
    assertEquals(100, h.getCurrentHealth(), "current health for a player should be 100");
    assertEquals(false, h.isDead(), "player should be alive");
    h.takeDamage(15);
    h.update();
    assertEquals(100, h.getMaxHealth(), "max health for a player should be unchanged at 100");
    assertEquals(85, h.getCurrentHealth(), "current health for a player should be 85");
    assertEquals(false, h.isDead(), "player should be alive");
    h.takeDamage(80);
    h.update();
    assertEquals(5, h.getCurrentHealth(), "current health for a player should be 5");
    assertEquals(false, h.isDead(), "player should be alive");
    h.takeDamage(5);
    h.update();
    assertEquals(true, h.isDead(), "player should be dead");

    Health b = new Health(Tag.P_BOT);
    assertEquals(5, b.getMaxHealth(), "max health for a bot should be 5");
    assertEquals(5, b.getCurrentHealth(), "current health for a bot should be 5");
    assertEquals(false, b.isDead(), "bot should be alive");
    b.takeDamage(1);
    b.update();
    assertEquals(5, b.getMaxHealth(), "max health for a bot should be unchanged at 5");
    assertEquals(4, b.getCurrentHealth(), "current health for a bot should be 4");
    assertEquals(false, b.isDead(), "bot should be alive");
    b.takeDamage(4);
    b.update();
    assertEquals(0, b.getCurrentHealth(), "current health for a bot should be 0");
    assertEquals(true, b.isDead(), "bot should be dead");
  }
}
