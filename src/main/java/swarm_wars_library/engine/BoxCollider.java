package swarm_wars_library.engine;

import swarm_wars_library.map.Map;

import swarm_wars_library.comms.CommsGlobal;

public class BoxCollider {

	BoxCollider(){}

	//checks for tags dealDamage and only check collisions that matter
	public static void boundingCheck(Entity dealDamage, Entity takeDamage){
		// Prevents any dead entity having impact
		if (dealDamage.isDead() || takeDamage.isDead()){
			return;
		}
		// E_BULLET -> P_BOT
		if (dealDamage.getTag().equals(Tag.E_BULLET)){
			if (takeDamage.getTag().equals(Tag.P_BOT)){
				if (hasCollision(dealDamage, takeDamage)){
					dealDamage.kill();
					takeDamage.kill();
				}
			}	
		} 
		// E_BULLET -> PLAYER
		if (dealDamage.getTag().equals(Tag.E_BULLET)){
			if (takeDamage.getTag().equals(Tag.PLAYER)){
				if (hasCollision(dealDamage, takeDamage)){
					dealDamage.kill();
					takeDamage.takeDamage(5);
				}
			}	
		} 
		// P_BULLET -> ENEMY
		if (dealDamage.getTag().equals(Tag.P_BULLET)){
			if(takeDamage.getTag().equals(Tag.ENEMY)){
				if (hasCollision(dealDamage, takeDamage)){
					dealDamage.kill();
					CommsGlobal.get("PLAYER")
										 .getPacket(0)
										 .addScore(10);
					takeDamage.kill();
					takeDamage.setPosition(
						Math.random() * Map.getInstance().getMapWidth(),
						Math.random() * Map.getInstance().getMapHeight()
					);
					takeDamage.setAlive(true);
				}	
			}
		}
	}

	public static boolean hasCollision(Entity dealDamage, Entity takeDamage){
		if(Vector2D.sub(dealDamage.getPosition(), takeDamage.getPosition()).mag() < 
			//(dealDamage.getBoundingLength() + takeDamage.getBoundingLength())){
			(dealDamage.getScale() + takeDamage.getScale())){
			return true;
		}
		return false;
	}
}
