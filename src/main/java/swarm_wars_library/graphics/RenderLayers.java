package swarm_wars_library.graphics;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.Event;
import swarm_wars_library.map.Map;

import processing.core.PApplet;

public class RenderLayers{

  private Map map;
  private RenderStar renderStar;
  private RenderPlayer renderPlayer;
  private RenderPlayerBullet renderPlayerBullet;
  private RenderEnemy renderEnemy;
  private RenderEnemyBullet renderEnemyBullet;
  private RenderBot renderBot;
  private RenderPlayerScore renderPlayerScore;
  private RenderPlayerHealth renderPlayerHealth;
  private RenderMiniMap renderMiniMap;
  private RenderMiniMapPlayer renderMiniMapPlayer;
  private RenderMiniMapEnemy renderMiniMapEnemy;
  private RenderMiniMapBot renderMiniMapBot;

  public RenderLayers(PApplet sketch){
    this.map = Map.getInstance();
    this.renderStar = new RenderStar(sketch);
    this.renderPlayer = new RenderPlayer(sketch);
    this.renderPlayerBullet = new RenderPlayerBullet(sketch);
    this.renderEnemy = new RenderEnemy(sketch);
    this.renderEnemyBullet = new RenderEnemyBullet(sketch);
    this.renderBot = new RenderBot(sketch);
    this.renderPlayerScore = new RenderPlayerScore(sketch);
    this.renderPlayerHealth = new RenderPlayerHealth(sketch);
    this.renderMiniMap = new RenderMiniMap(sketch);
    this.renderMiniMapPlayer = new RenderMiniMapPlayer(sketch);
    this.renderMiniMapEnemy = new RenderMiniMapEnemy(sketch);
    this.renderMiniMapBot = new RenderMiniMapBot(sketch);
  }

  public void update(){
    this.renderBackgroundLayer();
    this.renderEntitiesLayer();
    this.renderDisplaysLayer();
    this.renderMiniMapEntitiesLayer();
  }

  private void renderBackgroundLayer(){
    // Render Stars
    for (int i = 0; i < this.map.getMapStars().size(); i++){
      this.renderStar.update(this.map.getMapStars().get(i),
                             CommsGlobal.get("PLAYER")
                                        .getPacket(0)
                                        .getLocation());
    }
  }

  private void renderEntitiesLayer(){
    // Render Enemy Bullets
    for (int i = 0; i < CommsGlobal.get("E_BULLET").getNumberOfReceivers(); i++){
      if(CommsGlobal.get("E_BULLET").getPacket(i).isAlive()){
        this.renderEnemyBullet.update(CommsGlobal.get("E_BULLET")
                                                 .getPacket(i)
                                                 .getLocation(),
                                      CommsGlobal.get("PLAYER")
                                                 .getPacket(0)
                                                 .getLocation());
      }
      if(CommsGlobal.get("E_BULLET").getPacket(i).getEvent()
        .equals(Event.EXPLODE)){
          this.renderEnemyBullet.updateExplosion(CommsGlobal.get("E_BULLET")
                                                            .getPacket(i)
                                                            .getLocation(),
                                                 CommsGlobal.get("PLAYER")
                                                            .getPacket(0)
                                                            .getLocation(),
                                                 3);
      }
    }

    // Render Player Bullets
    for (int i = 0; i < CommsGlobal.get("P_BULLET").getNumberOfReceivers(); i++){
      if(CommsGlobal.get("P_BULLET").getPacket(i).isAlive()){
        this.renderPlayerBullet.update(CommsGlobal.get("P_BULLET")
                                                  .getPacket(i)
                                                  .getLocation(),
                                        CommsGlobal.get("PLAYER")
                                                  .getPacket(0)
                                                  .getLocation());
      }
      if(CommsGlobal.get("P_BULLET").getPacket(i).getEvent()
        .equals(Event.EXPLODE)){
          this.renderPlayerBullet.updateExplosion(CommsGlobal.get("P_BULLET")
                                                             .getPacket(i)
                                                             .getLocation(),
                                                  CommsGlobal.get("PLAYER")
                                                             .getPacket(0)
                                                             .getLocation(),
                                                  3);
      }
    }

    // Render Bots
    for (int i = 0; i < CommsGlobal.get("P_BOT").getNumberOfReceivers(); i++){
      if(CommsGlobal.get("P_BOT").getPacket(i).isAlive()){
        this.renderBot.update(CommsGlobal.get("P_BOT")
                                         .getPacket(i)
                                         .getLocation(),
                              CommsGlobal.get("PLAYER")
                                         .getPacket(0)
                                         .getLocation());
      }
      if(CommsGlobal.get("P_BOT").getPacket(i).getEvent()
        .equals(Event.EXPLODE)){
          this.renderBot.updateExplosion(CommsGlobal.get("P_BOT")
                                                    .getPacket(i)
                                                    .getLocation(),
                                         CommsGlobal.get("PLAYER")
                                                    .getPacket(0)
                                                    .getLocation(),
                                         5);
      }
    }
  
    // Render Enemy
    for (int i = 0; i < CommsGlobal.get("ENEMY").getNumberOfReceivers(); i++){
      if(CommsGlobal.get("ENEMY").getPacket(i).isAlive()){
        this.renderEnemy.update(CommsGlobal.get("ENEMY")
                                           .getPacket(i)
                                           .getLocation(),
                                CommsGlobal.get("PLAYER")
                                           .getPacket(0)
                                           .getLocation());
      }
      if(CommsGlobal.get("ENEMY").getPacket(i).getEvent()
        .equals(Event.EXPLODE)){
          this.renderEnemy.updateExplosion(CommsGlobal.get("ENEMY")
                                                      .getPacket(i)
                                                      .getLocation(),
                                           CommsGlobal.get("PLAYER")
                                                      .getPacket(0)
                                                      .getLocation(),
                                           10);
          this.renderEnemy.updateVoid(CommsGlobal.get("ENEMY")
                                                 .getPacket(i)
                                                 .getLocation(),
                                       CommsGlobal.get("PLAYER")
                                                 .getPacket(0)
                                                 .getLocation());
      }
    }

    // Render Player
    this.renderPlayer.update(CommsGlobal.get("PLAYER")
                                        .getPacket(0)
                                        .getLocation(),
                             CommsGlobal.get("PLAYER")
                                        .getPacket(0)
                                        .getLocation());    
  }
  
  private void renderDisplaysLayer(){
    // Render player score
    this.renderPlayerScore.update(CommsGlobal.get("PLAYER")
                                             .getPacket(0)
                                             .getScore());

    // Render health bar
    this.renderPlayerHealth.update(CommsGlobal.get("PLAYER")
                                              .getPacket(0)
                                              .getHealth());

    // Render (empty) mini map
    this.renderMiniMap.update();
  }

  private void renderMiniMapEntitiesLayer(){

    // Render Bots & objects in Bots line of sight
    for (int i = 0; i < CommsGlobal.get("P_BOT").getNumberOfReceivers(); i++){
      this.renderMiniMapBot.update(CommsGlobal.get("P_BOT")
                                              .getPacket(i)
                                              .getLocation());

      for (int j = 0; j < CommsGlobal.get("ENEMY").getNumberOfReceivers(); j++){
        boolean enemyFlag = false;
        enemyFlag = this.renderMiniMapEnemy.checkInLineOfSight(
          CommsGlobal.get("P_BOT")
                     .getPacket(i)
                     .getLocation(),
          CommsGlobal.get("ENEMY")
                     .getPacket(j)
                     .getLocation(),        
          this.renderMiniMapBot.botMapLineOfSight);

        if(enemyFlag){
          this.renderMiniMapEnemy.update(CommsGlobal.get("ENEMY")
                                                    .getPacket(j)
                                                    .getLocation());
        }
      }
    }

    // Render Player
    this.renderMiniMapPlayer.update(CommsGlobal.get("PLAYER")
                                              .getPacket(0)
                                              .getLocation());
  }
}