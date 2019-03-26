package swarm_wars_library.engine;

public class Health {

  private int currentHealth;
  private int maxHealth;
  private int damage;

  public Health (Tag tag) {
    if (tag.equals(Tag.PLAYER)){
      maxHealth = 100;
    } else {
      maxHealth = 5;
    }
    currentHealth = maxHealth;
}

  public void update(){
    currentHealth -= damage;
  }

  public void takeDamage(int d){
    damage = d;
  }

  public int getCurrentHealth(){
    return currentHealth;
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
}
