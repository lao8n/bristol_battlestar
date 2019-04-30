package swarm_wars_logic.comms;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsChannel;
import swarm_wars_library.comms.CommsPacket;
import swarm_wars_library.entities.STATE;
import swarm_wars_library.physics.Vector2D;

class CommsTests {

  @Test
  @DisplayName("CommsPacket testing getters and setters")
  void CommsPacketTest(){
    CommsPacket testPacket = new CommsPacket();
    testPacket.setLocation(new Vector2D(0, 10));
    assertEquals(testPacket.getLocation().getX(), 0);
    assertEquals(testPacket.getLocation().getY(), 10);
    testPacket.setState(STATE.ALIVE);
    assertEquals(testPacket.getState(), STATE.ALIVE);
  }

  @Test
  @DisplayName("CommsChannel testing get errors")
  void CommsChannelTest() {
    CommsChannel testChannel = new CommsChannel(10);
    CommsPacket testPacket = new CommsPacket();

    try {
      testChannel.getPacket(1).getLocation();
      assertTrue(false); // test that error has been thrown
    } catch (Error e) {}

    try {
      testChannel.getPacket(11).getLocation();
      assertTrue(false); // test that error has been thrown
    } catch (Error e) {}

    try {
      testChannel.setPacket(testPacket, 11);
      assertTrue(false); // test that error has been thrown
    } catch (Error e) {}

    try {
      testChannel.setPacket(null, 11); // is there point to this as can't send and unitialised object
      assertTrue(false); // test that error has been thrown
    } catch (Error e) {}
  }

  @Test
  @DisplayName("CommsChannel testing update - current/future")
  void CommsChannelUpdateTest() {
    CommsChannel testChannel = new CommsChannel(10);
    CommsPacket testPacket = new CommsPacket();

    testPacket.setLocation(new Vector2D(0, 10));
    // CHANGE: Added Packet at index 0 because cannot add to ArrayLists
    // at index 1 if index 0 has nothing.
    testChannel.setPacket(testPacket, 0);
    testChannel.setPacket(testPacket, 1);

    // test that packet is not accesible yet, i.e in future
    try {
      testChannel.getPacket(1);
      assertTrue(false, "Error should of been thrown by CommsChannel returning a null packet");
    } catch (Error e) {}

    // test that packet is accesible after update, i.e in current
    testChannel.update();
    assertEquals(testChannel.getPacket(1).getLocation().getX(), 0);

    // test that nothing has been changed
    // CHANGE: Surely update() should change things because all the
    // futurePackets get flushed and the currentPackets set as futurePackets?
    // testChannel.update();
    // assertEquals(testChannel.getPacket(1).getLocation().getX(), 0);

    // add a new packet and test update cycle
    CommsPacket anotherTestPacket = new CommsPacket();
    anotherTestPacket.setLocation(new Vector2D(10, 20));
    // CHANGE: Again needed something at index 0 otherwise cannot add to
    // ArrayList at index 1
    testChannel.setPacket(anotherTestPacket, 0);
    testChannel.setPacket(anotherTestPacket, 1);
    // test that nothing has been changed, i.e new packet is in future
    assertEquals(testChannel.getPacket(1).getLocation().getX(), 0);
    testChannel.update();
    // test that new packet is moved into current
    assertEquals(testChannel.getPacket(1).getLocation().getX(), 10);
  }

  @Test
  @DisplayName("CommsGlobal testing update")
  void CommsGlobalTest() {
    CommsPacket testPacket = new CommsPacket();
    testPacket.setLocation(new Vector2D(0, 1));
    CommsPacket testPacket2 = new CommsPacket();
    testPacket2.setLocation(new Vector2D(10, 11));
    CommsGlobal.add("PLAYER1", new CommsChannel(2));
    CommsGlobal.add("TURRET", new CommsChannel(2));

    assertNotNull(CommsGlobal.get("PLAYER1"), "CommsChannel should exist");

    try {
      CommsGlobal.add("PLAYER1", new CommsChannel(2));
      assertTrue(false, "Error should of been thrown by CommsGlobal adding a non uniquely named CommsChannel");
    } catch (Error e) {}

    CommsGlobal.get("PLAYER1").addPacket(testPacket);
    CommsGlobal.get("TURRET").addPacket(testPacket2);

    try {
      CommsGlobal.get("PLAYER1").getPacket(1);
      assertTrue(false, "Error should of been thrown by CommsChannel returning a null packet");
    } catch (Error e) {}

    // test that new staged packet in PLAYER1 CommsChannel is made accesible through CommsGlobal.update()
    CommsGlobal.update();
    assertNotNull(CommsGlobal.get("PLAYER1").getPacket(0));
    assertEquals(CommsGlobal.get("PLAYER1").getPacket(0).getLocation().getX(), 0);

    assertNotNull(CommsGlobal.get("TURRET").getPacket(0));
    assertEquals(CommsGlobal.get("TURRET").getPacket(0).getLocation().getX(), 10);
  }

  @Test
  @DisplayName("CommsGlobal testing update")
  void CommsTestIntegration() {

  }

}