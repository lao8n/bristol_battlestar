package swarm_wars_library.engine;

import swarm_wars_library.engine.Vector2D;
import swarm_wars_library.engine.Tag;

import processing.core.PApplet;

public class Render {

  private PApplet sketch;
  private int scale;

  public Render(PApplet sketch, int s){
    this.sketch = sketch;
    this.scale = s;
  }

    public void update(Vector2D loc, Tag tag, double heading){
		//drawBackground();
		this.sketch.pushMatrix();
        //this.sketch.ellipseMode(0);
		this.sketch.stroke(0);
		this.sketch.translate((float)loc.getX(), (float)loc.getY());
		this.sketch.rotate((float) heading);
		drawEntity(loc, tag);
		this.sketch.popMatrix();
	 }
  
  public void drawEntity(Vector2D loc, Tag tag){
    switch(tag){
      case PLAYER: drawPlayer(loc);
        break;
      case ENEMY: drawEnemy(loc);
        break;
      case E_BULLET: drawBullet(loc, false);
      case P_BULLET: drawBullet(loc, true);
        break;
      case ROCK:
        break;
      case EMPTY:
        break;
    }
  }
    
  public void drawPlayer(Vector2D loc){ 
    this.sketch.fill(70, 102, 255); 
    //this.sketch.ellipse((int)loc.getX(), (int)loc.getY(), this.scale-10, this.scale); 
    this.sketch.rectMode(2);
    this.sketch.rect(0, 0, this.scale, this.scale);
  }
  
  public void drawEnemy(Vector2D loc){
    this.sketch.fill(98, 16, 6); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale, this.scale); 
  }
  
  public void drawBullet(Vector2D loc, boolean p){
    if (p){
      this.sketch.fill(255); 
    } else {
      this.sketch.fill(0, 250, 0);
    }
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0,0, this.scale, this.scale); 
  }
}