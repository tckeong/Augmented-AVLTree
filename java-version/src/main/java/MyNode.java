import AVLTree.Node;
public class MyNode<T extends Comparable<T>> extends Node<T> {
    private int weight;
    public MyNode(T value) {
        super(value);
        this.weight = 1;
    }

    public int getWeight() {
        updateWeight();
        return this.weight;
    }

    public int getRank() {
        MyNode<T> left = (MyNode<T>) getLeft();
        return left == null ? 1 : left.getWeight() + 1;
    }

    public void updateWeight() {
        MyNode<T> left = (MyNode<T>) getLeft();
        MyNode<T> right = (MyNode<T>) getRight();

        int leftWeight =  left == null ? 0 : left.getWeight();
        int rightWeight = right == null ? 0 : right.getWeight();
        weight = 1 + leftWeight + rightWeight;
    }

    @Override
    public void update() {
        super.update();
        updateWeight();
    }
}
