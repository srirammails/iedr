package pl.nask.crs.web;

/**
 * Pair of values
 * 
 * @author Artur Gniadzik
 * 
 * @param <LEFT>
 *            left value type
 * @param <RIGHT>
 *            right value type
 */
public class Pair<LEFT, RIGHT> {
    private LEFT left;
    private RIGHT right;

    public Pair(LEFT left, RIGHT right) {
        this.left = left;
        this.right = right;
    }

    public LEFT getLeft() {
        return left;
    }

    public void setLeft(LEFT left) {
        this.left = left;
    }

    public RIGHT getRight() {
        return right;
    }

    public void setRight(RIGHT right) {
        this.right = right;
    }
}
