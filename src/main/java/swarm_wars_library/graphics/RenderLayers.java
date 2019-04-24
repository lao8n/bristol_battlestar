package swarm_wars_library.graphics;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.entities.STATE;
import swarm_wars_library.map.Map;

import processing.core.PApplet;

public class RenderLayers{

  private Map map;
  private RenderBackground renderBackground;
  private RenderStar renderStar;
  private RenderPlayer renderPlayer;
  private RenderPlayerBullet renderPlayerBullet;
  private RenderTurret renderTurret;
  private RenderTurretBullet renderTurretBullet;
  private RenderBot renderBot;
  private RenderPlayerScore renderPlayerScore;
  private RenderPlayerHealth renderPlayerHealth;
  private RenderMiniMap renderMiniMap;
  private RenderMiniMapPlayer renderMiniMapPlayer;
  private RenderMiniMapTurret renderMiniMapTurret;
  private RenderMiniMapBot renderMiniMapBot;

  public RenderLayers(PApplet sketch){
    this.map = Map.getInstance();
    this.renderBackground = new RenderBackground(sketch);
    this.renderStar = new RenderStar(sketch);
    this.renderPlayer = new RenderPlayer(sketch);
    this.renderPlayerBullet = new RenderPlayerBullet(sketch);
    this.renderTurret = new RenderTurret(sketch);
    this.renderTurretBullet = new RenderTurretBullet(sketch);
    this.renderBot = new RenderBot(sketch);
    this.renderPlayerScore = new RenderPlayerScore(sketch);
    this.renderPlayerHealth = new RenderPlayerHealth(sketch);
    this.renderMiniMap = new RenderMiniMap(sketch);
    this.renderMiniMapPlayer = new RenderMiniMapPlayer(sketch);
    this.renderMiniMapTurret = new RenderMiniMapTurret(sketch);
    this.renderMiniMapBot = new RenderMiniMapBot(sketch);
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
                             CommsGlobal.get("PLAYER1")
                                        .getPacket(0)
                                        .getLocation());
    }
  }

  //=========================================================================//
  // Render Entities Layer                                                   //
  //=========================================================================//
  private void renderEntitiesLayer(){
    this.renderPlayer1Bots();
    this.renderTurrets();
    this.renderPlayer1();
    this.renderPlayer2();
    this.renderTurretBullets();
    this.renderPlayer1Bullets();
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
        this.renderBot.update(CommsGlobal.get("PLAYER1_BOT")
                                         .getPacket(i)
                                         .getLocation(),
                              CommsGlobal.get("PLAYER1")
                                         .getPacket(0)
                                         .getLocation());
      }
      // Render explosions
      if(CommsGlobal.get("PLAYER1_BOT")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.EXPLODE)){
          this.renderBot.updateExplosion(CommsGlobal.get("PLAYER1_BOT")
                                                    .getPacket(i)
                                                    .getLocation(),
                                         CommsGlobal.get("PLAYER1")
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
                                CommsGlobal.get("PLAYER1")
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
                                           CommsGlobal.get("PLAYER1")
                                                      .getPacket(0)
                                                      .getLocation(),
                                           5);
          this.renderTurret.updateVoid(CommsGlobal.get("TURRET")
                                                 .getPacket(i)
                                                 .getLocation(),
                                       CommsGlobal.get("PLAYER1")
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
      this.renderPlayer.update(CommsGlobal.get("PLAYER1")
                                          .getPacket(0)
                                          .getLocation(),
                              CommsGlobal.get("PLAYER1")
                                          .getPacket(0)
                                          .getLocation());
    }

    // Render explosions
    if(CommsGlobal.get("PLAYER1")
                  .getPacket(0)
                  .getState()
                  .equals(STATE.EXPLODE)){
      this.renderPlayer.updateExplosion(CommsGlobal.get("PLAYER1")
                                                   .getPacket(0)
                                                   .getLocation(),
                                        CommsGlobal.get("PLAYER1")
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
      this.renderPlayer.update(CommsGlobal.get("PLAYER2")
                                          .getPacket(0)
                                          .getLocation(),
                              CommsGlobal.get("PLAYER1")
                                          .getPacket(0)
                                          .getLocation());
    }

    // Render explosions
    if(CommsGlobal.get("PLAYER2")
                  .getPacket(0)
                  .getState()
                  .equals(STATE.EXPLODE)){
      this.renderPlayer.updateExplosion(CommsGlobal.get("PLAYER2")
                                                   .getPacket(0)
                                                   .getLocation(),
                                        CommsGlobal.get("PLAYER1")
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
                                      CommsGlobal.get("PLAYER1")
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
                                                CommsGlobal.get("PLAYER1")
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
        this.renderPlayerBullet.update(CommsGlobal.get("PLAYER1_BULLET")
                                                  .getPacket(i)
                                                  .getLocation(),
                                        CommsGlobal.get("PLAYER1")
                                                  .getPacket(0)
                                                  .getLocation());
      }
      // Render explosions
      if(CommsGlobal.get("PLAYER1_BULLET")
                    .getPacket(i)
                    .getState()
                    .equals(STATE.EXPLODE)){
          this.renderPlayerBullet.updateExplosion(CommsGlobal.get(
                                                    "PLAYER1_BULLET")
                                                             .getPacket(i)
                                                             .getLocation(),
                                                  CommsGlobal.get("PLAYER1")
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
    // Render player score
    this.renderPlayerScore.update(CommsGlobal.get("PLAYER1")
                                             .getPacket(0)
                                             .getScore());

    // Render health bar
    this.renderPlayerHealth.update(CommsGlobal.get("PLAYER1")
                                              .getPacket(0)
                                              .getHealth());

    // Render (empty) mini map
    this.renderMiniMap.update();
  }

  //=========================================================================//
  // Render Mini Map Entities Layer                                          //
  //=========================================================================//
  private void renderMiniMapEntitiesLayer(){

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
          boolean turretFlag = false;
          turretFlag = this.renderMiniMapTurret.checkInLineOfSight(
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
      }
    }
    // Render Player1
    this.renderMiniMapPlayer.update(CommsGlobal.get("PLAYER1")
                                              .getPacket(0)
                                              .getLocation());
  }
}
