package swarm_wars_logic.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;
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
        testPacket.setIsAlive(true);
        assertEquals(testPacket.getIsAlive(), true);
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
        CommsGlobal testGlobal = new CommsGlobal();
        CommsPacket testPacket = new CommsPacket();
        testPacket.setLocation(new Vector2D(0, 1));
        CommsPacket testPacket2 = new CommsPacket();
        testPacket2.setLocation(new Vector2D(10, 11));
        testGlobal.add("PLAYER", new CommsChannel(2));
        testGlobal.add("ENEMY", new CommsChannel(2));

        assertNotNull(testGlobal.get("PLAYER"), "CommsChannel should exist");

        try {
            testGlobal.add("PLAYER", new CommsChannel(2));
            assertTrue(false, "Error should of been thrown by CommsGlobal adding a non uniquely named CommsChannel");
        } catch (Error e) {}

        testGlobal.get("PLAYER").setPacket(testPacket, 1);
        testGlobal.get("ENEMY").setPacket(testPacket2, 1);

        try {
            testGlobal.get("PLAYER").getPacket(1);
            assertTrue(false, "Error should of been thrown by CommsChannel returning a null packet");
        } catch (Error e) {}

        // test that new staged packet in PLAYER CommsChannel is made accesible through CommsGlobal.update()
        testGlobal.update();
        assertNotNull(testGlobal.get("PLAYER").getPacket(1));
        assertEquals(testGlobal.get("PLAYER").getPacket(1).getLocation().getX(), 0);

        assertNotNull(testGlobal.get("ENEMY").getPacket(1));
        assertEquals(testGlobal.get("ENEMY").getPacket(1).getLocation().getX(), 10);

    }
}
