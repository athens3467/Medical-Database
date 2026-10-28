public class BinarySearchTree<T extends ComparableTree<T>>
        implements TreeCollection<T> {

    private class TreeNode {
        private T data;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(T data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    private TreeNode root;
    private int count;

    public BinarySearchTree() {
        root = null;
        count = 0;
    }

    public boolean add(T item) {
        if (item == null) {
            return false;
        }

        if (root == null) {
            root = new TreeNode(item);
            count++;
            return true;
        }

        TreeNode current = root;

        while (true) {
            if (item.match(current.data)) {
                return false;
            }

            if (item.isLessThan(current.data)) {
                if (current.left == null) {
                    current.left = new TreeNode(item);
                    count++;
                    return true;
                }

                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new TreeNode(item);
                    count++;
                    return true;
                }

                current = current.right;
            }
        }
    }

    public T find(T item) {
        if (item == null) {
            return null;
        }

        TreeNode current = root;

        while (current != null) {
            if (item.match(current.data)) {
                return current.data;
            }

            if (item.isLessThan(current.data)) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    public boolean contains(T item) {
        return find(item) != null;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void visitMatching(TreeMatcher<T> matcher,
                              TreeVisitor<T> visitor) {
        if (matcher == null || visitor == null) {
            return;
        }

        visitMatching(root, matcher, visitor);
    }

    private void visitMatching(TreeNode node,
                               TreeMatcher<T> matcher,
                               TreeVisitor<T> visitor) {
        if (node == null) {
            return;
        }

        int comparison = matcher.compare(node.data);

        if (comparison < 0) {
            visitMatching(node.left, matcher, visitor);
        } else if (comparison > 0) {
            visitMatching(node.right, matcher, visitor);
        } else {
            visitMatching(node.left, matcher, visitor);
            visitor.visit(node.data);
            visitMatching(node.right, matcher, visitor);
        }
    }
}
