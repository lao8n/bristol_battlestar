package swarm_wars_library.fsm_ui;

import java.util.ArrayList;
import processing.core.PApplet;
import swarm_wars_library.fsm.FSMSTATE;
import swarm_wars_library.physics.Vector2D;


public class FSMOption3 {

    private PApplet sketch;

    private FSMBackground FSM3;
    private FSMBackground information;
    private FSMBackground content;
    private FSMBackground chooseOption3;
    private boolean showContent;
    private boolean selected;


    //SIZING
    private int boxWidth;
    private int boxHeight;
    private int boxX;
    private int boxY;
    //private Vector2D buttonLocation;

    //STAR SET UP
    private Star star1;
    private Star star2;
    private Star star3;
    private Star star4;

    //LABELS FROM STARS
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;

    //ARROW SET UP
    private Arrow arrow1;
    private Arrow arrow2;
    private Arrow arrow3;
    private Arrow arrow4;
    private Arrow arrow5;
    private Arrow arrow6;
    private Arrow arrow7;
    private Arrow arrow8;

    //FSM POSITIONS
    private Vector2D location1;
    private Vector2D location2;
    private Vector2D location3;
    private Vector2D location4;

    private Vector2D location1A;
    private Vector2D location2A;
    private Vector2D location1B;
    private Vector2D location2B;

    private Vector2D location3A;
    private Vector2D location4A;
    private Vector2D location3B;
    private Vector2D location4B;

    private Vector2D location2C;
    private Vector2D location3C;
    private Vector2D location2D;
    private Vector2D location3D;

    private Vector2D location4C;
    private Vector2D location1C;
    private Vector2D location4D;
    private Vector2D location1D;


    //SWAP VARIABLES
    private String buttonToSwap;
    private boolean buttons[] = {false, false, false, false};
    private boolean mousePressed = false;

    // Order
    private ArrayList<FSMSTATE> orderFSMStates = new ArrayList<FSMSTATE>();

    //=========================================================================//
    // Constructor                                                             //
    //=========================================================================//
    public FSMOption3(PApplet sketch, int boxWidth, int boxHeight, int boxX, 
        int boxY){
        this.sketch = sketch;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.boxX = boxX;
        this.boxY = boxY;

        this.setupLocations();
        this.setupButtons();
        this.setupArrows();
        this.setupStars();
        this.setupLabels();
        this.setOrderFSMStates();
    }

    //=========================================================================//
    // Update method                                                           //
    //=========================================================================//
    public void update(){
        this.updateButtons();
        this.updateArrows();
        this.updateStars();
        this.updateLabels();
        this.updateMousePressButton();
    }

    //=========================================================================//
    // FSM Order methods                                                       //
    //=========================================================================//
    public void setOrderFSMStates(){
        this.orderFSMStates.add(0, FSMSTATE.SPECIAL);
        this.orderFSMStates.add(1, FSMSTATE.DEFEND);
        this.orderFSMStates.add(2, FSMSTATE.SPECIAL);
        this.orderFSMStates.add(3, FSMSTATE.SCOUT);
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
        //INFO RELATED BUTTONS
        this.FSM3 = new FSMBackground(this.sketch,
                "",
                new Vector2D(boxX, boxY),
                new Vector2D(boxHeight, boxHeight),
                0, 0, 0, 90);

        this.information = new FSMBackground(this.sketch,
                "CYCLICAL FINITE STATE MACHINE ->",
                new Vector2D(boxX, boxY + boxHeight + 20),
                new Vector2D(boxWidth, 50),
                0, 0, 0, 90);
      /*  this.content = new FSMBackground(this.sketch,
                "The number of states in a finite state machine does not\n necessarily define " +
                        "how you transition from one state to another\n. You can build and invent any transitions " +
                        "that you like\n. Here, the transitions are bidirectional and cyclical.",
                new Vector2D(boxX, boxY + boxHeight + 20),
                new Vector2D(boxWidth, 200),
                0, 0, 0, 90); */
        this.chooseOption3 = new FSMBackground(this.sketch,
            "SELECT CYCLICAL FSM",
            new Vector2D(boxX, boxY - 50 - 20),
            new Vector2D(boxWidth, 50), 
            0, 0, 0, 90);
    }

    private void updateButtons() {
        this.FSM3.update();
        this.information.update();
        this.chooseOption3.update();
        if (this.showContent == true) {
            this.content.update();
        }
        if (this.selected == true) {
            this.chooseOption3.changeColour(255, 255, 255, 80);
        }
        if (this.selected == false) {
            this.chooseOption3.changeColour(0, 0, 0, 80);
        }
    }

    //=========================================================================//
    // Arrow methods                                                           //
    //=========================================================================//
    private void setupArrows(){
       this.arrow1 = new Arrow(this.sketch,
                this.location1A,
                Vector2D.add(this.location2A,
                        new Vector2D(0, 0)));
       this.arrow2 = new Arrow(this.sketch,
                this.location2B,
                Vector2D.add(this.location1B,
                        new Vector2D(0, 0)));

       this.arrow3 = new Arrow(this.sketch,
                this.location2C,
                Vector2D.add(this.location3C,
                        new Vector2D(0, 0)));
        this.arrow4 = new Arrow(this.sketch,
                this.location3D,
                Vector2D.add(this.location2D,
                        new Vector2D(0, 0)));

        this.arrow5 = new Arrow(this.sketch,
                this.location3A,
                Vector2D.add(this.location4A,
                        new Vector2D(0, 0)));
        this.arrow6 = new Arrow(this.sketch,
                this.location4B,
                Vector2D.add(this.location3B,
                        new Vector2D(0, 0)));

        this.arrow7 = new Arrow(this.sketch,
                this.location4C,
                Vector2D.add(this.location1C,
                        new Vector2D(0, 0)));
        this.arrow8 = new Arrow(this.sketch,
                this.location1D,
                Vector2D.add(this.location4D,
                        new Vector2D(0, 0)));
    }

    private void updateArrows() {
        this.arrow1.update();
        this.arrow2.update();
        this.arrow3.update();
        this.arrow4.update();
        this.arrow5.update();
        this.arrow6.update();
        this.arrow7.update();
        this.arrow8.update();
    }

    //=========================================================================//
    // Star methods                                                            //
    //=========================================================================//
    private void setupStars(){
        Vector2D dimensions = new Vector2D(20, 45);
        int nPoints = 5;
        this.star1 = new Star(this.sketch, 
                            this.location1, 
                            dimensions, 
                            nPoints, 
                            252, 74, 85);
        this.star2 = new Star(this.sketch, 
                            this.location2, 
                            dimensions, 
                            nPoints, 
                            65, 136, 65);
        this.star3 = new Star(this.sketch, 
                            this.location3, 
                            dimensions, 
                            nPoints, 
                            252, 74, 85);
        this.star4 = new Star(this.sketch, 
                            this.location4, 
                            dimensions, 
                            nPoints, 
                            241, 189, 0);
    }

    private void updateStars(){
        this.star1.update();
        this.star2.update();
        this.star3.update();
        this.star4.update();
    }

    //=========================================================================//
    // Locations methods                                                       //
    //=========================================================================//
    private void setupLocations(){
        this.location1 = new Vector2D(boxWidth/4+boxX,
                boxHeight/4+boxY);
        this.location2 = new Vector2D(boxWidth/4+boxX,
                boxHeight/4*3+boxY);
        this.location3 = new Vector2D(boxWidth/4*3+boxX,
                boxHeight/4*3+boxY);
        this.location4 = new Vector2D(boxWidth/4*3+boxX,
                boxHeight/4+boxY);


        //FROM 1 TO 2
        this.location1A = new Vector2D(boxWidth/4+boxX,
                (boxHeight/4+boxY) + 60);
        this.location2A = new Vector2D(boxWidth/4+boxX,
                boxHeight/4*3+boxY - 45);
        this.location1B = new Vector2D(boxWidth/4+boxX,
                (boxHeight/4+boxY) + 45);
        this.location2B = new Vector2D(boxWidth/4+boxX,
                boxHeight/4*3+boxY - 60);

        //FROM 3 TO 4
        this.location3A = new Vector2D(boxWidth/4*3+boxX,
                boxHeight/4*3+boxY - 60);
        this.location4A = new Vector2D(boxWidth/4*3+boxX,
                boxHeight/4+boxY + 45);
        this.location3B = new Vector2D(boxWidth/4*3+boxX,
                boxHeight/4*3+boxY - 45);
        this.location4B = new Vector2D(boxWidth/4*3+boxX,
                boxHeight/4+boxY + 60);

        //FROM 2 TO 3
        this.location2C = new Vector2D(boxWidth/4+boxX + 60,
                boxHeight/4*3+boxY);
        this.location3C = new Vector2D(boxWidth/4*3+boxX - 45,
                boxHeight/4*3+boxY);
        this.location2D = new Vector2D(boxWidth/4+boxX + 60,
                boxHeight/4*3+boxY);
        this.location3D = new Vector2D(boxWidth/4*3+boxX - 45,
                boxHeight/4*3+boxY);

        //FROM 4 TO 1
        this.location4C = new Vector2D(boxWidth/4*3+boxX - 45,
                boxHeight/4+boxY);
        this.location1C = new Vector2D(boxWidth/4+boxX + 60,
                boxHeight/4+boxY);
        this.location4D = new Vector2D(boxWidth/4*3+boxX - 45,
                boxHeight/4+boxY);
        this.location1D = new Vector2D(boxWidth/4+boxX + 60,
                boxHeight/4+boxY);

    }

    //=========================================================================//
    // Labels methods                                                          //
    //=========================================================================//
    private void setupLabels(){
        int labelColour = 0;

        //LABELS TO PUT ON STARS
        this.label1 = new Label(this.sketch, labelColour, "Special",
                this.location1);
        this.label2 = new Label(this.sketch, labelColour, "Defend",
                this.location2);
        this.label3 = new Label(this.sketch, labelColour, "Special",
                this.location3);
        this.label4 = new Label(this.sketch, labelColour, "Scout",
                this.location4);

    }

    private void updateLabels(){
        this.label1.update();
        this.label2.update();
        this.label3.update();
        this.label4.update();
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
        // Swap button4
        else if(this.checkMousePressStar(this.location4, 45) == true) {
            this.swapButton4();
        }
        else if (checkMousePressButton(new Vector2D(boxX, boxY + boxHeight + 20),
                new Vector2D(this.boxWidth, 50)) == true) {
                this.information = new FSMBackground(this.sketch,
                        "CYCLICAL FINITE STATE MACHINE\n\n" +
                                "The number of states in a finite state machine does not\n necessarily define " +
                                "how you transition from one state to another\n. You can build and invent any transitions " +
                                "that you like\n. Here, the transitions are bidirectional and cyclical.\n",
                        new Vector2D(boxX, boxY + boxHeight + 20),
                        new Vector2D(boxWidth, 190),
                        0, 0, 0, 90);
        }
       else if (checkMousePressButton(new Vector2D(boxX, boxY + boxHeight + 20),
                new Vector2D(this.boxWidth, 200)) == true) {
            this.information = new FSMBackground(this.sketch,
                    "CYCLICAL FINITE STATE MACHINE ->",
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
            for (int i=0; i <=3; i++) {
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
                else if (buttons[3] == true) {
                    this.label4.changeLabel(this.label1.getLabelString());
                    this.star4.changeColourSwap(this.label1.getLabelString());
                    this.label1.changeLabel(this.buttonToSwap);
                    this.star1.changeColourSwap(this.buttonToSwap);
                    buttons[3] = false;
                    this.buttonToSwap = null;
                    this.swapOrderFSMStates(1, 4);
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
            for (int i=0; i <=3; i++) {
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
                else if (buttons[3] == true) {
                    this.label4.changeLabel(this.label2.getLabelString());
                    this.star4.changeColourSwap(this.label2.getLabelString());
                    this.label2.changeLabel(this.buttonToSwap);
                    this.star2.changeColourSwap(this.buttonToSwap);
                    buttons[3] = false;
                    this.buttonToSwap = null;
                    this.swapOrderFSMStates(2, 4);
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
            for (int i=0; i <=3; i++) {
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
                else if (buttons[3] == true) {
                    this.label4.changeLabel(this.label3.getLabelString());
                    this.star4.changeColourSwap(this.label3.getLabelString());
                    this.label3.changeLabel(this.buttonToSwap);
                    this.star3.changeColourSwap(this.buttonToSwap);
                    buttons[3] = false;
                    this.buttonToSwap = null;
                    this.swapOrderFSMStates(3, 4);
                }
            }
        }
    }

    public void swapButton4() {
        if (this.buttonToSwap == null) {
            this.buttonToSwap = this.label4.getLabelString();
            buttons[3] = true;
        }
        else {
            for (int i=0; i <=3; i++) {
                if (buttons[0] == true) {
                    this.label1.changeLabel(this.label4.getLabelString());
                    this.star1.changeColourSwap(this.label4.getLabelString());
                    this.label4.changeLabel(this.buttonToSwap);
                    this.star4.changeColourSwap(this.buttonToSwap);
                    buttons[0] = false;
                    this.buttonToSwap = null;
                    this.swapOrderFSMStates(4, 1);
                }
                else if (buttons[1] == true) {
                    this.label2.changeLabel(this.label4.getLabelString());
                    this.star2.changeColourSwap(this.label4.getLabelString());
                    this.label4.changeLabel(this.buttonToSwap);
                    this.star4.changeColourSwap(this.buttonToSwap);
                    buttons[1] = false;
                    this.buttonToSwap = null;
                    this.swapOrderFSMStates(4, 2);
                }
                else if (buttons[2] == true) {
                    this.label3.changeLabel(this.label4.getLabelString());
                    this.star3.changeColourSwap(this.label4.getLabelString());
                    this.label4.changeLabel(this.buttonToSwap);
                    this.star4.changeColourSwap(this.buttonToSwap);
                    buttons[2] = false;
                    this.buttonToSwap = null;
                    this.swapOrderFSMStates(4, 3);
                }
            }
        }
    }
    //=========================================================================//
    // Getter & Setter methods for selected                                    //
    //=========================================================================//


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}


