package tree;

import java.util.*;

public class MyBST<K extends Comparable<K>, V> implements Iterable<MyBST<K, V>.Node> {
    public class Node {

        K key;
        V val;
        Node left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K getKey() {
            return key;
        }

        public V getVal() {
            return val;
        }

        @Override
        public String toString() {
            return getKey().toString() + ": " + getVal().toString();
        }

    }

    private Node root;
    private int size = 0;

    public void put(K key, V value) {
        root = put(root, key, value);
    }

    public V get(K key) {
        return get(root, key);
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    @Override
    public Iterator<Node> iterator() {
        return new Itr();
    }

    @Override
    public String toString() {
        List<String> arr = new ArrayList<>(size);
        for (Node node : this)
            arr.add(node.toString());
        return "{" + String.join(", ", arr) + "}";
    }


    private Node put(Node current, K key, V val) {

        if (current == null) {
            size++;
            return new Node(key, val);
        }
        int cmp = current.getKey().compareTo(key);
        if (cmp > 0) {
            current.left = put(current.left, key, val);
            size++;
        } else if (cmp < 0) {
            current.right = put(current.right, key, val);
            size++;
        } else
            current.val = val;
        return current;
    }

    private V get(Node node, K key) {
        if (node == null) return null;
        int cmp = node.getKey().compareTo(key);
        if (cmp == 0) return node.getVal();
        else if (cmp > 0) return get(node.left, key);
        else return get(node.right, key);
    }

    private Node delete(Node root, K key) {
        if (root == null)
            return null;

        if (root.getKey().compareTo(key) > 0)
            root.left = delete(root.left, key);
        else if (root.getKey().compareTo(key) < 0)
            root.right = delete(root.right, key);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            Node temp = findSmallest(root.right);
            root.key = temp.key;
            root.val = temp.val;
            root.right = delete(root.right, temp.key);
        }
        return root;
    }


    private Node findSmallest(Node node) {
        return node.left == null ? node : findSmallest(node.right);
    }

    private class Itr implements Iterator<Node> {
        private final Stack<Node> stack;

        public Itr() {
            stack = new Stack<>();
            insertLeft(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public Node next() {
            if (!hasNext())
                throw new EmptyStackException();

            Node node = stack.pop();
            insertLeft(node.right);
            return node;
        }

        private void insertLeft(Node node) {
            if (node == null)
                return;
            stack.push(node);
            insertLeft(node.left);
        }
    }

}