package swarm_wars_library.fsm_ui;

import java.util.ArrayList;
import processing.core.PApplet;
import swarm_wars_library.fsm.FSMSTATE;
import swarm_wars_library.physics.Vector2D;


public class FSMOption1 {

    private PApplet sketch;

    //Buttons
    private FSMBackground FSM1;
    private FSMBackground information;
    private FSMBackground content;
    private FSMBackground chooseOption1;
    private Button transition1;
    private Button transition2;
    private Button transition3;
    private boolean showContent;
    private boolean selected;


    //SIZING
    private int boxWidth;
    private int boxHeight;
    private int boxX;
    private int boxY;

    //STAR SET UP
    private Star star1;
    private Star star2;
    private Star star3;

    //LABELS FROM STARS
    private Label label1;
    private Label label2;
    private Label label3;

    //ARROW SET UP
    private Arrow arrow1;
    private Arrow arrow2;
    private Arrow arrow3;

    //FSM POSITIONS
    private Vector2D location1;
    private Vector2D location2;
    private Vector2D location3;

    //SWAP VARIABLES
    private String buttonToSwap;
    private boolean buttons[] = {false, false, false};
    private boolean mousePressed = false;

    // Order
    private ArrayList<FSMSTATE> orderFSMStates = new ArrayList<FSMSTATE>();

    //=========================================================================//
    // Constructor                                                             //
    //=========================================================================//
    public FSMOption1(PApplet sketch, int boxWidth, int boxHeight, int boxX, 
        int boxY){

        this.sketch = sketch;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.boxX = boxX;
        this.boxY = boxY;

        this.setupLocations();
        this.setupArrows();
        this.setupButtons();
        this.setupStars();
        this.setupLabels();
        this.setOrderFSMStates();
    }

    //=========================================================================//
    // Update method                                                           //
    //=========================================================================//
    public void update(){
        this.updateArrows();
        this.updateButtons();
        this.updateStars();
        this.updateLabels();
        this.updateMousePressButton();
    }

    //=========================================================================//
    // FSM Order methods                                                       //
    //=========================================================================//
    public void setOrderFSMStates(){
        this.orderFSMStates.add(0, FSMSTATE.DEFEND);
        this.orderFSMStates.add(1, FSMSTATE.SCOUT);
        this.orderFSMStates.add(2, FSMSTATE.SPECIAL);
    }

    public ArrayList<FSMSTATE> getOrderFSMStates(){
        return this.orderFSMStates;
    }

    public void swapOrderFSMStates(int i, int j){
        FSMSTATE temp = this.orderFSMStates.get(i - 1);
        this.orderFSMStates.set(i - 1, this.orderFSMStates.get(j - 1));
        this.orderFSMStates.set(j - 1, temp);
    }

    //=========================================================================//
    // Button methods                                                          //
    //=========================================================================//
    private void setupButtons() {

        this.FSM1 = new FSMBackground(this.sketch,
                "",
                new Vector2D(boxX, boxY),
                new Vector2D(boxWidth, boxHeight),
                0, 0, 0, 90);

        this.information = new FSMBackground(this.sketch,
                "ASYCLICAL FINITE STATE MACHINE ->",
                new Vector2D(boxX, boxY + boxHeight + 20),
                new Vector2D(boxWidth, 50),
                0, 0, 0, 90);

        this.chooseOption1 = new FSMBackground(this.sketch,
            "SELECT ACYCLICAL FSM",
            new Vector2D(boxX, boxY - 50 - 20),
            new Vector2D(boxWidth, 50), 
            0, 0, 0, 90);

        //transitions
        Vector2D transitionDimensions = new Vector2D(70, 50);
        this.transition1  = new Button (
                this.sketch,
                "Enemy distance\nmore than\n300 stars away",
                Vector2D.add(this.getMidPoint(this.location1, this.location2),
                        new Vector2D(-60, -27)),
                transitionDimensions);
        this.transition2  = new Button (
                this.sketch,
                "Enemy health\nless than\n 30 points",
                Vector2D.add(this.getMidPoint(this.location2, this.location3),
                        new Vector2D(-30, -27)),
                transitionDimensions);
        this.transition3  = new Button (
                this.sketch,
                "Your health\nless than\n30 points",
                Vector2D.add(this.getMidPoint(this.location3, this.location1),
                        new Vector2D(-30, -27)),
                transitionDimensions);
    }

    private void updateButtons() {
        this.FSM1.update();
        this.information.update();
        this.chooseOption1.update();
        if (this.selected == true) {
            this.chooseOption1.changeColour(255, 255, 255, 80);
        }
        if (this.selected == false) {
            this.chooseOption1.changeColour(0, 0, 0, 80);
        }
        this.sketch.textSize(9);
        this.transition1.update();
        this.transition2.update();
        this.transition3.update();
        this.sketch.textSize(13);
    }

    private Vector2D getMidPoint(Vector2D location1, Vector2D location2){
        return new Vector2D((location1.getX() + location2.getX()) / 2,
                (location1.getY() + location2.getY()) / 2);
    }

    //=========================================================================//
    // Arrow methods                                                           //
    //=========================================================================//
    private void setupArrows(){
        this.arrow1 = new Arrow(this.sketch,
                this.location1,
                Vector2D.add(this.location2,
                        new Vector2D(0, -50)));

        this.arrow2 = new Arrow(this.sketch,
                this.location2,
                Vector2D.add(this.location3,
                        new Vector2D(-50, 0)));
        this.arrow3 = new Arrow(this.sketch,
                this.location3,
                Vector2D.add(this.location1,
                        new Vector2D(15, 50)));

    }

    private void updateArrows() {
        this.arrow1.update();
        this.arrow2.update();
        this.arrow3.update();
    }

    //=========================================================================//
    // Star methods                                                            //
    //=========================================================================//
    private void setupStars(){
        Vector2D dimensions = new Vector2D(25, 55);
        int nPoints = 5;
        this.star1 = new Star(this.sketch, 
                            this.location1, 
                            dimensions, 
                            nPoints, 
                            65, 136, 65);
        this.star2 = new Star(this.sketch, 
                            this.location2, 
                            dimensions, 
                            nPoints, 
                            241, 189, 0);
        this.star3 = new Star(this.sketch, 
                            this.location3, 
                            dimensions, 
                            nPoints, 
                            252, 74, 85);
    }


    private void updateStars(){
        this.star1.update();
        this.star2.update();
        this.star3.update();
    }

    //=========================================================================//
    // Locations methods                                                       //
    //=========================================================================//
    private void setupLocations(){
        this.location1 = new Vector2D(boxWidth/2+boxX,
                boxHeight/4+boxY);
        this.location2 = new Vector2D(boxWidth/4+boxX,
                boxHeight/4*3+boxY);
        this.location3 = new Vector2D(boxWidth/4*3+boxX,
                boxHeight/4*3+boxY);
    }

    //=========================================================================//
    // Labels methods                                                          //
    //=========================================================================//
    private void setupLabels(){
        int labelColour = 0;

        //LABELS TO PUT ON STARS
        this.label1 = new Label(this.sketch, labelColour, "Defend",
                this.location1);
        this.label2 = new Label(this.sketch, labelColour, "Scout",
                this.location2);
        this.label3 = new Label(this.sketch, labelColour, "Special",
                this.location3);

    }

    private void updateLabels(){
        this.label1.update();
        this.label2.update();
        this.label3.update();
    }


    //=========================================================================//
    // Mouse methods                                                           //
    //=========================================================================//

    public void updateMousePressButton(){
        // Swap button1
        if(this.checkMousePressStar(this.location1, 45) == true) {
            this.swapButton1();
        }
        // Swap button2
        else if(this.checkMousePressStar(this.location2, 45) == true) {
            this.swapButton2();
        }
        // Swap button3
        else if(this.checkMousePressStar(this.location3, 45) == true) {
            this.swapButton3();
        }
        else if (checkMousePressButton(new Vector2D(boxX, boxY + boxHeight + 20),
                new Vector2D(boxWidth, 50)) == true) {
                this.information = new FSMBackground(this.sketch,
                        "ASYCLICAL FINITE STATE MACHINE\n\n" +
                                "One of the most common finite state machine you would have encountered\n, is " +
                                "a traffic light. There are three states: red, orange and green\n. The lights change "+
                                "under certain conditions\n. This finite state machine is acylical as it goes in\n" +
                                "a circle, always in the same direction.",
                        new Vector2D(boxX, boxY + boxHeight + 20),
                        new Vector2D(boxWidth, 190),
                        0, 0, 0, 90);
        }
        else if (checkMousePressButton(new Vector2D(boxX, boxY + boxHeight + 20 + 50),
                new Vector2D(this.boxWidth, 200)) == true) {
            this.information = new FSMBackground(this.sketch,
                    "ASYCLICAL FINITE STATE MACHINE ->",
                    new Vector2D(boxX, boxY + boxHeight + 20),
                    new Vector2D(boxWidth, 50),
                    0, 0, 0, 90);
        }
    }


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
    // Swap methods                                                            //
    //=========================================================================//
    public void swapButton1() {
        if (this.buttonToSwap == null) {
            this.buttonToSwap = this.label1.getLabelString();
            buttons[0] = true;
        }
        else {
            for (int i=0; i <=2; i++) {
                if (buttons[1] == true) {
                    this.label2.changeLabel(this.label1.getLabelString());
                    this.star2.changeColourSwap(this.label1.getLabelString());
                    this.label1.changeLabel(this.buttonToSwap);
                    this.star1.changeColourSwap(this.buttonToSwap);
                    buttons[1] = false;
                    this.buttonToSwap = null;
                    this.swapOrderFSMStates(1, 2);
                }
                else if (buttons[2] == true) {
                    this.label3.changeLabel(this.label1.getLabelString());
                    this.star3.changeColourSwap(this.label1.getLabelString());
                    this.label1.changeLabel(this.buttonToSwap);
                    this.star1.changeColourSwap(this.buttonToSwap);
                    buttons[2] = false;
                    this.buttonToSwap = null;
                    this.swapOrderFSMStates(1, 3);
                }
            }
        }
    }

    public void swapButton2() {
        if (this.buttonToSwap == null) {
            this.buttonToSwap = this.label2.getLabelString();
            buttons[1] = true;
        }
        else {
            for (int i=0; i <=2; i++) {
                if (buttons[0] == true) {
                    this.label1.changeLabel(this.label2.getLabelString());
                    this.star1.changeColourSwap(this.label2.getLabelString());
                    this.label2.changeLabel(this.buttonToSwap);
                    this.star2.changeColourSwap(this.buttonToSwap);
                    buttons[0] = false;
                    this.buttonToSwap = null;
                    this.swapOrderFSMStates(2, 1);
                }
                else if (buttons[2] == true) {
                    this.label3.changeLabel(this.label2.getLabelString());
                    this.star3.changeColourSwap(this.label2.getLabelString());
                    this.label2.changeLabel(this.buttonToSwap);
                    this.star2.changeColourSwap(this.buttonToSwap);
                    buttons[2] = false;
                    this.buttonToSwap = null;
                    this.swapOrderFSMStates(2, 3);
                }
            }
        }
    }

    public void swapButton3() {
        if (this.buttonToSwap == null) {
            this.buttonToSwap = this.label3.getLabelString();
            buttons[2] = true;
        }
        else {
            for (int i=0; i <=2; i++) {
                if (buttons[0] == true) {
                    this.label1.changeLabel(this.label3.getLabelString());
                    this.star1.changeColourSwap(this.label3.getLabelString());
                    this.label3.changeLabel(this.buttonToSwap);
                    this.star3.changeColourSwap(this.buttonToSwap);
                    buttons[0] = false;
                    this.buttonToSwap = null;
                    this.swapOrderFSMStates(3, 1);
                }
                else if (buttons[1] == true) {
                    this.label2.changeLabel(this.label3.getLabelString());
                    this.star2.changeColourSwap(this.label3.getLabelString());
                    this.label3.changeLabel(this.buttonToSwap);
                    this.star3.changeColourSwap(this.buttonToSwap);
                    buttons[1] = false;
                    this.buttonToSwap = null;
                    this.swapOrderFSMStates(3, 2);
                }
            }
        }
    }

    //=========================================================================//
    // Getter & Setter methods                                                 //
    //=========================================================================//


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isShowContent() {
        return showContent;
    }

    public void setShowContent(boolean showContent) {
        this.showContent = showContent;
    }
}
