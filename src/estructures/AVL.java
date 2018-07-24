package estructures;

/**
 * Clàssic arbre AVL on les claus son Strings. L'ordenament, per tant, es basarà en l'ordre alfabètic.
 *
 * @author albertferrando i BerniR4
 * @since 20/07/2018
 * @version 1.0
 */
public class AVL {
    private Node arrel;

    /**
     * Funció que afegeix un Node a l'arbre. Per a fer-ho tan sols crida a la funció privada afegeix.
     *
     * @param clau Clau que volem afegir junt amb el valor.
     * @param valor Valor que volem afegir junt amb la clau.
     */
    public void afegeix(String clau, Object valor) {
        this.arrel = this.afegeix(this.arrel, clau, valor);
    }

    /**
     * Funció recursiva que s'encarrega d'afegir el Node.
     *
     * @param node Node "arrel".
     * @param clau Clau que volem afegir.
     * @param valor Valor que volem afegir.
     * @return Anirem retornant trossos de arbre per a refer l'arbre total mentre desfem la recursivitat.
     */
    private Node afegeix(Node node, String clau, Object valor) {
        //En cas que el node que rebem sigui null voldrà dir que aquí es on volem afegir el node, per tant, el retornarem.
        if (node == null) {
            return (new Node(clau, valor));
        }
        //En cas contrari, mirarem si ens toca afegir el node al subarbre dret o al subarbre esquerra i tornarem a cridar
        //aquesta mateixa funció passant com a arbre l'arrel del subarbre en concret.
        if (clau.compareTo(node.clau) < 0) {
            node.esquerra = afegeix(node.esquerra, clau, valor);
        } else if (clau.compareTo(node.clau) > 0) {
            node.dreta = afegeix(node.dreta, clau, valor);
        } else {
            //Si entrem aquí voldrà dir que la clau ja existeix a l'arbre, per tant, no hem d'afegir res.
            return node;
        }
        //Un cop arribem aquí voldrà dir que ja hem afegit el nou node, per tant, haurem d'actualitzar l'getAlcada de tots
        //els nodes pels quals hem passat mentre baixàvem.
        node.alcada = 1 + max(getAlcada(node.esquerra), getAlcada(node.dreta));

        //Un cop actualitzada l'getAlcada cal mirar si l'arbre segueix equil·librat i realitzar les rotacions necessàries
        //per a que torni a estar-ho si es que no ho està.
        int balance = factorEquilibri(node);

        if (balance > 1 && factorEquilibri(node.esquerra) > 0) {
            return rotacioLL(node);
        }

        if (balance < -1 && factorEquilibri(node.dreta) < 0) {
            return rotacioRR(node);
        }

        if (balance > 1 && factorEquilibri(node.esquerra) < 0) {
            //Rotació LR
            node.esquerra = rotacioRR(node.esquerra);
            return rotacioLL(node);
        }

        if (balance < -1 && factorEquilibri(node.dreta) > 0) {
            //Rotació RL
            node.dreta = rotacioLL(node.dreta);
            return rotacioRR(node);
        }
        return node;
    }

    /**
     * Aquest mètode s'encarrega de recuperar el valor donada una clau.
     *
     * @param clau Clau de la qual volem recuperar el valor.
     * @return Valor de la clau indicada. Si no hem trobat la clau retornem -1.
     */
    public Object recupera(String clau) {
        Node node = arrel;
        //Mentre no trobem el node que busquem.
        while(!node.clau.equals(clau)) {
            //Comparem la clau que busquem amb la que ens trobem actualment per veure si hem de seguir buscant a dreta o esquerra.
            if(node.clau.compareTo(clau) > 0) {
                //En cas que ja no hi hagin més nodes on cercar retornem -1.
                if(node.esquerra == null){
                    return -1;
                }
                node = node.esquerra;
            } else {
                //En cas que ja no hi hagin més nodes on cercar retornem -1.
                if(node.dreta == null) {
                    return -1;
                }
                node = node.dreta;
            }
        }
        return node.valor;
    }

    /**
     * Mètode que retorna l'getAlcada d'un node. Com que quan cridem aquesta funció no sabem segur si el node és null o no,
     * no podem declarar aquesta funció dins de la mateixa classe.
     *
     * @param node Node del qual volem l'getAlcada.
     * @return Alcada del node.
     */
    private int getAlcada(Node node) {
        if (node == null) {
            return 0;
        }
        return node.alcada;
    }

    /**
     * Funció que ens retorna el valor més gran d'una parella de valors. Ens serà útil per a trobar l'alcada dels nodes.
     *
     * @param a Valor a.
     * @param b Valor b.
     * @return Valor més gran.
     */
    private int max(int a, int b) {
        if(a >= b) {
            return a;
        } else {
            return b;
        }
    }

    private Node rotacioLL(Node arrel) {
        Node x = arrel.esquerra;
        Node T2 = x.dreta;

        x.dreta = arrel;
        arrel.esquerra = T2;

        arrel.alcada = max(getAlcada(arrel.esquerra), getAlcada(arrel.dreta)) + 1;
        x.alcada = max(getAlcada(x.esquerra), getAlcada(x.dreta)) + 1;
        return x;
    }

    private Node rotacioRR(Node arrel) {
        Node y = arrel.dreta;
        Node T2 = y.esquerra;

        y.esquerra = arrel;
        arrel.dreta = T2;
        arrel.alcada = max(getAlcada(arrel.esquerra), getAlcada(arrel.dreta)) + 1;
        y.alcada = max(getAlcada(y.esquerra), getAlcada(y.dreta)) + 1;

        return y;
    }

    /**
     * Mètode que retorna el factor d'equilibri d'un node. Com que no sabem si el node es null o no no podem declarar
     * la funció dins de la mateixa classe Node.
     * En cas que el factor d'equilibri retornat sigui major a 0 voldrà dir que el subarbre esquerra és més alt i en cas
     * que sigui menor a 0 voldrà dir el subarbre dret és més alt.
     *
     * @param node Node del qual volem el factor d'equilibri.
     * @return Factor d'equilibri.
     */
    private int factorEquilibri(Node node) {
        if (node == null) {
            return 0;
        }
        return getAlcada(node.esquerra) - getAlcada(node.dreta);
    }

    /**
     * La classe node representa cada objecte de l'arbre AVL.
     * El node està format per una clau i un valor. A més, també té un paràmetre getAlcada que indica a quina getAlcada es troba
     * i lògicament, té dos punters que condueixen al seu fill dret i esquerra.
     */
    class Node {
        String clau;
        Object valor;
        int alcada;
        Node esquerra, dreta;

        /**
         * Constructor amb paràmetres.
         *
         * @param clau Clau del node.
         * @param valor Valor del node.
         */
        Node(String clau, Object valor) {
            this.clau = clau;
            this.valor = valor;
            alcada = 1;
        }
    }
}