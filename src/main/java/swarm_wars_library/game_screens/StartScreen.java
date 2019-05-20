package swarm_wars_library.game_screens;

import processing.core.PApplet;
import processing.core.PImage;
import swarm_wars_library.SwarmWars;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.swarm_select.TextButton;
import processing.core.PConstants;


public class StartScreen {

    // Processing
    private PApplet sketch;
    private PImage backgroundImage;
    private PImage background;
    private PImage battlestarLogo;
    private PImage shipLogo;

    // Game Screen
    private GAMESCREEN currentScreen;

    // Buttons
    private Vector2D dim1PlayerButton;
    private Vector2D dim2Player1stButton;
    private Vector2D dim2Player2ndButton;

    private Vector2D location1PlayerButton;
    private Vector2D location2Player1stButton;
    private Vector2D location2Player2ndButton;

    private TextButton render1PlayerButton;
    private TextButton render2Player1stButton;
    private TextButton render2Player2ndButton;

    private boolean twoPlayer = false;

    // Mouse
    private boolean mousePressed = false;

    public StartScreen(PApplet sketch) {
        this.sketch = sketch;
        background = sketch.loadImage("resources/images/background.png");
        this.backgroundImage = background.get(0, 0, sketch.width, sketch.height);

        shipLogo = sketch.loadImage("resources/images/shipLogo.png"); 
        battlestarLogo = sketch.loadImage("resources/images/battlestarLogo.png");

        currentScreen = GAMESCREEN.START;

        this.setup1PlayerButton();
        this.setup2Player1stButton();
        this.setup2Player2ndButton();
    }

    public void update() {
        this.updateBackground();
        this.updateMousePressButton();
        this.update1PlayerButton();
        this.update2Player1stButton();
        this.update2Player2ndButton();
    }

    //=========================================================================//
    //            BUTTON SETUP                                                 //
    //=========================================================================//

    private void setup1PlayerButton() {

        int buttonWidth = 200;
        int buttonHeight = 60;
        int windowWidth = sketch.width;
        int windowHeight = sketch.height;

        dim1PlayerButton = new Vector2D(buttonWidth, buttonHeight);
        location1PlayerButton = new Vector2D(windowWidth / 3 - buttonWidth
                , (windowHeight / 6)*5 - buttonHeight / 2);

        this.render1PlayerButton = new TextButton(
                this.sketch
                , "1 PLAYER"
                , location1PlayerButton
                , dim1PlayerButton
                , 40, 40, 80
        );
    }

    private void setup2Player1stButton() {

        int buttonWidth = 200;
        int buttonHeight = 60;
        int windowWidth = sketch.width;
        int windowHeight = sketch.height;

        dim2Player1stButton = new Vector2D(buttonWidth, buttonHeight);
        location2Player1stButton = new Vector2D(windowWidth/2 - buttonWidth/2
        , (windowHeight / 6)*5 - buttonHeight / 2);

        this.render2Player1stButton = new TextButton(
                this.sketch
                , "2 PLAYER (1)"
                , location2Player1stButton
                , dim2Player1stButton
                , 40, 40, 80
        );
    }

    private void setup2Player2ndButton() {

        int buttonWidth = 200;
        int buttonHeight = 60;
        int windowWidth = sketch.width;
        int windowHeight = sketch.height;

        dim2Player2ndButton = new Vector2D(buttonWidth, buttonHeight);
        location2Player2ndButton = new Vector2D((windowWidth/3)*2
                , (windowHeight / 6)*5 - buttonHeight / 2);

        this.render2Player2ndButton = new TextButton(
                this.sketch
                , "2 PLAYER (2)"
                , location2Player2ndButton
                , dim2Player2ndButton
                , 40, 40, 80
        );
    }

    private void update1PlayerButton() {
        this.sketch.textSize(25);
        this.render1PlayerButton.update();
        this.sketch.textSize(12);
    }

    private void update2Player1stButton() {
        this.sketch.textSize(25);
        this.render2Player1stButton.update();
        this.sketch.textSize(12);
    }

    private void update2Player2ndButton() {
        this.sketch.textSize(25);
        this.render2Player2ndButton.update();
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

        // single player mode
        if(this.checkMousePressButton(this.location1PlayerButton, this.dim1PlayerButton)) {
            // navigate to finite state machine UI screen
            Map.getInstance().setPlayerId(1);
            Map.getInstance().setEnemyId(2);
            this.currentScreen = GAMESCREEN.RULES;
        }

        // multiplayer as player 1
        if(this.checkMousePressButton(this.location2Player1stButton, this.dim2Player1stButton)) {
            SwarmWars.playNetworkGame = true;
            Map.getInstance().setPlayerId(1);
            Map.getInstance().setEnemyId(2);
            twoPlayer = true;
            this.currentScreen = GAMESCREEN.RULES;
        }

        // multiplayer as player 2
        if(this.checkMousePressButton(this.location2Player2ndButton, this.dim2Player2ndButton)) {
            SwarmWars.playNetworkGame = true;
            Map.getInstance().setPlayerId(2);
            Map.getInstance().setEnemyId(1);
            twoPlayer = true;
            this.currentScreen = GAMESCREEN.RULES;
        }
    }

    //=========================================================================//
    // Background methods                                                      //
    //=========================================================================//
    private void updateBackground(){
        //this.sketch.background(13, 30, 40);
        this.sketch.imageMode(PConstants.CORNERS);

        // draw background stars
        this.sketch.image(backgroundImage, 0, 0, this.sketch.width, this.sketch.height);

        // draw gameover & ship logos
        this.sketch.image(this.battlestarLogo, 10, 10,
                this.sketch.width-10, this.sketch.height/3-10);

        //this.sketch.imageMode(PConstants.CENTER);
        this.sketch.image(this.shipLogo, this.sketch.width/20, this.sketch.height/3, (this.sketch.width/20)*19, (this.sketch.height/5)*4); 
    }

    public GAMESCREEN getGameScreen() {
        return currentScreen;
    }

    public void resetCurrentScreen() {
        this.currentScreen = GAMESCREEN.START;
    }

    public boolean is2Player(){
        return twoPlayer;
    }
}
