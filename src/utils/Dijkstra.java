package utils;

import estructures.Graf;
import estructures.Llista;
import model.Ciutat;
import model.Connexio;

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

    public Dijkstra(Graf graf, int source, int opcio, int optimization) {
        this.graf = graf;
        this.opcio = opcio;
        this.opt = optimization;
        restants = new Llista();
        D = new long[graf.mida()];
        M = new long[graf.mida()];
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
        if(opcio == 1) {
            while (!restants.buida()) {
                int closest = getClosest();
                Llista connexions = graf.recuperaConnexions(closest);
                for (int i = 0; i < connexions.mida(); i++) {
                    Connexio c = (Connexio) connexions.recuperar(i);
                    int to = Helper.getInstance().searchCity(opt, c.getTo());
                    if (D[closest] + c.getDistance() < D[to]) {
                        D[to] = D[closest] + c.getDuration();
                        M[to] = M[closest] + c.getDistance();
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
        System.out.println("Distance: " + M[this.dest]/1000);
        int[] time = Helper.getInstance().toTime(D[this.dest]);
        String timeString = String.format("%02d:%02d:%02d", time[0], time[1], time[2]);
        System.out.println("Time: " + timeString);
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
        System.out.println("\tSearch time: " + (timeFinal - timeStart) / 1000000 + " milliseconds.");
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
