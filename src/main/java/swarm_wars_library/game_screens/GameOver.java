package swarm_wars_library.game_screens;

import processing.core.PApplet;
import processing.core.PImage;
import swarm_wars_library.SwarmWars;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.swarm_select.TextButton;
import processing.core.PConstants;

import swarm_wars_library.comms.CommsGlobal;


public class GameOver {

    // Processing
    private PApplet sketch;
    //private PImage backgroundImage;
    private PImage background;
    private PImage gameOverLogo;
    private PImage brokenShipLogo;

    // Game Screen
    private GAMESCREEN currentScreen;

    // Game Score
    private int myScore;
    private int enemyScore;

    // Replay Button
    private Vector2D dimReplayButton;
    private Vector2D locationReplayButton;
    private TextButton renderReplayButton;

    // Mouse
    private boolean mousePressed = false;

    public GameOver(PApplet sketch) {
        this.sketch = sketch;
        background = sketch.loadImage("resources/images/background.png");
        //this.backgroundImage = background.get(0, 0, sketch.width, sketch.height);

        brokenShipLogo = sketch.loadImage("resources/images/brokenShipLogo.png"); 
        gameOverLogo = sketch.loadImage("resources/images/gameoverLogo.png");

        this.myScore = 0;
        this.enemyScore = 0;

        this.setupReplayButton();
    }

    public void update() {
        this.updateBackground();
        this.updateMousePressButton();
        this.updateReplayButton();
    }

    //=========================================================================//
    //                                                                         //
    //=========================================================================//

    private void setupReplayButton() {

        int buttonWidth = 150;
        int buttonHeight = 60;
        int windowWidth = sketch.width;
        int windowHeight = sketch.height;

        dimReplayButton = new Vector2D(buttonWidth, buttonHeight);
        locationReplayButton = new Vector2D(windowWidth / 2 - buttonWidth / 2
                , windowHeight / 2 - buttonHeight / 2);

        this.renderReplayButton = new TextButton(
                this.sketch
                , "REPLAY"
                , locationReplayButton
                , dimReplayButton
                , 40, 40, 40
        );
    }

    private void updateReplayButton() {
        this.sketch.textSize(25);
        this.renderReplayButton.update();
        this.sketch.textSize(12);
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

    private void updateMousePressButton() {
        if(this.checkMousePressButton(this.locationReplayButton, this.dimReplayButton)) {
            this.currentScreen = GAMESCREEN.FSMUI;
        }
    }

    //=========================================================================//
    // Background methods                                                      //
    //=========================================================================//
    private void updateBackground(){
        //this.sketch.background(13, 30, 40);
        this.sketch.imageMode(PConstants.CORNERS);

        // draw background stars
        this.sketch.image(background, 0, 0, this.sketch.width, this.sketch.height);

        // draw gameover & ship logos
        this.sketch.image(this.gameOverLogo, 0, 0,
                this.sketch.width, this.sketch.height/2);

        this.sketch.imageMode(PConstants.CENTER);
        this.sketch.image(this.brokenShipLogo, this.sketch.width/2, (this.sketch.height*3)/4); 
    }

    public GAMESCREEN getGameScreen() {
        return currentScreen;
    }

    public void resetCurrentScreen() {
        this.currentScreen = GAMESCREEN.GAMEOVER;
    }

}
