package swarm_wars_library.sound;

import processing.sound.SoundFile;
import processing.core.PApplet;

public class SoundMixer{
  private SoundFile soundFile;
  private PApplet sketch; 
  
  public SoundMixer(PApplet sketch){
    this.sketch = sketch;

    soundFile = new SoundFile(sketch, "resources/sound/star_wars.aiff");
  }

  public static void playShip1Move(){
    //soundFile.play();
    //this.soundFile.loop();
  }
}