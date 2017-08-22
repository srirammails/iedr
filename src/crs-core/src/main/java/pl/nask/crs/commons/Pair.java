package pl.nask.crs.commons;

/**
 * 
 * @author Artur Gniadzik
 *
 * Represents a pair of values 
 * @param <LEFT> first value type
 * @param <RIGHT> second value type
 */
public class Pair<LEFT, RIGHT> {
	private LEFT left;
	private RIGHT right;
	
	public Pair() {
	}
	
	public Pair(LEFT left, RIGHT right) {
		this.left = left;
		this.right = right;
	}

	public LEFT getLeft() {
		return left;
	}
	
	public RIGHT getRight() {
		return right;
	}

	public void setLeft(LEFT left) {
		this.left = left;
	}
	
	public void setRight(RIGHT right) {
		this.right = right;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pair ["+ left + ", " + right + "]";
	}
}
