RigidBody r;
RigidBody r2;
int moveForce = 10;


void setup() {
  
  smooth(4);
  frameRate(60);
  
  size(400, 400, JAVA2D);
  r = new RigidBody(10, 10);
  r.scale.x = 30; r.scale.y = 20;
}

void draw(){
  int i = 0;
  background(255);
  r.update();
  drawRect(r);

}

void drawRect(RigidBody r) {
  rectMode(CENTER);
  stroke(0);
  fill(100);
  rect((float)r.location.x, (float)r.location.y, (float)r.scale.x, (float)r.scale.y);
}

void keyPressed() {
  if(key == 'w' || key == 'W'){
    r.location.y += -1 * moveForce;
  }
  if(key == 'a' || key == 'A'){
    r.location.x += -1 * moveForce;
  }
  if(key == 's' || key == 'S'){
    r.location.y += 1 * moveForce;
  }
  if(key == 'd' || key == 'D'){
    r.location.x += 1 * moveForce;
  }
}