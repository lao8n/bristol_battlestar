package swarm_wars_library.graphics;

import processing.core.PApplet;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.STATE;
import swarm_wars_library.map.Map;

public class RenderLayers{
  private Map map;
  private RenderBackground renderBackground;
  private RenderStar renderStar;
  private RenderTurret renderTurret;
  private RenderTurretBullet renderTurretBullet;
  private RenderPlayer1 renderPlayer1;
  private RenderPlayer1Bullet renderPlayer1Bullet;
  private RenderPlayer1Bot renderPlayer1Bot;
  private RenderPlayer1Score renderPlayer1Score;
  private RenderPlayer1Health renderPlayer1Health;
  private RenderPlayer2 renderPlayer2;
  private RenderPlayer2Bullet renderPlayer2Bullet;
  private RenderPlayer2Bot renderPlayer2Bot;
  private RenderPlayer2Score renderPlayer2Score;
  private RenderPlayer2Health renderPlayer2Health;
  private RenderMiniMap renderMiniMap;
  private RenderMiniMapPlayer1 renderMiniMapPlayer1;
  private RenderMiniMapPlayer2 renderMiniMapPlayer2;
  private RenderMiniMapTurret renderMiniMapTurret;
  private RenderMiniMapBot renderMiniMapBot;
  private String playerMe;
  private String playerEnemy;

  public RenderLayers(PApplet sketch){
    this.map = Map.getInstance();
    this.renderBackground = new RenderBackground(sketch);
    this.renderStar = new RenderStar(sketch);
    this.renderTurret = new RenderTurret(sketch);
    this.renderTurretBullet = new RenderTurretBullet(sketch);
    this.renderPlayer1 = new RenderPlayer1(sketch);
    this.renderPlayer1Bullet = new RenderPlayer1Bullet(sketch);
    this.renderPlayer1Bot = new RenderPlayer1Bot(sketch);
    this.renderPlayer1Score = new RenderPlayer1Score(sketch);
    this.renderPlayer1Health = new RenderPlayer1Health(sketch);
    this.renderPlayer2 = new RenderPlayer2(sketch);
    this.renderPlayer2Bullet = new RenderPlayer2Bullet(sketch);
    this.renderPlayer2Bot = new RenderPlayer2Bot(sketch);
    this.renderPlayer2Score = new RenderPlayer2Score(sketch);
    this.renderPlayer2Health = new RenderPlayer2Health(sketch);
    this.renderMiniMap = new RenderMiniMap(sketch, 200, 20);
    this.renderMiniMapPlayer1 = new RenderMiniMapPlayer1(sketch);
    this.renderMiniMapPlayer2 = new RenderMiniMapPlayer2(sketch);
    this.renderMiniMapTurret = new RenderMiniMapTurret(sketch);
    this.renderMiniMapBot = new RenderMiniMapBot(sketch);

    int playerId = map.getPlayerId();
    int enemyId = map.getEnemyId();
    playerMe = "PLAYER" + Integer.toString(playerId);
    playerEnemy = "PLAYER" + Integer.toString(enemyId);
  }

  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  public void update(){
    this.renderBackgroundLayer();
    this.renderEntitiesLayer();
    this.renderDisplaysLayer();
    this.renderMiniMapEntitiesLayer();
  }

  //=========================================================================//
  // Render Background Layer                                                 //
  //=========================================================================//
  private void renderBackgroundLayer(){
    // Render background
    this.renderBackground.update();
    // Render Stars
    for (int i = 0; i < this.map.getMapStars().size(); i++){
      this.renderStar.update(this.map.getMapStars().get(i),
                             CommsGlobal.get(playerMe)
                                        .getPacket(0)
                                        .getLocation());
    }
  }

  //=========================================================================//
  // Render Entities Layer                                                   //
  //=========================================================================//
  private void renderEntitiesLayer(){
    this.renderPlayer1Bots();
    this.renderPlayer2Bots();
    this.renderTurrets();
    this.renderPlayer1();
    this.renderPlayer2();
    this.renderTurretBullets();
    this.renderPlayer1Bullets();
    this.renderPlayer2Bullets();
  }

  //=========================================================================//
  // Render Player1 Bots Layer                                               //
  //=========================================================================//
  private void renderPlayer1Bots(){
    for (int i = 0; i < CommsGlobal.get("PLAYER1_BOT")
                                   .getNumberOfReceivers(); i++){
      // Render entity if alive
      if(CommsGlobal.get("PLAYER1_BOT")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.ALIVE)){
        this.renderPlayer1Bot.update(CommsGlobal.get("PLAYER1_BOT")
                                                .getPacket(i)
                                                .getLocation(),
                                      CommsGlobal.get(playerMe)
                                                .getPacket(0)
                                                .getLocation());
      }
      // Render explosions
      if(CommsGlobal.get("PLAYER1_BOT")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.EXPLODE)){
          this.renderPlayer1Bot.updateExplosion(CommsGlobal.get("PLAYER1_BOT")
                                                          .getPacket(i)
                                                          .getLocation(),
                                              CommsGlobal.get(playerMe)
                                                          .getPacket(0)
                                                          .getLocation(),
                                              5);
      }
    }
  }

  //=========================================================================//
  // Render Player2 Bots Layer                                               //
  //=========================================================================//
  private void renderPlayer2Bots(){
    for (int i = 0; i < CommsGlobal.get("PLAYER2_BOT")
                                   .getNumberOfReceivers(); i++){
      // Render entity if alive
      if(CommsGlobal.get("PLAYER2_BOT")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.ALIVE)){
        this.renderPlayer2Bot.update(CommsGlobal.get("PLAYER2_BOT")
                                                .getPacket(i)
                                                .getLocation(),
                                      CommsGlobal.get(playerMe)
                                                .getPacket(0)
                                                .getLocation());
      }
      // Render explosions
      if(CommsGlobal.get("PLAYER2_BOT")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.EXPLODE)){
          this.renderPlayer2Bot.updateExplosion(CommsGlobal.get("PLAYER2_BOT")
                                                            .getPacket(i)
                                                            .getLocation(),
                                                CommsGlobal.get(playerMe)
                                                            .getPacket(0)
                                                            .getLocation(),
                                                5);
      }
    }
  }

  //=========================================================================//
  // Render Turrets Layer                                                    //
  //=========================================================================//
  private void renderTurrets(){
    for (int i = 0; i < CommsGlobal.get("TURRET").getNumberOfReceivers(); i++){
      // Render entity if alive
      if(CommsGlobal.get("TURRET")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.ALIVE)){
        this.renderTurret.update(CommsGlobal.get("TURRET")
                                           .getPacket(i)
                                           .getLocation(),
                                CommsGlobal.get(playerMe)
                                           .getPacket(0)
                                           .getLocation());
      }
      // Render explosions
      if(CommsGlobal.get("TURRET")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.EXPLODE)){
          this.renderTurret.updateExplosion(CommsGlobal.get("TURRET")
                                                      .getPacket(i)
                                                      .getLocation(),
                                           CommsGlobal.get(playerMe)
                                                      .getPacket(0)
                                                      .getLocation(),
                                           5);
          this.renderTurret.updateVoid(CommsGlobal.get("TURRET")
                                                 .getPacket(i)
                                                 .getLocation(),
                                       CommsGlobal.get(playerMe)
                                                 .getPacket(0)
                                                 .getLocation());
      }
    }
  }

  //=========================================================================//
  // Render Player1                                                          //
  //=========================================================================//
  private void renderPlayer1(){
    // Render entity if alive
    if(CommsGlobal.get("PLAYER1")
                  .getPacket(0)
                  .getState()
                  .equals(STATE.ALIVE)){
      this.renderPlayer1.update(CommsGlobal.get("PLAYER1")
                                            .getPacket(0)
                                            .getLocation(),
                                CommsGlobal.get(playerMe)
                                            .getPacket(0)
                                            .getLocation());
    }

    // Render explosions
    if(CommsGlobal.get("PLAYER1")
                  .getPacket(0)
                  .getState()
                  .equals(STATE.EXPLODE)){
      this.renderPlayer1.updateExplosion(CommsGlobal.get("PLAYER1")
                                                    .getPacket(0)
                                                    .getLocation(),
                                          CommsGlobal.get(playerMe)
                                                    .getPacket(0)
                                                    .getLocation(),
                                          5);
    }
  }

  //=========================================================================//
  // Render Player2                                                          //
  //=========================================================================//
  private void renderPlayer2(){
    // Render entity if alive
    if(CommsGlobal.get("PLAYER2")
                  .getPacket(0)
                  .getState()
                  .equals(STATE.ALIVE)){
      this.renderPlayer2.update(CommsGlobal.get("PLAYER2")
                                            .getPacket(0)
                                            .getLocation(),
                                CommsGlobal.get(playerMe)
                                            .getPacket(0)
                                            .getLocation());
      }

    // Render explosions
    if(CommsGlobal.get("PLAYER2")
                  .getPacket(0)
                  .getState()
                  .equals(STATE.EXPLODE)){
      this.renderPlayer2.updateExplosion(CommsGlobal.get("PLAYER2")
                                                    .getPacket(0)
                                                    .getLocation(),
                                          CommsGlobal.get(playerMe)
                                                    .getPacket(0)
                                                    .getLocation(),
                                          5);
      }
  }


  //=========================================================================//
  // Render Turret Bullets Layer                                             //
  //=========================================================================//
  private void renderTurretBullets(){
    for (int i = 0; i < CommsGlobal.get("TURRET_BULLET")
                                   .getNumberOfReceivers(); i++){
      // Render entity if alive
      if(CommsGlobal.get("TURRET_BULLET")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.ALIVE)){
        this.renderTurretBullet.update(CommsGlobal.get("TURRET_BULLET")
                                                 .getPacket(i)
                                                 .getLocation(),
                                      CommsGlobal.get(playerMe)
                                                 .getPacket(0)
                                                 .getLocation());
      }
      // Render explosions
      if(CommsGlobal.get("TURRET_BULLET")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.EXPLODE)){
        this.renderTurretBullet.updateExplosion(CommsGlobal.get("TURRET_BULLET")
                                                          .getPacket(i)
                                                          .getLocation(),
                                                CommsGlobal.get(playerMe)
                                                          .getPacket(0)
                                                          .getLocation(),
                                                3);
      }
    }
  }
  
  //=========================================================================//
  // Render Player1 Bullets Layer                                            //
  //=========================================================================//
  private void renderPlayer1Bullets(){
    for (int i = 0; i < CommsGlobal.get("PLAYER1_BULLET")
                                   .getNumberOfReceivers(); i++){
      // Render entity if alive
      if(CommsGlobal.get("PLAYER1_BULLET")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.ALIVE)){
        this.renderPlayer1Bullet.update(CommsGlobal.get("PLAYER1_BULLET")
                                                    .getPacket(i)
                                                    .getLocation(),
                                          CommsGlobal.get(playerMe)
                                                    .getPacket(0)
                                                    .getLocation());
      }
      // Render explosions
      if(CommsGlobal.get("PLAYER1_BULLET")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.EXPLODE)){
          this.renderPlayer1Bullet.updateExplosion(CommsGlobal.get(
                                                      "PLAYER1_BULLET")
                                                              .getPacket(i)
                                                              .getLocation(),
                                                    CommsGlobal.get(playerMe)
                                                              .getPacket(0)
                                                              .getLocation(),
                                                    7);
      }
    }
  }

  //=========================================================================//
  // Render Player2 Bullets Layer                                            //
  //=========================================================================//
  private void renderPlayer2Bullets(){
    for (int i = 0; i < CommsGlobal.get("PLAYER2_BULLET")
                                   .getNumberOfReceivers(); i++){
      // Render entity if alive
      if(CommsGlobal.get("PLAYER2_BULLET")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.ALIVE)){
        this.renderPlayer2Bullet.update(CommsGlobal.get("PLAYER2_BULLET")
                                                  .getPacket(i)
                                                  .getLocation(),
                                        CommsGlobal.get(playerMe)
                                                  .getPacket(0)
                                                  .getLocation());
      }
      // Render explosions
      if(CommsGlobal.get("PLAYER2_BULLET")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.EXPLODE)){
          this.renderPlayer2Bullet.updateExplosion(CommsGlobal.get(
                                                    "PLAYER2_BULLET")
                                                             .getPacket(i)
                                                             .getLocation(),
                                                  CommsGlobal.get(playerMe)
                                                             .getPacket(0)
                                                             .getLocation(),
                                                  7);
      }
    }
  }

  //=========================================================================//
  // Render Displays Layer                                                   //
  //=========================================================================//
  private void renderDisplaysLayer(){
    // Render player1 score
    this.renderPlayer1Score.update(CommsGlobal.get("PLAYER1")
                                             .getPacket(0)
                                             .getScore());
    // Render player2 score
    this.renderPlayer2Score.update(CommsGlobal.get("PLAYER2")
                                             .getPacket(0)
                                             .getScore());
    // Render player1 health bar
    this.renderPlayer1Health.update(CommsGlobal.get("PLAYER1")
                                              .getPacket(0)
                                              .getHealth());

    // Render player2 health bar
    this.renderPlayer2Health.update(CommsGlobal.get("PLAYER2")
                                                .getPacket(0)
                                                .getHealth());

    // Render (empty) mini map
    this.renderMiniMap.update();
  }

  //=========================================================================//
  // Render Mini Map Entities Layer                                          //
  //=========================================================================//
  private void renderMiniMapEntitiesLayer(){

    boolean turretFlag = false;
    boolean player2Flag = false;

    // Render Bots
    for (int i = 0; i < CommsGlobal.get("PLAYER1_BOT")
                                   .getNumberOfReceivers(); i++){
      // Render entity if alive
      if(CommsGlobal.get("PLAYER1_BOT")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.ALIVE)){
        this.renderMiniMapBot.update(CommsGlobal.get("PLAYER1_BOT")
                                                .getPacket(i)
                                                .getLocation());
        // Render any turrets in line of sight of bots
        for (int j = 0; j < CommsGlobal.get("TURRET")
                                      .getNumberOfReceivers(); j++){
          turretFlag = this.renderMiniMapBot.checkInLineOfSight(
            CommsGlobal.get("PLAYER1_BOT")
                      .getPacket(i)
                      .getLocation(),
            CommsGlobal.get("TURRET")
                      .getPacket(j)
                      .getLocation(),        
            this.renderMiniMapBot.botMapLineOfSight);

          if(turretFlag){
            this.renderMiniMapTurret.update(CommsGlobal.get("TURRET")
                                                      .getPacket(j)
                                                      .getLocation());
            break;
          }
        }
        // Render any Players in line of sight of bots
        player2Flag = this.renderMiniMapBot.checkInLineOfSight(
          CommsGlobal.get("PLAYER1_BOT")
                     .getPacket(i)
                     .getLocation(),
          CommsGlobal.get("PLAYER2")
                     .getPacket(0)
                     .getLocation(),
          this.renderMiniMapBot.botMapLineOfSight);
        if(player2Flag){
          this.renderMiniMapPlayer2.update(CommsGlobal.get("PLAYER2")
                                                      .getPacket(0)
                                                      .getLocation());
        }
      }
    }
    // Render Player1
    this.renderMiniMapPlayer1.update(CommsGlobal.get("PLAYER1")
                                              .getPacket(0)
                                              .getLocation());
  }
}
