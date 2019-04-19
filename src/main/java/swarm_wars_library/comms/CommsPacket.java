package swarm_wars_library.comms;

import swarm_wars_library.engine.Vector2D;

public class CommsPacket {
  private Vector2D location;
  private boolean isAlive;
  private Vector2D velocity;
  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLocation(Vector2D location) {
    this.location = location;
  }

  public Vector2D getLocation() {
    return this.location;
  }

  public boolean isAlive() {
    return this.isAlive;
  }

  public void setAlive(boolean isAlive) {
    this.isAlive = isAlive;
  }

  public Vector2D getVelocity(){
    return this.velocity;
  }

  public void setVelocity(Vector2D velocity){
    this.velocity = velocity;
  }
}
