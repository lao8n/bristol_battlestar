package swarm_wars_library.swarm_select;

import java.util.ArrayList;
import org.javatuples.Pair;

import processing.core.PApplet;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.TOP;

import swarm_wars_library.fsm.FSMStateTransition;
import swarm_wars_library.fsm.FSMSTATE;
import swarm_wars_library.physics.Vector2D;

public class FSMVisualisation {
  PApplet sketch;
  Vector2D centreFSM;
  double radiusFSM;
  ArrayList<Vector2D> listPointsOnCircle;
  ArrayList<FSMStateTransition> collFSMStateTransition;
  FSMSTATE fsmState = FSMSTATE.DEFEND;

  //=========================================================================//
  // FSM Visualisation Constructor                                           //
  //=========================================================================//
  public FSMVisualisation(PApplet sketch, Vector2D locationFSMButton, 
    Vector2D dimFSMButton, 
    ArrayList<FSMStateTransition> collFSMStateTransition){

    this.sketch = sketch;
    this.collFSMStateTransition = collFSMStateTransition;
    this.centreFSM = Vector2D.add(locationFSMButton, 
                                  Vector2D.mult(dimFSMButton, 0.5));
    this.radiusFSM = Math.min(dimFSMButton.getX(), dimFSMButton.getY()) * 0.4;
    this.calculatePointsOnCircle();
  }

  //=========================================================================//
  //=========================================================================//
  public void update(){
    for(int i = 0; i < this.listPointsOnCircle.size(); i++){
      int r = 0;
      int g = 0;
      int b = 0;
      this.sketch.noStroke();
      switch(this.collFSMStateTransition.get(i).getFSMState()){
        case SPECIAL:
          r = 252;
          g = 74;
          b = 85;
          break;
        case DEFEND:
          r = 65;
          g = 136;
          b = 65;
          break;
        case SCOUT:
          r = 241;
          g = 189;
          b = 0;
          break;
        default:
          System.out.println("Aaahhh!");
      }
      this.sketch.fill(r, g, b);
      this.sketch.ellipse((float) this.listPointsOnCircle.get(i).getX(),
                          (float) this.listPointsOnCircle.get(i).getY(),
                          (float) 20,
                          (float) 20);
      this.sketch.fill(r, g, b, 40);
      this.sketch.ellipse((float) this.listPointsOnCircle.get(i).getX(),
                          (float) this.listPointsOnCircle.get(i).getY(),
                          (float) 20 + 2,
                          (float) 20 + 2);
      this.sketch.fill(r, g, b, 80);
      this.sketch.ellipse((float) this.listPointsOnCircle.get(i).getX(),
                          (float) this.listPointsOnCircle.get(i).getY(),
                          (float) 20 + 5,
                          (float) 20 + 5);
      this.sketch.noStroke();
      this.sketch.textSize(15);
      this.sketch.fill(255, 255, 255);
      this.sketch.textAlign(CENTER, TOP);
      if(this.listPointsOnCircle.get(i).getX() - this.centreFSM.getX() > 0){
        this.sketch.text(i + 1, 
        (float) this.listPointsOnCircle.get(i).getX() + 20,
        (float) this.listPointsOnCircle.get(i).getY());
      }
      else {
        this.sketch.text(i + 1, 
        (float) this.listPointsOnCircle.get(i).getX() - 20,
        (float) this.listPointsOnCircle.get(i).getY());
      }
    }
    this.drawArrows();
  }

  public Pair<FSMSTATE, Integer> checkMousePressedFSMState(int mouseX, 
    int mouseY){
    for(int i = 0; i < this.listPointsOnCircle.size(); i++){
      if(Vector2D.sub(this.listPointsOnCircle.get(i), 
                      new Vector2D(mouseX, mouseY)).mag() < 25){
        return new Pair<FSMSTATE, Integer>(
          this.collFSMStateTransition.get(i).getFSMState(), i + 1);
      }
    }
    return new Pair<FSMSTATE, Integer>(
      FSMSTATE.DEFEND, -1);  
  }

  //=========================================================================//
  // Helper functions                                                        //
  //=========================================================================//
  private void calculatePointsOnCircle(){
    int numStates = this.collFSMStateTransition.size();
    double angle = Math.PI * 2 / numStates;
    this.listPointsOnCircle = new ArrayList<Vector2D>();
    for(int i = 0; i < numStates; i++){
      Vector2D pointOnCircle = Vector2D.add(
        this.centreFSM, 
        new Vector2D(this.radiusFSM * Math.cos(angle * i), 
                     this.radiusFSM * Math.sin(angle * i)));
      this.listPointsOnCircle.add(pointOnCircle);
    }
  }

  private void drawArrows(){
    Arrow arrow = new Arrow(this.sketch);
    for(int i = 0; i < this.collFSMStateTransition.size(); i++){
      ArrayList<Integer> list = 
        this.collFSMStateTransition.get(i).getTransitions();
      for(int j = 0; j < list.size(); j++){
        Vector2D temp = Vector2D.mult(
          Vector2D.sub(this.listPointsOnCircle.get(list.get(j) - 1), 
                       this.listPointsOnCircle.get(i)), 0.8);
        arrow.update(Vector2D.sub(
                      this.listPointsOnCircle.get(list.get(j) - 1), temp),
                     Vector2D.add(this.listPointsOnCircle.get(i), temp));
      }
    }
  }

}
