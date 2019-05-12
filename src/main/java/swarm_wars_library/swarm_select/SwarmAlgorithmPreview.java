package swarm_wars_library.swarm_select;

import java.util.ArrayList;
import processing.core.PApplet;

import swarm_wars_library.entities.AbstractEntity;
import swarm_wars_library.entities.Bot;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.PlayerUI;
import swarm_wars_library.entities.TurretUI;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsChannel;
import swarm_wars_library.map.Map;

public class SwarmAlgorithmPreview {

  PlayerUI player1;
  ArrayList<TurretUI> listTurrets;
  ArrayList<Bot> listBots;
  Map map = Map.getInstance();


  public SwarmAlgorithmPreview(PApplet sketch){

    CommsGlobal.add("PLAYERUI", new CommsChannel(1));
    CommsGlobal.add("PLAYERUI_BOT", 
      new CommsChannel(map.getNumBotsPerPlayer()));
    CommsGlobal.add("TURRET", new CommsChannel(map.getNumTurrets()));

    this.player1 = new PlayerUI(sketch, ENTITY.PLAYERUI);
    this.listBots = new ArrayList<Bot>();
    for(int i = 0; i < this.map.getNumBotsPerPlayer(); i++){
      Bot bot = new Bot(ENTITY.PLAYERUI_BOT, "scoutBee", i);
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
    this.player1.update();
    for(int i = 0; i < this.listBots.size(); i++){
      this.listBots.get(i).update();
    }
    for(int i = 0; i < this.listTurrets.size(); i++){
      this.listTurrets.get(i).update();
    }
    CommsGlobal.update();
  }
}