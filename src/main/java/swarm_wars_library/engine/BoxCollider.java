package swarm_wars_library.engine;

public class BoxCollider {

	BoxCollider(){}

	//checks for tags first and only check collisions that matter
	public static void boundingCheck(Entity first, Entity second){
		// Prevents any dead entity having impact
		if (first.isDead() || !first.isRendering() || second.isDead() || !second.isRendering()){
			return;
		}

		// Player - Enemy bullet
		if (first.getTag().equals(Tag.PLAYER)){
			if (second.getTag().equals(Tag.E_BULLET)){
				if (hasCollision(first, second)){
					first.takeDamage(5);
					second.kill();
				}
		  } // any other collisions we care about involving player: 
		  
		} else if (first.getTag().equals(Tag.E_BULLET)){
		//if (second.getTag().equals(Tag.PLAYER)){
		//	if (hasCollision(first, second)){
		//		second.takeDamage(5);
		//		first.kill();
		//	} else
			if (second.getTag().equals(Tag.P_BOT)){
				if (hasCollision(first, second)){
					first.kill();
					second.kill();
				}
			}	

		} else if (first.getTag().equals(Tag.P_BOT)){
			if (second.getTag().equals(Tag.E_BULLET)){
				if (hasCollision(first, second)){
					first.kill();
					second.kill();
				}
			}
		
		// Enemy - Player bullet
		} else if (first.getTag().equals(Tag.ENEMY)){
			if(second.getTag().equals(Tag.P_BULLET)){
				if (hasCollision(first, second)){
					first.takeDamage(5);
					second.kill();
				}	
			}
		} else if (first.getTag().equals(Tag.P_BULLET)){
			if(second.getTag().equals(Tag.ENEMY)){
				if (hasCollision(first, second)){
					second.takeDamage(5);
					first.kill();
				}	
			}
		}

	}

	public static boolean hasCollision(Entity first, Entity second){
		if(Vector2D.sub(first.getPosition(), second.getPosition()).mag() < 
			//(first.getBoundingLength() + second.getBoundingLength())){
			(first.getScale() + second.getScale())){
			return true;
		}
		return false;
	}

}
