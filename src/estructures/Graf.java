package estructures;

/**
 * Graf típic que conté una llista de nodes amb les seves respectives connexions.
 *
 * @author albertferrando i BerniR4
 * @since 20/07/2018
 * @version 1.0
 */
public class Graf {
    private Llista nodes;

    /**
     * Constructor sense paràmetres.
     */
    public Graf() {
        nodes = new Llista();
    }

    /**
     * Mètode que retorna la quantitat de nodes actual del graf.
     *
     * @return Mida del graf.
     */
    public int mida() {
        return nodes.mida();
    }

    /**
     * Retorna l'element en la posició i del graf. Sense les connexions.
     *
     * @param i Posició de la qual volem recuperar l'element.
     * @return Element recuperat.
     */
    public Object recuperaElement(int i) {
        return ((Node)nodes.recuperar(i)).getElement();
    }

    /**
     * Retorna el node en la posició i del graf.
     *
     * @param i Posició de la qual volem recuperar el node.
     * @return Node recuperat.
     */
    public Node recuperaNode(int i) {
        return (Node) nodes.recuperar(i);
    }

    /**
     * Recupera la llista de connexions d'un node.
     *
     * @param i Posició del node del qual volem recuperar les connexions.
     * @return Llista de connexions del node i.
     */
    public Llista recuperaConnexions(int i) {
        return ((Node)nodes.recuperar(i)).getConnexions();
    }

    /**
     * Recupera la llista de nodes del graf.
     *
     * @return Llista de nodes.
     */
    public Llista recuperaNodes() {
        return nodes;
    }

    /**
     * Afegeix un node al graf. Simplement inicialitzem el node amb l'element que ens passin i creem la llista de connexions.
     *
     * @param c Element del node a afegir.
     */
    public void afegeixNode(Object c) {
        nodes.afegeix(new Node(c));
    }

    /**
     * Afegeix una connexió a la llista de connexions del node i.
     *
     * @param o Connexió a afegir.
     * @param i Posició node origen.
     */
    public void afegeixConnexio(Object o, int i) {
        ((Node)nodes.recuperar(i)).getConnexions().afegeix(o);
    }

    /**
     * Classe node del nostre graf. Cada Node estara comformat per un element i una llista de connexions.
     */
    public class Node {
        Object element;
        Llista connexions;

        /**
         * Constructor amb paràmetre.
         * @param element Element amb el que volem inicialitzar el node.
         */
        Node(Object element) {
            this.element = element;
            connexions = new Llista();
        }

        /**
         * Getter de l'element d'un node.
         * @return Element del node.
         */
        public Object getElement() {
            return element;
        }

        /**
         * Getter de les connexions d'un node.
         * @return Connexions del node.
         */
        public Llista getConnexions() {
            return connexions;
        }
    }
}