package swarm_wars_library.entities;

public enum ENTITY {
  EMPTY, 
  PLAYER1,
  PLAYER1_BOT,
  PLAYER1_BULLET,
  PLAYER1_MISSILE,
  PLAYER2,
  PLAYER2_BOT,
  PLAYER2_BULLET, 
  PLAYER2_MISSILE,
  PLAYER3, 
  PLAYER3_BOT,
  PLAYER3_BULLET, 
  PLAYER4, 
  PLAYER4_BOT,
  PLAYER4_BULLET, 
  TURRET, 
  TURRET_BULLET, 
  STAR,
  PLAYERUI,
  PLAYERUI_BOT,
  PLAYERUI_BULLET;

  public static int entityToPlayerId(ENTITY e) {
    if (e.equals(PLAYER1) || e.equals(PLAYER1_BOT)) {
      return 1;
    }else if (e.equals(PLAYER2) || e.equals(PLAYER2_BOT)) {
      return 2;
    }
    return -1;
  }
}