package estructures;

/**
 *
 */
public class taulaHash {
    private final int PRIMERS[] = {3, 5, 7, 13, 17, 19, 23};
    private int TABLE_SIZE;
    Llista[] table;

    public taulaHash(int size) {
        TABLE_SIZE = size;
        table = new Llista[TABLE_SIZE];
        for(int i = 0; i < TABLE_SIZE; i++) {
            table[i] = new Llista();
        }
    }

    public int hashFunction(String key) {
        int hash = 0;
        for(int i = 0; i < key.length(); i++) {
            hash = hash + key.charAt(i) * PRIMERS[i % 7];
        }
        return hash % TABLE_SIZE;
    }

   public Object get(String key) {
        int hash = hashFunction(key);
        for(int i = 0; i < table[hash].mida(); i++) {
            Node n = (Node) table[hash].recuperar(i);
            if(n.getKey().equals(key)) {
                return n.getInfo();
            }
        }
        return -1;
    }

    public void put(String key, Object info) {
        int hash = hashFunction(key);
        table[hash].afegeix(new Node(key, info));
    }

    class Node {
        private String key;
        private Object info;

        Node(String key, Object info) {
            this.key = key;
            this.info = info;
        }

        public String getKey() {
            return key;
        }

        public Object getInfo() {
            return info;
        }
    }
}
