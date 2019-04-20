/* Component arch system - everything is an entity*/
package swarm_wars_library.engine;

import java.util.ArrayList;

import processing.core.PApplet;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsPacket;
import swarm_wars_library.comms.Event;
import swarm_wars_library.input.Input;
import swarm_wars_library.map.Map;

public class Entity {

  private Tag tag;
  private Transform transform;
  public Input input;
  private Shooter shooter;
  private Health health;
  private RigidBody rb;
  private AI ai;
  //BOT Specific comps
  private CommsPacket commsPacket;
  //private State state;
  private SwarmLogic swarmLogic;
  private boolean hasInput, hasShooter, hasHealth, hasComms, isBot, hasRb, isMothership, hasAI;
  private boolean isAlive = true;
  private Map map;
  private int score = 0;
  private int id;
  private static int nextId = 0;
  private Event event = Event.DEFAULT;

  //Entity(sketch, tag, scale, hasInput, hasShooter, hasHealth, hasComms, hasRb, isAI)
  public Entity(
          PApplet sketch,
          Tag t,
          int sc,
          boolean i,
          boolean s,
          boolean h,
          boolean coms,
          boolean rigbod,
          boolean hai) {

    this.map = Map.getInstance();
    tag = t;
    transform = new Transform();
    hasInput = i;
    hasShooter = s;
    hasHealth = h;
    hasComms = coms;
    hasRb = rigbod;
    isMothership = false;
    hasAI = hai;
    id = nextId;
    nextId++;

    if (tag.equals(Tag.P_BOT) || (tag.equals(Tag.E_BOT))) {
      isBot = true;
      transform.setScale(map.getBotScale(), map.getBotScale());
      //NOTICE: must call method to init swarmLogic in main loop
    }
    if (tag.equals(Tag.P_BOT)){
      transform.setPosition(this.map.getMapWidth() / 2 - 100 +  Math.random() * 200,
                            this.map.getMapHeight() / 2 - 100 +  Math.random() * 200);
      transform.setVelocity(-0.01 + Math.random() * 0.02, -0.01 + Math.random() * 0.02);
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
    }
    if (tag.equals(Tag.PLAYER)) {
      isMothership = true;
      transform.setScale(map.getPlayerScale(), map.getPlayerScale());

    }
    if(tag.equals(Tag.ENEMY)){
      transform.setScale(map.getEnemyScale(), map.getEnemyScale());
    }
    if (tag.equals(Tag.P_BULLET) || tag.equals(Tag.E_BULLET)) {
      transform.setScale(map.getBulletScale(), map.getBulletScale());
    }
  }

  public void update() {
    // Set position with either Input or AI
    if (hasInput) {
      input.update();
      transform.setPosition(input.getLocation());
      transform.setHeading(input.getHeading());
    }
    if (hasAI) {
      //pass it current player position, its own transform
      Vector2D playerLoc = CommsGlobal.get("PLAYER").getPacket(0).getLocation();
      ai.update(playerLoc, transform);
      //shooter uses this info below to target player
    }

    if (hasShooter && hasInput) {
      if(input.getMouse() == 1) {
        shooter.shoot(transform.getPosition(), transform.getHeading());
      }
      shooter.update();
    }

    if (hasShooter && hasAI){
      //need to set heading as direction to player
      if(ai.getInRange()){
        shooter.shoot(transform.getPosition(), ai.getHeading());
      }
      shooter.update();
    }

    if (hasHealth) {
      this.health.update();
    }

    if (isBot) {
      //update bot based on swarm/bot logic using the swarm component
      swarmLogic.setTransform(transform);
      swarmLogic.update();
      transform = swarmLogic.getTransform();
    }

    this.score = CommsGlobal.get("PLAYER").getPacket(0).getScore();
    

    if (hasComms) {
      sendPacket();
    }
    if(this.event.equals(Event.EXPLODE)){
      this.event = Event.DEFAULT;
    }
  }

  public Tag getTag(){
    return this.tag;
  }

  public void takeDamage(int d){
    this.health.takeDamage(d);
  }

  public void setVelocity(double x, double y) {
    transform.setVelocity(x, y);
  }

  public Vector2D getVelocity() {
    return transform.getVelocity();
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

  public void setBoundingLength(){
    transform.setBoundingLength();
  }

  public double getBoundingLength(){
    return transform.getBoundingLength();
  }

  public void kill() {
    hasShooter = false;
    isAlive = false;   
    this.event = Event.EXPLODE;
  }

  public boolean isDead() {
    if (hasHealth) {
      return health.isDead();
    // Bots and some entities don't have health
    } else if (isAlive){
      return false;
    }
    return true;
  }

  public void setAlive(){
    isAlive = true;
  }

  public void setAlive(boolean value){
    hasShooter = value;
    isAlive = value;
    if (hasHealth){
      health.reset();
    }
  }

  public int getScale() {
    return (int) transform.getScale().getX();
  }

  public void setComms() {
    this.commsPacket = new CommsPacket();
    sendPacket();
  }

  //BOT methods
  public void setSwarmLogic() {
    //init swarm logic
    swarmLogic = new SwarmLogic(transform, rb, id);
  }

  public void selectStartingSwarmAlgorithm(String swarm_algorithm){
    swarmLogic.selectSwarmAlgorithm(swarm_algorithm);
  }

  //ALLL COMMS
  public void sendPacket() {
    this.commsPacket.setLocation(transform.getPosition());
    this.commsPacket.setAlive(this.isAlive);
    this.commsPacket.setVelocity(transform.getVelocity());
    this.commsPacket.setId(this.id);
    this.commsPacket.setHealth(this.getHealth());
    this.commsPacket.setScore(this.score);
    this.commsPacket.setEvent(this.event);
    
    CommsGlobal.get(tag.toString()).addPacket(this.commsPacket);
  }

  public ArrayList<Entity> getMagazine(){
    if (shooter != null){
      return shooter.getMagazine();
    } else {
      return null;
    }
  }

  public int getHealth(){
    if (health == null){return -1;}
    return health.getCurrentHealth();
  }

  public void resetHealth(){
    if (hasHealth){
      health.reset();
    }
  }
}
