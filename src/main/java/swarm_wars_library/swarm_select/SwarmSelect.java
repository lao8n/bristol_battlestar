package swarm_wars_library.swarm_select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import processing.core.PApplet;
import processing.core.PImage;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;

import swarm_wars_library.SwarmWars;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.fsm.FSMCOMPARISON;
import swarm_wars_library.fsm.FSMManager;
import swarm_wars_library.fsm.FSMSTATE;
import swarm_wars_library.fsm.FSMStateTransition;
import swarm_wars_library.fsm.FSMVARIABLE;
import swarm_wars_library.entities.STATE;
import swarm_wars_library.game_screens.GAMESCREEN;
import swarm_wars_library.graphics.RenderMiniMap;
import swarm_wars_library.graphics.RenderUIMiniMapBot;
import swarm_wars_library.graphics.RenderUIMiniMapPlayer1;
import swarm_wars_library.graphics.RenderUIMiniMapTurret;
import swarm_wars_library.map.Map;
import swarm_wars_library.network.Constants;
import swarm_wars_library.network.Headers;
import swarm_wars_library.network.MessageHandlerMulti;
import swarm_wars_library.network.NetworkClientFunctions;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

public class SwarmSelect{
  // Processing 
  private PApplet sketch;
  private PImage backgroundImage;

  // Game Screen
  private GAMESCREEN currentScreen;

  // Minimap
  private int dimMiniMap;
  private int offsetMiniMap = 20;
  private RenderMiniMap renderMiniMap;
  private RenderUIMiniMapBot renderUIMiniMapBot;
  private RenderUIMiniMapPlayer1 renderUIMiniMapPlayer1;
  private RenderUIMiniMapTurret renderUIMiniMapTurret;

  // Mouse 
  private boolean mousePressed = false;

  // Options
  private Vector2D dimOptionButton;
  private Vector2D locationOptionButton;
  private ArrayList<OptionButton> renderOptionButtons;
  private ArrayList<OptionText> renderOptionTexts;

  // FSM Button
  private Vector2D locationFSMButton;
  private Vector2D dimFSMButton;
  private OptionButton renderFSMButton;

  // FSM Graph
  private FSMVisualisation fsmVisualisation;
  private FSMManager fsmManager;
  private Pair<FSMSTATE, Integer> fsmAddState = 
    new Pair<FSMSTATE, Integer>(FSMSTATE.DEFEND, -1);

  // Swarm Algorithm Preview
  private SwarmAlgorithmPreview swarmAlgorithmPreview;
  private SWARMALGORITHM swarmAlgorithm = SWARMALGORITHM.DEFENDSHELL;

  // Swarm Explanation
  private Vector2D dimSwarmSelection;
  private Vector2D locationSwarmSelection;
  private TextButton renderSwarmSelection;
  private Vector2D dimSwarmExplanation;
  private Vector2D locationSwarmExplanation;
  private TextExplanation renderSwarmExplanation;

  // Start Button 
  private Vector2D dimStartButton;
  private Vector2D locationStartButton;
  private TextButton renderStartButton;  
  private boolean buttonActive = false;

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================//
  public SwarmSelect(PApplet sketch){
    this.sketch = sketch;
    this.dimMiniMap = (int) (this.sketch.height / 2.5);
    this.currentScreen = GAMESCREEN.SWARMSELECT;
    PImage background = sketch.loadImage("resources/images/background.png");
    this.backgroundImage = background.get(0, 0, sketch.width, sketch.height);
    this.sketch.strokeWeight(1);
    this.setupUIMiniMap();
    this.setupOptions();
    this.setupStartButton();
    this.setupSwarmAlgorithmPreview();
    this.setupFSMVisualisation();
    this.setupSwarmExplanation();
    this.setupSwarmSelection();
  }

  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  public void update(){
    this.updateSwarmAlgorithmPreview();
    this.updateBackground();
    this.updateUIMiniMap();
    this.updateOptions();
    this.updateMousePressButton();
    this.updateStartButton();
    this.updateFSMVisualisation();
    this.updateSwarmExplanation();
    this.updateSwarmSelection();
  }

  //=========================================================================//
  // Background methods                                                      //
  //=========================================================================//
  private void updateBackground(){
    // this.sketch.background(13, 30, 40);
    this.sketch.image(this.backgroundImage, 0, 0, 
                      this.sketch.width, this.sketch.height);  
  }

  //=========================================================================//
  // FSM Visualisation                                                       //
  //=========================================================================//
  public void setupFSMVisualisation(){
    this.fsmManager = FSMManager.getInstance();
    this.fsmVisualisation = new FSMVisualisation(
      this.sketch, 
      this.locationFSMButton,
      this.dimFSMButton,
      new ArrayList<>(this.fsmManager.getMapFSMStateTransition(
        Map.getInstance().getPlayerId()).values()));
  }
  
  public void updateFSMVisualisation(){
    if(this.mousePressed){
      this.fsmAddState = 
        this.fsmVisualisation.checkMousePressedFSMState(this.sketch.mouseX, 
                                                        this.sketch.mouseY);
    }
    this.fsmVisualisation.update();
  }

  //=========================================================================//
  // Game screen method                                                      //
  //=========================================================================//
  public GAMESCREEN getGameScreen(){
    return this.currentScreen;
  }

  public void resetGameScreen(){
    this.currentScreen = GAMESCREEN.SWARMSELECT;
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
    // Start Button
    if(this.checkMousePressButton(this.locationStartButton, 
                                  this.dimStartButton)){
      // TODO: Send setup package
      // TODO: Get everything from FSMManger
      if (buttonActive){
        this.currentScreen = GAMESCREEN.GAME;
        if(SwarmWars.playNetworkGame) {
          sendSetUp();
          NetworkClientFunctions.sendStart(Map.getInstance().getPlayerId());
          NetworkClientFunctions.awaitStart();
        }
      }
    }
  }

  private void sendSetUp() {
    java.util.Map<String, Object> packToBeSent = new HashMap<String, Object>();
    int myPlayId = Map.getInstance().getPlayerId();

    packToBeSent.put(Headers.PLAYER, myPlayId);

    //  Number of different FSM states
    java.util.Map<Integer, FSMSTATE> myStates = fsmManager.getFSMStates(myPlayId);
    java.util.Map<Integer, Integer> states = new HashMap<Integer, Integer>();
    // The FSM id number for each state
    for (Integer i : myStates.keySet()) {
      states.put(i, myStates.get(i).ordinal());
    }
    packToBeSent.put(Headers.STATES, states);

    HashMap<Integer, FSMStateTransition> myTransitions = fsmManager.getMapFSMStateTransition(myPlayId);
    List<java.util.Map<Integer, Object>> transitions = new ArrayList<java.util.Map<Integer, Object>>();
    java.util.Map<Integer, Integer> swarmAlgorithms = new HashMap<Integer, Integer>();
    for (int i : myTransitions.keySet()) {
      FSMStateTransition fst = myTransitions.get(i);
      List l = fst.getMyTransitions();
      // Swarm logic for each state
      swarmAlgorithms.put(i, fst.getSwarmAlgorithm().ordinal());
      // Transition list from state i to j
      for (int j = 0; j < l.size(); j++) {
        Quartet q = (Quartet) l.get(j);
        java.util.Map<Integer, Object> transition = new HashMap<Integer, Object>();
        transition.put(Constants.FROM_STATE, i);
        transition.put(Constants.TO_STATE, q.getValue0());
        transition.put(Constants.FSMVARIABLE, ((FSMVARIABLE) q.getValue1()).ordinal());
        transition.put(Constants.FSMCOMPARISON, ((FSMCOMPARISON) q.getValue2()).ordinal());
        transition.put(Constants.VALUE, q.getValue3());
        transitions.add(transition);
      }
    }
    packToBeSent.put(Headers.TRANSITIONS, transitions);
    packToBeSent.put(Headers.SWARM_LOGIC, swarmAlgorithms);
    packToBeSent.put(Headers.TYPE, Constants.SETUP);

    MessageHandlerMulti.putPackage(packToBeSent);
    System.out.println("Step 3");
  }

  //=========================================================================//
  // Option methods                                                          //
  //=========================================================================// 
  private void setupOptions(){
    // Option Buttons
    int dimButton = (int) (this.dimMiniMap - this.offsetMiniMap * 2) / 3;
    this.dimOptionButton = new Vector2D(dimButton, dimButton);
    this.locationOptionButton = 
      new Vector2D(this.offsetMiniMap * 3, 
                   this.sketch.height - this.dimMiniMap - this.offsetMiniMap);
    this.renderOptionButtons = new ArrayList<OptionButton>();
    int numTypes = 3;
    int numAlgosPerType = 4;
    for(int i = 0; i < numTypes; i++){
      for(int j = 0; j < numAlgosPerType; j++){
        OptionButton tempRenderOptionButton = new OptionButton(
          this.sketch,
          new Vector2D((this.locationOptionButton.getX() + 
                       j * (this.dimOptionButton.getX() + 
                            this.offsetMiniMap)),
                       (this.locationOptionButton.getY() + 
                       i * (this.dimOptionButton.getY() + 
                            this.offsetMiniMap))),
          this.dimOptionButton);
        this.renderOptionButtons.add(tempRenderOptionButton);
      }
    }
    // Option Texts
    this.renderOptionTexts = new ArrayList<OptionText>();
    OptionText attackOptionText = new OptionText(
      this.sketch, "SPECIAL", 
      Vector2D.add(this.locationOptionButton, 
                   new Vector2D(-this.offsetMiniMap, 0)), 
      new Vector2D(dimButton, this.offsetMiniMap), 
      177,
      177,
      177
    );
    this.renderOptionTexts.add(attackOptionText);

    OptionText defendOptionText = new OptionText(
      this.sketch, "DEFEND", 
      Vector2D.add(this.locationOptionButton, 
                   new Vector2D(-this.offsetMiniMap, 
                                this.offsetMiniMap + dimButton)), 
      new Vector2D(dimButton, this.offsetMiniMap), 
      177,
      177,
      177
    );
    this.renderOptionTexts.add(defendOptionText);

    OptionText scoutOptionText = new OptionText(
      this.sketch, "SCOUT", 
      Vector2D.add(this.locationOptionButton, 
                   new Vector2D(-this.offsetMiniMap, 
                                2 * (this.offsetMiniMap + dimButton))), 
      new Vector2D(dimButton, this.offsetMiniMap), 
      177,
      177,
      177
    );
    this.renderOptionTexts.add(scoutOptionText);

    this.locationFSMButton = 
      Vector2D.add(this.locationOptionButton,
                   new Vector2D(numAlgosPerType * 
                                (dimButton + this.offsetMiniMap),
                                0));
    
    this.dimFSMButton = new Vector2D(
      this.sketch.width - this.dimMiniMap - this.offsetMiniMap * 2 - 
      this.locationFSMButton.getX(),
      this.sketch.height - this.locationFSMButton.getY() - this.offsetMiniMap);

    this.renderFSMButton = new OptionButton(
      this.sketch, 
      this.locationFSMButton, 
      this.dimFSMButton
    );

  }

  private void updateOptions(){
    int algCount = 0; 
    for(int i = 0; i < this.renderOptionButtons.size(); i++){
      if(this.checkMousePressButton(
        this.renderOptionButtons.get(i).getTopLeftLocation(),
        this.renderOptionButtons.get(i).getDimensions())){
        this.swarmAlgorithmPreview.selectSwarmAlgorithm(
          SWARMALGORITHM.valueOf(i));
        this.renderSwarmSelection.setLabel("SELECT SWARM ALGORITHMS: " + 
                                           SWARMALGORITHM.valueOf(i).toString());
        this.renderSwarmSelection.setRGB(SWARMALGORITHM.valueOf(i));
        this.renderSwarmExplanation.setLabel(this.getTextExplanation(
          SWARMALGORITHM.valueOf(i)
        ));
        if(SWARMALGORITHM.valueOf(i).getFSMState() == 
          this.fsmAddState.getValue(0) & (int) this.fsmAddState.getValue(1) >= 0){
          this.fsmManager.setSwarmAlgorithm(Map.getInstance().getPlayerId(), (int) this.fsmAddState.getValue(1),
                                            SWARMALGORITHM.valueOf(i));
       }
      }
      int selectedCheck = -1;
      for(int j = 1; j <= this.fsmManager.getMapFSMStateTransition(Map.getInstance().getPlayerId()).size();
          j++){        
        if(this.fsmManager.getMapFSMStateTransition(Map.getInstance().getPlayerId()).get(j).getSwarmAlgorithm()
           == SWARMALGORITHM.valueOf(i)){
          selectedCheck = j;
          if (i == 0 || i == 1 || i == 4 || i == 5 || i == 8 || i == 9){
            algCount++; 
          }
        }
      }
      this.renderOptionButtons.get(i)
                              .update(SWARMALGORITHM.valueOf(i), selectedCheck);
    }
    this.sketch.textSize(15);
    for(int i = 0; i < this.renderOptionTexts.size(); i++){
      this.renderOptionTexts.get(i).update();
    }
    this.renderFSMButton.update();

    // activate button check
    if (algCount == this.fsmManager.getMapFSMStateTransition(Map.getInstance().getPlayerId()).size()){
      this.updateActivateButton(true);
    } else {
      this.updateActivateButton(false);
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
  // Activate Button                                                         //
  //=========================================================================//
  private void updateActivateButton(boolean activate){
    if (activate){
      this.setupReadyButton();
    } else {
      this.setupStartButton();
    }
  }


  //=========================================================================//
  // Start methods                                                           //
  //=========================================================================//
  private void setupStartButton(){
    // setup button to be red (not clickable yet)
    buttonActive = false;
    this.dimStartButton = new Vector2D(200, 60);
    this.locationStartButton = 
      new Vector2D(this.sketch.width - this.dimStartButton.getX() - 
                   this.offsetMiniMap, 
                   this.offsetMiniMap);
    this.renderStartButton = new TextButton(
      this.sketch, 
      "STOP",
      this.locationStartButton,
      this.dimStartButton, 
      127,
      0,
      0
    );
  }

  private void setupReadyButton(){
    buttonActive = true;
    this.dimStartButton = new Vector2D(200, 60);
    this.locationStartButton = 
      new Vector2D(this.sketch.width - this.dimStartButton.getX() - 
                    this.offsetMiniMap, 
                    this.offsetMiniMap);
    this.renderStartButton = new TextButton(
      this.sketch, 
      "READY",
      this.locationStartButton,
      this.dimStartButton, 
      40,
      127,
      0
    );
  }

  private void updateStartButton(){
    this.sketch.textAlign(LEFT, CENTER);
    this.sketch.textSize(25);
    this.renderStartButton.update();
  }

  //=========================================================================//
  // Swarm Algorithm Selection / algorithms                                  //
  //=========================================================================//
  private void setupSwarmSelection(){
    this.dimSwarmSelection = new Vector2D(this.sketch.width - 
                                          this.dimStartButton.getX() - 
                                          this.offsetMiniMap * 3, 
                                          this.dimStartButton.getY());
    this.locationSwarmSelection = new Vector2D(this.offsetMiniMap, 
                                               this.offsetMiniMap);
    this.renderSwarmSelection = new TextButton(
      this.sketch,
      "SELECT YOUR SWARM ALGORITHM: CHOOSE NOW!!",
      this.locationSwarmSelection,
      this.dimSwarmSelection,
      177, 
      177, 
      177
    );
  }
  
  private void setupSwarmExplanation(){
    this.dimSwarmExplanation = new Vector2D(this.sketch.width - 2 * 
                                            this.offsetMiniMap,
                                            this.sketch.height - 
                                            this.dimMiniMap - 
                                            this.dimStartButton.getY() - 4 *
                                            this.offsetMiniMap);
    this.locationSwarmExplanation = new Vector2D(this.offsetMiniMap,
                                                 this.offsetMiniMap * 2 + 
                                                 this.dimStartButton.getY());
    this.renderSwarmExplanation = new TextExplanation(
      this.sketch,
      "Welcome to the Swarm Algorithm selection screen!!\n" + 
      "Use this screen to try out different swarm algorithms by clicking on " + 
      "the icons below and see their behaviour in the preview screen.\n" + 
      "Fill in your finite state machine by clicking on a coloured node and " + 
      "then choosing a correspondingly coloured swarm algorithm.\n",
      this.locationSwarmExplanation,
      this.dimSwarmExplanation,
      0, 
      0, 
      0
    );
  }

  private void updateSwarmExplanation(){
    this.sketch.textAlign(LEFT, TOP);
    this.sketch.textSize(15);
    this.renderSwarmExplanation.update();
  }

  private void updateSwarmSelection(){
    this.sketch.textSize(25);
    this.sketch.textAlign(LEFT, CENTER);
    this.renderSwarmSelection.update();
    this.sketch.textSize(12);
  }

  private String getTextExplanation(SWARMALGORITHM swarmAlgorithm){
    switch(swarmAlgorithm){
      case SPECIALSUICIDE:
        return "The suicide algorithm is a dangerous strategy. Your bots leave " + 
               "the mothership and hunt down turrets. You may rack up the \n" + 
               "points but you leave yourself extremely exposed to any potential " + 
               "attackers.";
      case SPECIALGHOST:
        return "";
      case SPECIALSTAR:
        return  "";
      case SPECIALSACRIFICE:
        return "";
      case DEFENDSHELL:
        return "";
      case DEFENDFLOCK:  
        return "Sometimes the best form of attack is defense. Flock behaviour is " + 
                "a classic swarm algorithm. \n" + 
                "Just like a flock of birds, each bot in the swarm follows three rules:\n" + 
                " 1. Separate = if distance to another bot is less than 20 then apply a force to " + 
                " separate them. \n" + 
                " 2. Cohese = if the distance to another bot is less than 40 then apply a force " + 
                " draw them closer together. \n" + 
                " 3. Align = for all the bots within a radius of 40, apply the average velocity" + 
                " of the group\n" + 
                "When combined with tracking of the mothership, flocks are a powerful way to " + 
                "defend yourself.";
      case DEFENDINVINCIBLE:
        return "";
      case DEFENDHIBERNATE:
        return "";
      case SCOUTRANDOM:
        return "In war, information is everything. Use this scout algorithm to search the map " + 
               "for enemies. \n" + 
               "The bots randomly diffuse over the map like brownian motion. Given enough time " + 
               "no hiding place is safe!";
      case SCOUTBEE:
        return "Nothing is scarier than a swarm of angry bees! Use this algorithm to scout for " + 
               "turrets and find them before your enemy does.\n";
      case SCOUTANT:
        return "";
      case SCOUTPSO:
        return "";
      default:
        return "";
    }
  }

  //=========================================================================//
  // UI Mini map methods                                                     //
  //=========================================================================// 
  private void setupUIMiniMap(){
    this.renderMiniMap = new RenderMiniMap(this.sketch,
                                           this.dimMiniMap, 
                                           this.offsetMiniMap);
    this.renderUIMiniMapBot = 
      new RenderUIMiniMapBot(this.sketch, 
                             this.dimMiniMap, 
                             this.offsetMiniMap);
    this.renderUIMiniMapPlayer1 = 
      new RenderUIMiniMapPlayer1(this.sketch, 
                                 this.dimMiniMap,
                                 this.offsetMiniMap);
    this.renderUIMiniMapTurret = 
      new RenderUIMiniMapTurret(this.sketch,
                                this.dimMiniMap,
                                this.offsetMiniMap);
  }  

  private void updateUIMiniMap() {
    this.renderMiniMap.update();
    for (int i = 0; i < CommsGlobal.get("TURRET")
            .getNumberOfReceivers(); i++) {
      this.renderUIMiniMapTurret.update(CommsGlobal.get("TURRET")
              .getPacket(i)
              .getLocation());
    }
    for (int i = 0; i < CommsGlobal.get("PLAYERUI_BOT")
            .getNumberOfReceivers(); i++) {
      // Render entity if alive
      if (CommsGlobal.get("PLAYERUI_BOT")
              .getPacket(i)
              .getState()
              .equals(STATE.ALIVE)) {
        this.renderUIMiniMapBot.update(CommsGlobal.get("PLAYERUI_BOT")
                .getPacket(i)
                .getLocation());
      }
      this.renderUIMiniMapPlayer1.update(CommsGlobal.get("PLAYERUI")
              .getPacket(0)
              .getLocation());
    }
  }
}