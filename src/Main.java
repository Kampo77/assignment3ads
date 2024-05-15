import hash.MyHashTable;
import tree.MyBST;

public class Main {
    public static void main(String[] args) {
        MyHashTable<String, Integer> hashTable = new MyHashTable<>();
        hashTable.put("One", 1);
        hashTable.put("Two", 2);
        hashTable.put("Three", 3);
        System.out.println("HashTable: " + hashTable);
        System.out.println("Get value for 'Two': " + hashTable.get("Two"));
        hashTable.delete("Two");
        System.out.println("HashTable after deleting 'Two': " + hashTable);
        MyBST<String, Integer> bst = new MyBST<>();
        bst.put("One", 1);
        bst.put("Two", 2);
        bst.put("Three", 3);
        System.out.println("BST: " + bst);
        System.out.println("Get value for 'Two': " + bst.get("Two"));
        bst.delete("Two");
        System.out.println("BST after deleting 'Two': " + bst);
    }
}