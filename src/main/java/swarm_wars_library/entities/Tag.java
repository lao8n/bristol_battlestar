package swarm_wars_library.entities;

public class Tag {
  public static ENTITY getBulletTag(ENTITY tag){
    if(tag.equals(ENTITY.PLAYER1)){
      return ENTITY.PLAYER1_BULLET;
    }
    if(tag.equals(ENTITY.PLAYER2)){
      return ENTITY.PLAYER2_BULLET;
    }
    if(tag.equals(ENTITY.PLAYERUI)){
      return ENTITY.PLAYERUI_BULLET;
    }
    return ENTITY.TURRET_BULLET;
  }

  public static ENTITY getMissileTag(ENTITY tag){
    if(tag.equals(ENTITY.PLAYER1)){
      return ENTITY.PLAYER1_MISSILE;
    }
    else {
      return ENTITY.PLAYER2_MISSILE;
    }
  }

  public static ENTITY getMotherShipTag(ENTITY tag){
    if(tag.equals(ENTITY.PLAYER1_BOT)){
      return ENTITY.PLAYER1;
    }
    if(tag.equals(ENTITY.PLAYER2_BOT)){
      return ENTITY.PLAYER2;
    }
    if(tag.equals(ENTITY.PLAYERUI_BOT)){
      return ENTITY.PLAYERUI;
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
    if(tag.equals(ENTITY.PLAYERUI_BULLET)){
      return ENTITY.PLAYERUI;
    }
    if(tag.equals(ENTITY.PLAYER1_MISSILE)){
      return ENTITY.PLAYER1;
    }
    return ENTITY.TURRET;
  }
}