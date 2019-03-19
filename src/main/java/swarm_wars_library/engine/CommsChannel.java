package swarm_wars_library.engine;

public class CommsChannel {

  private int numberOfReceivers;
  private CommsPacket[] currentPackets;
  private CommsPacket[] futurePackets;

  public CommsChannel(int numberOfReceivers) {
    this.numberOfReceivers = numberOfReceivers;
    currentPackets = new CommsPacket[numberOfReceivers];
    futurePackets = new CommsPacket[numberOfReceivers];
  }

  public CommsPacket getPacket(int i) {
    return currentPackets[i];
  }

  public void setPacket(CommsPacket packet, int i) {
    futurePackets[i] = packet;
  }

  public int getNumberOfReceivers() {
    return numberOfReceivers;
  }

  public void update() {
    currentPackets = futurePackets.clone();
  }
}
