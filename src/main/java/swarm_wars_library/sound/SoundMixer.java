/* Class to handle all sounds in the game, with the exception fo the background music
All files must be .aif or .aiff 
*/

package swarm_wars_library.sound;

import processing.sound.SoundFile;
import processing.core.PApplet;

public class SoundMixer{
  private static SoundFile thruster;
  private static SoundFile player1Shoot; 
  private static SoundFile player2Shoot;
  private static SoundFile turretShoot;
  private static SoundFile shipExplode;
  private static SoundFile droneExplode;
  private static SoundFile explosion1; 
  //private PApplet sketch; 
  
  public SoundMixer(PApplet sketch){
    //this.sketch = sketch;

    thruster = new SoundFile(sketch, "resources/sound/rocketThrust.aif");
    player1Shoot = new SoundFile(sketch, "resources/sound/laser_3.aif");
    turretShoot = new SoundFile(sketch, "resources/sound/laser_2.aif");
    explosion1 = new SoundFile(sketch, "resources/sound/deep_laser.aif");

    // TODO set volume ratio for all sound files
  }

  public static void playPlayer1Shoot(){
    player1Shoot.play();
  }

  public static void turretShoot(){
    turretShoot.play();
  }

  public static void playThruster(){
    if (!thruster.isPlaying()){
      thruster.play();
      thruster.loop();
    }
  }

  public static void stopThruster(){
    thruster.pause();
  }

  public static void playShipExplosion(){
    explosion1.play();
  }
}