package swarm_wars_logic.comms;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsChannel;
import swarm_wars_library.comms.CommsPacket;
import swarm_wars_library.engine.Vector2D;

class CommsTests {

    @Test
    @DisplayName("CommsPacket testing getters and setters")
    void CommsPacketTest(){
        CommsPacket testPacket = new CommsPacket();
        testPacket.setLocation(new Vector2D(0, 10));
        assertEquals(testPacket.getLocation().getX(), 0);
        assertEquals(testPacket.getLocation().getY(), 10);
        testPacket.setAlive(true);
        assertEquals(testPacket.isAlive(), true);
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
        testChannel.update();
        assertEquals(testChannel.getPacket(1).getLocation().getX(), 0);

        // add a new packet and test update cycle
        CommsPacket anotherTestPacket = new CommsPacket();
        anotherTestPacket.setLocation(new Vector2D(10, 20));
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
        CommsGlobal.add("PLAYER", new CommsChannel(2));
        CommsGlobal.add("ENEMY", new CommsChannel(2));

        assertNotNull(CommsGlobal.get("PLAYER"), "CommsChannel should exist");

        try {
            CommsGlobal.add("PLAYER", new CommsChannel(2));
            assertTrue(false, "Error should of been thrown by CommsGlobal adding a non uniquely named CommsChannel");
        } catch (Error e) {}

        CommsGlobal.get("PLAYER").addPacket(testPacket);
        CommsGlobal.get("ENEMY").addPacket(testPacket2);

        try {
            CommsGlobal.get("PLAYER").getPacket(1);
            assertTrue(false, "Error should of been thrown by CommsChannel returning a null packet");
        } catch (Error e) {}

        // test that new staged packet in PLAYER CommsChannel is made accesible through CommsGlobal.update()
        CommsGlobal.update();
        assertNotNull(CommsGlobal.get("PLAYER").getPacket(0));
        assertEquals(CommsGlobal.get("PLAYER").getPacket(0).getLocation().getX(), 0);

        assertNotNull(CommsGlobal.get("ENEMY").getPacket(0));
        assertEquals(CommsGlobal.get("ENEMY").getPacket(0).getLocation().getX(), 10);
    }

    @Test
    @DisplayName("CommsGlobal testing update")
    void CommsTestIntegration() {

    }

}
