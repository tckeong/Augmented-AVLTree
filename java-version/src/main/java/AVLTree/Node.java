package AVLTree;

/**
 * Override the update() method if want augmented AVL Tree.
 */
public class Node <T extends Comparable<T>> implements Comparable<Node<T>> {
    private T value;
    private Node<T> left = null;
    private Node<T> right = null;
    private Node<T> parent = null;
    private int height = 0;

    public Node(T value) {
        this.value = value;
    }
    public void setValue(T value) {
        this.value = value;
    }
    public T getValue() {
        return value;
    }

    public int getHeight() {
        return height;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public int getBalance() {
        int leftHeight = left == null ? 0 : left.getHeight();
        int rightHeight = right == null ? 0 : right.getHeight();
        return leftHeight - rightHeight;
    }

    protected void updateHeight() {
        int leftHeight = left == null ? 0 : left.getHeight();
        int rightHeight = right == null ? 0 : right.getHeight();
        height = 1 + Math.max(leftHeight, rightHeight);
    }

    public void update() {
        updateHeight();
    }

    public boolean leftHeavy() {
        int leftHeight = left == null ? 0 : left.getHeight();
        int rightHeight = right == null ? 0 : right.getHeight();

        return leftHeight > rightHeight;
    }

    public boolean rightHeavy() {
        int leftHeight = left == null ? 0 : left.getHeight();
        int rightHeight = right == null ? 0 : right.getHeight();

        return rightHeight > leftHeight;
    }

    public int compareTo(T key) {
        return value.compareTo(key);
    }

    @Override
    public int compareTo(Node<T> node) {
        return value.compareTo(node.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
