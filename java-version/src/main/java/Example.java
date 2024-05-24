import AVLTree.Find;
import AVLTree.Tree;
public class Example {
    public static void main(String[] args) {
        Tree<Integer> tree = Tree.createTree(new MyNode<>(1));
        tree.insert(new MyNode<>(3));
        tree.insert(new MyNode<>(4));
        tree.insert(new MyNode<>(5));
        tree.insert(new MyNode<>(2));
        tree.insert(new MyNode<>(100));
        tree.insert(new MyNode<>(20));
        tree.insert(new MyNode<>(13));

        Find<Integer> findRank = (root, key) -> {
            MyNode<Integer> curr = (MyNode<Integer>) root;
            Integer target = (Integer) key;

            while (curr != null) {
                int rank = curr.getRank();

                if (rank > target) {
                    curr = (MyNode<Integer>) curr.getLeft();
                } else if (rank < target) {
                    target -= rank;
                    curr = (MyNode<Integer>) curr.getRight();
                } else {
                    return curr;
                }
            }

            return null;
        };

        // find node with rank 3
        MyNode<Integer> node = (MyNode<Integer>) tree.find(findRank, 3);
        System.out.println(node.getValue()); // 3
    }
}
