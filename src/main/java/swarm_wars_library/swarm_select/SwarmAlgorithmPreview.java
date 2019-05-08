package swarm_wars_library.swarm_select;

import java.util.ArrayList;
import processing.core.PApplet;

import swarm_wars_library.entities.AbstractEntity;
import swarm_wars_library.entities.Bot;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.PlayerUI;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsChannel;
import swarm_wars_library.map.Map;

public class SwarmAlgorithmPreview {

  PlayerUI player1;
  ArrayList<AbstractEntity> listBots;
  Map map = Map.getInstance();


  public SwarmAlgorithmPreview(PApplet sketch){

    CommsGlobal.add("PLAYERUI", new CommsChannel(1));
    CommsGlobal.add("PLAYERUI_BOT", 
      new CommsChannel(map.getNumBotsPerPlayer()));

    this.player1 = new PlayerUI(sketch, ENTITY.PLAYERUI);
    this.listBots = new ArrayList<AbstractEntity>();
    for(int i = 0; i < this.map.getNumBotsPerPlayer(); i++){
      Bot bot = new Bot(ENTITY.PLAYERUI_BOT, "defendFlock", i);
      this.listBots.add(bot);
    }
    CommsGlobal.update();
  }

  public void update(){
    this.player1.update();
    for(int i = 0; i < this.listBots.size(); i++){
      this.listBots.get(i).update();
    }
    CommsGlobal.update();
  }
}