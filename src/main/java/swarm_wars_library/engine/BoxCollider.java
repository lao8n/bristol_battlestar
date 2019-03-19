package swarm_wars_library.engine;

public class BoxCollider {

	BoxCollider(){
	}

	public static boolean boundingCheck(Entity first, Entity second){
		System.out.println("get here");
		if(Vector2D.sub(first.getPosition(), second.getPosition()).mag() 
			<= (first.getBoundingLength() + second.getBoundingLength())){
			//bullet collision
			if(second.getTag().equals(Tag.E_BULLET)){
				if(first.getTag().equals(Tag.PLAYER)){
					first.takeDamage(5);
					second.kill();
					return true;
				}
				if(first.getTag().equals(Tag.P_BOT)){
					return true;
				}
			}
			if(first.getTag().equals(Tag.P_BULLET)){
				if(second.getTag().equals(Tag.P_BOT)){
					System.out.println("Friendly fire");
					second.takeDamage(5);
					first.kill();
					return true;
				}
			}
		}
		return false;
	}
}