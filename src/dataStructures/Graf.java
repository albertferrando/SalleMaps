package dataStructures;

public class Graf {
    private Llista nodes;
    private Llista connexions;
    public static final Object elementIndefinit = elementIndefinit();

    public Graf() {
        nodes = new Llista();
        connexions = new Llista();
    }

    private static Object elementIndefinit() {
        return -1;
    }

    public static boolean isElementIndefinit(Object o) {
        return o == elementIndefinit();
    }

    public int mida() {
        return nodes.mida();
    }

    public Object recuperaNode(int i) {
        return nodes.recuperar(i);
    }

    public Llista recuperaConnexions(int i) {
        return (Llista) connexions.recuperar(i);
    }

    public Llista recuperaNodes() {
        return nodes;
    }

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

    public void afegeixConnexio(Object o, int i, int j) {
        ((Llista)connexions.recuperar(i)).posarAlIndex(j, o);
    }

    public Object recuperaConnexio(int i, int j) {
        return ((Llista) connexions.recuperar(i)).recuperar(j);
    }
}