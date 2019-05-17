package swarm_wars_library.engine;

import java.util.ArrayList;

import swarm_wars_library.entities.Bullet;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.sound.SoundMixer;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.STATE;
import swarm_wars_library.entities.Tag;
import swarm_wars_library.map.Map;

public class Shooter{

  ArrayList<Bullet> magazine;
  int magCount = 0;
  int numBullets = Map.getInstance().getNumBulletsPerMagazine();
  int shooterCount = 0;
  int shooterTimer = Map.getInstance().getShooterTimer();

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================//
  public Shooter(ENTITY tag, int bulletForce){
    this.magazine = new ArrayList<Bullet>();

    for(int i = 0; i < this.numBullets; i++){
      Bullet bullet = new Bullet(Tag.getBulletTag(tag), bulletForce);
      this.magazine.add(bullet);
    }
  }

  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  public void update(){
    for(int i = 0; i < this.numBullets; i++){
      if(this.magazine.get(i).isState(STATE.ALIVE)){
        this.magazine.get(i).update();
      }
    }
  }

  //=========================================================================//
  // Magazine getter method                                                  //
  //=========================================================================//
  public ArrayList<Bullet> getMagazine(){
    return this.magazine;
  }

  //=========================================================================//
  // Shooter method                                                          //
  //=========================================================================//
  public void shoot(Vector2D location, double heading){
    if(this.shooterCount++ % this.shooterTimer == 0){

      this.magazine.get(this.magCount).shootBullet(location, heading);
      this.magCount++;
      if(this.magCount >= magazine.size()){
        this.magCount = 0;
      }
    }
  }
}