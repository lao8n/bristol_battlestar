package swarm_wars_library.graphics;

import swarm_wars_library.engine.Vector2D;
import swarm_wars_library.engine.Tag;
import swarm_wars_library.comms.CommsGlobal;

import java.util.*;

import processing.core.PApplet;

public class Render {

  private PApplet sketch;
  private int scale;
  private int numParticles = 20;
  private double render_x;
  private double render_y;
  private Vector2D view_centre;

  public Render(PApplet sketch, int s){
    this.sketch = sketch;
    this.scale = s;
  }

  public void update(Vector2D loc, Tag tag, double heading, Vector2D view_centre){
      //drawBackground();
      this.view_centre = view_centre;
      this.render_x = loc.getX() - this.view_centre.getX()
                                 + this.sketch.width / 2;
      this.render_y = loc.getY() - this.view_centre.getY()
                                 + this.sketch.height / 2;
      
      if(this.render_x >= 0 && this.render_x <= this.sketch.width &&
        this.render_y >= 0 && this.render_y <= this.sketch.height){
        this.sketch.pushMatrix();
        //this.sketch.ellipseMode(0);
        this.sketch.stroke(0);
        this.sketch.translate((float) this.render_x, (float) this.render_y);
        this.sketch.rotate((float) heading);
        drawEntity(loc, tag);
        this.sketch.popMatrix();
      }
   }

  public void drawEntity(Vector2D loc, Tag tag){
    
    switch(tag){
      case PLAYER: drawPlayer(loc);
        break;
      case ENEMY: drawEnemy(loc);
        break;
      case E_BULLET: drawBullet(loc, false);
        break;
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
    this.sketch.noStroke();
    this.sketch.fill(70, 102, 255); 
    //this.sketch.ellipse((int)loc.getX(), (int)loc.getY(), this.scale-10, this.scale); 
    //this.sketch.rectMode(2);
    //this.sketch.rect(0, 0, this.scale, this.scale);
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale, this.scale); 
    // Dark inside
    this.sketch.fill(17, 8, 117, 50); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale-2, this.scale-2); 
    this.sketch.fill(9, 3, 71); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale-3, this.scale-3); 
    // Add glow
    this.sketch.fill(21, 0, 255, 60); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale+2, this.scale+2); 
    this.sketch.fill(21, 0, 255, 40); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale+3, this.scale+3); 
  }
  
  public void drawBot(Vector2D loc){ 
    this.sketch.noStroke();
    this.sketch.fill(50, 50, 255); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale, this.scale); 
    // Dark Inside 
    this.sketch.fill(17, 17, 135); 
    this.sketch.ellipse(0, 0, this.scale-2, this.scale-2); 
    // Add Glow
    this.sketch.fill(15, 15, 221, 50); 
    this.sketch.ellipse(0, 0, this.scale+2, this.scale+2); 
  }

  public void drawEnemy(Vector2D loc){
    this.sketch.noStroke();
    this.sketch.fill(168, 5, 78); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale, this.scale); 
    //this.sketch.fill(150, 0, 35); 
    //this.sketch.ellipseMode(2);
    //this.sketch.ellipse(0, 0, this.scale-5, this.scale-5); 
    // Dark inside
    this.sketch.fill(81, 4, 37, 90); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale-3, this.scale-3); 
    this.sketch.fill(81, 4, 37, 85); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale-2, this.scale-2); 
    // Add glow
    this.sketch.fill(239, 2, 57, 60); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale+1, this.scale+1); 
    this.sketch.fill(239, 2, 57, 50); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale+2, this.scale+2);
    this.sketch.fill(239, 2, 57, 30); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale+3, this.scale+3);  
  }

  public void drawEnemyVoid(Vector2D loc){
    this.sketch.fill(0, 0, 0); 
    this.sketch.noStroke();
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0, 0, this.scale, this.scale); 
  }
  
  public void drawBullet(Vector2D loc, boolean isPlayer){
    this.sketch.noStroke();
    if (isPlayer){
      this.sketch.fill(0, 237, 255); //neon blue
    } else {
      this.sketch.fill(255, 0, 199); //neon pink
    }
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(0,0, this.scale, this.scale); 
  }

  public void drawHealth(int health){
    //draw border
    this.sketch.rectMode(0);
    this.sketch.stroke(20, 100, 0);
    this.sketch.fill(0, 72, 150);
    this.sketch.rect(775, 5, 100 ,30);
    //draw health
    this.sketch.stroke(25, 25, 76);
    this.sketch.fill(31, 126, 226);
    this.sketch.rect(775, 5, health, 30);
  }

  public void drawPoints(int points){
    this.sketch.fill(0, 101, 255);
    this.sketch.textSize(30);
    this.sketch.textAlign(this.sketch.LEFT, this.sketch.TOP);
    this.sketch.text("POINTS: " + points, 5, 5);
  }

  public void drawExplosion(Vector2D loc, Tag tag){

    int r = 0, g = 0, b = 0, alpha; 

    // Bigger entities have longer explosions
    int frames = 5;
    if (tag.equals(Tag.ENEMY)){
      frames = 10;
      // To put a black hole briefly where enemy was
      drawEnemyVoid(loc);
      r = 229; g = 11; b = 109;
    } else if (tag.equals(Tag.P_BULLET)){
      r = 0; g = 237; b = 255; 
    } else if (tag.equals(Tag.E_BULLET)){
      r = 255; g = 0; b = 199;
    }

    this.sketch.fill(242, 227, 6, 50); 
    this.sketch.noStroke();
    this.sketch.ellipseMode(2);

    List<Particle> list = new ArrayList<Particle>();

    for (int i = 0; i < numParticles; i++){
      // Create particle in randomised circle around entity
      float startX = (float) (loc.getX() + (-1 + (1 - - 1) * (float) Math.random()));
      float startY = (float) (loc.getY() + (-1 + (1 - - 1) * (float) Math.random()));
      Vector2D start = new Vector2D(startX, startY);

      Particle p = new Particle(startX, startY);

      // Set force for each particle to move away from entity
      Vector2D temp = Vector2D.sub(start, loc);
      p.setForce(temp);

      // Add to particle list
      list.add(p);
    }

    // draw explosion
    for (int j = 0; j < frames; j++){
      this.sketch.fill(r, g, b, 50);
      alpha = 20;
      for (Particle p : list){
        if (tag.equals(Tag.ENEMY)){drawEnemyParticle(p, r, g, b, alpha);}
        else if (tag.equals(Tag.P_BOT)){drawPlayerBotParticle(p, r, g, b, alpha);}
        else {drawBulletParticle(p, r, g, b, alpha);}
        alpha += 10;
        p.update();
      }
    }
  }

  private void drawEnemyParticle(Particle p, int r, int g, int b, int alpha){
    this.sketch.fill(r, g, b, alpha); 
    this.sketch.ellipse((float) p.getX(), (float) p.getY(), 6, 6);
  }

  private void drawPlayerBotParticle(Particle p, int r, int g, int b, int alpha){
    this.sketch.fill(r, g, b, alpha); 
    this.sketch.ellipse((float) p.getX(), (float) p.getY(), 4, 4);
  }

  private void drawBulletParticle(Particle p, int r, int g, int b, int alpha){
    this.sketch.fill(r, g, b, alpha); 
    this.sketch.ellipse((float) p.getX(), (float) p.getY(), 3, 3);
  }

  public void drawInitScreen(float width, float height){
    this.sketch.background(56,1,9);
    this.sketch.fill(25, 0, 255);
    this.sketch.textAlign(this.sketch.CENTER);
    this.sketch.textSize(50);
    this.sketch.text("BATTLESTAR\n\nsurvive the swarm", width / 2, height / 2);
  }

  public void drawGameOverScreen(float width, float height){
    this.sketch.background(17, 0, 2);
    // Pink Glow
    this.sketch.fill(255, 0, 89); 
    this.sketch.textAlign(this.sketch.CENTER);
    this.sketch.textSize(81);
    this.sketch.text("GAME OVER", width / 2, height / 2);
    //DARK BLUE
    this.sketch.fill(19, 0, 232); 
    this.sketch.textAlign(this.sketch.CENTER);
    this.sketch.textSize(80);
    this.sketch.text("GAME OVER", width / 2, height / 2);

    // random particle explosion
    Vector2D v = new Vector2D(Math.random() * width +1, Math.random() * height + 1);
    drawExplosion(v, Tag.ENEMY);
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
