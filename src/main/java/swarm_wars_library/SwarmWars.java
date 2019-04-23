package swarm_wars_library;

import java.util.*;
import processing.core.PApplet;

import swarm_wars_library.engine.CollisionDetection;
import swarm_wars_library.entities.AbstractEntity;
import swarm_wars_library.entities.Bot;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.PlayerN;
import swarm_wars_library.entities.Turret;
import swarm_wars_library.graphics.RenderLayers;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsChannel;
import swarm_wars_library.map.Map;

//added by Steph
import swarm_wars_library.ui.UI;

public class SwarmWars extends PApplet {

  // Players
  PlayerN player1;
  PlayerN player2;

  // Entity lists that has all our game things.
  ArrayList <AbstractEntity> player1TakeDamage;  
  ArrayList <AbstractEntity> player1DealDamage;
  ArrayList <AbstractEntity> player2TakeDamage;
  ArrayList <AbstractEntity> player2DealDamage;
  ArrayList <AbstractEntity> gameObjectsTakeDamage;
  ArrayList <AbstractEntity> gameObjectsDealDamage;

  // Game Backend Objects
  Map map = Map.getInstance();
  RenderLayers renderLayers;

  //=========================================================================//
  // Processing Settings                                                     //
  //=========================================================================//
  public void settings() {
    this.size(1200, 800, "processing.awt.PGraphicsJava2D");
  }

  //=========================================================================//
  // Processing Setup                                                        //
  //=========================================================================//
  public void setup() {
    this.frameRate(60); 
    this.commsSetup();
    this.entitiesSetup();
    CommsGlobal.update();
    this.renderSetup();
  }
  //=========================================================================//
  // Processing Game Loop                                                    //
  //=========================================================================//
  public void draw() {
    this.background(0, 0, 0);
    this.checkCollisions();
    this.entitiesUpdate();
    this.renderLayers.update();
    CommsGlobal.update(); 
  }

  //=========================================================================//
  // Processing Main                                                         //
  //=========================================================================//
  public static void main(String[] passedArgs) {
    String[] appletArgs = new String[] {
      "swarm_wars_library.SwarmWars"
    };
    PApplet.main(appletArgs);
  }
  //=========================================================================//
  // Comms Setup                                                             //
  //=========================================================================//
  public void commsSetup(){
    // player1 setup
    CommsGlobal.add("PLAYER1", new CommsChannel(1));
    CommsGlobal.add("PLAYER1_BOT", 
      new CommsChannel(map.getNumBotsPerPlayer()));
    CommsGlobal.add("PLAYER1_BULLET", 
      new CommsChannel(1 * map.getNumBulletsPerMagazine()));

    // player2 setup
    CommsGlobal.add("PLAYER2", new CommsChannel(1));
    CommsGlobal.add("PLAYER2_BOT", 
      new CommsChannel(map.getNumBotsPerPlayer()));
    CommsGlobal.add("PLAYER2_BULLET", 
      new CommsChannel(1 * map.getNumBulletsPerMagazine()));

    // game objects setup
    CommsGlobal.add("TURRET", new CommsChannel(map.getNumTurrets()));
    CommsGlobal.add("TURRET_BULLET", 
      new CommsChannel(map.getNumTurrets() * map.getNumBulletsPerMagazine()));
  }

  //=========================================================================//
  // Entities Setup                                                          //
  //=========================================================================//
  public void entitiesSetup(){

    this.player1TakeDamage = new ArrayList<AbstractEntity>();  
    this.player1DealDamage = new ArrayList<AbstractEntity>();
    this.player2TakeDamage = new ArrayList<AbstractEntity>();
    this.player2DealDamage = new ArrayList<AbstractEntity>();
    this.gameObjectsTakeDamage = new ArrayList<AbstractEntity>();
    this.gameObjectsDealDamage = new ArrayList<AbstractEntity>();

    // player1 setup
    this.player1 = new PlayerN(this, ENTITY.PLAYER1);
    this.player1TakeDamage.add(this.player1);
    this.player1DealDamage.addAll(player1.getBullets());
    for(int i = 0; i < this.map.getNumBotsPerPlayer(); i++){
      Bot bot = new Bot(ENTITY.PLAYER1_BOT, "boids_flock", i);
      this.player1TakeDamage.add(bot);
    }

    // TODO player 2 setup

    // turrets setup
    for(int i = 0; i < this.map.getNumTurrets(); i++){
      Turret turret = new Turret(ENTITY.TURRET);
      this.gameObjectsTakeDamage.add(turret);
      this.gameObjectsDealDamage.addAll(turret.getBullets());
    }
  }

  //=========================================================================//
  // Render Setup                                                            //
  //=========================================================================//
  public void renderSetup(){
    this.renderLayers = new RenderLayers(this);
  }

  //=========================================================================//
  // Collision checks                                                        //
  //=========================================================================//
  public void checkCollisions() {
    CollisionDetection.checkCollisions(this.gameObjectsDealDamage,
                                       this.player1TakeDamage);
    CollisionDetection.checkCollisions(this.player1DealDamage,
                                       this.gameObjectsTakeDamage);
  }

  //=========================================================================//
  // Entities update                                                         //
  //=========================================================================//
  public void entitiesUpdate(){
    // Update all entities
    for(int i = 0; i < player1TakeDamage.size(); i++){
      player1TakeDamage.get(i).update();
    }
    for(int i = 0; i < player1DealDamage.size(); i++){
      player1DealDamage.get(i).update();
    }
    for(int i = 0; i < gameObjectsTakeDamage.size(); i++){
      gameObjectsTakeDamage.get(i).update();
    }
    for(int i = 0; i < gameObjectsDealDamage.size(); i++){
      gameObjectsDealDamage.get(i).update();
    }
  }

  //=========================================================================//
  // Event listeners                                                         //
  //=========================================================================//
  public void keyPressed() {
    this.player1.listenKeyPressed(this.keyCode);
  }

  public void keyReleased() {
    this.player1.listenKeyReleased(this.keyCode);
  }

  public void mousePressed() {
    this.player1.listenMousePressed();
  }
  public void mouseReleased() {
    this.player1.listenMouseReleased();
  }


  // ------ UI Related Events - added by Steph -------
  public void mouseClicked() {
    //LABEL SWAPS FOR STATES
    //label1 swap - stars
    if (mouseX >= positionx1- 10 && mouseX <=  positionx1+ 10 &&
            mouseY >= positiony1- 10 && mouseY <= positiony1+10) {
      if (swapLabel(label1) == false) {
        star1.changecolor();
      }
      else {
        checkForColourChanges();
      }

    }
    //label3 swap - stars
    else if (mouseX >= positionx3 - 10 && mouseX <=  positionx3+ 10 &&
            mouseY >= positiony3 - 10 && mouseY <= positiony3 + 10) {
      if (swapLabel(label3) == false) {
        star3.changecolor();
      }
      else {
        checkForColourChanges();
      }

    }
    //swapping label2 - stars
    else if (mouseX >= positionx2 - 10 && mouseX <=  positionx2 + 10 &&
            mouseY >= positiony2 -10 && mouseY <= positionx2 + 10) {
      if (swapLabel(label2) == false) {
        star2.changecolor();
      }
      else {
        checkForColourChanges();
      }
    }
    //swapping label4 - stars
    else if (mouseX >= positionx4 - 10 && mouseX <=  positionx4 + 10 &&
            mouseY >= positiony4 - 10 && mouseY <= positiony4 + 10) {

      if (swapLabel(label4) == false) {
        star4.changecolor();
      }
      else {
        checkForColourChanges();
      }
    }
    //swapping for label5 - stars
    else if (mouseX >= positionx5 - 10 && mouseX <=  positionx5 + 10 &&
            mouseY >= positiony5 - 10  && mouseY <= positiony5 + 10) {

      if (swapLabel(label5) == false) {
        star5.changecolor();
      }
      else {
        checkForColourChanges();
      }
    }


    //LABEL SWAPS FOR TRANSITIONS
    else if (mouseX >= (positionx1+positionx2)/2-30 &&
            mouseX <= (positionx1+positionx2)/2-30+120 &&
            mouseY >= (positiony1+positiony2)/2-27 &&
            mouseY <= (positiony1+positiony2)/2-27+55) {
      swapButton1();
    }
    else if (mouseX >= (positionx2+positionx5)/2-60 &&
            mouseX <= (positionx2+positionx5)/2-60+120 &&
            mouseY >= (positiony2+positiony5)/2-27 &&
            mouseY <= (positiony2+positiony5)/2-27+55) {
      swapButton2();
    }
    else if (mouseX >= (positionx4+positionx5)/2-60 &&
            mouseX <= (positionx4+positionx5)/2-60 +120 &&
            mouseY >= positiony5-27 &&
            mouseY <= positiony5-27 +55) {
      swapButton3();
    }
    else if (mouseX >= (positionx3+positionx4)/2-60 &&
            mouseX <=  (positionx3+positionx4)/2-60+120 &&
            mouseY >= (positiony3+positiony4)/2-27 &&
            mouseY <= (positiony3+positiony4)/2-27 +55) {
      swapButton4();
    }
    else if (mouseX >= (positionx3+positionx1)/2-90 &&
            mouseX <=  (positionx3+positionx1)/2-90+120 &&
            mouseY >= (positiony3+positiony1)/2-27 &&
            mouseY <= (positiony3+positiony1)/2-27+55) {
      swapButton5();
    }

    //START GAME
    else if (mouseX >= 500 && mouseX <= 500+100 &&
            mouseY >= 30 && mouseY <= 30+50) {
      GameState = 2;
      start.changecolor();
    }

    //TOOLTIP
    else if (mouseX >= 50 - 20 && mouseX <= 50 +20 &&
            mouseY >= 45 -20 && mouseY <= 45 + 20) {
      if (showTooltip == false) {
        showTooltip = true;
        tooltip.changecolor();
        info.changecolor();
      }
      else {
        showTooltip = false;
        tooltip.changecolor();
        info.changecolor();
      }
    }
  }

}
