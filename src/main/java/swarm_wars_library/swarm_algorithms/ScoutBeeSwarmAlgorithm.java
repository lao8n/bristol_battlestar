package swarm_wars_library.swarm_algorithms;

import java.util.ArrayList;
import java.util.Random;

import swarm_wars_library.comms.CommsChannel;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.Tag;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Transform;
import swarm_wars_library.physics.RigidBody;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.swarm_algorithms.AbstractSwarmAlgorithm;
import swarm_wars_library.swarm_algorithms.ScoutBeeTargets;

public class ScoutBeeSwarmAlgorithm extends AbstractSwarmAlgorithm {

  private int id;
  public RigidBody rb;
  public Transform transform;
  private Vector2D randomV2D;
  private double weightRandom = 0.001;
  private double weightAvoidEdge = 0.9;
  private double weightHeading = 0.3;
  private BEESTATE beeState;
  private Random random;
  private ScoutBeeTargets scoutBeeTargets;
  private Vector2D scoutBeeTarget;
  private Map map = Map.getInstance();
  private ArrayList<ENTITY> listTargetEntities = new ArrayList<ENTITY>();
  private boolean scoutBeeTargetFlag = false;
  private Vector2D heading = new Vector2D(0, 0);

  //=========================================================================//
  // Scout Random Constructor                                                //
  //=========================================================================//
  public ScoutBeeSwarmAlgorithm(ENTITY tag, int id, Transform transform, 
    RigidBody rb){
    super(tag, transform);
    this.id = id;
    this.rb = rb;
    this.transform = transform;
    this.beeState = BEESTATE.ATHIVE;
    this.random = new Random();
    this.scoutBeeTargets = ScoutBeeTargets.getInstance();
    this.listTargetEntities.add(ENTITY.TURRET);
  }  

  //=========================================================================//
  // Swarm Algorithm                                                         //
  //=========================================================================//
  @Override 
  public void applySwarmAlgorithm(){
    // Get vectors from rules
    if(this.beeState == BEESTATE.ATHIVE){
      this.transform.setHeading(0);
      this.transform.setVelocity(0, 0);   
      this.rb.setVelocity(new Vector2D(0, 0));   
      if(this.scoutBeeTargets.getTargetSize() > 0 & 
         this.random.nextInt(100) > 40){
        this.scoutBeeTarget = this.scoutBeeTargets.getRandomTarget();
      }
      else {
        // Random target
        this.scoutBeeTarget = new Vector2D(Math.random() * 
                                            this.map.getMapWidth(), 
                                            Math.random() * 
                                            this.map.getMapHeight());
      }
      this.heading = this.seekScoutTarget(this.scoutBeeTarget);
      this.beeState = BEESTATE.SCOUT;
    }
    else if (this.beeState == BEESTATE.SCOUT){
      if(Vector2D.sub(this.transform.getLocation(), 
                      this.scoutBeeTarget).mag() < 50){
        this.beeState = BEESTATE.ATTARGET;
      }
      if(this.transform.getLocation().getX() == 0 || 
         this.transform.getLocation().getX() == this.map.getMapWidth() ||
         this.transform.getLocation().getY() == 0 ||
         this.transform.getLocation().getY() == this.map.getMapHeight()){
        this.beeState = BEESTATE.ATTARGET;
      }
    }
    else if (this.beeState == BEESTATE.ATTARGET){
      boolean flag = false;
      for(int i = 0; i < this.listTargetEntities.size(); i++){
        CommsChannel commsChannel = 
          CommsGlobal.get(this.listTargetEntities.get(i).toString());
        for(int j = 0; j < commsChannel.getNumberOfReceivers(); j++){
          if(Vector2D.sub(commsChannel.getPacket(j).getLocation(), 
                          this.transform.getLocation()).mag() < 50){
            flag = true;
          }
        }
      }
      this.scoutBeeTargetFlag = flag;
      this.transform.setHeading(0);
      this.transform.setVelocity(0, 0);
      this.beeState = BEESTATE.RETURN;
    }
    else {
      this.heading = this.seekMotherShip();
      if(Vector2D.sub(CommsGlobal.get(Tag.getMotherShipTag(this.tag).toString())
                                 .getPacket(0)
                                 .getLocation(), 
                      this.transform.getLocation()).mag() < 30){
        this.beeState = BEESTATE.ATHIVE;
        this.heading = new Vector2D(0, 0);
        if(this.scoutBeeTargetFlag){
          this.scoutBeeTargets.addTarget(this.scoutBeeTarget);
          this.scoutBeeTargetFlag = false;
        }
        else{
          this.scoutBeeTargets.removeTarget(this.scoutBeeTarget);
        }
      }
    }

    this.randomV2D = this.randomRule();

    // Apply weights to vectors
    this.heading.mult(this.weightHeading);
    this.randomV2D.mult(this.weightRandom);
    this.avoidEdge(this.weightAvoidEdge);

    // Apply forces
    this.rb.applyForce(this.heading);
    this.rb.applyForce(this.randomV2D);
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
    return new Vector2D(Math.random() - 0.5, Math.random() - 0.5);
  }

  public void addTargetEntity(ENTITY type){
    this.listTargetEntities.add(type);
  }

  public Vector2D seekScoutTarget(Vector2D scoutTarget){
    Vector2D target = Vector2D.sub(scoutTarget, 
                                   transform.getLocation());
    target.normalise();
    target.mult(rb.getMaxSpeed());
    target.limit(rb.getMaxForce());
    return target; 
  }
}