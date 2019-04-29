package swarm_wars_library.graphics;

import java.util.List;
import java.util.ArrayList;

import swarm_wars_library.physics.Vector2D;

public class ParticleExplosion{

  List<Particle> particles = new ArrayList<Particle>();

  ParticleExplosion(Vector2D objectMapLocation){
    int numParticles = 20;
    // Particle p = new Particle(0, 0);
    // Vector2D start = new Vector2D(0, 0);
  
    for (int i = 0; i < numParticles; i++){
      // Create particle in randomised circle around entity
      float startX = (float) (objectMapLocation.getX() + 
                            (-1 + 2 * (float) Math.random()));
      float startY = (float) (objectMapLocation.getY() + 
                            (-1 + 2 * (float) Math.random()));
      Vector2D start = new Vector2D(startX, startY);
      Particle p = new Particle(startX, startY);
  
      // Set force for each particle to move away from entity
      Vector2D temp = Vector2D.sub(start, objectMapLocation);
      p.setForce(temp);
  
      // Add to particle list
      this.particles.add(p);
    }
  }

  List<Particle> getParticleExplosion(){
    return this.particles;
  }
}
