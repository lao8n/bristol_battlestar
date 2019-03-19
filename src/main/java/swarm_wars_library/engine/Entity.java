/* Component arch system - everything is an entity*/
package swarm_wars_library.engine;

import javax.swing.text.Position;

import processing.core.PApplet;

public class Entity {

  private PApplet sketch;
  private Tag tag;
  private Transform transform;
  private Render render;
  public Input input;
  private int points;
  private Shooter shooter;
  private Health health;
  private RigidBody rb;
  private AI ai; 
  //BOT Specific comps
  private CommsGlobal comms;
  private CommsPacket commsPacket;
  //private State state;
  private SwarmLogic swarmLogic;
  private boolean hasRender, hasInput, hasShooter, hasHealth, hasComms, isBot, hasRb, isMothership, hasAI;

  //Entity(sketch, tag, scale, hasRender, hasInput, hasShooter, hasHealth, hasComms, hasRb, isAI)
  public Entity(PApplet sketch, Tag t, int sc, boolean r, boolean i, boolean s, boolean h, boolean coms, boolean rigbod, boolean hai) {

    this.sketch = sketch;
    tag = t;
    transform = new Transform();
    hasRender = r;
    hasInput = i;
    hasShooter = s;
    hasHealth = h;
    hasComms = coms;
    hasRb = rigbod;
    isMothership = false;
    hasAI = hai; 

    if (tag.equals(Tag.P_BOT) || (tag.equals(Tag.E_BOT))) {
      isBot = true;
      //NOTICE: must call method to init swarmLogic in main loop
    }
    if (hasRender) {
      render = new Render(sketch, (int) transform.getScale().getX());
    }
    if (hasInput) {
      input = new Input(sketch);
    }
    if (hasShooter) {
      shooter = new Shooter(sketch, tag);
    }
    if (hasHealth) {
      health = new Health(tag);
    }
    if (hasRb) {
      rb = new RigidBody();
    }
    if (hasAI) {
      ai = new AI();
      System.out.println("AI created");
    }

    if (tag.equals(Tag.PLAYER) || tag.equals(Tag.ENEMY)) {
      isMothership = true;
      transform.setScale(30, 30);

    } else if (tag.equals(Tag.P_BULLET) || tag.equals(Tag.E_BULLET)) {
      transform.setScale(5, 5);
    }
  }

  public void update() {
    //draw it
    if (hasRender) {
      render.update(transform.getPosition(), tag, transform.getHeading());
    }

    //set position with either Input or AI
    if (hasInput) {
      input.update();
      transform.setPosition(input.getLocation());
      transform.setHeading(input.getHeading());
    }

    if (hasComms && !hasAI) {
      sendPacket();
    }

    if (hasAI) {

      //pass it current player position, its own transform
      //ERROR this is giving a null pointer: 
      Vector2D playerLoc = comms.get("PLAYER").getPacket(0).getLocation();
      // Vector2D playerLoc = new Vector2D(0,0);
      ai.update(playerLoc, transform);
      //shooter uses this info below to target player
    }

    if (hasShooter && hasInput) {
      shooter.shoot(transform.getPosition(), transform.getHeading());
      shooter.update();
    }

    if (hasShooter && hasAI){
      //need to set heading as direction to player
      // System.out.println("AI SHOOT");
      shooter.shoot(transform.getPosition(), ai.getHeading(), true);
      shooter.update();
    }

    if (hasHealth) {
      health.update();
    }

    if (isBot) {
      //update bot based on swarm/bot logic using the swarm component
      swarmLogic.setTransform(transform);
      swarmLogic.update();
      transform = swarmLogic.getTransform();
    }
  }

  public void setVelocity(double x, double y) {
    transform.setVelocity(x, y);
  }

  public Vector2D getVelocity() {
    return transform.getVelocity();
  }

  //set if visible
  public void setRender(boolean value) {
    hasRender = value;
  }

  //to check if entity is visible
  public boolean isRendering() {
    return hasRender;
  }

  public void setHeading(double heading) {
    transform.setHeading(heading);
  }

  public double getHeading() {
    return transform.getHeading();
  }

  //for use by shooter class to set bullet position & heading
  public void setPosition(Vector2D position, double heading) {
    transform.setHeading(heading);
    transform.setPosition(position);
  }

  //for setting position of stationary enemies/entities
  public void setPosition(double x, double y){
    transform.setPosition(x, y);
  }

  //for use by shooter to get bullet position
  public Vector2D getPosition() {
    return transform.getPosition();
  }

  public void kill() {
    hasRender = false;
    hasShooter = false;
  }

  public boolean isDead() {
    if (hasHealth) {
      return health.isDead();
    }
    return false;
  }

  public void setAlive() {
    //reset velocity to 0
    //if (hasAI){
    //  ai.reset();
    //}
    hasRender = true;
    hasShooter = true;
    //add something for health
  }

  public int getScale() {
    return (int) transform.getScale().getX();
  }

  public void setComms(CommsGlobal comms) {
    this.comms = comms;
    commsPacket = new CommsPacket();
    if (isBot) {
      swarmLogic.setComms(this.comms);
    }
    sendPacket();
  }

  //BOT methods
  public void setSwarmLogic() {
    //init swarm logic
    swarmLogic = new SwarmLogic(transform, rb);
  }

  //ALLL COMMS
  public void sendPacket() {
    //update this logic
    commsPacket.setLocation(transform.getPosition());
    commsPacket.setIsAlive(true);
    if (isMothership) {
      comms.get("PLAYER").setPacket(commsPacket, 0);
    } else {
      comms.get("PLAYER").setPacket(commsPacket, swarmLogic.getId());
    }
  }

  //will need to get and set state here for FSM
}