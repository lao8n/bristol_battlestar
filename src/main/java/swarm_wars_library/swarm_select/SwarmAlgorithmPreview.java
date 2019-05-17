package swarm_wars_library.swarm_select;

import java.util.ArrayList;
import processing.core.PApplet;

import swarm_wars_library.entities.*;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsChannel;
import swarm_wars_library.map.Map;
import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;
import swarm_wars_library.engine.CollisionDetection;
import swarm_wars_library.physics.Vector2D;

public class SwarmAlgorithmPreview {

  PlayerUI player1;
  ArrayList<TurretUI> listTurrets;
  ArrayList<BotUI> listBots;
  Map map = Map.getInstance();



  public SwarmAlgorithmPreview(PApplet sketch){

    CommsGlobal.add("PLAYERUI", new CommsChannel(1));
    CommsGlobal.add("PLAYERUI_BOT", 
      new CommsChannel(map.getNumBotsPerPlayer()));
    CommsGlobal.add("TURRET", new CommsChannel(map.getNumTurrets()));

    this.player1 = new PlayerUI(sketch, ENTITY.PLAYERUI);
    this.listBots = new ArrayList<BotUI>();
    for(int i = 0; i < this.map.getNumBotsPerPlayer(); i++){
      BotUI bot = new BotUI(ENTITY.PLAYERUI_BOT, SWARMALGORITHM.SCOUTBEE, i);
      this.listBots.add(bot);
    }
    this.listTurrets = new ArrayList<TurretUI>();
    for(int i = 0; i < this.map.getNumTurrets(); i++){
      TurretUI turret = new TurretUI(ENTITY.TURRET);
      this.listTurrets.add(turret);
    }

    CommsGlobal.update();
  }

  public void update(){
    // Collision detection
    for(int i = 0; i < this.listBots.size(); i++){
      for(int j = 0; j < this.listTurrets.size(); j++){
        if(hasCollision(this.listBots.get(i),
                        this.listTurrets.get(j))){
          this.listBots.get(i)
                       .collidedWith(this.listTurrets.get(j).getTag());
          this.listTurrets.get(j)
                       .collidedWith(this.listBots.get(i).getTag());
        }
      }
    }
    this.player1.update();
    for(int i = 0; i < this.listBots.size(); i++){
      this.listBots.get(i).update();
    }
    for(int i = 0; i < this.listTurrets.size(); i++){
      this.listTurrets.get(i).update();
    }
    CommsGlobal.update();
  }

  public void selectSwarmAlgorithm(SWARMALGORITHM swarmAlgorithm){
    for(int i = 0; i < this.listBots.size(); i++){
      this.listBots.get(i).setSwarmAlgorithm(swarmAlgorithm);
    }
  }

  public static boolean hasCollision(AbstractEntity dealDamage,
                                     AbstractEntity takeDamage){
    if (dealDamage instanceof BotUI && dealDamage.isState(STATE.ALIVE)) {
      System.out.println("is bot");
      return false;
    }
    else if((dealDamage.isState(STATE.ALIVE) || dealDamage.isState(STATE.SUICIDE)) &&
            takeDamage.isState(STATE.ALIVE) &&
            (Vector2D.sub(dealDamage.getLocation(),takeDamage.getLocation()).mag()
                    < dealDamage.getScale() + takeDamage.getScale())){
      return true;
    }
    else {
      return false;
    }
  }
}