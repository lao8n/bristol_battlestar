class Vector2D {
	
	public double x;
	public double y;

	Vector2D(double x_, double y_){
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
	
	Vector2D normal() {
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


	/* -------   TESTS  -------- */

	public static void main(String[] args) {
		Vector2D program = new Vector2D(0, 0);
		program.run();
	}

	private void run() {
		boolean testing = false;
		assert(testing = true);
		if (! testing) throw new Error("Use java -ea Vector2D");
		testAdd();
		testStaticAdd();
		testSub();
		testStaticSub();
		testMult();
		testStaticMult();
		testDiv();
		testStaticDiv();
		testMag();
		testSetMag();
		testNomalise();
		testNormal();
		testLimt();
		testHeading();
		testRandom2D();
	}

	private void testAdd() {
		Vector2D p = new Vector2D(0, 0);
		Vector2D p2 = new Vector2D(1, 2);
		p.add(p2);
		assert(p.x == 1);
		assert(p.y == 2);
	}

	private void testStaticAdd() {
		Vector2D p1 = new Vector2D(0, 0);
		Vector2D p2 = new Vector2D(1, 2);
		assert(Vector2D.add(p1, p2).x == 1);
		assert(Vector2D.add(p1, p2).y == 2);
	}

	private void testSub() {
		Vector2D p = new Vector2D(0, 0);
		Vector2D p2 = new Vector2D(1, 2);
		p.sub(p2);
		assert(p.x == -1);
		assert(p.y == -2);
	}

	private void testStaticSub() {
		Vector2D p1 = new Vector2D(0, 0);
		Vector2D p2 = new Vector2D(1, 2);
		assert(Vector2D.sub(p1, p2).x == -1);
		assert(Vector2D.sub(p1, p2).y == -2);
	}

	private void testMult() {
		Vector2D p = new Vector2D(3, 2);
		p.mult(3);
		assert(p.x == 9);
		assert(p.y == 6);
	}

	private void testStaticMult() {
		Vector2D p = new Vector2D(1, 2);
		assert(Vector2D.mult(p, 10).x == 10);
		assert(Vector2D.mult(p, 10).y == 20);
	}

	private void testDiv() {
		Vector2D p = new Vector2D(4, 2);
		p.div(2);
		assert(p.x == 2);
		assert(p.y == 1);
	}

	private void testStaticDiv() {
		Vector2D p = new Vector2D(1, 2);
		assert(Vector2D.div(p, 10).x == 0.1);
		assert(Vector2D.div(p, 10).y == 0.2);
	}

	private void testMag() {
		Vector2D p = new Vector2D(3, 4);
		assert(p.mag() == 5);
	}

	private void  testSetMag() {
		Vector2D p = new Vector2D(0, 4);
		p.setMag(10);
		assert(p.x == 0);
		assert(p.y == 10);
	}

	private void testNomalise() {
		Vector2D p = new Vector2D(0, 5);
		p.normalise();
		assert(p.x == 0);
		assert(p.y == 1);
	}

	private void testNormal() {
		Vector2D p = new Vector2D(0, 5);
		assert(p.normal().x == 0);
		assert(p.normal().y == 1);

	}

	private void testLimt() {
		Vector2D p = new Vector2D(0, 10);
		p.limit(8);
		assert(p.x == 0);
		assert(p.y == 8);
	}

	private void testHeading() {
		Vector2D p = new Vector2D(1 , 1);
		assert(p.heading() == Math.PI / 4);
		p = new Vector2D(1 , 0);
		assert(p.heading() == 0);
	}

	private void testRandom2D() {
		Vector2D p = new Vector2D(0, 0);
		p.random2D();
		assert(p.x >= 0 && p.x <= 1);
		assert(p.y >= 0 && p.y <= 1);
	}

}


/*

rotate()
lerp() - linea interpolate to another Vector2D
dist()
angleBetween()
dot()
cross()
random2D()

*/