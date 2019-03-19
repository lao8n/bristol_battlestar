package swarm_wars_library.engine;

public class CommsChannel {

  private int numberOfReceivers;
  private CommsPacket[] currentPackets;

  public CommsChannel(int numberOfReceivers) {
    this.numberOfReceivers = numberOfReceivers;
    this.currentPackets = new CommsPacket[numberOfReceivers];
  }

  public CommsPacket getPacket(int i) {
    return currentPackets[i];
  }

  public void setPacket(CommsPacket packet, int i) {
    currentPackets[i] = packet;
  }

  public int getNumberOfReceivers() {
    return numberOfReceivers;
  }
}
