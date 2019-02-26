package swarm_wars_library.engine;

public class CommsChannel {

  private Vector2D motherLocation;

  public Vector2D getMotherLocation () {
    return motherLocation;
  }

  public void setMotherLocation (Vector2D motherLocation) {
    this.motherLocation = motherLocation;
  }

  public double getMotherLocationX(){
    return motherLocation.getX();
  }

  public double getMotherLocationY(){
    return motherLocation.getY();
  }

  /*public double getMotherheading(){
    return motherLocation.getY();
  }*/
}
