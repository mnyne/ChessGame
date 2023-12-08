package Game;

public class MovementArray {
	private final int BOARD_LENGTH = 8;
	private boolean[] moveAtIndexAllowed;

	public MovementArray() {
		moveAtIndexAllowed = new boolean[BOARD_LENGTH * BOARD_LENGTH];
	}

	/**
	 * Returns whether moving at the specified index is allowed.
	 *
	 * @param  index  the index to check
	 * @return        true if moving at the index is allowed, false otherwise
	 */
	public boolean moveAtIndexAllowed(int index) {
		return moveAtIndexAllowed[index];
	}

	/**
	 * Sets the specified index in the moveAtIndexAllowed array to true.
	 *
	 * @param  index  the index to be set to true
	 */
	public void setIndexToTrue(int index) {
		moveAtIndexAllowed[index] = true;
	}

	/**
	 * Sets the value at the specified index in the moveAtIndexAllowed array to false.
	 *
	 * @param  index  the index at which to set the value to false
	 */
	public void setIndexToFalse(int index) {
		moveAtIndexAllowed[index] = false;
	}

	/**
	 * Returns the length of the array moveAtIndexAllowed.
	 *
	 * @return the length of the array moveAtIndexAllowed
	 */
	public int getLength() {
		return moveAtIndexAllowed.length;
	}
}