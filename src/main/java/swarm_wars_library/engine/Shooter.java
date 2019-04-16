/*Handles adding force to bullets, and setting bullet start location as Entity holding the shooter*/
package swarm_wars_library.engine;

import processing.core.PApplet;
import swarm_wars_library.engine.Vector2D;
import java.util.*;

class Shooter {

  Vector2D location;
  Vector2D currPos; //temp for bullets
  //has list of bullets entities
  ArrayList<Entity> magazine;
  int numBullets = 20;
  int magCount = 0;
  int shooterCount = 0;
  int shootTimer = 10;
  Tag bulletTag;
  int bulletForce = 5;
  int enemyHandicap = 2;
  int bulletdemage=10;
  PApplet sketch;
  EntityBuilder eb;

  Shooter(PApplet sketch, Tag t){
    this.sketch = sketch;
    location = new Vector2D(-1000,-1000);
    currPos = new Vector2D(0,0);
    magazine = new ArrayList<Entity>();
    eb = new EntityBuilder(sketch);
    
    //use P_BULLET for PLAYER and E_BULLET for ENEMY
    if (t.equals(Tag.PLAYER)){
      bulletTag = Tag.P_BULLET;
      bulletForce=10;
    } else if (t.equals(Tag.ENEMY)){
      bulletTag = Tag.E_BULLET;
      shootTimer += 50;
    }

    //add bullets
    for (int i = 0; i < numBullets; i++){
      // Entity(sketch, tag, scale, hasRender, hasInput, hasShooter, hasHealth, hasComms, hasRb, isAI)
      Entity bullet = eb.newBullet(bulletTag);
      bullet.kill();
      magazine.add(bullet);
      magazine.get(i).setPosition(location, 1.50);
    }
  }

  void update(){
    //loops over its list of bullets and renders them if visible
    for(int i = 0; i < magazine.size(); i++){
      //should this be isAlive instead of rendering?
      if (magazine.get(i).isRendering()){
        currPos = magazine.get(i).getPosition();
        //magazine.get(i).setPosition(currPos.add(currPos, magazine.get(i).getVelocity()), magazine.get(i).getHeading());
        magazine.get(i).setPosition(Vector2D.add(currPos, magazine.get(i).getVelocity()), 
                                    magazine.get(i).getHeading());
        //update bullet to render it
        magazine.get(i).update();
      }
    }
  }

  void shoot(Vector2D location, double heading){
    //add delay between shooting
    if (shooterCount++ % shootTimer == 0){
      //makes a bullet visible
      // magazine.get(magCount).setRender(true);
      //set bullet heading
      if (heading < 0){
          heading = heading + 2 * Math.PI;
      }
      magazine.get(magCount).setVelocity(bulletForce * Math.cos(heading),
                                         bulletForce * Math.sin(heading));
      //set bullet position of entity

      magazine.get(magCount).setHeading(heading);
      //sets its location to location
      magazine.get(magCount++).setPosition(location, heading);

      if (magCount >= magazine.size()){
        magCount = 0;
      }

      magazine.get(magCount).setRender(true);
    }
  }

    void shoot(Vector2D location, double heading, boolean ai){
      //add delay between shooting
      if (shooterCount++ % shootTimer == 0){
        //makes a bullet visible
        magazine.get(magCount).setRender(true);
        magazine.get(magCount).setAlive();

        //set bullet heading
          if (heading < 0){
              heading = heading + 2 * Math.PI;
          }
          magazine.get(magCount).setVelocity(bulletForce * Math.cos(heading),
                                             bulletForce * Math.sin(heading));
          //set bullet position of entity

        magazine.get(magCount).setHeading(heading);

        //sets its location to location
        magazine.get(magCount++).setPosition(location, heading);
        if (magCount >= magazine.size()){
          magCount = 0;
        }
      }
    }

  //used by main game loop to check for collisions
  ArrayList<Entity> getMagazine(){
    return magazine;
  }
}
