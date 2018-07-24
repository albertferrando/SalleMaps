package estructures;

/**
 * TaulaHash és una taula de hash d'adreçament obert de tota la vida.
 *
 * @author albertferrando i BerniR4
 * @since 06/07/2018
 * @version 1.0
 */
public class TaulaHash {
    private final int PRIMERS[] = {3, 5, 7, 13, 17, 19, 23};
    private int MIDA;
    private Llista[] taula;

    /**
     * Constructor amb paràmetre.
     *
     * @param mida Indica la mida que tindrà la taula de hash.
     */
    public TaulaHash(int mida) {
        MIDA = mida;
        taula = new Llista[MIDA];
        for(int i = 0; i < MIDA; i++) {
            taula[i] = new Llista();
        }
    }

    /**
     * Calcula la funció de hash a partir d'una clau (de tipus String). La funció de hash en concret consisteix
     * en multiplicar cada caràcter de l'String pel nombre primer que ocupa la posició posicióDelCaracter % 7 de l'array
     * de nombres primers.
     *
     * @param clau Clau de la qual farem el hash.
     * @return Valor resultant d'aplicar la funció.
     */
    private int funcioHash(String clau) {
        int hash = 0;
        for(int i = 0; i < clau.length(); i++) {
            hash = hash + clau.charAt(i) * PRIMERS[i % 7];
        }
        return hash % MIDA;
    }

    /**
     * Funció que recupera el valor lligat a una clau.
     *
     * @param clau Clau de la qual es vol recuperar el "valor".
     * @return Valor de la clau en concret. En cas de que la clau no es trobi a la taula de hash retorna -1.
     */
    public Object recupera(String clau) {
        int hash = funcioHash(clau);
        for(int i = 0; i < taula[hash].mida(); i++) {
            Node n = (Node) taula[hash].recuperar(i);
            if(n.getClau().equals(clau)) {
                return n.getValor();
            }
        }
        return -1;
    }

    /**
     * Mètode que afegeix una parella clau-valor a la taula de hash.
     *
     * @param clau Clau.
     * @param valor Valor o informació que es guardarà amb la clau.
     */
    public void afegeix(String clau, Object valor) {
        int hash = funcioHash(clau);
        taula[hash].afegeix(new Node(clau, valor));
    }

    /**
     * La classe node representa cada objecte de la taula de hash.
     * Està format per una clau i per un valor.
     */
    class Node {
        private String clau;
        private Object valor;

        /**
         * Constructor amb paràmetres.
         * @param clau Clau del node.
         * @param valor Valor del node.
         */
        Node(String clau, Object valor) {
            this.clau = clau;
            this.valor = valor;
        }

        /**
         * Getter de la clau.
         * @return Clau del node.
         */
        String getClau() {
            return clau;
        }

        /**
         * Getter del valor
         * @return Valor del node.
         */
        Object getValor() {
            return valor;
        }
    }
}
