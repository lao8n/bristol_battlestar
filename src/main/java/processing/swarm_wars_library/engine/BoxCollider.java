package processing.swarm_wars_library.engine;

public class BoxCollider {

	BoxCollider(){
	}

	public static boolean boundingCheck(GameObject first, GameObject second){
		if(Vector2D.sub(first.location, second.location).mag() <= (first.boundingLength + second.boundingLength)){
			return true;
		}
		return false;
	}

	/* Advanced collision detection using Seperating Axis Theorem */
	boolean SATCheck(){
		return false;
	}

	public static void main(String[] args) {
		BoxCollider program = new BoxCollider();
		program.run();
	}

	private void run() {
		boolean testing = false;
		assert(testing = true);
		if (! testing) throw new Error("Use java -ea BoxCollider");
	}

}