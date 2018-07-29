package utils;

import estructures.Graf;
import estructures.Llista;
import model.Ciutat;
import model.Connexio;

/**
 * Dijkstra típic. Restants son la llista de nodes per explorar.
 * D és l'array que indica el cost actual per arribar el node (ja sigui temps o distancia).
 * M és l'array que anirà acumulant el cost que no és el principal (per tant, si D acumula temps, M acumula distància i al reves.
 * P és l'array que indica el node anterior.
 */
public class Dijkstra {
    private Graf graf;
    private int opcio;
    private final long INF = 999999999;
    private Llista restants;
    private long D[];
    private long M[];
    private int P[];
    private int src;
    private int dest;
    private int opt;

    /**
     * Inicialitzem tot.
     * @param graf El necessitarem per a recuperar les connexions.
     * @param source Node source.
     * @param opcio Opcio que ens diu si volem buscar shortest o fastest.
     * @param optimization Opció que ens diu el tipus d'optimització.
     */
    public Dijkstra(Graf graf, int source, int opcio, int optimization) {
        this.graf = graf;
        this.opcio = opcio;
        this.opt = optimization;
        restants = new Llista();
        //A l'array D anirem acumulant la distància o el temps segons l'opció triada.
        D = new long[graf.mida()];
        //A l'array M anirem acumulant l'altre que no estigui acumulant D.
        M = new long[graf.mida()];
        //P indicarà el node previ per arribar al node de l'índex d'aquest.
        P = new int[graf.mida()];
        src = source;
        for(int i = 0; i < graf.mida(); i++) {
            restants.afegeix(i);
            D[i] = INF;
        }
        P[src] = -1;
        D[src] = 0;
        M[src] = 0;
    }

    public void calculateRoute(int dest) {
        double timeStart = System.nanoTime();
        this.dest = dest;
        //Segons opció mirarem la distància o el temps, però serà exactament igual el procés.

        if(opcio == 1) {
            //Mentre quedin nodes per explorar.
            while (!restants.buida()) {
                //Trobo quin node restant està més aprop i el recupero.
                int closest = getClosest();
                //Agafo les connexions del node triat.
                Llista connexions = graf.recuperaConnexions(closest);
                for (int i = 0; i < connexions.mida(); i++) {
                    //Recupero la connexió i l'index del node desti.
                    Connexio c = (Connexio) connexions.recuperar(i);
                    int to = Helper.getInstance().searchCity(opt, c.getTo());
                    //Si el nou camí es millor que el camí que ja existia l'actualitzem.
                    if (D[closest] + c.getDistance() < D[to]) {
                        D[to] = D[closest] + c.getDistance();
                        M[to] = M[closest] + c.getDuration();
                        P[to] = closest;
                    }
                }
            }
        } else {
            while (!restants.buida()) {
                int closest = getClosest();
                Llista connexions = graf.recuperaConnexions(closest);
                for (int i = 0; i < connexions.mida(); i++) {
                    Connexio c = (Connexio) connexions.recuperar(i);
                    int to = Helper.getInstance().searchCity(opt, c.getTo());
                    if (D[closest] + c.getDuration() < D[to]) {
                        D[to] = D[closest] + c.getDuration();
                        M[to] = M[closest] + c.getDistance();
                        P[to] = closest;
                    }
                }
            }
        }
        double timeFinal = System.nanoTime();
        System.out.println();
        System.out.println("Source: " + ((Ciutat) graf.recuperaElement(src)).getName());
        System.out.println("Destination: " + ((Ciutat) graf.recuperaElement(this.dest)).getName());
        if(opcio != 1) {
            System.out.println("Distance: " + M[this.dest]/1000 + " km.");
            int[] time = Helper.getInstance().toTime(D[this.dest]);
            String timeString = String.format("%02d:%02d:%02d", time[0], time[1], time[2]);
            System.out.println("Time: " + timeString);
        } else {
            System.out.println("Distance: " + D[this.dest]/1000 + " km.");
            int[] time = Helper.getInstance().toTime(M[this.dest]);
            String timeString = String.format("%02d:%02d:%02d", time[0], time[1], time[2]);
            System.out.println("Time: " + timeString);
        }
        System.out.println("Pathing: ");
        StringBuilder pathString = new StringBuilder();
        pathString.append(((Ciutat) graf.recuperaElement(this.dest)).getName());
        int i = P[this.dest];
        while(P[i] != -1){
            pathString.insert(0, ((Ciutat) graf.recuperaElement(i)).getName() + "  ->  ");
            i = P[i];
        }
        pathString.insert(0, ((Ciutat) graf.recuperaElement(i)).getName() + "  ->  ");
        System.out.println("\t" + pathString);
        System.out.println();
        System.out.println("\tSearch time: " + (timeFinal - timeStart) / 1000 + " microseconds.");
    }

    private int getClosest() {
        int closest = 0;
        long min = INF;

        for(int i = 0; i < this.restants.mida(); i++) {
            int aux = (int) restants.recuperar(i);
            if(D[aux] <= min && restants.conte(aux)) {
                closest = aux;
                min = D[aux];
            }
        }
        restants.elimina(closest);
        return closest;
    }
}
