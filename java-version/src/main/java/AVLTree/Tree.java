package AVLTree;

/**
 * Implementation AVL Tree.
 * inspired by github.com/4ndrelim/data-structures-and-algorithms AVLTree.
 * add feature that the user can have their own augmented Node
 *
 * @param <T> generic type of object to be stored; must be comparable
 *            insert(T key)
 *            insert(Node<T> node)
 *            delete(T key)
 *            find(Find<T> find, Object key)
 *            search(T key)
 *            predecessor(T key)
 *            successor(T key)
 */
public class Tree<T extends Comparable<T>> {
    private Node<T> root;
    private int size;
    private Tree(Node<T> node) {
        this.root = node;
        size = 1;
    }

    public static <T extends Comparable<T>> Tree<T> createTree() {
        return new Tree<>(null);
    }
    public static <T extends Comparable<T>> Tree<T> createTree(Node<T> node) {
        return new Tree<>(node);
    }

    public static <T extends Comparable<T>> Tree<T> createTree(T value) {
        return new Tree<>(new Node<T>(value));
    }

    private void checkRoot() {
        if (root == null) {
            throw new NullPointerException("Tree is empty");
        }
    }

    private int getBalance(Node<T> n) {
        return n.getBalance();
    }

    private Node<T> rotateRight(Node<T> n) {
        Node<T> newRoot = n.getLeft();
        Node<T> newLeftSub = newRoot.getRight();
        newRoot.setRight(n);
        n.setLeft(newLeftSub);

        newRoot.setParent(n.getParent());
        n.setParent(newRoot);

        n.update();
        newRoot.update();
        return newRoot;
    }

    private Node<T> rotateLeft(Node<T> n) {
        Node<T> newRoot = n.getRight();
        Node<T> newRightSub = newRoot.getLeft();
        newRoot.setLeft(n);
        n.setRight(newRightSub);

        newRoot.setParent(n.getParent());
        n.setParent(newRoot);

        n.update();
        newRoot.update();
        return newRoot;
    }

    private Node<T> rebalance(Node<T> n) {
        n.update();
        int balance = getBalance(n);
        if (balance < -1) { // right-heavy case
            if (n.getRight().leftHeavy()) {
                n.setRight(rotateRight(n.getRight()));
            }
            n = rotateLeft(n);
        } else if (balance > 1) { // left-heavy case
            if (n.getLeft().rightHeavy()) {
                n.setLeft(rotateLeft(n.getLeft()));
            }
            n = rotateRight(n);
        }
        return n;
    }

    private Node<T> getMostLeft(Node<T> n) {
        if (n.getLeft() == null) {
            return n;
        } else {
            return getMostLeft(n.getLeft());
        }
    }

    private Node<T> getMostRight(Node<T> n) {
        if (n.getRight() == null) {
            return n;
        } else {
            return getMostRight(n.getRight());
        }
    }

    public void insert(T key) {
        root = insert(root, key);
    }

    public void insert(Node<T> node) {
        root = insert(root, node);
    }

    private Node<T> insert(Node<T> node, Node<T> key) {
        if (node == null) {
            size++;
            return key;
        } else if (node.compareTo(key) < 0) {
            node.setRight(insert(node.getRight(), key));
            node.getRight().setParent(node);
        } else if (node.compareTo(key) > 0) {
            node.setLeft(insert(node.getLeft(), key));
            node.getLeft().setParent(node);
        } else {
            throw new RuntimeException("Duplicate key not supported!");
        }

        return rebalance(node);
    }

    private Node<T> insert(Node<T> node, T key) {
        if (node == null) {
            size++;
            return new Node<>(key);
        } else if (node.compareTo(key) < 0) {
            node.setRight(insert(node.getRight(), key));
            node.getRight().setParent(node);
            // note that insufficient to update parent in rotateLeft & rotateRight if still considered balanced
        } else if (node.compareTo(key) > 0) {
            node.setLeft(insert(node.getLeft(), key));
            node.getLeft().setParent(node);
        } else {
            throw new RuntimeException("Duplicate key not supported!");
        }

        return rebalance(node);
    }

    public void delete(T key) {
        checkRoot();
        root = delete(root, key);
    }

    private Node<T> delete(Node<T> node, T key) {
        if (node == null) {
            return null;
        } else if (node.compareTo(key) < 0) {
            node.setRight(delete(node.getRight(), key));
        } else if (node.compareTo(key) > 0) {
            node.setLeft(delete(node.getLeft(), key));
        } else {
            if (node.getLeft() == null || node.getRight() == null) { // case of 1 or 0 child
                if (node.getLeft() == null && node.getRight() == null) {
                    node = null; // 0-child case
                } else if (node.getRight() == null) {
                    node.getLeft().setParent(node.getParent());
                    node = node.getLeft();
                } else {
                    node.getRight().setParent(node.getParent());
                    node = node.getRight();
                }

                size--;
            } else { // 2-children case
                Node<T> successor = getMostLeft(node.getRight());
                node.setValue(successor.getValue());
                // since this is a 2-children case, successor of deleted node have
                // at most one child; right-child (else it would continue going left)
                node.setRight(delete(node.getRight(), successor.getValue()));
            }
        }

        if (node != null) { // make sure it isn't the 0-child case
            return rebalance(node);
        }
        return node; // null; case when nothing left
    }

    public Node<T> find(Find<T> find, Object key) {
        checkRoot();
        return find.find(root, key);
    }

    public Node<T> search(T key) {
        checkRoot();
        Node<T> curr = root;
        while (curr != null) {
            if (curr.compareTo(key) < 0) {
                curr = curr.getRight();
            } else if (curr.compareTo(key) > 0) {
                curr = curr.getLeft();
            } else {
                return curr;
            }
        }

        return null;
    }

    public T predecessor(T key) {
        checkRoot();
        Node<T> curr = root;
        while (curr != null) {
            if (curr.compareTo(key) == 0) {
                break;
            } else if (curr.compareTo(key) < 0) {
                if (curr.getRight() == null) {
                    break;
                }
                curr = curr.getRight();
            } else {
                if (curr.getLeft() == null) {
                    break;
                }
                curr = curr.getLeft();
            }
        }
        if (curr.compareTo(key) < 0) { // we are done
            return curr.getValue();
        }

        return predecessor(curr); // pred could be an ancestor or child of curr node and hence handled separately
    }

    private T predecessor(Node<T> node) {
        Node<T> curr = node;
        if (curr.getLeft() != null) { // has left-child
            return getMostRight(curr.getLeft()).getValue();
        } else { // so pred must be an ancestor
            while (curr != null) {
                if (curr.compareTo(node) < 0) {
                    return curr.getValue();
                }
                curr = curr.getParent();
            }
        }
        return null;
    }

    public T successor(T key) {
        checkRoot();
        Node<T> curr = root;
        while (curr != null) {
            if (curr.compareTo(key) == 0) {
                break;
            } else if (curr.compareTo(key) < 0) {
                if (curr.getRight() == null) {
                    break;
                }
                curr = curr.getRight();
            } else {
                if (curr.getLeft() == null) {
                    break;
                }
                curr = curr.getLeft();
            }
        }
        if (curr.compareTo(key) > 0) { // we are done
            return curr.getValue();
        }
        return successor(curr); // same exp as in the pred fn
    }

    private T successor(Node<T> node) {
        Node<T> curr = node;
        if (curr.getRight() != null) { // has right-child
            return getMostLeft(curr.getRight()).getValue();
        }
        while (curr != null) {
            if (curr.compareTo(node) > 0) {
                return curr.getValue();
            }
            curr = curr.getParent();
        }
        return null;
    }

    public int size() {
        return size;
    }
}
