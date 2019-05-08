// package swarm_wars_library.swarm_algorithms;

// import swarm_wars_library.comms.CommsGlobal;
// import swarm_wars_library.entities.ENTITY;
// import swarm_wars_library.entities.Tag;
// import swarm_wars_library.physics.Transform;
// import swarm_wars_library.physics.RigidBody;
// import swarm_wars_library.physics.Vector2D;
// import swarm_wars_library.swarm_algorithms.AbstractSwarmAlgorithm;

// /**
//  * DefensiveShell Class is an implementation of the previous SwarmLogic
//  * but making use of the SwarmRule and SwarmAlgorithm abstract classes to make
//  * the coder slightly cleaner. It forms a rough shell around the Mothership
//  * with a slight lag moving across the map.
//  * <p>
//  * The class contains references to GlobalComms and properties of 
//  * the entity calling it including id, Transform and RigidBody.
//  * It implements abstract methods applySwarmLogic() and seekMotherShip() and 
//  * also includes helper functions to help with this implementation. 
//  * applySwarmLogic(), in particular, contains the private class SeparateRule
//  * which implements the abstract class SwarmRule which is how the swarm 
//  * behaviour is calculated. 
//  * <p>
//  * Issues
//  * 1. Ideally you'd like, via say the FSM or user input, to directly tweak the
//  *    mult() hyperparameters (separate_v2d.mult() and seek_mothership_v2d), but
//  *    it is unclear to me how to do this and make it generic, where you have 
//  *    n rules each with n potential hyperparameters. Currently the 
//  *    hyperparameters are hard-coded in but this probably needs to be changed.
//  * 2. You might find the behaviour is slighlty different from the previous 
//  *    version of the game. If we want to change it back, tweaking the 
//  *    hyperparameters should be able to achieve this.
//  * 
//  */

// public class DefendShellOld extends AbstractSwarmAlgorithm {
//   private int id;
//   private double orbitDistance = 70;
//   public RigidBody rb;
//   public Transform transform;
//   private SeparateRule separate_rule;
//   private double stopDistance = 100;

//   public DefendShellOld(ENTITY tag, int id, Transform transform, RigidBody rb){
//     super(tag, transform);
//     this.id = id;
//     this.rb = rb;
//     this.transform = transform;
//     this.separate_rule = new SeparateRule(this.id, this.rb, this.transform);
//   }

//   @Override
//   public void applySwarmAlgorithm(){
//     Vector2D separate_v2d = this.separate_rule.iterateOverSwarm(this.tag, 20);
//     Vector2D seek_mothership_v2d = 
//       seekMotherShip(CommsGlobal.get(Tag.getMotherShipTag(this.tag).toString())
//                                 .getPacket(0)
//                                 .getLocation());

//     separate_v2d.mult(0.2);
//     seek_mothership_v2d.mult(0.8);
    
//     this.avoidEdge(0.1);
//     this.rb.applyForce(separate_v2d);
//     this.rb.applyForce(seek_mothership_v2d);
//     this.transform.setHeading(this.rb.getVelocity().heading());
//     this.rb.update(this.transform.getLocation(), this.transform.getHeading());
//   }

//   @Override
//   public Vector2D seekMotherShip(Vector2D mothership_location) {
//     Vector2D target = Vector2D.sub(mothership_location, transform.getLocation());
//     target = findOrbit(target);
//     target = checkStopDistance(target);
//     target.limit(rb.getMaxForce());
//     return target;
//   }

//   private Vector2D checkStopDistance(Vector2D desired) {
//     double dist = desired.mag();
//     desired.normalise();
//     if (dist > stopDistance) {
//       desired.mult(rb.getMaxSpeed());
//     } else {
//       double mappedSpeed = dist * rb.getMaxSpeed() / (stopDistance);
//       desired.mult(mappedSpeed);
//     }
//     return desired;
//   }

//   private Vector2D findOrbit(Vector2D desired) {
//     Vector2D orbitTarget = new Vector2D(desired.getX(), desired.getY());
//     orbitTarget.normalise();
//     orbitTarget.mult(orbitDistance);
//     desired.sub(orbitTarget);

//     return desired;
//   }

//   private class SeparateRule extends AbstractSwarmRule {  
//     public SeparateRule(int rule_id,
//       RigidBody rule_rb, Transform rule_transform){
//       super(rule_id, rule_rb, rule_transform);
//     }
//     @Override
//     public void swarmRule(){
//       Vector2D diff = Vector2D.sub(this.rule_transform.getLocation(), 
//                                         this.rule_otherBot.getLocation());
//       diff.normalise();
//       diff.div(this.rule_dist);
//       this.rule_v2d.add(diff);
//       this.rule_neighbourCount++;
//     }
//     @Override
//     public void neighbourCountRule(){
//       this.rule_v2d.div(this.rule_neighbourCount);
//       this.rule_v2d.normalise();
//       this.rule_v2d.mult(rb.getMaxSpeed());
//       this.rule_v2d.sub(rb.getVelocity());
//       this.rule_v2d.limit(rb.getMaxForce());
//     }
//   }
// }