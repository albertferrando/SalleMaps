package dataStructures;

public class Hashtable {
    private final int PRIMERS[] = {3, 5, 7, 13, 17, 19, 23};
    private int TABLE_SIZE;
    Llista[] table;

    public Hashtable(int size) {
        TABLE_SIZE = size;
        table = new Llista[TABLE_SIZE];
    }

    public int hashFunction(String key) {
        int hash = 0;
        for(int i = 0; i < key.length(); i++) {
            hash = hash + key.charAt(i) * PRIMERS[i % 7];
        }
        return hash % TABLE_SIZE;
    }

   /* public Object get(String key) {
        int hash = hashFunction(key);
        while (table[hash] != null && ((Node)(table[hash].rec.getKey() != key)
            hash = (hash + 1) % TABLE_SIZE;
        if (table[hash] == null)
            return -1;
        else
            return table[hash].getInfo();
    }

    public void put(String key, Object info) {
        int hash = hashFunction(key);
        while (table[hash] != null && table[hash].getKey() != key)
            hash = (hash + 1) % TABLE_SIZE;
        table[hash] = new Node(key, info);
    }*/

    class Node {
        private int key;
        private Object info;

        Node(int key, Object info) {
            this.key = key;
            this.info = info;
        }

        public int getKey() {
            return key;
        }

        public Object getInfo() {
            return info;
        }
    }
}
