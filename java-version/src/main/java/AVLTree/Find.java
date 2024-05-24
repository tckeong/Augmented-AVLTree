package AVLTree;

@FunctionalInterface
public interface Find<T extends Comparable<T>> {
    Node<T> find(Node<T> root, Object key);
}

