package swarm_wars_library.engine;

class Health {

  int currentHealth;
  int maxHealth;
  int damage;
  
  Health (Tag tag) {
      if (tag.equals(Tag.PLAYER)){
        maxHealth = 100;
      } else {
        maxHealth = 5; 
      }
      currentHealth = maxHealth;
  }
  
  void update(){
    currentHealth -= damage;
  }

  void takeDamage(int d){
    damage = d;
  }
  
  int getHealth(){
    return currentHealth;
  }
  
  boolean isDead(){
    if (currentHealth <= 0){
      return true;
    }
    return false;
  }
}