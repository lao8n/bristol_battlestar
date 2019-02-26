/* Component arch system - everything is an entity*/
package swarm_wars_library.engine;

import processing.core.PApplet; 

public class Entity {

  private PApplet sketch; 
  private Tag tag;
  private Render render;
  private Vector2D position; 
  private  Vector2D velocity; 
  private int scale, points;
  private double heading; 
  public Input input;
  private Shooter shooter;
  private Health health;
  private boolean hasRender, hasInput, hasShooter, hasHealth; 
  
  //Entity(tag, scale, hasRender, hasInput, hasShooter, hasHealth)
  public Entity(PApplet sketch, Tag t, int sc, boolean r, boolean i, boolean s, boolean h){
    
    this.sketch = sketch; 
    heading = 0; 
    tag = t; 
    scale = sc; 
    hasRender = r;
    hasInput = i;
    hasShooter = s; 
    hasHealth = h;
    velocity = new Vector2D(0,0);
    
    position = new Vector2D(30,30); 
    if (hasRender){
      render = new Render(sketch, scale);
    }
    if (hasInput) {
      input = new Input(sketch);
    }
    if (hasShooter){
      shooter = new Shooter(sketch, tag);
    }
    if (hasHealth){
      health = new Health(tag);
    }
    
    if (tag.equals(Tag.PLAYER) || tag.equals(Tag.ENEMY)){
      scale = 30; 
    } else if (tag.equals(Tag.P_BULLET) || tag.equals(Tag.E_BULLET)){
      scale = 5;
    }
  }

  public void update(){
    //draw it
    if (hasRender){
      render.update(position, tag, heading);
    }
    
    //set position with either Input or AI
    if (hasInput){
      input.update();
      position.setAll(input.getLocation());
      heading = input.getHeading();
      System.out.println(heading);
    } 
    //else if (hasAI) {
    //  ai.update(); 
    //  position.setAll(ai.getLocation());
    //}
    
    if (hasShooter){
      shooter.shoot(position, heading); 
      shooter.update();
    }
    
    if (hasHealth){
      health.update();
    }
    
  }

  public void setVelocity(double x, double y){
      velocity.setX(x);
      velocity.setY(y);
  }

  public Vector2D getVelocity(){
      return velocity;
  }
  
  //set if visible
  public void setRender(boolean value){
    hasRender = value;
  }
  
  //to check if entity is visible
  public boolean isRendering(){
    return hasRender; 
  }

  public void setHeading(double heading){
      this.heading = heading;
  }

  public double getHeading(){
      return heading;
  }
  
  //for use by shooter class to set bullet position & heading
  public void setPosition(Vector2D position, double heading){
    this.heading = heading;
    this.position = position; 
    //if (hasAI){
    //  ai.setLocation(pos);
    //}
  }
  
  //for use by shooter to get bullet position
  public Vector2D getPosition(){
    return position;
  }
  
  public void kill(){
    hasRender = false;
    hasShooter = false;
  }
  
  public boolean isDead(){
    if (hasHealth){
      return health.isDead();
    }
    return false;
  }
  
  public void setAlive(){
    //reset velocity to 0
    //if (hasAI){
    //  ai.reset();
    //}
    hasRender = true;
    hasShooter = true;
    //add something for health
  }
  
  public int getScale(){
    return scale;
  }
}