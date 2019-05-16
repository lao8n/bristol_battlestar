package swarm_wars_library.engine;

import java.util.ArrayList;

import swarm_wars_library.entities.AbstractEntity;
import swarm_wars_library.entities.STATE;
import swarm_wars_library.physics.Vector2D;

public class CollisionDetection {

	private CollisionDetection(){}

	public static void checkCollisions(
		ArrayList<AbstractEntity> entitiesDealDamage,
		ArrayList<AbstractEntity> entitiesTakeDamage){

		for(int i = 0; i < entitiesDealDamage.size(); i++){
			for(int j = 0; j < entitiesTakeDamage.size(); j++){
				if(hasCollision(entitiesDealDamage.get(i), 
												entitiesTakeDamage.get(j))){
					entitiesDealDamage.get(i)
														.collidedWith(entitiesTakeDamage.get(j).getTag());
					entitiesTakeDamage.get(j)
														.collidedWith(entitiesDealDamage.get(i).getTag());
				}
			}
		}
	}

	public static boolean hasCollision(AbstractEntity dealDamage, 
		AbstractEntity takeDamage){
		if((dealDamage.isState(STATE.ALIVE) || dealDamage.isState(STATE.SUICIDE)) &&
			 takeDamage.isState(STATE.ALIVE) &&
			 (Vector2D.sub(dealDamage.getLocation(),takeDamage.getLocation()).mag()
			 < dealDamage.getScale() + takeDamage.getScale())){
			return true;
		}
		return false;
	}
}
