package swarm_wars_library.engine;

public class BoxCollider {

	BoxCollider(){
	}

	public static boolean boundingCheck(GameObject first, GameObject second){
		if(Vector2D.sub(first.getLocation(), second.getLocation()).mag() 
				<= (first.getBoundingLength() + second.getBoundingLength())){
			second.setHasCollision(true);
			return true;
		}
		return false;
	}

	//to use (in a loop) at start of the game loop, to clear set all hasCollisions to false
	public static void clearCollision(GameObject go){
		go.setHasCollision(false);
	}

	/* Advanced collision detection using Seperating Axis Theorem */
	boolean SATCheck(){
		return false;
	}
}