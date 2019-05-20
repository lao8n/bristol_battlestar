package swarm_wars_library.fsm_ui;

import processing.core.PApplet;
import processing.core.PImage;

import swarm_wars_library.game_screens.GAMESCREEN;
import swarm_wars_library.physics.Vector2D;

public class Rules {

        // Processing
        private PApplet sketch;
        private PImage backgroundImage;
        private PImage deco1;
        private PImage deco2;
        private PImage deco3;
        private Star starRules;
        private Label label1;
        private Vector2D location1;
        private Vector2D location2;


    // Game Screen
        private GAMESCREEN currentScreen;


        private boolean mousePressed = false;

        private FSMBackground start;
        private FSMBackground background;
        private FSMBackground rule;
        private boolean show = false;

        //=========================================================================//
        // Constructor                                                             //
        //=========================================================================//
        public Rules(PApplet sketch){
            this.sketch = sketch;
            this.currentScreen = GAMESCREEN.RULES;
            PImage background = sketch.loadImage(
                    "resources/images/background.png");
            this.backgroundImage = background.get(0, 0, sketch.width, sketch.height);

            this.deco1 = sketch.loadImage("resources/images/shipSingle.png");
            //this.deco2 = sketch.loadImage("resources/images/scoutBee.png");
            this.deco3 = sketch.loadImage("resources/images/defendFlock.png");



            this.setupButtons();
            this.setupLocations();
            this.setupStars();
            this.setupLabels();
            this.setupOptions();

        }

        //=========================================================================//
        // Update method                                                           //
        //=========================================================================//
        public void update(){
            this.updateBackground();
            this.sketch.image(this.deco3, this.sketch.width/3, this.sketch.height/5);
            this.updateButtons();
            this.updateStars();
            this.updateLabels();
            this.updateOptions();
            this.updateMousePressButton();
            this.updateImages();
        }

        //=========================================================================//
        // Game screen method                                                      //
        //=========================================================================//
        public GAMESCREEN getGameScreen(){
            return this.currentScreen;
        }

        public void resetGameScreen() {
            this.currentScreen = GAMESCREEN.RULES;
        }

        //=========================================================================//
        // Background methods                                                      //
        //=========================================================================//
        private void updateBackground(){
            this.sketch.image(this.backgroundImage, 0, 0,
                    this.sketch.width, this.sketch.height);

        }

        private void updateImages(){
          this.sketch.image(this.deco1, 50, 50);
          //this.sketch.image(this.deco2, 50, 70);

         }

        public void setupOptions() {

        }

        public void updateOptions() {

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
            this.background = new FSMBackground(this.sketch,
                    "\n1,200 light years away from here, spaceships gathered their armies " +
                    "into powerful swarms.\n As they learnt new algorithms, their collective " +
                    "intelligence grew.\n With their growth in power, wars shacking the universe burst out.\n"+
                            "The question remains, who won?\n",
                    new Vector2D((this.sketch.width - ((this.sketch.width/3) * 2)) / 2,
                            (this.sketch.height - ((this.sketch.height/3) * 2)) / 2),
                    new Vector2D((this.sketch.width/3) * 2, (this.sketch.height/3) * 2),
                    0, 0, 0, 90);
            // this.background.setSetTextSize(true);
            this.rule = new FSMBackground(this.sketch,
                    "In this game, it's your job to control the mothership.\n\n 1. Design a " +
                            "finite state machine (FSM) for the bots so that they can be the best army"+
                            "possible.\n\n 2. On the next screen you will see three types of FSM.\n You can " +
                            "pick one based on what gameplay strategy you will use.\n One FSM might be more "+
                            "simple, but you can cycle through states more often.\n Another FSM might be " +
                            "more complex with more state options, but the trade off is that you only get\n" +
                            "to use each one for a shorter time.\n You need to strategies before you make " +
                            "your decision!\n\n 3. After you pick the state machine, you get to preview all of " +
                            "the defending, scouting,\n and special algorithms that the bots cycle through " +
                            "based on the machine design.\n\n 4. Click on the algorithm logos to preview how your "+
                            "bots will behave in the game,\n and if you like one then assign it to the " +
                            "matching coloured section on your FSM.\n\n 5. Control the motherships movement with " +
                            "the WASD keys and use the mouse to aim and shoot.\n\n 6. When you are ready to begin " +
                            "your journey, press Start Game and get ready to fight!",
                    new Vector2D((this.sketch.width - ((this.sketch.width/3) * 2)) / 2,
                            (this.sketch.height - ((this.sketch.height/3) * 2)) / 2),
                    new Vector2D((this.sketch.width/3) * 2, (this.sketch.height/3) * 2),
                    0, 0, 0, 100);
        }

        private void updateButtons() {
            this.start.update();
            this.sketch.textSize(20);
            this.background.update();
            this.sketch.textSize(15);

           if (this.show == true) {
               //this.rule.setSetTextSize(false);
               this.sketch.textSize(20);
               this.rule.update();
               this.sketch.textSize(15);
           }
        }


    //=========================================================================//
    // Mouse events                                                            //
    //=========================================================================//

    private boolean checkMousePressStar(Vector2D location, int radius) {
        if(this.mousePressed){
            if(this.sketch.mouseX >= location.getX() - radius &&
                    this.sketch.mouseX <= location.getX() + radius &&
                    this.sketch.mouseY >= location.getY() - radius &&
                    this.sketch.mouseY <= location.getY() + radius){
                return true;
            }
        }
        return false;
    }


    private void updateMousePressButton() {
        int labelColour = 255;
        Vector2D dimensions = new Vector2D(45, 85);
        int nPoints = 5;
        if (this.checkMousePressButton(new Vector2D(this.sketch.width - 150, 30),
                new Vector2D(100, 50))) {
            System.out.println("press start");
            this.currentScreen = GAMESCREEN.FSMUI;
        }
        else if(this.checkMousePressStar(this.location1, 80) == true) {

            this.show = true;
            this.background = new FSMBackground(this.sketch,
                        "",
                        new Vector2D((this.sketch.width - ((this.sketch.width/3) * 2)) / 2,
                                (this.sketch.height - ((this.sketch.height/3) * 2)) / 2),
                        new Vector2D((this.sketch.width/3) * 2, (this.sketch.height/3) * 2),
                        0, 0, 0, 90);
            this.starRules = new Star(this.sketch,
                    this.location2,
                    dimensions,
                    nPoints,
                    241, 189, 0);
            this.label1 = new Label(this.sketch, labelColour, "HIDE\nGAME\nRULES", this.location2);
        }

        else if (this.checkMousePressStar(this.location2, 80) == true) {

            this.show = false;
            this.background = new FSMBackground(this.sketch,
                    "\n1,200 light years away from here, spaceships gathered their armies " +
                            "into powerful swarms.\n As they learnt new algorithms, their collective " +
                            "intelligence grew.\n With their growth in power, wars shacking the universe burst out.\n"+
                            "The question remains, who won?\n",
                    new Vector2D((this.sketch.width - ((this.sketch.width/3) * 2)) / 2,
                            (this.sketch.height - ((this.sketch.height/3) * 2)) / 2),
                    new Vector2D((this.sketch.width/3) * 2, (this.sketch.height/3) * 2),
                    0, 0, 0, 90);
            this.starRules = new Star(this.sketch,
                    this.location1,
                    dimensions,
                    nPoints,
                    241, 189, 0);
            this.label1 = new Label(this.sketch, labelColour, "SHOW\nGAME\nRULES", this.location1);
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
    }

    public void listenMouseReleased(){
        this.mousePressed = false;
    }


    //=========================================================================//
    // Star methods                                                            //
    //=========================================================================//

    private void setupStars(){
        Vector2D dimensions = new Vector2D(45, 85);
        int nPoints = 5;
        this.starRules = new Star(this.sketch,
                this.location1,
                dimensions,
                nPoints,
                241, 189, 0);
    }


    private void updateStars(){
        this.starRules.update();
    }


    //=========================================================================//
    // Locations methods                                                       //
    //=========================================================================//
    private void setupLocations(){
        this.location1 = new Vector2D((this.sketch.width - this.sketch.width/3 * 2) / 2 + 100,
                (this.sketch.height/3) * 2);
        this.location2 = new Vector2D((this.sketch.width - this.sketch.width/3 * 2) / 2,
                (this.sketch.height/3) * 2 + 100);
    }

    //=========================================================================//
    // Labels methods                                                          //
    //=========================================================================//
    private void setupLabels(){
        int labelColour = 255;

        this.label1 = new Label(this.sketch, labelColour, "GAME\nRULES", this.location1);

    }

    private void updateLabels(){
        this.label1.update();
    }



}
