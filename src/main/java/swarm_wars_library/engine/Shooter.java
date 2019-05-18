package swarm_wars_library.engine;

import java.util.ArrayList;

import swarm_wars_library.entities.Bullet;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.sound.SoundMixer;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.Missile;
import swarm_wars_library.entities.STATE;
import swarm_wars_library.entities.Tag;
import swarm_wars_library.map.Map;

public class Shooter{

  ArrayList<Bullet> magazine;
  //add missile
  ArrayList<Missile> magazine1;
  int magCount = 0;
  int magCount1 = 0;
  int numBullets = Map.getInstance().getNumBulletsPerMagazine();
  int numMissile = Map.getInstance().getNumMissilesPerMagazine();
  int shooterCount = 0;
  int shooterTimer = Map.getInstance().getShooterTimer();
  boolean havemissile=false;
  int missileNum = 0;

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================//
  public Shooter(ENTITY tag, int bulletForce, boolean Havemissile){
    this.magazine = new ArrayList<Bullet>();

    for(int i = 0; i < this.numBullets; i++){
      Bullet bullet = new Bullet(Tag.getBulletTag(tag), bulletForce);
      this.magazine.add(bullet);
    }
    this.havemissile = Havemissile;

    if(this.havemissile){
      this.magazine1 = new ArrayList<Missile>();
        for (int i = 0; i < this.numMissile; i++) {
          Missile missile = new Missile(Tag.getMissileTag(tag), bulletForce);
          this.magazine1.add(missile);
        }
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

    if(this.havemissile){
      for(int i = 0; i < this.numMissile; i++){
       // System.out.println(magazine1.get(i).getState());
        if(this.magazine1.get(i).isState(STATE.ALIVE)){
          this.magazine1.get(i).update();
        }
      }
    }

  }

  //=========================================================================//
  // Magazine getter method                                                  //
  //=========================================================================//
  public ArrayList<Bullet> getMagazine(){
    return this.magazine;
  }
  public ArrayList<Missile> getMagazine1(){
    return this.magazine1;
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

  public void shootM(Vector2D location, double heading){
    if(this.shooterCount++ % this.shooterTimer == 0){
      if(this.missileNum>0){
        this.magazine1.get(this.magCount1).shootMissile(location, heading);
        this.magCount1++;
        this.missileNum--;
      }
      if(this.magCount1 >= magazine1.size()){
        this.magCount1 = 0;
      }
    }
  }

  public void addMissile(){
    this.missileNum=this.missileNum+3;
  }
  public int getMissileNum(){
    return this.missileNum;
  }
}