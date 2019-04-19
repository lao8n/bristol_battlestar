package swarm_wars_library.comms;

import java.util.ArrayList;

/*
  CommsChannel - is a collection of shared information, packets
  When you get a packet it comes from the current packet list
  New packets that are set are put into a future packet list
  Update copies the future list into the current list
  This means that each entity works off the same information within one loop
 */
public class CommsChannel {

  private int numberOfReceivers;
  private ArrayList<CommsPacket> currentPackets;
  private ArrayList<CommsPacket> futurePackets;

  public CommsChannel(int numberOfReceivers) {
    this.numberOfReceivers = numberOfReceivers;
    currentPackets = new ArrayList<CommsPacket>();
    futurePackets = new ArrayList<CommsPacket>();
  }

  // TODO this should probably return an immutable copy of the packet
  public CommsPacket getPacket(int i) {
    if(i >= currentPackets.size()) throw new Error ("Getting packet out of range of CommsChannel..");
    CommsPacket packet = currentPackets.get(i);
    if(packet == null) throw new Error ("Getting packet from CommsChannel that is null, have you set it yet?");
    return packet;
  }

  // TODO this should probably return an immutable copy of the packet
  public ArrayList<CommsPacket> getPackets(){
    return currentPackets;
  }

  public void setPacket(CommsPacket packet, int i) {
    if(packet == null) throw new Error ("Setting a null packet in CommsChannel");
    // CHANGE: size() doesn't make sense here as it is the number of values
    // already in the arraylist not the maximum size (which is unlimited). 
    // Instead check that adding to just next value (or less)
    if(i >= futurePackets.size() + 1) throw new Error ("Setting packet out of range of CommsChannel..");

    // CHANGE: was set for Arrays, not add for ArrayLists
    futurePackets.add(i, packet);
  }

  public void addPacket(CommsPacket packet) {
    if(packet == null) throw new Error ("Setting a null packet in CommsChannel");
    futurePackets.add(packet);
  }

  public int getNumberOfReceivers() {
    return numberOfReceivers;
  }


  public void update() {
    currentPackets = new ArrayList<>(futurePackets);
    futurePackets = new ArrayList<>();
  }
}
