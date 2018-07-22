package estructures;

/**
 * Graf típic que conté una llista de nodes i una matriu de connexions.
 *
 * @author albertferrando i BerniR4
 * @since 20/07/2018
 * @version 1.0
 */
public class Graf {
    //Llista de nodes del graf.
    private Llista nodes;
    //Llista de connexions del graf: Matriu cuadrada on la posició i, j indica la connexió entre el node i i el j.
    private Llista connexions;
    //Element indefinit que indicarà que no hi ha connexió entre dos nodes.
    public static final Object elementIndefinit = elementIndefinit();

    /**
     * Constructor sense paràmetres.
     */
    public Graf() {
        nodes = new Llista();
        connexions = new Llista();
    }

    /**
     * Retorna l'element indefinit, en aquest cas -1.
     * @return Element indefinit.
     */
    private static Object elementIndefinit() {
        return -1;
    }

    /**
     * Mètode que comprova si l'objecte passat per paràmetre és l'element indefinit.
     *
     * @param o Objecte que volem comprovar si és element indefinit.
     * @return Cert en cas que si que ho sigui, fals en cas contrari.
     */
    public static boolean isElementIndefinit(Object o) {
        return o.equals(elementIndefinit());
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
     * Retorna el node en la posició i del graf.
     *
     * @param i Posició de la qual volem recuperar el node.
     * @return Node recuperat.
     */
    public Object recuperaNode(int i) {
        return nodes.recuperar(i);
    }

    /**
     * Recupera la llista de connexions d'un node.
     *
     * @param i Posició del node del qual volem recuperar les connexions.
     * @return Llista de connexions del node i.
     */
    public Llista recuperaConnexions(int i) {
        return (Llista) connexions.recuperar(i);
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
     * Afegeix un node al graf. Per a fer-ho, l'afegeix a la llista de nodes i amplia la mida de la matriu (que és quadrada)
     * en 1. Inicialitza totes les connexions noves a elementIndefinit.
     *
     * @param c Node a afegir.
     */
    public void afegeixNode(Object c) {
        for(int i = 0; i < nodes.mida(); i++) {
            Llista l = (Llista) connexions.recuperar(i);
            l.afegeix(elementIndefinit);
        }
        nodes.afegeix(c);
        connexions.afegeix(new Llista());
        Llista l = (Llista) connexions.recuperar(nodes.mida());
        for(int i = 0; i < nodes.mida(); i++) {
            l.afegeix(elementIndefinit);
        }
    }

    /**
     * Afegeix una connexió entre el node i i el node j.
     *
     * @param o Connexió a afegir.
     * @param i Posició node origen.
     * @param j Posició node destí.
     */
    public void afegeixConnexio(Object o, int i, int j) {
        ((Llista)connexions.recuperar(i)).posarAlIndex(j, o);
    }

    /**
     * Recupera la connexió entre el node i i el j.
     *
     * @param i Posició node origen.
     * @param j Posició node destí.
     * @return Connexió entre i i j.
     */
    public Object recuperaConnexio(int i, int j) {
        return ((Llista) connexions.recuperar(i)).recuperar(j);
    }
}