public class Figure {
	private int x;
	private int y;
	private boolean black;
	
	public Figure(int in_x, int in_y, String color) {
		x = in_x;
		y = in_y;
		if(color.equals("black")) {
			black = true;
		}
	}
	
	public boolean isBlack() {
		return black;
	}
	
	public int getXNotation() {
		int xt = (int) x;
		xt = xt + 96;
		char xc = (char) xt;
		return xc;
	}

	public int getYNotation() {
		return y;
	}
	
	public int getXBoard() {
		return x * 42;
	}
	
	public int getYBoard() {
		return y * 42;
	}
	
	public int getRawX() {
		return x;
	}
	
	public int getRawY() {
		return y;
	}
	
	public String printPosition() {
		String s = "x:" + x +", y:" + y;
		return s;
	}
}
