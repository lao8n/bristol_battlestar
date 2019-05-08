package swarm_wars_library.swarm_select;

import processing.core.PApplet;
import processing.core.PImage;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.game_screens.GAMESCREEN;
import swarm_wars_library.graphics.RenderMiniMap;
import swarm_wars_library.graphics.RenderUIMiniMapBot;
import swarm_wars_library.graphics.RenderUIMiniMapPlayer1;
import swarm_wars_library.physics.Vector2D;

public class SwarmSelect{
  // Processing 
  private PApplet sketch;
  private PImage backgroundImage;

  // Game Screen
  private GAMESCREEN currentScreen;

  // FSM Locations
  private Vector2D locationFSMAttack;
  private Vector2D locationFSMDefend;
  private Vector2D locationFSMScout;

  // FSM Render
  private Circle renderFSMAttack;
  private Circle renderFSMDefend;
  private Circle renderFSMScout;

  // Info Locatons
  private Vector2D locationInfoAlgo;
  private Vector2D locationInfoExplanation; 

  // Info Text 
  private Label renderInfoTextAlgo;
  private Label renderInfoTextExplanation;

  // Minimap
  private RenderMiniMap renderMiniMap;
  private RenderUIMiniMapBot renderUIMiniMapBot;
  private RenderUIMiniMapPlayer1 renderUIMiniMapPlayer1;

  // Mouse 
  private boolean mousePressed = false;

  //Options Locations
  private Vector2D locationOptionsAttack;
  private Vector2D locationOptionsDefend;
  private Vector2D locationOptionsScout;

  // Options Render
  private Circle renderOptionsAttack;
  private Circle renderOptionsDefend;
  private Circle renderOptionsScout;

  // Options Text Headings
  private Label renderOptionsTextAttack;
  private Label renderOptionsTextDefend;
  private Label renderOptionsTextScout;

  // Option Lines
  private Line renderOptionsLine1Attack;
  private Line renderOptionsLine1Defend;
  private Line renderOptionsLine1Scout;

  // Options Text Algorithms
  private Label renderOptionsTextAttackAlgo1;
  private Label renderOptionsTextAttackAlgo2;
  private Label renderOptionsTextAttackAlgo3;
  private Label renderOptionsTextDefendAlgo1;
  private Label renderOptionsTextDefendAlgo2;
  private Label renderOptionsTextDefendAlgo3;
  private Label renderOptionsTextScoutAlgo1;
  private Label renderOptionsTextScoutAlgo2;
  private Label renderOptionsTextScoutAlgo3;

  // Swarm Algorithm Preview
  private SwarmAlgorithmPreview swarmAlgorithmPreview;

  // Start Button Render
  private Button renderStartButton;  

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================//
  public SwarmSelect(PApplet sketch){
    this.sketch = sketch;
    this.currentScreen = GAMESCREEN.SWARMSELECT;
    PImage background = sketch.loadImage(
      "src/main/java/swarm_wars_library/swarm_select/background.png");
    this.backgroundImage = background.get(0, 0, sketch.width, sketch.height);
    this.setupUIMiniMap();
    this.setupFSM();
    this.setupInfo();
    this.setupOptions();
    this.setupStartButton();
    this.setupSwarmAlgorithmPreview();
  }

  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  public void update(){
    this.updateSwarmAlgorithmPreview();
    this.updateBackground();
    this.updateFSM();
    this.updateInfo();
    this.updateUIMiniMap();
    this.updateOptions();
    this.updateMousePressButton();
    this.updateStartButton();
  }

  //=========================================================================//
  // Background methods                                                      //
  //=========================================================================//
  private void updateBackground(){
    // this.sketch.background(73, 67, 67);
    this.sketch.image(this.backgroundImage, 0, 0, 
                      this.sketch.width, this.sketch.height);  
  }

  //=========================================================================//
  // FSM methods                                                             //
  //=========================================================================//
  private void setupFSM(){
    // FSM locations
    double fsm_unit = this.sketch.width / 4 - this.sketch.height / 8;
    this.locationFSMAttack = new Vector2D(this.sketch.width - fsm_unit,
                                          this.sketch.height / 8);
    this.locationFSMDefend = new Vector2D(this.sketch.width - fsm_unit * 3 / 2,
                                          this.sketch.height * 1 / 3);
    this.locationFSMScout = new Vector2D(this.sketch.width - fsm_unit / 2,
                                          this.sketch.height * 1 / 3);

    // Render FSM buttons
    Vector2D dimensionsFSM = new Vector2D(this.sketch.height / 10, 
                                          this.sketch.height / 10);
    this.renderFSMAttack = new Circle(this.sketch, 
                                      this.locationFSMAttack,
                                      dimensionsFSM,
                                      254,
                                      73, 
                                      78);
    this.renderFSMDefend = new Circle(this.sketch,
                                this.locationFSMDefend,
                                dimensionsFSM,
                                62,
                                138,
                                65);
    this.renderFSMScout = new Circle(this.sketch,
                                    this.locationFSMScout,
                                    dimensionsFSM,
                                    237,
                                    186,
                                    0);
  }

  private void updateFSM(){
    // Render FSM Circles
    this.renderFSMAttack.update();
    this.renderFSMDefend.update();
    this.renderFSMScout.update();
  }

  //=========================================================================//
  // Game screen method                                                      //
  //=========================================================================//
  public GAMESCREEN getGameScreen(){
    return this.currentScreen;
  }

  //=========================================================================//
  // Info methods                                                            //
  //=========================================================================//
  private void setupInfo(){
    // Title
    this.locationInfoAlgo = new Vector2D(200,
                                         50);
    
    this.renderInfoTextAlgo = new Label(this.sketch,
                                        255, 
                                        "SCOUT: Bees algorithm",
                                        this.locationInfoAlgo);

    // Explanation
    this.locationInfoExplanation = new Vector2D(200, 100);
    this.renderInfoTextExplanation = new Label(
      this.sketch,
      255, 
      "TODO - add explanation of bees algorithm",
      this.locationInfoExplanation);
  }

  private void updateInfo(){
    // Title
    this.sketch.textSize(30);
    this.renderInfoTextAlgo.update();

    // Explanation
    this.sketch.textAlign(LEFT);
    this.sketch.textSize(12);
    this.renderInfoTextExplanation.update();

    // Reset
    this.sketch.textAlign(CENTER);
  }

  //=========================================================================//
  // Options methods                                                         //
  //=========================================================================//
  private void setupOptions(){
    // Options section
    double offset = 50;
    double thirdDistance = (this.sketch.width - this.sketch.height / 2) / 3;
    this.locationOptionsAttack = new Vector2D(offset,
                                            this.sketch.height / 2 + offset);
    this.locationOptionsDefend = new Vector2D(offset + thirdDistance,
                                            this.sketch.height / 2 + offset);
    this.locationOptionsScout = new Vector2D(offset + thirdDistance * 2,
                                            this.sketch.height / 2 + offset);

    // Render Options buttons
    Vector2D dimensionsOptions = new Vector2D(30, 
                                           30);
    this.renderOptionsAttack = new Circle(this.sketch, 
                                       this.locationOptionsAttack,
                                       dimensionsOptions,
                                       253,
                                       73, 
                                       78);
    this.renderOptionsDefend = new Circle(this.sketch,
                                       this.locationOptionsDefend,
                                       dimensionsOptions,
                                       62,
                                       138,
                                       65);
    this.renderOptionsScout = new Circle(this.sketch,
                                      this.locationOptionsScout,
                                      dimensionsOptions,
                                      237,
                                      186,
                                      0);

    // Render Options text headings
    this.renderOptionsTextAttack = new Label(
      this.sketch,
      255,
      "ATTACK ALGORITHMS",
      Vector2D.add(this.locationOptionsAttack, 
                   new Vector2D(100, 0))
    );
    this.renderOptionsTextDefend = new Label(
      this.sketch,
      255,
      "DEFEND ALGORITHMS",
      Vector2D.add(this.locationOptionsDefend, 
                   new Vector2D(100, 0))
    );
    this.renderOptionsTextScout = new Label(
      this.sketch,
      255,
      "SCOUT ALGORITHMS",
      Vector2D.add(this.locationOptionsScout, 
                   new Vector2D(100, 0))
    );

    // Render Option lines
    this.renderOptionsLine1Attack = new Line(
      this.sketch,
      this.locationOptionsAttack,
      Vector2D.add(this.locationOptionsAttack,
                   new Vector2D(0, this.sketch.height 
                                - this.locationOptionsAttack.getY()
                                - offset)),
      255,
      255,
      255
    );
    this.renderOptionsLine1Defend = new Line(
      this.sketch,
      this.locationOptionsDefend,
      Vector2D.add(this.locationOptionsDefend,
                   new Vector2D(0, this.sketch.height
                                   - this.locationOptionsDefend.getY()
                                   - offset)),
      255,
      255,
      255
    );
    this.renderOptionsLine1Scout = new Line(
      this.sketch,
      this.locationOptionsScout,
      Vector2D.add(this.locationOptionsScout,
                   new Vector2D(0, this.sketch.height 
                                - this.locationOptionsScout.getY()
                                - offset)),
      255,
      255,
      255
    );

    // Render Options text algorithms
    this.renderOptionsTextAttackAlgo1 = new Label(
      this.sketch,
      255,
      "Attack 1 TODO",
      Vector2D.add(this.locationOptionsAttack, 
                   new Vector2D(100, this.sketch.height / 9))
    );
    this.renderOptionsTextAttackAlgo2 = new Label(
      this.sketch,
      255,
      "Attack 2 TODO",
      Vector2D.add(this.locationOptionsAttack, 
                   new Vector2D(100, this.sketch.height / 9 * 2))
    );
    this.renderOptionsTextAttackAlgo3 = new Label(
      this.sketch,
      255,
      "Attack 3 TODO",
      Vector2D.add(this.locationOptionsAttack, 
                   new Vector2D(100, this.sketch.height / 9 * 3))
    );
    this.renderOptionsTextDefendAlgo1 = new Label(
      this.sketch,
      255,
      "Defend 1 TODO",
      Vector2D.add(this.locationOptionsDefend, 
                   new Vector2D(100, this.sketch.height / 9))
    );
    this.renderOptionsTextDefendAlgo2 = new Label(
      this.sketch,
      255,
      "Defend 2 TODO",
      Vector2D.add(this.locationOptionsDefend, 
                   new Vector2D(100, this.sketch.height / 9 * 2))
    );
    this.renderOptionsTextDefendAlgo3 = new Label(
      this.sketch,
      255,
      "Defend 3 TODO",
      Vector2D.add(this.locationOptionsDefend, 
                   new Vector2D(100, this.sketch.height / 9 * 3))
    );
    this.renderOptionsTextScoutAlgo1 = new Label(
      this.sketch,
      255,
      "Scout 1 TODO",
      Vector2D.add(this.locationOptionsScout, 
                   new Vector2D(100, this.sketch.height / 9))
    );
    this.renderOptionsTextScoutAlgo2 = new Label(
      this.sketch,
      255,
      "Scout 2 TODO",
      Vector2D.add(this.locationOptionsScout, 
                   new Vector2D(100, this.sketch.height / 9 * 2))
    );
    this.renderOptionsTextScoutAlgo3 = new Label(
      this.sketch,
      255,
      "Scout 3 TODO",
      Vector2D.add(this.locationOptionsScout, 
                   new Vector2D(100, this.sketch.height / 9 * 3))
    );

  }

  public void updateOptions(){
    // Render Options Lines
    this.renderOptionsLine1Attack.update();
    this.renderOptionsLine1Defend.update();
    this.renderOptionsLine1Scout.update();

    // Render Options Circles

    this.renderOptionsAttack.update();
    this.renderOptionsDefend.update();
    this.renderOptionsScout.update();

    // Render Options Text 
    this.renderOptionsTextAttack.update();
    this.renderOptionsTextDefend.update();
    this.renderOptionsTextScout.update();

    // Render Options Algorithms Text;
    this.renderOptionsTextAttackAlgo1.update();
    this.renderOptionsTextAttackAlgo2.update();
    this.renderOptionsTextAttackAlgo3.update();
    this.renderOptionsTextDefendAlgo1.update();
    this.renderOptionsTextDefendAlgo2.update();
    this.renderOptionsTextDefendAlgo3.update();
    this.renderOptionsTextScoutAlgo1.update();
    this.renderOptionsTextScoutAlgo2.update();
    this.renderOptionsTextScoutAlgo3.update();
  }

  //=========================================================================//
  // Mouse methods                                                           //
  //=========================================================================// 

  public void listenMousePressed(){
    this.mousePressed = true;
  }

  public void listenMouseReleased(){
    this.mousePressed = false;
  }

  private boolean checkMousePressButton(Vector2D location, 
    Vector2D dimensions){
    if(this.mousePressed){
      if(this.sketch.mouseX >= location.getX() &&
      this.sketch.mouseX <= location.getX() + dimensions.getX() &&
      this.sketch.mouseY >= location.getY() &&
      this.sketch.mouseY <= location.getY() + dimensions.getY()){
        return true;
      }
    }
    return false;
  }

  private void updateMousePressButton(){
    if(this.checkMousePressButton(
      new Vector2D(this.sketch.width - this.sketch.height / 2 - 200 - 10, 35),
      new Vector2D(200, 40))){
      this.currentScreen = GAMESCREEN.GAME;
    }
  }


  //=========================================================================//
  // Swarm Algorithm methods                                                 //
  //=========================================================================//
  private void setupSwarmAlgorithmPreview(){
    this.swarmAlgorithmPreview = new SwarmAlgorithmPreview(this.sketch);
  }

  private void updateSwarmAlgorithmPreview(){
    this.swarmAlgorithmPreview.update();
  }

  //=========================================================================//
  // Start methods                                                           //
  //=========================================================================//
  private void setupStartButton(){
    this.renderStartButton = new Button(
      this.sketch, 
      "START GAME",
      new Vector2D(this.sketch.width - this.sketch.height / 2 - 200 - 10, 
                   35),
      new Vector2D(200, 40), 
      44,
      44,
      44
    );
  }

  private void updateStartButton(){
    this.sketch.textSize(25);
    this.renderStartButton.update();
    this.sketch.textSize(12);
  }

  //=========================================================================//
  // UI Mini map methods                                                        //
  //=========================================================================// 
  private void setupUIMiniMap(){
    this.renderMiniMap = new RenderMiniMap(sketch,
                                           (int) (sketch.height /  2.2), 
                                           20);
    this.renderUIMiniMapBot = new RenderUIMiniMapBot(sketch);
    this.renderUIMiniMapPlayer1 = new RenderUIMiniMapPlayer1(sketch);
  }  

  private void updateUIMiniMap(){
    this.renderMiniMap.update();
    for(int i = 0; i < CommsGlobal.get("PLAYERUI_BOT")
                                  .getNumberOfReceivers(); i++){
      this.renderUIMiniMapBot.update(CommsGlobal.get("PLAYERUI_BOT")
                                                .getPacket(i)
                                                .getLocation());
    }
    this.renderUIMiniMapPlayer1.update(CommsGlobal.get("PLAYERUI")
                                                  .getPacket(0)
                                                  .getLocation());
  }
}