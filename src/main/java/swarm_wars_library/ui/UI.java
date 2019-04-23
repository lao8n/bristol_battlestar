package swarm_wars_library.ui;
import java.util.ArrayList;
PImage backgroundimg;

public class UI {


    int GameState = 0;

    //SCREEN SIZE
    int width = 700;
    int height = 900;

    //POSITIONS
    float positionx1 = width*0.5;
    float positiony1 = height*0.15;
    float positionx2 = width*0.9;
    float positiony2 = height*0.40;
    float positionx3 = width*0.1;
    float positiony3 = height*0.40;
    float positionx4 = width*0.25;
    float positiony4 = height*0.75;
    float positionx5 = width*0.75;
    float positiony5 = height*0.75;

    //BUTTONS START
    Button start  = new Button ("Start Game", 500, 30, 100, 40);

    //INFO RELATED BUTTONS
    Circle tooltip = new Circle(50, 45, 30, 30);
    Label questionMark = new Label(255, "?", 50, 45);
    String instructions = "WELCOME TO THE \nFINITE STATE MACHINE!\n\n" +
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
    Button info = new Button(instructions, 50, 45, 250, 300);
    boolean showTooltip = false;

    //BUTTON TRANSITIONS
    Button transition1  = new Button ("If enemy is\n" + "more than 2\n" +
            "star meter away",
            (positionx1+positionx2)/2-30,
            (positiony1+positiony2)/2-27,
            120, 55);
    Button transition2  = new Button ("If enemy is\n" + "more than 5\n" +
            "star meters away\n",
            (positionx2+positionx5)/2-60,
            (positiony2+positiony5)/2-27,
            120, 55);
    Button transition3  = new Button ("If fuel is\n" + "less than 1\n" +
            "gallons of stardust",
            (positionx4+positionx5)/2-60,
            positiony5-27,
            120, 55);
    Button transition4  = new Button ("If enemy is\n" + "less than 1\n" +
            "star meter away",
            (positionx3+positionx4)/2-60,
            (positiony3+positiony4)/2-27,
            120, 55);
    Button transition5  = new Button ("If enemy is\n" + "less than 2\n" +
            "star meter away",
            (positionx3+positionx1)/2-90,
            (positiony3+positiony1)/2-27,
            120, 55);

    //STARS
    Star star1 = new Star(0, 0, 25, 60, 5, positionx1, positiony1);
    Star star2 = new Star(0, 0, 25, 60, 5, positionx2, positiony2);
    Star star3 = new Star(0, 0, 25, 60, 5, positionx3, positiony3);
    Star star4 = new Star(0, 0, 25, 60, 5, positionx4, positiony4);
    Star star5 = new Star(0, 0, 25, 60, 5, positionx5, positiony5);

    //LABELS TO PUT ON STARS
    Label label1 = new Label(255, "attack", positionx1, positiony1);
    Label label2 = new Label(255, "orbit", positionx2, positiony2);
    Label label3 = new Label(255, "defence", positionx3, positiony3);
    Label label4 = new Label(255, "re-fuel", positionx4, positiony4);
    Label label5 = new Label(255, "seek", positionx5, positiony5);

    //ARROWS
    Arrow arrow1 = new Arrow(positionx1, positiony1, positionx2-25, positiony2-25);
    Arrow arrow2 = new Arrow(positionx2, positiony2, positionx5, positiony5-30);
    Arrow arrow3 = new Arrow(positionx3, positiony3, positionx1-25, positiony1);
    Arrow arrow4 = new Arrow(positionx4, positiony4, positionx3, positiony3+30);
    Arrow arrow5 = new Arrow(positionx5, positiony5, positionx4+30, positiony4);

    //VARIABLES FOR SWAPS
    ArrayList<Label> allLabels;
    ArrayList<Star> allStars;
    String labelToSwap = null;
    String buttonToSwap = null;
    boolean buttons[] = {false, false, false, false, false};
    Label lab[] = {null, null};


    void UIsetup() {
        size(700, 900);
        backgroundimg = loadImage("bluestars.jpg");

        allLabels = new ArrayList<Label>();
        allLabels.add(label1);
        allLabels.add(label2);
        allLabels.add(label3);
        allLabels.add(label4);
        allLabels.add(label5);

        allStars = new ArrayList<Star>();
        allStars.add(star1);
        allStars.add(star2);
        allStars.add(star3);
        allStars.add(star4);
        allStars.add(star5);
    }

    void Draw() {
        background(backgroundimg);

        //draws twickly backround stars
        for (int i = 0; i <= 10; i++) {
            fill(255, 255, 204);
            ellipse(random(width), random(height), 2, 2);
        }


        start.Draw();


        arrow1.Draw();
        arrow2.Draw();
        arrow3.Draw();
        arrow4.Draw();
        arrow5.Draw();

        transition1.Draw();
        transition2.Draw();
        transition3.Draw();
        transition4.Draw();
        transition5.Draw();

        star1.Draw();
        star2.Draw();
        star3.Draw();
        star4.Draw();
        star5.Draw();

        label1.Draw();
        label2.Draw();
        label3.Draw();
        label4.Draw();
        label5.Draw();

        tooltip.Draw();
        questionMark.Draw();
        if (showTooltip == true) {
            info.Draw();
        }
    }

    public void mouseEvents() {
        //LABEL SWAPS FOR STATES
        //label1 swap - stars
        if (mouseX >= positionx1- 10 && mouseX <=  positionx1+ 10 &&
                mouseY >= positiony1- 10 && mouseY <= positiony1+10) {
            if (swapLabel(label1) == false) {
                star1.changecolor();
            }
            else {
                checkForColourChanges();
            }

        }
        //label3 swap - stars
        else if (mouseX >= positionx3 - 10 && mouseX <=  positionx3+ 10 &&
                mouseY >= positiony3 - 10 && mouseY <= positiony3 + 10) {
            if (swapLabel(label3) == false) {
                star3.changecolor();
            }
            else {
                checkForColourChanges();
            }

        }
        //swapping label2 - stars
        else if (mouseX >= positionx2 - 10 && mouseX <=  positionx2 + 10 &&
                mouseY >= positiony2 -10 && mouseY <= positionx2 + 10) {
            if (swapLabel(label2) == false) {
                star2.changecolor();
            }
            else {
                checkForColourChanges();
            }
        }
        //swapping label4 - stars
        else if (mouseX >= positionx4 - 10 && mouseX <=  positionx4 + 10 &&
                mouseY >= positiony4 - 10 && mouseY <= positiony4 + 10) {

            if (swapLabel(label4) == false) {
                star4.changecolor();
            }
            else {
                checkForColourChanges();
            }
        }
        //swapping for label5 - stars
        else if (mouseX >= positionx5 - 10 && mouseX <=  positionx5 + 10 &&
                mouseY >= positiony5 - 10  && mouseY <= positiony5 + 10) {

            if (swapLabel(label5) == false) {
                star5.changecolor();
            }
            else {
                checkForColourChanges();
            }
        }


        //LABEL SWAPS FOR TRANSITIONS
        else if (mouseX >= (positionx1+positionx2)/2-30 &&
                mouseX <= (positionx1+positionx2)/2-30+120 &&
                mouseY >= (positiony1+positiony2)/2-27 &&
                mouseY <= (positiony1+positiony2)/2-27+55) {
            swapButton1();
        }
        else if (mouseX >= (positionx2+positionx5)/2-60 &&
                mouseX <= (positionx2+positionx5)/2-60+120 &&
                mouseY >= (positiony2+positiony5)/2-27 &&
                mouseY <= (positiony2+positiony5)/2-27+55) {
            swapButton2();
        }
        else if (mouseX >= (positionx4+positionx5)/2-60 &&
                mouseX <= (positionx4+positionx5)/2-60 +120 &&
                mouseY >= positiony5-27 &&
                mouseY <= positiony5-27 +55) {
            swapButton3();
        }
        else if (mouseX >= (positionx3+positionx4)/2-60 &&
                mouseX <=  (positionx3+positionx4)/2-60+120 &&
                mouseY >= (positiony3+positiony4)/2-27 &&
                mouseY <= (positiony3+positiony4)/2-27 +55) {
            swapButton4();
        }
        else if (mouseX >= (positionx3+positionx1)/2-90 &&
                mouseX <=  (positionx3+positionx1)/2-90+120 &&
                mouseY >= (positiony3+positiony1)/2-27 &&
                mouseY <= (positiony3+positiony1)/2-27+55) {
            swapButton5();
        }

        //START GAME
        else if (mouseX >= 500 && mouseX <= 500+100 &&
                mouseY >= 30 && mouseY <= 30+50) {
            GameState = 1;
            start.changecolor();
        }

        //TOOLTIP
        else if (mouseX >= 50 - 20 && mouseX <= 50 +20 &&
                mouseY >= 45 -20 && mouseY <= 45 + 20) {
            if (showTooltip == false) {
                showTooltip = true;
                tooltip.changecolor();
                info.changecolor();
            }
            else {
                showTooltip = false;
                tooltip.changecolor();
                info.changecolor();
            }
        }
      }
    }




    public void checkForColourChanges() {
        if (star1.ColourR == 255) {
            star1.changecolor();
        }
        if (star2.ColourR == 255) {
            star2.changecolor();
        }
        if (star3.ColourR == 255) {
            star3.changecolor();
        }
        if (star4.ColourR == 255) {
            star4.changecolor();
        }
        if (star5.ColourR == 255) {
            star5.changecolor();
        }
    }

    //swap button1
    public void swapButton1() {
        if (buttonToSwap == null) {
            buttonToSwap = transition1.label;
            transition1.changecolor();
            System.out.println(buttonToSwap);
            buttons[0] = true;
        }
        else {
            for (int i=0; i <=4; i++) {
                if (buttons[1] == true) {
                    transition2.changeLabel(transition1.label);
                    transition1.changeLabel(buttonToSwap);

                    transition2.changecolor();

                    buttons[1] = false;
                    buttonToSwap = null;

                }
                else if (buttons[2] == true) {
                    transition3.changeLabel(transition1.label);
                    transition1.changeLabel(buttonToSwap);
                    transition3.changecolor();
                    buttons[2] = false;
                    labelToSwap = null;
                }
                else if (buttons[3] == true) {
                    transition4.changeLabel(transition1.label);
                    transition1.changeLabel(buttonToSwap);
                    transition4.changecolor();
                    buttons[3] = false;
                    buttonToSwap = null;
                }
                else if (buttons[4] == true) {
                    transition5.changeLabel(transition1.label);
                    transition1.changeLabel(buttonToSwap);
                    transition5.changecolor();
                    buttons[4] = false;
                    buttonToSwap = null;
                }
            }
        }
    }


    public void swapButton3() {
        if (buttonToSwap == null) {
            buttonToSwap = transition3.label;
            transition3.changecolor();
            buttons[2] = true;
            System.out.println(buttonToSwap);
        }
        else {
            for (int i=0; i <=4; i++) {
                if (buttons[0] == true) {
                    System.out.print(buttonToSwap);
                    System.out.println(transition3.label);

                    transition1.changeLabel(transition3.label);
                    transition3.changeLabel(buttonToSwap);
                    transition1.changecolor();

                    buttons[0] = false;
                    buttonToSwap = null;
                }
                else if (buttons[1] == true) {
                    transition2.changeLabel(transition3.label);
                    transition3.changeLabel(buttonToSwap);
                    transition2.changecolor();
                    buttons[1] = false;
                    buttonToSwap = null;
                }
                else if (buttons[3] == true) {
                    transition4.changeLabel(transition3.label);
                    transition3.changeLabel(buttonToSwap);
                    transition4.changecolor();
                    buttons[3] = false;
                    buttonToSwap = null;
                }
                else if (buttons[4] == true) {
                    transition5.changeLabel(transition3.label);
                    transition3.changeLabel(buttonToSwap);
                    transition5.changecolor();
                    buttons[4] = false;
                    buttonToSwap = null;
                }
            }
        }
    }

    public void swapButton2() {
        if (buttonToSwap == null) {
            buttonToSwap = transition2.label;
            transition2.changecolor();
            buttons[1] = true;
            System.out.println(buttonToSwap);
        }
        else {
            for (int i=0; i <=4; i++) {
                if (buttons[0] == true) {
                    transition1.changeLabel(transition2.label);
                    transition2.changeLabel(buttonToSwap);
                    transition1.changecolor();

                    buttons[0] = false;
                    buttonToSwap = null;
                }
                else if (buttons[2] == true) {
                    transition3.changeLabel(transition2.label);
                    transition2.changeLabel(buttonToSwap);
                    transition3.changecolor();
                    buttons[2] = false;
                    buttonToSwap = null;
                }
                else if (buttons[3] == true) {
                    transition4.changeLabel(transition2.label);
                    transition2.changeLabel(buttonToSwap);
                    transition4.changecolor();
                    buttons[3] = false;
                    buttonToSwap = null;
                }
                else if (buttons[4] == true) {
                    transition5.changeLabel(transition2.label);
                    transition2.changeLabel(buttonToSwap);
                    transition5.changecolor();
                    buttons[4] = false;
                    buttonToSwap = null;
                }
            }
        }
    }
    public void swapButton4() {
        if (buttonToSwap == null) {
            buttonToSwap = transition4.label;
            transition4.changecolor();
            buttons[3] = true;
            System.out.println(buttonToSwap);
        }
        else {
            for (int i=0; i <=4; i++) {
                if (buttons[0] == true) {
                    transition1.changeLabel(transition4.label);
                    transition4.changeLabel(buttonToSwap);
                    transition1.changecolor();
                    buttons[0] = false;
                    buttonToSwap = null;
                }
                else if (buttons[1] == true) {
                    transition2.changeLabel(transition4.label);
                    transition4.changeLabel(buttonToSwap);
                    transition2.changecolor();
                    buttons[1] = false;
                    buttonToSwap = null;
                }
                else if (buttons[2] == true) {
                    transition3.changeLabel(transition4.label);
                    transition4.changeLabel(buttonToSwap);
                    transition3.changecolor();
                    buttons[2] = false;
                    buttonToSwap = null;
                }
                else if (buttons[4] == true) {
                    transition5.changeLabel(transition4.label);
                    transition4.changeLabel(buttonToSwap);
                    transition5.changecolor();
                    buttons[4] = false;
                    buttonToSwap = null;
                }
            }
        }
    }
    public void swapButton5() {
        if (buttonToSwap == null) {
            buttonToSwap = transition5.label;
            transition5.changecolor();
            buttons[4] = true;
            System.out.println(buttonToSwap);
        }
        else {
            for (int i=0; i <=4; i++) {
                if (buttons[0] == true) {
                    transition1.changeLabel(transition5.label);
                    transition5.changeLabel(buttonToSwap);
                    transition1.changecolor();
                    buttons[0] = false;
                    buttonToSwap = null;
                }
                else if (buttons[1] == true) {
                    transition2.changeLabel(transition5.label);
                    transition5.changeLabel(buttonToSwap);
                    transition2.changecolor();
                    buttons[1] = false;
                    buttonToSwap = null;
                }
                else if (buttons[2] == true) {
                    transition3.changeLabel(transition5.label);
                    transition5.changeLabel(buttonToSwap);
                    transition3.changecolor();
                    buttons[2] = false;
                    buttonToSwap = null;
                }
                else if (buttons[3] == true) {
                    transition4.changeLabel(transition5.label);
                    transition5.changeLabel(buttonToSwap);
                    transition4.changecolor();
                    buttons[3] = false;
                    buttonToSwap = null;
                }
            }
        }
    }

    public boolean swapLabel(Label oldLabel) {
        if (lab[0] == null) {
            lab[0] = oldLabel;
            return false;
        }
        else if (lab[1] == null && lab[0].label != oldLabel.label) {
            lab[1] = oldLabel;
            System.out.println("hey1 " + lab[0].label);
            System.out.println("hey2 " + lab[1].label);

            swapAction();
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
            if (i.label == lab[0].label) {
                for (Label j : allLabels) {
                    if (j.label == lab[1].label) {
                        temp = i.label;
                        i.changeLabel(j.label);
                        j.changeLabel(temp);
                        return;
                    }
                }
            }
        }
    }

    public String[] getTransitions() {

        String TransitionsOrder[] = {"", "", "", "", ""};
        TransitionsOrder[0] = transition1.label;
        TransitionsOrder[1] = transition2.label;
        TransitionsOrder[2] = transition3.label;
        TransitionsOrder[3] = transition4.label;
        TransitionsOrder[4] = transition5.label;
        return TransitionsOrder;
    }

    public String[] getOrders() {
        String StatesOrder[] = {"", "", "", "", ""};
        StatesOrder[0] = label1.label;
        StatesOrder[1] = label1.label;
        StatesOrder[2] = label1.label;
        StatesOrder[3] = label1.label;
        StatesOrder[4] = label1.label;
        return StatesOrder;
    }

    public int GameState() {
       return GameState;
    }

}