package swarm_wars_library.entities;

public class Tag {
  public static ENTITY getBulletTag(ENTITY tag){
    if(tag.equals(ENTITY.PLAYER1)){
      return ENTITY.PLAYER1_BULLET;
    }
    if(tag.equals(ENTITY.PLAYER2)){
      return ENTITY.PLAYER2_BULLET;
    }
    return ENTITY.TURRET_BULLET;
  }

  public static ENTITY getMothershipTag(ENTITY tag){
    if(tag.equals(ENTITY.PLAYER1_BOT)){
      return ENTITY.PLAYER1;
    }
    if(tag.equals(ENTITY.PLAYER2_BOT)){
      return ENTITY.PLAYER2;
    }
    return ENTITY.TURRET;
  }

  public static ENTITY getShooterTag(ENTITY tag){
    if(tag.equals(ENTITY.PLAYER1_BULLET)){
      return ENTITY.PLAYER1;
    }
    if(tag.equals(ENTITY.PLAYER2_BULLET)){
      return ENTITY.PLAYER2;
    }
    return ENTITY.TURRET;
  }
}