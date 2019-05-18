package swarm_wars_library.sound;

import processing.sound.SoundFile;
import processing.core.PApplet;

public class PlayBackgroundMusic{
  private SoundFile soundFile;
  
  public PlayBackgroundMusic(PApplet sketch){
    this.soundFile = new SoundFile(sketch, 
      "resources/sound/Envision.aif");
    this.soundFile.loop();
  }
}