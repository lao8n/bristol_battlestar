class RectangleParticle {
	public Vector2D location; /*CENTRE OF MASS / CENTRE OF RECT */
	public Vector2D scale; /* {WIDTH, HEIGHT} */
	public double angle;

	RectangleParticle(float x_, float y_, float scale_x, float scale_y) {
		location = new Vector2D(x_, y_);
		scale = new Vector2D(scale_x, scale_y);
		angle = 0;
	}

	void update() {

	}

	void draw() {
	}
}