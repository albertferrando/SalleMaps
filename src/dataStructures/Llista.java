package dataStructures;

/**
 * Llista és una llista encadenada de tota la vida.
 *
 * @author albertferrando i BerniR4
 * @since 06/07/2018
 * @version 1.0
 */
public class Llista {
    private Node cap;
    private int numNodes;

    /**
     * Constructor sense paràmetres.
     */
    public Llista() {}

    /**
     * Afegeix un element al final de la llista.
     *
     * @param element Element a afegir.
     */
    public void afegeix(Object element) {
        if(cap == null) {
            this.cap = new Node(element);
        } else {
            Node aux = cap;
            while (aux.seg != null) {
                aux = aux.seg;
            }
            aux.seg = new Node(element);
        }
        numNodes++;
    }

    /**
     * Aquest mètode canvia l'element que es troba en la posició indicada per index.
     *
     * @param index Indica quin element volem modificar.
     * @param element Element actualitzat que volem posar a la posicio index.
     */
    void posarAlIndex(int index, Object element) {
        if(index != -1) {
            Node aux = cap;
            for (int i = 0; i < index; i++) {
                aux = aux.seg;
            }
            aux.element = element;
        } else {
            System.out.println("Invalid index.");
        }
    }

    /**
     * Getter de l'element en la posició indicada per index.
     *
     * @param index Indica la posicio de l'element que volem recuperar.
     * @return Element de la posició index.
     */
    public Object recuperar(int index) {
        Node aux = cap;
        if(index != -1) {
            for (int i = 0; i < index; i++) {
                if(aux.seg != null) {
                    aux = aux.seg;
                }
            }
        } else {
            System.out.println("Invalid index.");
        }
        return aux.element;
    }

    /**
     * Elimina de la llista l'element que rep per paràmetre.
     *
     * @param element Element a eliminar.
     */
    public void elimina(Object element) {
        if(!this.buida()) {
            if(cap.element.equals(element)) {
                cap = cap.seg;
                numNodes--;
                return;
            }
            Node aux = cap;
            while (aux.seg != null) {
                if(aux.seg.element.equals(element)) {
                    if(aux.seg.seg != null) {
                        aux.seg = aux.seg.seg;
                        break;
                    } else {
                        aux.seg = null;
                        break;
                    }
                }
                aux = aux.seg;
            }
            numNodes--;
        } else {
            System.out.println("No es pot eliminar l'element ja que la llista està buida.");
        }
    }

    /**
     * Comprova que la llista contingui l'element passat per paràmetre.
     *
     * @param element L'element que volem saber si la llista conté o no.
     * @return Cert en cas que el contingui. Fals en el cas contrari.
     */
    public boolean conte(Object element) {
        Node aux = cap;
        for(int i = 0; i < numNodes; i++){
            if(aux.element.equals(element)) {
                return true;
            }
            if(aux.seg != null) {
                aux = aux.seg;
            }
        }
        return false;
    }

    /**
     * Retorna la quantitat de nodes de la llista.
     *
     * @return Quantitat de nodes de la llista.
     */
    public int mida() {
        return numNodes;
    }

    /**
     * Comprova si la llista és buida.
     *
     * @return Retorna cert en cas que ho estigui i fals en cas contrari.
     */
    public boolean buida() {
        return numNodes == 0;
    }

    /**
     * La classe node representa cada objecte de la llista.
     * Està format per la part que conté la informació (l'element) i per un punter que serà el que s'encadenarà amb el
     * node següent.
     */
    class Node {
        private Object element;
        private Node seg;

        /**
         * Constructor amb paràmentres.
         *
         * @param element Element amb el qual es vol inicialitzar el node.
         */
        Node(Object element) {
            this.element = element;
        }
    }
}
