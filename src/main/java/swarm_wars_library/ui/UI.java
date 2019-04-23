package swarm_wars_library.ui;

import java.util.Arrays;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

import swarm_wars_library.game_screens.GAMESCREEN;
import swarm_wars_library.physics.Vector2D;;

public class UI{

  // Processing 
  private PApplet sketch;
  private PImage backgroundImage;

  // Game Screen
  private GAMESCREEN currentScreen;

  //FSM POSITIONS
  private Vector2D location1;
  private Vector2D location2;
  private Vector2D location3;
  private Vector2D location4;
  private Vector2D location5;

  // Arrows
  private Arrow arrow1;
  private Arrow arrow2;
  private Arrow arrow3;
  private Arrow arrow4;
  private Arrow arrow5;

  // Buttons
  private Button start;
  private Button info;
  private Button transition1;
  private Button transition2;
  private Button transition3;
  private Button transition4;
  private Button transition5;
  private Circle tooltip;
  private boolean showTooltip;
  private String buttonToSwap;
  private boolean buttons[];

  // Labels
  private Label questionMark;
  private Label label1;
  private Label label2;
  private Label label3;
  private Label label4;
  private Label label5;
  private ArrayList<Label> allLabels;
  private Label lab[];

  // Mouse 
  private boolean mousePressed = false;

  // Stars
  private Star star1;
  private Star star2;
  private Star star3;
  private Star star4;
  private Star star5;
  private ArrayList<Star> allStars;


  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================//
  public UI(PApplet sketch){
    this.sketch = sketch;
    this.currentScreen = GAMESCREEN.FSMUI;
    PImage background = sketch.loadImage(
      "src/main/java/swarm_wars_library/ui/star-blue.jpg");
    this.backgroundImage = background.get(0, 0, sketch.width, sketch.height);
    this.setupLocations();
    this.setupButtons();
    this.setupStars();
    this.setupArrows();
    this.setupLabels();
  }

  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  public void update(){
    this.updateBackground();
    this.updateArrows();
    this.updateStars();
    this.updateLabels();
    this.updateButtons();
    this.updateMousePressButton();
    this.updateMousePressStar();
  }

  //=========================================================================//
  // Game screen method                                                      //
  //=========================================================================//
  public GAMESCREEN getGameScreen(){
    return this.currentScreen;
  }

  //=========================================================================//
  // Arrow methods                                                           //
  //=========================================================================//
  private void setupArrows(){
    this.arrow1 = new Arrow(this.sketch, 
                            this.location1, 
                            Vector2D.add(this.location2, 
                                         new Vector2D(-25, -25)));
    this.arrow2 = new Arrow(this.sketch, 
                            this.location2,
                            Vector2D.add(this.location5, 
                                         new Vector2D(0, -30)));
    this.arrow3 = new Arrow(this.sketch,
                            this.location3, 
                            Vector2D.add(this.location1,
                                         new Vector2D(-25, 0)));
    this.arrow4 = new Arrow(this.sketch,
                            this.location4, 
                            Vector2D.add(this.location3,
                                         new Vector2D(0, 30)));
    this.arrow5 = new Arrow(this.sketch,
                            this.location5,
                            Vector2D.add(this.location4,
                                         new Vector2D(30, 0)));
  }

  private void updateArrows(){
    this.arrow1.update();
    this.arrow2.update();
    this.arrow3.update();
    this.arrow4.update();
    this.arrow5.update();
  }

  //=========================================================================//
  // Background methods                                                      //
  //=========================================================================//
  private void updateBackground(){
    this.sketch.image(this.backgroundImage, 0, 0, 
                      this.sketch.width, this.sketch.height);
    
    for(int i = 0; i <= 10; i++){
      this.sketch.fill(255, 255, 204);
      this.sketch.ellipse(this.sketch.random(this.sketch.width),
                          this.sketch.random(this.sketch.height),
                          2, 
                          2);
    }
  }

  //=========================================================================//
  // Button methods                                                          //
  //=========================================================================//
  private void setupButtons(){       
    //INFO RELATED BUTTONS
    this.start = new Button (this.sketch, 
                             "Start Game", 
                             new Vector2D(500, 30),
                             new Vector2D(100, 40));

    String instructions = 
            "WELCOME TO THE \nFINITE STATE MACHINE!\n\n" +
            "Your swarm will transite through\n"+
            "each of the five states represented\n" +
            "in the stars below when the transition\n" +
            "statement in the boxes between the\n" +
            "stars is true.\n\n" +
            "You can swap around the order of the\n" +
            "states and transitions by clicking\n" +
            "on the two states or transitions you\n" +
            "wish to swap around.\n\n" +
            "When you are happy with the order of\n" +
            "the states and transitions\n" +
            "of your finite state machine\n" +
            "click 'Start Game'.";

    this.info = new Button(this.sketch, 
                           instructions, 
                           new Vector2D(50, 45),
                           new Vector2D(250, 300));

    this.tooltip = new Circle(this.sketch,
                              new Vector2D(50, 45),
                              new Vector2D(30, 30));

    this.showTooltip = false;
    
    // Button transitions
    Vector2D transitionDimensions = new Vector2D(120, 55);
    this.transition1  = new Button (
      this.sketch,
      "If enemy is\n" + "more than 2\n star meter away",
      Vector2D.add(this.getMidPoint(this.location1, this.location2),
                   new Vector2D(-30, -27)),
      transitionDimensions);

    this.transition2  = new Button (
      this.sketch,
      "If enemy is\n" + "more than 5\n star meters away\n",
      Vector2D.add(this.getMidPoint(this.location2, this.location5),
                   new Vector2D(-60, -27)),
      transitionDimensions);

    this.transition3  = new Button (
      this.sketch,
      "If fuel is\n" + "less than 1\n gallons of stardust",
      Vector2D.add(this.getMidPoint(this.location4, this.location5),
                   new Vector2D(-60, -27)),
      transitionDimensions);
    
    this.transition4  = new Button (
      this.sketch,
      "If enemy is\n" + "less than 1\n star meter away",
      Vector2D.add(this.getMidPoint(this.location3, this.location4),
                   new Vector2D(-60, -27)),
      transitionDimensions);
    
    this.transition5  = new Button (
      this.sketch,
      "If enemy is\n" + "less than 2\n star meter away",
      Vector2D.add(this.getMidPoint(this.location1, this.location3),
                   new Vector2D(-90, -27)),
      transitionDimensions);
    
    // Buttons for swaps
    this.buttonToSwap = null;
    this.buttons = new boolean[5];
    Arrays.fill(this.buttons, false);
  }

  private void updateButtons(){
    this.start.update();

    this.transition1.update();
    this.transition2.update();
    this.transition3.update();
    this.transition4.update();
    this.transition5.update();

    this.tooltip.update();
    if(this.showTooltip){
      this.info.update();
    }
  }

  //=========================================================================//
  // FSM methods                                                             //
  //=========================================================================//
  public String[] getTransitions(){
    String transitionsOrder[] = {"", "", "", "", ""};
    transitionsOrder[0] = this.transition1.getLabelString();
    transitionsOrder[1] = this.transition2.getLabelString();
    transitionsOrder[2] = this.transition3.getLabelString();
    transitionsOrder[3] = this.transition4.getLabelString();
    transitionsOrder[4] = this.transition5.getLabelString();
    return transitionsOrder;
  }

    public String[] getStates() {
      String statesOrder[] = {"", "", "", "", ""};
      statesOrder[0] = this.label1.getLabelString();
      statesOrder[1] = this.label1.getLabelString();
      statesOrder[2] = this.label1.getLabelString();
      statesOrder[3] = this.label1.getLabelString();
      statesOrder[4] = this.label1.getLabelString();
      return statesOrder;
    }

  //=========================================================================//
  // Labels methods                                                          //
  //=========================================================================//
  private void setupLabels(){
    int labelColour = 255;
    this.questionMark = new Label(this.sketch, 
                                  labelColour, 
                                  "?", 
                                  new Vector2D(50, 45));

    //LABELS TO PUT ON STARS
    this.label1 = new Label(this.sketch, labelColour, "attack", 
                            this.location1);
    this.label2 = new Label(this.sketch, labelColour, "orbit", 
                            this.location2);
    this.label3 = new Label(this.sketch, labelColour, "defence", 
                            this.location3);
    this.label4 = new Label(this.sketch, labelColour, "re-fuel", 
                            this.location4);
    this.label5 = new Label(this.sketch, labelColour, "seek", 
                            this.location5);

    this.allLabels = new ArrayList<Label>();
    this.allLabels.add(this.label1);
    this.allLabels.add(this.label2);
    this.allLabels.add(this.label3);
    this.allLabels.add(this.label4);
    this.allLabels.add(this.label5);

    // Labels for swaps
    this.lab = new Label[2];
    Arrays.fill(this.lab, null);
  } 

  private void updateLabels(){
    this.label1.update();
    this.label2.update();
    this.label3.update();
    this.label4.update();
    this.label5.update();

    this.questionMark.update();
  }

  //=========================================================================//
  // Locations methods                                                       //
  //=========================================================================//
  private void setupLocations(){
    this.location1 = new Vector2D(this.sketch.width * 0.5,
                                  this.sketch.height * 0.15);
    this.location2 = new Vector2D(this.sketch.width * 0.9,
                                  this.sketch.height * 0.4);                                  
    this.location3 = new Vector2D(this.sketch.width * 0.1,
                                  this.sketch.height * 0.4);
    this.location4 = new Vector2D(this.sketch.width * 0.25,
                                  this.sketch.height * 0.75);
    this.location5 = new Vector2D(this.sketch.width * 0.75,
                                  this.sketch.height * 0.75);
  }

  private Vector2D getMidPoint(Vector2D location1, Vector2D location2){
    return new Vector2D((location1.getX() + location2.getX()) / 2,
                        (location2.getY() + location2.getY()) / 2);
  }

  //=========================================================================//
  // Mouse methods                                                           //
  //=========================================================================// 
  public void updateMousePressStar(){
    // Swap label1 for star
    if(this.checkMousePressStar(this.location1, this.label1)){
      this.star1.changeColour();
    }
    // Swap label2 for star
    else if(this.checkMousePressStar(this.location2, this.label2)){
      this.star2.changeColour();
    }
    // Swap label3 for star
    else if(this.checkMousePressStar(this.location3, this.label3)){
      this.star3.changeColour();
    }
    // Swap label4 for star
    else if(this.checkMousePressStar(this.location4, this.label4)){
      this.star4.changeColour();
    }
    // Swap label5 for star
    else if(this.checkMousePressStar(this.location5, this.label5)){
      this.star5.changeColour();
    }
    else {
      this.checkForColourChanges();
    }
  }

  public void updateMousePressButton(){
    Vector2D buttonDimensions = new Vector2D(120, 55);
    // Swap button1
    if(this.checkMousePressButton(
        Vector2D.add(this.getMidPoint(this.location1, this.location2),
                     new Vector2D(-30, -27)),                   
        buttonDimensions)){
      this.swapButton1();
    }
    // Swap button2
    else if(this.checkMousePressButton(
        Vector2D.add(this.getMidPoint(this.location2, this.location5),
                     new Vector2D(-60, -27)),                   
        buttonDimensions)){
      this.swapButton2();
    }  
    // Swap button3
    else if(this.checkMousePressButton(
        Vector2D.add(this.getMidPoint(this.location4, this.location5),
                     new Vector2D(-60, -27)),                   
        buttonDimensions)){
      this.swapButton3();
    } 
    // Swap button 4
    else if(this.checkMousePressButton(
        Vector2D.add(this.getMidPoint(this.location3, this.location4),
                     new Vector2D(-60, -27)),                   
        buttonDimensions)){
      this.swapButton4();
    }
    // Swap button 5
    else if(this.checkMousePressButton(
        Vector2D.add(this.getMidPoint(this.location3, this.location1),
                     new Vector2D(-90, -27)),                   
        buttonDimensions)){
      this.swapButton5();
    }
    else if(this.checkMousePressButton(new Vector2D(30, 25),
                                       new Vector2D(40, 40))){
      if(this.showTooltip){
        this.showTooltip = false;
      }
      else {
        this.showTooltip = true;
      }
      this.tooltip.changeColour();
      this.info.changeColour();
    }
    // Start game
    else if(this.checkMousePressButton(new Vector2D(500, 30),
                                       new Vector2D(100, 50))){
      this.start.changeColour();
      this.currentScreen = GAMESCREEN.GAME;
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

  private boolean checkMousePressStar(Vector2D location, Label label){
    if(this.mousePressed && !this.swapLabel(label)){
      if(Vector2D.sub(new Vector2D(this.sketch.mouseX, this.sketch.mouseY),
                      location)
                 .mag() < 10){
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
    Vector2D dimensions = new Vector2D(25, 60);
    int nPoints = 5;
    this.star1 = new Star(this.sketch, this.location1, dimensions, nPoints);
    this.star2 = new Star(this.sketch, this.location2, dimensions, nPoints);
    this.star3 = new Star(this.sketch, this.location3, dimensions, nPoints);
    this.star4 = new Star(this.sketch, this.location4, dimensions, nPoints);
    this.star5 = new Star(this.sketch, this.location5, dimensions, nPoints);

    this.allStars = new ArrayList<Star>();
    this.allStars.add(this.star1);
    this.allStars.add(this.star2);
    this.allStars.add(this.star3);
    this.allStars.add(this.star4);
    this.allStars.add(this.star5);
  }  

  private void checkForColourChanges(){
    if (this.star1.getColourR() == 255) {
      this.star1.changeColour();
    }
    if (this.star2.getColourR() == 255) {
      this.star2.changeColour();
    }
    if (this.star3.getColourR() == 255) {
      this.star3.changeColour();
    }
    if (star4.getColourR() == 255) {
      star4.changeColour();
    }
    if (this.star5.getColourR() == 255) {
      this.star5.changeColour();
    }
  }

  private void updateStars(){
    this.star1.update();
    this.star2.update();
    this.star3.update();
    this.star4.update();
    this.star5.update();
  }

  //=========================================================================//
  // Swap methods                                                            //
  //=========================================================================//
  public void swapButton1() {
    if (this.buttonToSwap == null) {
      this.buttonToSwap = this.transition1.getLabelString();
      this.transition1.changeColour();
      buttons[0] = true;
    }
    else {
      for (int i=0; i <=4; i++) {
        if (buttons[1] == true) {
          this.transition2.changeLabel(this.transition1.getLabelString());
          this.transition1.changeLabel(this.buttonToSwap);
          this.transition2.changeColour();
          buttons[1] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[2] == true) {
          this.transition3.changeLabel(this.transition1.getLabelString());
          this.transition1.changeLabel(this.buttonToSwap);
          this.transition3.changeColour();
          buttons[2] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[3] == true) {
          this.transition4.changeLabel(this.transition1.getLabelString());
          this.transition1.changeLabel(this.buttonToSwap);
          this.transition4.changeColour();
          buttons[3] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[4] == true) {
          this.transition5.changeLabel(this.transition1.getLabelString());
          this.transition1.changeLabel(this.buttonToSwap);
          this.transition5.changeColour();
          buttons[4] = false;
          this.buttonToSwap = null;
        }
      }
    }
  }

  public void swapButton2() {
    if (this.buttonToSwap == null) {
      this.buttonToSwap = this.transition2.getLabelString();
      this.transition2.changeColour();
      buttons[1] = true;
      System.out.println(this.buttonToSwap);
    }
    else {
      for (int i=0; i <=4; i++) {
        if (buttons[0] == true) {
          this.transition1.changeLabel(this.transition2.getLabelString());
          this.transition2.changeLabel(this.buttonToSwap);
          this.transition1.changeColour();
          buttons[0] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[2] == true) {
          this.transition3.changeLabel(this.transition2.getLabelString());
          this.transition2.changeLabel(this.buttonToSwap);
          this.transition3.changeColour();
          buttons[2] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[3] == true) {
          this.transition4.changeLabel(this.transition2.getLabelString());
          this.transition2.changeLabel(this.buttonToSwap);
          this.transition4.changeColour();
          buttons[3] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[4] == true) {
          this.transition5.changeLabel(this.transition2.getLabelString());
          this.transition2.changeLabel(this.buttonToSwap);
          this.transition5.changeColour();
          buttons[4] = false;
          this.buttonToSwap = null;
        }
      }
    }
  }

  public void swapButton3() {
    if (this.buttonToSwap == null) {
      this.buttonToSwap = this.transition3.getLabelString();
      this.transition3.changeColour();
      buttons[2] = true;
    }
    else {
      for (int i=0; i <=4; i++) {
        if (buttons[0] == true) {
          this.transition1.changeLabel(this.transition3.getLabelString());
          this.transition3.changeLabel(this.buttonToSwap);
          this.transition1.changeColour();
          buttons[0] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[1] == true) {
          this.transition2.changeLabel(this.transition3.getLabelString());
          this.transition3.changeLabel(this.buttonToSwap);
          this.transition2.changeColour();
          buttons[1] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[3] == true) {
          this.transition4.changeLabel(this.transition3.getLabelString());
          this.transition3.changeLabel(this.buttonToSwap);
          this.transition4.changeColour();
          buttons[3] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[4] == true) {
          this.transition5.changeLabel(this.transition3.getLabelString());
          this.transition3.changeLabel(this.buttonToSwap);
          this.transition5.changeColour();
          buttons[4] = false;
          this.buttonToSwap = null;
        }
      }
    }
  }

  public void swapButton4() {
    if (this.buttonToSwap == null) {
      this.buttonToSwap = this.transition4.getLabelString();
      this.transition4.changeColour();
      buttons[3] = true;
      System.out.println(this.buttonToSwap);
    }
    else {
      for (int i=0; i <=4; i++) {
        if (buttons[0] == true) {
          this.transition1.changeLabel(this.transition4.getLabelString());
          this.transition4.changeLabel(this.buttonToSwap);
          this.transition1.changeColour();
          buttons[0] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[1] == true) {
          this.transition2.changeLabel(this.transition4.getLabelString());
          this.transition4.changeLabel(this.buttonToSwap);
          this.transition2.changeColour();
          buttons[1] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[2] == true) {
          this.transition3.changeLabel(this.transition4.getLabelString());
          this.transition4.changeLabel(this.buttonToSwap);
          this.transition3.changeColour();
          buttons[2] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[4] == true) {
          this.transition5.changeLabel(this.transition4.getLabelString());
          this.transition4.changeLabel(this.buttonToSwap);
          this.transition5.changeColour();
          buttons[4] = false;
          this.buttonToSwap = null;
        }
      }
    }
  }

  public void swapButton5() {
    if (this.buttonToSwap == null) {
      this.buttonToSwap = this.transition5.getLabelString();
      this.transition5.changeColour();
      buttons[4] = true;
    }
    else {
      for (int i=0; i <=4; i++) {
        if (buttons[0] == true) {
          this.transition1.changeLabel(this.transition5.getLabelString());
          this.transition5.changeLabel(this.buttonToSwap);
          this.transition1.changeColour();
          buttons[0] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[1] == true) {
          this.transition2.changeLabel(this.transition5.getLabelString());
          this.transition5.changeLabel(this.buttonToSwap);
          this.transition2.changeColour();
          buttons[1] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[2] == true) {
          this.transition3.changeLabel(this.transition5.getLabelString());
          this.transition5.changeLabel(this.buttonToSwap);
          this.transition3.changeColour();
          buttons[2] = false;
          this.buttonToSwap = null;
        }
        else if (buttons[3] == true) {
          this.transition4.changeLabel(this.transition5.getLabelString());
          this.transition5.changeLabel(this.buttonToSwap);
          this.transition4.changeColour();
          buttons[3] = false;
          this.buttonToSwap = null;
        }
      }
    }
  }

  public boolean swapLabel(Label oldLabel) {
    if (lab[0] == null) {
      lab[0] = oldLabel;
      return false;
    }
    else if (lab[1] == null && 
            lab[0].getLabelString() != oldLabel.getLabelString()) {
      lab[1] = oldLabel;
      System.out.println("hey1 " + lab[0].getLabelString());
      System.out.println("hey2 " + lab[1].getLabelString());

      this.swapAction();
      // reset
      lab[0] = null;
      lab[1] = null;
      return true;
    }
    return false;
  }

  public void swapAction() {
    String temp;
    for (Label i : allLabels) {
      if (i.getLabelString() == lab[0].getLabelString()) {
        for (Label j : allLabels) {
          if (j.getLabelString() == lab[1].getLabelString()) {
            temp = i.getLabelString();
            i.changeLabel(j.getLabelString());
            j.changeLabel(temp);
            return;
          }
        }
      }
    }
  }
}