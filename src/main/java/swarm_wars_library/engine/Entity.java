/* Component arch system - everything is an entity*/
package swarm_wars_library.engine;

import processing.core.PApplet; 

public class Entity {

  private PApplet sketch; 
  private Tag tag;
  private Render render;
  private Vector2D position; 
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
      render.update(position, tag);
    }
    
    //set position with either Input or AI
    if (hasInput){
      input.update();
      position.setAll(input.getLocation());
      heading = input.getHeading();
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
  
  //set if visible
  public void setRender(boolean value){
    hasRender = value;
  }
  
  //to check if entity is visible
  public boolean isRendering(){
    return hasRender; 
  }
  
  //for use by shooter class to set bullet position
  public void setPosition(Vector2D pos){
    position = pos; 
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