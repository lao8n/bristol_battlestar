package swarm_wars_library.fsm_ui;

import processing.core.PApplet;
import processing.core.PImage;

import swarm_wars_library.game_screens.GAMESCREEN;
import swarm_wars_library.fsm.FSMCOMPARISON;
import swarm_wars_library.fsm.FSMManager;
import swarm_wars_library.fsm.FSMSTATE;
import swarm_wars_library.fsm.FSMVARIABLE;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;

public class FSMSelectScreen{

    // Processing
    private PApplet sketch;
    private PImage backgroundImage;

    // Game Screen
    private GAMESCREEN currentScreen;

    //FSM Options
    private FSMOption1 fsmOption1;
    private FSMOption2 fsmOption2;
    private FSMOption3 fsmOption3;
    private int chosenOption = 1;

    private boolean mousePressed = false;

    private FSMBackground start;
    private FSMBackground instructions;


    // Box dimensions
    private int boxWidthFSM1;
    private int boxHeightFSM1;
    private int boxXFSM1;
    private int boxYFSM1;

    //FSM 2
    private int boxWidthFSM2;
    private int boxHeightFSM2;
    private int boxXFSM2;
    private int boxYFSM2;

    //FSM 3
    private int boxWidthFSM3;
    private int boxHeightFSM3;
    private int boxXFSM3;
    private int boxYFSM3;


    // FSM Manager Methods
    private FSMManager fsmManager = FSMManager.getInstance();

    //=========================================================================//
    // Constructor                                                             //
    //=========================================================================//
    public FSMSelectScreen(PApplet sketch){
        this.sketch = sketch;
        this.currentScreen = GAMESCREEN.FSMUI;
        PImage background = sketch.loadImage(
                "resources/images/background.png");
        this.backgroundImage = background.get(0, 0, sketch.width, sketch.height);
        this.setupButtons();
        this.setupOptions();
    }

    //=========================================================================//
    // Update method                                                           //
    //=========================================================================//
    public void update(){
        this.updateBackground();
        this.updateButtons();
        this.updateOptions();
        this.updateMousePressButton();
    }

    //=========================================================================//
    // Game screen method                                                      //
    //=========================================================================//
    public GAMESCREEN getGameScreen(){
        return this.currentScreen;
    }

    public void resetGameScreen() {
        this.currentScreen = GAMESCREEN.FSMUI;
    }

    public void resetFSM(){
        this.fsmManager.reset();
    }

    //=========================================================================//
    // Background methods                                                      //
    //=========================================================================//
    private void updateBackground(){
        this.sketch.image(this.backgroundImage, 0, 0,
                this.sketch.width, this.sketch.height);

        // for(int i = 0; i <= 10; i++){
        //     this.sketch.fill(255, 255, 204);
        //     this.sketch.ellipse(this.sketch.random(this.sketch.width),
        //             this.sketch.random(this.sketch.height),
        //             2,
        //             2);
        // }
    }

    public void setupOptions() {

        //FSM 1
        this.boxWidthFSM1 = this.sketch.width/3-40;
        this.boxHeightFSM1 = this.sketch.width/3-40;
        this.boxXFSM1 = 30;
        this.boxYFSM1 = this.sketch.height/4;

        //FSM 2
        this.boxWidthFSM2 = this.sketch.width/3-40;
        this.boxHeightFSM2 = this.sketch.width/3-40;
        this.boxXFSM2 = this.sketch.width/3+20;
        this.boxYFSM2 = this.sketch.height/4;

        //FSM 3
        this.boxWidthFSM3 = this.sketch.width/3-40;
        this.boxHeightFSM3 = this.sketch.width/3-40;
        this.boxXFSM3 = (this.sketch.width/3)*2+10;
        this.boxYFSM3 = this.sketch.height/4;

        this.fsmOption1 = new FSMOption1(this.sketch, 
                                         this.boxWidthFSM1, 
                                         this.boxHeightFSM1, 
                                         this.boxXFSM1, 
                                         this.boxYFSM1);
        this.fsmOption2 = new FSMOption2(this.sketch, 
                                         this.boxWidthFSM2, 
                                         this.boxHeightFSM2, 
                                         this.boxXFSM2, 
                                         this.boxYFSM2);
        this.fsmOption3 = new FSMOption3(this.sketch, 
                                         this.boxWidthFSM3, 
                                         this.boxHeightFSM3, 
                                         this.boxXFSM3, 
                                         this.boxYFSM3);
    }

    public void updateOptions() {
        this.fsmOption1.update();
        this.fsmOption2.update();
        this.fsmOption3.update();
    }


    //=========================================================================//
    // Button methods                                                          //
    //=========================================================================//
    private void setupButtons() {
        //INFO RELATED BUTTONS

        this.start = new FSMBackground(this.sketch,
                "Next >",
                new Vector2D(this.sketch.width - 150, 30),
                new Vector2D(100, 50),
                0, 0, 0, 80);
        this.instructions = new FSMBackground(this.sketch,
                "INSTRUCTIONS: \n\nSelected your chosen finite state machine by clicking on the" +
                        "'Selected' button above it.\n You can chose the order of the states by clicking " +
                        "on the two stars you wish to swap around.",
                new Vector2D(30, 30),
                new Vector2D(800, 100),
                0, 0, 0, 80);
    }

    private void updateButtons() {
        this.start.update();
        this.instructions.update();
    }


    private void updateMousePressButton() {
        if (this.checkMousePressButton(new Vector2D(this.sketch.width - 150, 
                                       30),
                new Vector2D(100, 50))) {
            this.start.changeColour(255, 255, 255, 80);
            this.buildFSM(Map.getInstance().getPlayerId());
            this.currentScreen = GAMESCREEN.SWARMSELECT;
        }
        else if(this.checkMousePressButton(
            new Vector2D(this.boxXFSM1, this.boxYFSM1 - 50 - 20),
            new Vector2D(this.boxWidthFSM1, 50))){
            this.chosenOption = 1;
            this.fsmOption1.setSelected(true);
            if (this.fsmOption2.isSelected() == true) {
                this.fsmOption2.setSelected(false);
            }
            if (this.fsmOption3.isSelected() == true) {
                this.fsmOption3.setSelected(false);
            }
        }
        else if(this.checkMousePressButton(
            new Vector2D(this.boxXFSM2, this.boxYFSM2 - 50 - 20),
            new Vector2D(this.boxWidthFSM2, 50))){
            this.chosenOption = 2;
            this.fsmOption2.setSelected(true);
            if (this.fsmOption1.isSelected() == true) {
                this.fsmOption1.setSelected(false);
            }
            if (this.fsmOption3.isSelected() == true) {
                this.fsmOption3.setSelected(false);
            }
        }
        else if(this.checkMousePressButton(
            new Vector2D(this.boxXFSM3, this.boxYFSM3 - 50 - 20),
            new Vector2D(this.boxWidthFSM3, 50))){
            this.chosenOption = 3;
            this.fsmOption3.setSelected(true);
            if (this.fsmOption1.isSelected() == true) {
                this.fsmOption1.setSelected(false);
            }
            if (this.fsmOption2.isSelected() == true) {
                this.fsmOption2.setSelected(false);
            }
        }
        else if (checkMousePressButton(new Vector2D(boxXFSM1, boxYFSM1 + boxHeightFSM1 + 20),
                new Vector2D(boxWidthFSM1, 50)) == true) {
            if (this.fsmOption1.isShowContent() == true) {
                this.fsmOption1.setShowContent(false);
            }
            else {
                this.fsmOption1.setShowContent(true);
            }
        }
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

    public void listenMousePressed(){
        this.mousePressed = true;
        this.fsmOption1.listenMousePressed();
        this.fsmOption2.listenMousePressed();
        this.fsmOption3.listenMousePressed();
    }

    public void listenMouseReleased(){
        this.mousePressed = false;
        this.fsmOption1.listenMouseReleased();
        this.fsmOption2.listenMouseReleased();
        this.fsmOption3.listenMouseReleased();
    }




    //=========================================================================//
    // FSM Manager methods                                                     //
    //=========================================================================//
    public void buildFSM(int playerId){
        if(this.chosenOption == 1){
            for(int i = 0; i < this.fsmOption1.getOrderFSMStates().size(); i++){
                this.fsmManager.addFSMState(playerId, 
                                            i + 1, 
                                            this.fsmOption1.getOrderFSMStates()
                                                           .get(i)); 
            } 
            this.fsmManager.addTransition(
                playerId,
                1,
                2,
                FSMVARIABLE.ENEMYDISTANCE,
                FSMCOMPARISON.GREATERTHAN,
                300);
            this.fsmManager.addTransition(
                playerId,
                2,
                3,
                FSMVARIABLE.ENEMYHEALTH,
                FSMCOMPARISON.LESSTHAN,
                30);
            this.fsmManager.addTransition(
                playerId,
                3,
                1,
                FSMVARIABLE.PLAYERHEALTH,
                FSMCOMPARISON.LESSTHAN,
                30);       
        }
        else if (this.chosenOption == 2){
            for(int i = 0; i < this.fsmOption2.getOrderFSMStates().size(); i++){
                this.fsmManager.addFSMState(playerId, 
                                            i + 1, 
                                            this.fsmOption2.getOrderFSMStates()
                                                           .get(i)); 
            } 
            this.fsmManager.addTransition(
                playerId,
                1,
                2,
                FSMVARIABLE.ENEMYHEALTH,
                FSMCOMPARISON.LESSTHAN,
                70);
            this.fsmManager.addTransition(
                playerId,
                1,
                3,
                FSMVARIABLE.ENEMYDISTANCE,
                FSMCOMPARISON.GREATERTHAN,
                400);
            this.fsmManager.addTransition(
                playerId,
                2,
                3,
                FSMVARIABLE.ENEMYDISTANCE,
                FSMCOMPARISON.GREATERTHAN,
                200);   
            this.fsmManager.addTransition(
                playerId,
                3,
                4,
                FSMVARIABLE.ENEMYDISTANCE,
                FSMCOMPARISON.LESSTHAN,
                200);   
            this.fsmManager.addTransition(
                playerId,
                4,
                1,
                FSMVARIABLE.PLAYERHEALTH,
                FSMCOMPARISON.LESSTHAN,
                90);        
        }
        else if (this.chosenOption == 3){
            for(int i = 0; i < this.fsmOption3.getOrderFSMStates().size(); i++){
                this.fsmManager.addFSMState(playerId, 
                                            i + 1, 
                                            this.fsmOption3.getOrderFSMStates()
                                                           .get(i)); 
            }
            this.fsmManager.addTransition(
                playerId,
                1,
                2,
                FSMVARIABLE.ENEMYHEALTH,
                FSMCOMPARISON.LESSTHAN,
                30);
            this.fsmManager.addTransition(
                playerId,
                2,
                1,
                FSMVARIABLE.ENEMYHEALTH,
                FSMCOMPARISON.GREATERTHAN,
                70);
            this.fsmManager.addTransition(
                playerId,
                2,
                3,
                FSMVARIABLE.ENEMYDISTANCE,
                FSMCOMPARISON.LESSTHAN,
                200);
            this.fsmManager.addTransition(
                playerId,
                3,
                2,
                FSMVARIABLE.ENEMYDISTANCE,
                FSMCOMPARISON.GREATERTHAN,
                400);
            this.fsmManager.addTransition(
                playerId,
                3,
                4,
                FSMVARIABLE.PLAYERHEALTH,
                FSMCOMPARISON.LESSTHAN,
                30);
            this.fsmManager.addTransition(
                playerId,
                4,
                3,
                FSMVARIABLE.PLAYERHEALTH,
                FSMCOMPARISON.GREATERTHAN,
                70);
            this.fsmManager.addTransition(
                playerId,
                4,
                1,
                FSMVARIABLE.ENEMYDISTANCE,
                FSMCOMPARISON.LESSTHAN,
                200);
            this.fsmManager.addTransition(
                playerId,
                1,
                4,
                FSMVARIABLE.ENEMYDISTANCE,
                FSMCOMPARISON.GREATERTHAN,
                400);
        }
    }
    
}
