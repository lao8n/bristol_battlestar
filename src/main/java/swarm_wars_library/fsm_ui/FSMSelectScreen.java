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
        int boxWidthFSM1 = this.sketch.width/3-40;
        int boxHeightFSM1 = this.sketch.width/3-40;
        int boxXFSM1 = 30;
        int boxYFSM1 = this.sketch.height/4;

        //FSM 2
        int boxWidthFSM2 = this.sketch.width/3-40;
        int boxHeightFSM2 = this.sketch.width/3-40;
        int boxXFSM2 = this.sketch.width/3+20;
        int boxYFSM2 = this.sketch.height/4;

        //FSM 3
        int boxWidthFSM3 = this.sketch.width/3-40;
        int boxHeightFSM3 = this.sketch.width/3-40;
        int boxXFSM3 = (this.sketch.width/3)*2+10;
        int boxYFSM3 = this.sketch.height/4;

        this.fsmOption1 = new FSMOption1(this.sketch, boxWidthFSM1, boxHeightFSM1, 
            boxXFSM1, boxYFSM1);
        this.fsmOption2 = new FSMOption2(this.sketch, boxWidthFSM2, boxHeightFSM2, 
            boxXFSM2, boxYFSM2);
        this.fsmOption3 = new FSMOption3(this.sketch, boxWidthFSM3, boxHeightFSM3, 
            boxXFSM3, boxYFSM3);
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
    }

    private void updateButtons() {
        this.start.update();
    }


    private void updateMousePressButton() {
        if (this.checkMousePressButton(new Vector2D(this.sketch.width - 150, 
                                       30),
                new Vector2D(100, 50))) {
            this.buildFSM(Map.getInstance().getPlayerId());
            this.currentScreen = GAMESCREEN.SWARMSELECT;
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
    }
    
}
