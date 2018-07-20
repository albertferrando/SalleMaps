package dataStructures;

public class AVL {

    private Node root;

    //compareTo dóna positiu en cas que el paràmetre vagi abans alfabèticament. Per tant és a - b.

    private int height(Node N) {
        if (N == null)
            return 0;

        return N.height;
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // A utility function to right rotate subtree rooted with root
    // See the diagram given above.
    private Node rightRotate(Node root) {
        Node x = root.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = root;
        root.left = T2;

        // Update heights
        root.height = max(height(root.left), height(root.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with root
    // See the diagram given above.
    private Node leftRotate(Node root) {
        Node y = root.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = root;
        root.right = T2;

        //  Update heights
        root.height = max(height(root.left), height(root.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    private int getBalance(Node N) {
        if (N == null) {
            return 0;
        }
        return height(N.left) - height(N.right);
    }

    public void insert(String key, Object info) {
        this.root = this.insert(this.root, key, info);
    }

    private Node insert(Node node, String key, Object info) {
        if (node == null) {
            return (new Node(key, info));
        }
        if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, info);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insert(node.right, key, info);
        } else {
            return node;
        }

        node.height = 1 + max(height(node.left), height(node.right));

        int balance = getBalance(node);

        //Left left
        if (balance > 1 && getBalance(node.left) > 0) {
            return rightRotate(node);
        }

        //Right right
        if (balance < -1 && getBalance(node.right) < 0) {
            return leftRotate(node);
        }

        //Right Left
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    public Object getInfo(String key) {
        Node n = root;
        while(!n.key.equals(key)) {
            if(n.key.compareTo(key) > 0) {
                if(n.left == null){
                    return -1;
                }
                n = n.left;
            } else {
                if(n.right == null) {
                    return -1;
                }
                n = n.right;
            }
        }
        return n.info;
    }

    class Node {
        String key;
        Object info;
        int height;
        Node left, right;

        Node(String key, Object info) {
            this.key = key;
            this.info = info;
            height = 1;
        }
    }
}