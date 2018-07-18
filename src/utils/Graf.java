package utils;

public class Graf {
    private Llista nodes;
    private Llista connexions;
    public static final Object elementIndefinit = elementIndefinit();

    Graf() {
        nodes = new Llista();
        connexions = new Llista();
    }

    private static Object elementIndefinit() {
        return -1;
    }

    public static boolean isElementIndefinit(Object o) {
        return o == elementIndefinit();
    }

    public int size() {
        return nodes.mida();
    }

    public Object getElement(int i) {
        return nodes.recuperar(i);
    }

    public Llista getConnexions(int i) {
        return (Llista) connexions.recuperar(i);
    }

    public Llista getElements() {
        return nodes;
    }

    void addNode(Object c) {
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

    public void addConnection(Object o, int i, int j) {
        ((Llista)connexions.recuperar(i)).posarAlIndex(j, o);
    }

    public int getIndex(Object o) {
        for(int i = 0; i < nodes.mida(); i++) {
            if(nodes.recuperar(i).equals(o)) {
                return i;
            }
        }
        return -1;
    }

    public Object getConnexio(int i, int j) {
        return ((Llista) connexions.recuperar(i)).recuperar(j);
    }
}