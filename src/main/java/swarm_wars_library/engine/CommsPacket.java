package swarm_wars_library.engine;

public class CommsPacket {
  private Vector2D location;
  private boolean isAlive;

  public void setLocation(Vector2D location) {
    this.location = location;
  }

  public Vector2D getLocation() {
    return location;
  }

  public void setIsAlive(boolean isAlive) {
    this.isAlive = isAlive;
  }

  public boolean getIsAlive() {
    return isAlive;
  }
}
