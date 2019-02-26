package swarm_wars_library.engine;
import java.util.Timer;
import java.util.TimerTask;

/*need private List<Bullet> firedBullets = new ArrayList<Bullet>();
      public void fire(){
       Bullet bullet = new Bullet(playerX, playerY);
       firedbullets.add(bullet);
       */

public class Bullet extends Mover{
  private int bulletforce=3;
	private int count = 0;
	private Timer timer = new Timer();
	double PI=Math.PI;

  public Bullet(double x_,double y_,double heading_){
			super(new Vector2D(x_, y_));
			setScale(5,5);
		 	setHeading(heading_);
		 	setGOTag("BULLET");
			BulletTimer();
	}
	
  //not use timer
  public void BulletTimer(){
      //build a timer 
      timer.schedule(new TimerTask(){
        @Override
        public void run() {
						count++;
        }
		}, 0, 1000);
	}
	
	public int getTimer(){
		return count;
	}

	public void setBulletforce(int force){
		   bulletforce=force;
	}


	public void update(){

		double h=getHeading();
		if(h<0){
			h=h+2*PI;
		}
		 setLocationXY(getLocationX()+bulletforce*Math.cos(h),
		 getLocationY()+bulletforce*Math.sin(h));
	}
}