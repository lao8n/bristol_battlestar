Player p;
int moveForce = 10;


void setup() {  
  size(800, 800, JAVA2D);
  p = new Player(new Vector2D(width/2, height/2));
  p.scale.x = 30; p.scale.y = 20;
}

void draw(){
  int i = 0;
  background(255);

  p.update();

  p.display();

}



/* ------ EVENT LISTENERS ------ */

void keyPressed() {
  p.setMove(keyCode, true);
}

void keyReleased() {
  p.setMove(keyCode, false);
}

/* ------ PLAYER CLASS ------ */

class Player extends Mover {
  boolean moveLeft, moveRight, moveUp, moveDown;
  int moveForce = 10;

  Player(Vector2D location_) {
    super(location_);
  }

  void update(){
    /* I THINK WE SHOULD MOVE IN THE Y DIRECTION SLIGHTLY SLOWER THAN X - FOR ORTHO APPEARANCE */
    location.x += moveForce * (int(moveRight) - int(moveLeft));
    location.y += 0.8 * moveForce * (int(moveDown) - int(moveUp));

    rb.update(location, heading);
  }

  void display(){
    rectMode(CENTER);
    stroke(0);
    fill(100);
    rect((float)location.x, (float)location.y, (float)scale.x, (float)scale.y);
  }

  boolean setMove(int k, boolean b){
    switch(k) {
      case 'W':
      case 'w':
      case UP:
        return moveUp = b;
      case 'A':
      case 'a':
      case LEFT:
        return moveLeft = b;
      case 'S':
      case 's':
      case DOWN:
        return moveDown = b;
      case 'D':
      case 'd':
      case RIGHT:
        return moveRight = b;

      default:
        return b;
    }
  }
}