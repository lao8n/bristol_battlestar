package swarm_wars_library.swarm_algorithms;

import java.util.ArrayList;

import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.map.RandomGen;
import swarm_wars_library.physics.Transform;
import swarm_wars_library.physics.RigidBody;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.swarm_algorithms.AbstractSwarmAlgorithm;

public class ScoutRandomSwarmAlgorithm extends AbstractSwarmAlgorithm {

  private int id;
  public RigidBody rb;
  public Transform transform;
  private ScoutRandomSwarmRules scoutRandomSwarmRules;
  private Vector2D separateV2D;
  private Vector2D randomV2D;
  private double weightSeparate = 0.2;
  private double weightRandom = 0.001;
  private double weightAvoidEdge = 0.2;

  //=========================================================================//
  // Scout Random Constructor                                                //
  //=========================================================================//
  public ScoutRandomSwarmAlgorithm(ENTITY tag, int id, Transform transform, 
    RigidBody rb){
    super(tag, transform);
    this.id = id;
    this.rb = rb;
    this.transform = transform;
    this.scoutRandomSwarmRules = new ScoutRandomSwarmRules(this.id, 
                                                           this.rb,
                                                           this.transform);
  }  

  //=========================================================================//
  // Swarm Algorithm                                                         //
  //=========================================================================//
  @Override 
  public void applySwarmAlgorithm(){
    // Get vectors from rules
    ArrayList<Vector2D> rulesV2D = this.scoutRandomSwarmRules
                                       .iterateOverSwarm(this.tag);
    this.separateV2D = rulesV2D.get(0);
    this.randomV2D = this.randomRule();

    // Apply weights to vectors
    this.separateV2D.mult(this.weightSeparate);
    this.randomV2D.mult(this.weightRandom);
    this.avoidEdge(this.weightAvoidEdge);

    // Apply forces
    this.rb.applyForce(this.separateV2D);
    this.rb.applyForce(this.randomV2D);
    this.transform.setHeading(this.rb.getVelocity().heading());
    this.rb.update(this.transform.getLocation(), this.transform.getHeading()); 
  }

  //=========================================================================//
  // Misc Swarm Rules                                                        //
  //=========================================================================//
  @Override
  public Vector2D seekMotherShip(){
    // NA
    return new Vector2D(0, 0);
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

  public double getWeightRandom(){
    return this.weightRandom;
  }

  public void setWeightRandom(double weight){
    this.weightRandom = weight;
  }
}