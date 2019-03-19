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
      case P_BOT:
      case E_BOT: drawBot(loc);
        break;
    }
  }
    
  public void drawPlayer(Vector2D loc){ 
    this.sketch.fill(70, 102, 255); 
    //this.sketch.ellipse((int)loc.getX(), (int)loc.getY(), this.scale-10, this.scale); 
    this.sketch.rectMode(2);
    this.sketch.rect(0, 0, this.scale, this.scale);
  }
  
  public void drawBot(Vector2D loc){ 
    this.sketch.fill(50, 50, 255); 
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
  public void drawHealth(int health){
    //draw boreder
    this.sketch.rectMode(1);
    this.sketch.stroke(204, 102, 0);
    this.sketch.fill(25, 25, 76);
    this.sketch.rect(5, 5,100 ,30);
    //draw health
    this.sketch.stroke(25, 25, 76);
    this.sketch.fill(255); 
    this.sketch.rect(5, 5,health ,30);
    
  }
}

/* ------ DISPLAY CLASS ------ */
//class Display {
//   public void display(GameObject go){
//drawBackground();
//      pushMatrix();
//      rectMode(CENTER);
//      stroke(0);
//      translate((float)go.getLocationX(), (float)go.getLocationY());
//      rotate((float)go.getHeading());
//      drawObject(go);
//        popMatrix();
//   }

//TODO : add tracking, moving background image
//   private void drawBackground(){
//        background(25,25,76); //dark blue
//}

//     private void drawObject(GameObject go){
//       if (go.getGOTag() == "PLAYER"){
//         drawShip(go);
//       } else if (go.getGOTag() == "ENV"){
//         drawEnv(go);

//will add drawBullet, drawEnemy, drawBase etc
//     } else {
//do nothing
//       }
//     }

//   private void drawShip(GameObject go){
//go.setScale(30, 20);
//   go.setScale(20,10);
//   if (go.getHasCollision()){
//      fill(random(0, 255),random(0, 255),random(0, 255));
//   }
//shadow
//   noStroke();
//   fill(33,11,232, 20);
//   rect(0, 0, (float)go.getScaleX()*1.75, (float)go.getScaleY()*1.75);
//   fill(33,11,232, 50);
//   rect(0, 0, (float)go.getScaleX()*1.5, (float)go.getScaleY()*1.5);
//   fill(33,11,232, 70);
//   rect(0, 0, (float)go.getScaleX()*1.2, (float)go.getScaleY()*1.2);
//wing shadows
//     fill(33, 11, 232, 70);
//     rect(-(float)go.getScaleX()/1.75, (float)go.getScaleY()/2.5,
///         (float)go.getScaleX(), (float)go.getScaleY());
//     rect(-(float)go.getScaleX()/1.75, -(float)go.getScaleY()/2.5,
//        (float)go.getScaleX(), (float)go.getScaleY());
//wings
// fill(33, 11, 232);
// rect(-(float)go.getScaleX()/1.5, (float)go.getScaleY()/2,
//      (float)go.getScaleX(), (float)go.getScaleY());
// rect(-(float)go.getScaleX()/1.5, -(float)go.getScaleY()/2,
//      (float)go.getScaleX(), (float)go.getScaleY());
//ship head
//     fill(77, 77, 255);
//     rect(0, 0, (float)go.getScaleX(), (float)go.getScaleY());
//   }

// private void drawEnv(GameObject go){
//  noStroke();
// go.setScale(50, 40);
//fill(128, 128, 128);
//if (go.getHasCollision()){
//    fill(random(0, 255),random(0, 255),random(0, 255));
//}
//rect(0, 0, (float)go.getScaleX(), (float)go.getScaleY());
// }
//}
