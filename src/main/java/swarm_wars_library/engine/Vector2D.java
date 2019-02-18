package swarm_wars_library.engine;

public class Vector2D {
	
	public double x;
	public double y;

	public Vector2D(double x_, double y_){
		x = x_;
		y = y_;
	}

	void add(Vector2D v) {
		x = x + v.x;
		y = y + v.y;
	}

	static Vector2D add(Vector2D v1, Vector2D v2) {
		Vector2D v3 = new Vector2D(v1.x + v2.x, v1.y + v2.y);
		return v3;
	}

	void sub(Vector2D v) {
		x = x - v.x;
		y = y - v.y;
	}

	static Vector2D sub(Vector2D v1, Vector2D v2) {
		Vector2D v3 = new Vector2D(v1.x - v2.x, v1.y - v2.y);
		return v3;
	}

	void mult(double n) {
		x = x * n;
		y = y * n;
	}

	static Vector2D mult(Vector2D v1, double n) {
		Vector2D v2 = new Vector2D(v1.x * n, v1.y * n);
		return v2;
	}

	void div(double n) {

		if(n != 0){
			x = x / n;
			y = y / n;
		}
	}

	static Vector2D div(Vector2D v1, double n) {
		/*	if(n == 0 || n == 0){
			 NEED TO RAISE EXCEPTION << check for zero divide <<<<<<<<<
			return;
		}*/
		Vector2D v2 = new Vector2D (v1.x / n, v1.y / n);
		return v2;
	}


	double mag() {
		return Math.sqrt(x * x  + y * y);
	}

	void setMag(double m) {
		normalise();
		mult(m);
	}

	void normalise() {
		double m = mag();
		if (m != 0) {
			div(m);
		}
	}
	
	Vector2D unit() {
		Vector2D v2 = new Vector2D(x, y);
		v2.normalise();
		return v2;
	}

	void limit(double max) {
		if(mag() > max) {
			normalise();
			mult(max);
		}
	}

	double heading() {
		/* Converts cart coords to polar, returns degree in RADIANS*/
		return Math.atan2(y, x);
	}


	void random2D() {
		x = Math.random();
		y = Math.random();
	}

	void lerp(Vector2D v, double i){
		x = (v.x - x) * i;
		y = (v.y - y) * i;
	}
}