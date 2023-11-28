public class CoordinateHelper {
	public CoordinateHelper() {

	}

	public int convertXYtoIndex(int x, int y) {
		int index = 0;
		index = (y * 8) + x;
		return index;
	}

	public int convertIndextoX(int index) {
		int x = (index % 8);
		return x;
	}

	public int convertIndextoY(int index) {
		int y = (index / 8);
		return y;
	}

	public int convertIndextoOpticalX(int index) {
		int x = (index % 8);
		x = x * 42;
		return x;
	}

	public int convertIndextoOpticalY(int index) {
		int y = (index / 8);
		y = y * 42;
		return y;
	}

	public int convertOpticalXYtoIndex(int x, int y) {
		x = x / 42;
		y = y / 42;
		int index = (convertXYtoIndex(x, y));
		return index;
	}

	public int getAdjustedDiff(int potential, int current) {
		int diff = current - potential;
		if (diff < 0) {
			diff = -diff;
		}
		return diff;
	}

	public int getRawDiff(int potential, int current) {
		int diff = current - potential;
		return diff;
	}

	public String convertCoordsToNotationString(int x, int y) {
		x = x + 97;
		char convX = (char) x;
		int convY = 8 - y;
		return convX + "" + convY;
	}
}
