package swarm_wars_library.engine;

import swarm_wars_library.SwarmWars;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.entities.ENTITY;

import java.util.Map;

public class Health {

  private int currentHealth;
  private int maxHealth;
  private int damage;

  public Health (ENTITY tag) {
    if (tag.equals(ENTITY.PLAYER1) || tag.equals(ENTITY.PLAYER2)){
      maxHealth = 100;
    } else {
      maxHealth = 5;
    }
    currentHealth = maxHealth;
}

  public void update(){
    currentHealth -= damage;
    damage = 0;
  }

  public void takeDamage(int d){
    damage = d;
  }

  public int getCurrentHealth(){
    return currentHealth;
  }

  public void setHealth(int health){
    this.currentHealth = health;
  }

  public int getMaxHealth(){
    return maxHealth;
  }

  public boolean isDead(){
    if (currentHealth <= 0){
      return true;
    }
    return false;
  }

  public void reset(){
    currentHealth = maxHealth; 
  }
}
