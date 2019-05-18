package swarm_wars_library.entities;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsPacket;
import swarm_wars_library.entities.STATE;
import swarm_wars_library.physics.RigidBody;
import swarm_wars_library.physics.Transform;
import swarm_wars_library.physics.Vector2D;

public abstract class AbstractEntity implements IEntity, IComms{
  
  protected CommsPacket commsPacket;
  protected STATE state;
  protected RigidBody rigidbody;
  protected ENTITY tag;
  protected Transform transform;

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================//
  public AbstractEntity(ENTITY tag, int scale){
    this.commsPacket = new CommsPacket();
    this.state = STATE.ALIVE;
    this.rigidbody = new RigidBody();
    this.tag = tag;
    this.transform = new Transform(scale);
  }

  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  public abstract void update();

  //=========================================================================//
  // Collision method                                                        //
  //=========================================================================//
  public abstract void collidedWith(ENTITY tag);

  //=========================================================================//
  // State methods                                                           //
  //=========================================================================//
  @Override
  public boolean isState(STATE state){
    if(this.state.equals(state)){
      return true;
    }
    return false;
  }

  @Override
  public STATE getState(){
    return this.state;
  }

  @Override
  public void setState(STATE state){
    this.state = state;
  }

  @Override
  // Explode only lasts one turn
  public void updateExplode2Dead(){
    if(this.isState(STATE.EXPLODE)){
      this.setState(STATE.DEAD);
    }
  }

  //=========================================================================//
  // Tag method                                                              //
  //=========================================================================//
  @Override
  public ENTITY getTag(){
    // Public because needed by physics collision detection
    return this.tag;
  }

  //=========================================================================//
  // Transform getters and setter methods                                    //
  //=========================================================================//
  @Override
  public Vector2D getLocation(){
    // Public because needed by physics collision detection
    return this.transform.getLocation();
  }

  protected void setLocation(Vector2D location){
    this.transform.setLocation(location);
  }

  protected double getHeading(){
    return this.transform.getHeading();
  }

  protected void setHeading(double heading){
    this.transform.setHeading(heading);
  }

  protected Vector2D getVelocity(){
    return this.transform.getVelocity();
  }
  
  protected void setVelocity(Vector2D velocity){
    this.transform.setVelocity(velocity);
  }

  @Override
  public double getScale(){
    return this.transform.getScale();
  }

  //=========================================================================//
  // Comms methods                                                           //
  //=========================================================================//
  @Override
  public void sendCommsPacket(){
    CommsGlobal.get(this.tag.toString()).addPacket(this.commsPacket);
  }
}

