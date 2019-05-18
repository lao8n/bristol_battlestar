package swarm_wars_library.swarm_algorithms;

import java.util.ArrayList;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.Tag;
import swarm_wars_library.map.RandomGen;
import swarm_wars_library.physics.Transform;
import swarm_wars_library.physics.RigidBody;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.swarm_algorithms.AbstractSwarmAlgorithm;

public class DefendFlockSwarmAlgorithm extends AbstractSwarmAlgorithm {
  
  private int id;
  public RigidBody rb;
  public Transform transform;
  private DefendFlockSwarmRules defendFlockSwarmRules;
  private Vector2D separateV2D;
  private Vector2D alignV2D;
  private Vector2D coheseV2D;
  private Vector2D randomV2D;
  private Vector2D seekMotherShipV2D;
  private double weightSeparate = 0.05;
  private double weightAlign = 0.004;
  private double weightCohese = 0.003;
  private double weightRandom = 0.001;
  private double weightMotherShip = 0.1;
  private double weightAvoidEdge = 0.2;

  //=========================================================================//
  // Defend Flock Constructor                                                //
  //=========================================================================//
  public DefendFlockSwarmAlgorithm(ENTITY tag, int id, Transform transform, 
    RigidBody rb){
    super(tag, transform);
    this.id = id;
    this.rb = rb;
    this.transform = transform;
    this.defendFlockSwarmRules = new DefendFlockSwarmRules(this.id, 
                                                           this.rb,
                                                           this.transform);
  }

  //=========================================================================//
  // Swarm Algorithm                                                         //
  //=========================================================================//
  @Override 
  public void applySwarmAlgorithm(){
    // Get vectors from rules
    ArrayList<Vector2D> rulesV2D = this.defendFlockSwarmRules
                                       .iterateOverSwarm(this.tag);
    this.separateV2D = rulesV2D.get(0);
    this.alignV2D = rulesV2D.get(1);
    this.coheseV2D = rulesV2D.get(2);
    this.randomV2D = this.randomRule();
    this.seekMotherShipV2D = this.seekMotherShip();
    
    // Apply weights to vectors
    this.separateV2D.mult(this.weightSeparate);
    this.alignV2D.mult(this.weightAlign);
    this.coheseV2D.mult(this.weightCohese);
    this.randomV2D.mult(this.weightRandom);
    this.seekMotherShipV2D.mult(this.weightMotherShip);
    this.avoidEdge(this.weightAvoidEdge);

    // Apply forces
    this.rb.applyForce(this.separateV2D);
    this.rb.applyForce(this.alignV2D);
    this.rb.applyForce(this.coheseV2D);
    this.rb.applyForce(this.randomV2D);
    this.rb.applyForce(this.seekMotherShipV2D);
    this.transform.setHeading(this.rb.getVelocity().heading());
    this.rb.update(this.transform.getLocation(), this.transform.getHeading());  
  }

  //=========================================================================//
  // Misc Swarm Rules                                                        //
  //=========================================================================//
  @Override
  public Vector2D seekMotherShip(){
    Vector2D locationMotherShip = 
      CommsGlobal.get(Tag.getMotherShipTag(this.tag).toString())
                 .getPacket(0)
                 .getLocation();
    Vector2D target = Vector2D.sub(locationMotherShip, 
                                   transform.getLocation());
    target.normalise();
    target.mult(rb.getMaxSpeed());
    target.limit(rb.getMaxForce());
    return target;
  }

  private Vector2D randomRule(){
    return new Vector2D(RandomGen.getRand() - 0.5, RandomGen.getRand() - 0.5);
  }

  //=========================================================================//
  // Getters & setters for swarm weights                                     //
  //=========================================================================//
  public double getWeightSeparate(){
    return this.weightSeparate;
  }

  public void setWeightSeparate(double weight){
    this.weightSeparate = weight;
  }

  public double getWeightAlign(){
    return this.weightAlign;
  }

  public void setWeightAlign(double weight){
    this.weightAlign = weight;
  }

  public double getWeightCohese(){
    return this.weightCohese;
  }

  public void setWeightCohese(double weight){
    this.weightCohese = weight;
  }
}