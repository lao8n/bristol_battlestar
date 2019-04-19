/* Component arch system - everything is an entity*/
package swarm_wars_library.engine;

import java.util.ArrayList;

import processing.core.PApplet;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsPacket;
import swarm_wars_library.map.Map;

public class Entity {

  private Tag tag;
  private Transform transform;
  public Input input;
  private int points;
  private Shooter shooter;
  private Health health;
  private RigidBody rb;
  private AI ai;
  //BOT Specific comps
  private CommsPacket commsPacket;
  //private State state;
  private SwarmLogic swarmLogic;
  private boolean hasRender, hasInput, hasShooter, hasHealth, hasComms, isBot, hasRb, isMothership, hasAI;
  private boolean isAlive = true;
  private Vector2D view_centre = new Vector2D(0, 0);
  private Map map;

  private int id;
  private static int nextId = 0;

  //Entity(sketch, tag, scale, hasRender, hasInput, hasShooter, hasHealth, hasComms, hasRb, isAI)
  public Entity(
          PApplet sketch,
          Tag t,
          int sc,
          boolean r,
          boolean i,
          boolean s,
          boolean h,
          boolean coms,
          boolean rigbod,
          boolean hai) {

    this.map = Map.getInstance();
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
    points = 0;

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
    if (hasRender) {
      // render = new Render(sketch, sc);
    }
  }

  public void update() {
    // Update points
    if (tag.equals(Tag.PLAYER)){
      // render.drawPoints(points);
    }

    // Set position with either Input or AI
    if (hasInput) {
      input.update(this.view_centre);
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
      // System.out.println("AI SHOOT");
      shooter.shoot(transform.getPosition(), ai.getHeading());
      shooter.update();
    }

    if (hasHealth) {
      health.update();
      if(tag.equals(Tag.PLAYER)){
         // TODO render call should not be in update
        //  render.drawHealth(health.getCurrentHealth());
      }
      // draw explosion if dead
      if (health.getCurrentHealth() <= 0){
        // render.drawExplosion(transform.getPosition(), 
        //                      tag,
        //                      this.view_centre);
      }
    }

    if (isBot) {
      //update bot based on swarm/bot logic using the swarm component
      swarmLogic.setTransform(transform);
      swarmLogic.update();
      transform = swarmLogic.getTransform();
    }

    if (hasComms) {
      sendPacket();
    }

    //draw it
    if (hasRender) {
      // render.update(transform.getPosition(), tag, transform.getHeading(), this.view_centre);
    }
  }

  public void setViewCentre(Vector2D view_centre){
    this.view_centre = view_centre;
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

  public void setBoundingLength(){
    transform.setBoundingLength();
  }

  public double getBoundingLength(){
    return transform.getBoundingLength();
  }

  public void kill() {
    // render.drawExplosion(transform.getPosition(), 
    //                      tag,
    //                      this.view_centre);
    hasRender = false;
    hasShooter = false;
    isAlive = false;
  }

  public boolean isDead() {
    if (hasHealth) {
      return health.isDead();
    
    // Bots and some entities don't have health
    } else if (isAlive){
      return true;
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
    //isAlive = true; <-- this breaks collision detection, why? 
    //add something for health
  }

  public void setAlive(boolean value){
    hasRender = value;
    hasShooter = value;
    isAlive = value;
    if (isAlive){
      health.reset();
    }
  }

  public int getScale() {
    return (int) transform.getScale().getX();
  }

  public void setComms() {
    commsPacket = new CommsPacket();
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
    commsPacket.setLocation(transform.getPosition());
    commsPacket.setAlive(true);
    commsPacket.setVelocity(transform.getVelocity());
    commsPacket.setId(id);
    CommsGlobal.get(tag.toString()).addPacket(commsPacket);
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

  public void addPoints(int p){
    points += p;
  }

  //will need to get and set state here for FSM
}
