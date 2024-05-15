package hash;

public class MyHashTable<K, V> {
    private static class HashNode<K, V> {
        final K key;
        V value;
        HashNode<K, V> next;

        HashNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        @Override
        public String toString() {
            return "{" + key + " " + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray;
    private static final int DEFAULT_SIZE = 11;
    private int size;

    public MyHashTable() {
        chainArray = createArray(DEFAULT_SIZE);
    }

    public MyHashTable(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Size must be positive value!");
        chainArray = createArray(size);
    }

    public void put(K key, V value) {
        increaseSize();
        int index = getIndex(key);
        HashNode<K, V> newNode = new HashNode<>(key, value);

        if (chainArray[index] == null) {
            chainArray[index] = newNode;
            size++;
            return;
        }

        HashNode<K, V> node = chainArray[index];
        while (node != null) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
            if (node.next == null)
                break;
            node = node.next;
        }
        if (node == null)
            throw new NullPointerException("Something went wrong. Node element cannot be null.");
        node.next = newNode;
        size++;
    }

    public V get(K key) {
        int index = getIndex(key);
        HashNode<K, V> node = chainArray[index];
        while (node != null) {
            if (node.key.equals(key)) return node.value;
            node = node.next;
        }

        return null;
    }

    public V delete(K key) {
        int index = getIndex(key);
        HashNode<K, V> node = chainArray[index];
        if (node == null) return null;
        V value;

        if (node.key.equals(key)) {
            value = chainArray[index].value;
            chainArray[index] = null;
            return value;
        }

        while (node.next != null) {
            if (node.next.key.equals(key)) {
                value = node.next.value;
                node.next = node.next.next;
                return value;
            }
            node = node.next;
        }

        return null;
    }

    public boolean contains(V value) {
        return getKey(value) != null;
    }

    public K getKey(V value) {
        for (HashNode<K, V> node : chainArray)
            while (node != null) {
                if (node.value.equals(value)) return node.key;
                node = node.next;
            }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MyHashTable{");
        for (HashNode<K, V> node : chainArray)
            while (node != null) {
                builder.append(node).append(",");
                node = node.next;
            }
        builder.append("}");
        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    private HashNode<K, V>[] createArray(int M) {
        return new HashNode[M];
    }

    private void increaseSize() {
        if (size < chainArray.length * 3 / 4) return;

        HashNode<K, V>[] oldChain = chainArray;
        chainArray = createArray(chainArray.length * 2);
        reIndex(oldChain);
    }

    private void reIndex(HashNode<K, V>[] oldChain) {
        for (HashNode<K, V> node : oldChain) {
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
    }

    private int hash(K key) {
        return key.hashCode();
    }

    private int getIndex(K key) {
        int hash = hash(key);
        return Math.abs(hash) % chainArray.length;
    }
}
