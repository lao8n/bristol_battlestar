// package swarm_wars_library.fsm_ui;

// import java.util.Arrays;
// import java.util.ArrayList;

// import processing.core.PApplet;
// import processing.core.PImage;

// import swarm_wars_library.game_screens.GAMESCREEN;
// import swarm_wars_library.fsm.FSMCOMPARISON;
// import swarm_wars_library.fsm.FSMManager;
// import swarm_wars_library.fsm.FSMSTATE;
// import swarm_wars_library.fsm.FSMVARIABLE;
// import swarm_wars_library.physics.Vector2D;

// public class UI{

//   // Processing
//   private PApplet sketch;
//   private PImage backgroundImage;

//   // Game Screen
//   private GAMESCREEN currentScreen;

//   //FSM OPTIONS POSITIONS
//   private Vector2D location1;
//   private Vector2D location2;
//   private Vector2D location3;

//   //FSM Options
//   private FSMOption1 fsmOption1;
//   private FSMOption2 fsmOption2;
//   private FSMOption3 fsmOption3;

//   private boolean mousePressed;

//   private FSMBackground start;

//   //CHOICE
//   private int FSMselected = 1 ;

//   // FSM Manager Methods
//   private FSMManager fsmManager = FSMManager.getInstance();


//   //=========================================================================//
//   // Constructor                                                             //
//   //=========================================================================//
//   public UI(PApplet sketch){
//     this.sketch = sketch;
//     this.currentScreen = GAMESCREEN.FSMUI;
//     PImage background = sketch.loadImage(
//             "resources/images/background.png");
//     this.backgroundImage = background.get(0, 0, sketch.width, sketch.height);
//     this.setupButtons();
//     this.setupOptions();
//   }

//   //=========================================================================//
//   // Update method                                                           //
//   //=========================================================================//
//   public void update(){
//     this.updateBackground();
//     this.updateButtons();
//     this.updateOptions();
//     this.updateMousePressButton();
//   }

//   //=========================================================================//
//   // Game screen method                                                      //
//   //=========================================================================//
//   public GAMESCREEN getGameScreen(){
//     return this.currentScreen;
//   }

//   //=========================================================================//
//   // Background methods                                                      //
//   //=========================================================================//
//   private void updateBackground(){
//     this.sketch.image(this.backgroundImage, 0, 0,
//             this.sketch.width, this.sketch.height);

//     for(int i = 0; i <= 10; i++){
//       this.sketch.fill(255, 255, 204);
//       this.sketch.ellipse(this.sketch.random(this.sketch.width),
//               this.sketch.random(this.sketch.height),
//               2,
//               2);
//     }
//   }

//   public void setupOptions() {

//     int MapWidth = 1200;
//     int MapHeight = 800;

//     if (FSMselected == 1) {
//       //FSM 1
//       int boxWidthFSM1 = MapWidth/2;
//       int boxHeightFSM1 = MapWidth/2;
//       int boxXFSM1 = MapWidth/4;
//       int boxYFSM1 = MapHeight/8;

//       this.fsmOption1 = new FSMOption1(this.sketch, MapWidth, MapHeight,
//               boxWidthFSM1, boxHeightFSM1, boxXFSM1, boxYFSM1);
//     }
//     else if (FSMselected == 2) {
//       //FSM 2
//       int boxWidthFSM2 = MapWidth/2;
//       int boxHeightFSM2 = MapWidth/2;
//       int boxXFSM2 = MapWidth/4;
//       int boxYFSM2 = MapHeight/8;

//       this.fsmOption2 = new FSMOption2(this.sketch, MapWidth, MapHeight,
//               boxWidthFSM2, boxHeightFSM2, boxXFSM2, boxYFSM2);
//     }
//     else {
//       //FSM 3
//       int boxWidthFSM3 = MapWidth/2;
//       int boxHeightFSM3 = MapWidth/2;
//       int boxXFSM3 = MapWidth/4;
//       int boxYFSM3 = MapHeight/8;



//       this.fsmOption3 = new FSMOption3(this.sketch, MapWidth, MapHeight,
//               boxWidthFSM3, boxHeightFSM3, boxXFSM3, boxYFSM3);
//     }


//   }

//   public void updateOptions() {
//     if (FSMselected == 1) {
//       this.fsmOption1.update();
//     }
//     else if (FSMselected == 2) {
//       this.fsmOption2.update();
//     }
//     else {
//       this.fsmOption3.update();
//     }

//   }


//   //=========================================================================//
//   // Button methods                                                          //
//   //=========================================================================//
//   private void setupButtons() {
//     //INFO RELATED BUTTONS
//     int MapWidth = 1200;
//     int MapHeight = 800;

//     this.start = new FSMBackground(this.sketch,
//             "Next >",
//             new Vector2D(MapWidth - 150, 30),
//             new Vector2D(100, 50),
//             105, 105, 105);
//   }

//   private void updateButtons() {
//     this.start.update();
//   }

//   //=========================================================================//
//   // Mouse methods                                                           //
//   //=========================================================================//
//   private void updateMousePressButton() {
//     if (this.checkMousePressButton(new Vector2D(MapWidth - 150, 30),
//             new Vector2D(100, 50))) {
//      // this.exampleFSM();
//       this.currentScreen = GAMESCREEN.SWARMSELECT;
//     }
//   }


//   private boolean checkMousePressButton(Vector2D location,
//                                         Vector2D dimensions){
//     if(this.mousePressed){
//       if(this.sketch.mouseX >= location.getX() &&
//               this.sketch.mouseX <= location.getX() + dimensions.getX() &&
//               this.sketch.mouseY >= location.getY() &&
//               this.sketch.mouseY <= location.getY() + dimensions.getY()){
//         return true;
//       }
//     }
//     return false;
//   }

//   public void listenMousePressed(){
//     this.mousePressed = true;
//     System.out.println("Mouse Pressed true");
//     this.fsmOption1.listenMouseReleased();
//    /* if (FSMselected == 1) {
//       this.fsmOption1.listenMouseReleased();
//     }
//     else if (FSMselected == 2) {
//       this.fsmOption2.listenMouseReleased();
//     }
//     else {
//       this.fsmOption3.listenMouseReleased();
//     }*/
//   }

//   public void listenMouseReleased(){
//     this.mousePressed = false;
//     System.out.println("Mouse Pressed false");
//     this.fsmOption1.listenMouseReleased();
//    /* if (FSMselected == 1) {
//       this.fsmOption1.listenMouseReleased();
//     }
//     else if (FSMselected == 2) {
//       this.fsmOption2.listenMouseReleased();
//     }
//     else {
//       this.fsmOption3.listenMouseReleased();
//     }*/

//   }




//   //=========================================================================//
//   // FSM Manager methods                                                     //
//   //=========================================================================//
//   public void exampleFSM(int playerId){
//     // Add states first
//     this.fsmManager.addFSMState(playerId, 1, FSMSTATE.DEFEND);
//     this.fsmManager.addFSMState(playerId, 2, FSMSTATE.SCOUT);
//     this.fsmManager.addFSMState(playerId, 3, FSMSTATE.SPECIAL);
//     this.fsmManager.addFSMState(playerId, 4, FSMSTATE.DEFEND);
//     this.fsmManager.addFSMState(playerId, 5, FSMSTATE.SCOUT);

//     // Then add transitions after
//     this.fsmManager.addTransition(
//             playerId,
//             1,
//             2,
//             FSMVARIABLE.ENEMYDISTANCE,
//             FSMCOMPARISON.GREATERTHAN,
//             500);
//     this.fsmManager.addTransition(
//             playerId,
//             2,
//             1,
//             FSMVARIABLE.ENEMYDISTANCE,
//             FSMCOMPARISON.LESSTHAN,
//             300);
//     this.fsmManager.addTransition(
//             playerId,
//             3,
//             1,
//             FSMVARIABLE.ENEMYDISTANCE,
//             FSMCOMPARISON.LESSTHAN,
//             150);
//     this.fsmManager.addTransition(
//             playerId,
//             2,
//             3,
//             FSMVARIABLE.ENEMYDISTANCE,
//             FSMCOMPARISON.GREATERTHAN,
//             100);
//     this.fsmManager.addTransition(
//             playerId,
//             4,
//             2,
//             FSMVARIABLE.ENEMYDISTANCE,
//             FSMCOMPARISON.LESSTHAN,
//             200);
//     this.fsmManager.addTransition(
//             playerId,
//             3,
//             5,
//             FSMVARIABLE.ENEMYDISTANCE,
//             FSMCOMPARISON.GREATERTHAN,
//             100);
//     this.fsmManager.addTransition(
//             playerId,
//             5,
//             3,
//             FSMVARIABLE.ENEMYDISTANCE,
//             FSMCOMPARISON.LESSTHAN,
//             200);
//     this.fsmManager.addTransition(
//             playerId,
//             1,
//             4,
//             FSMVARIABLE.ENEMYDISTANCE,
//             FSMCOMPARISON.GREATERTHAN,
//             100);
//   }


// }
