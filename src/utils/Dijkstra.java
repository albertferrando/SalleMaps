package utils;

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

    public Dijkstra(Graf graf, String source, int opcio) {
        this.graf = graf;
        this.opcio = opcio;
        restants = new Llista();
        D = new long[graf.size()];
        M = new long[graf.size()];
        P = new int[graf.size()];
        src = Helper.getInstance().conte(graf, source);
        for(int i = 0; i < graf.size(); i++) {
            restants.afegeix(i);
            D[i] = INF;
        }
        P[src] = -1;
        D[src] = 0;
        M[src] = 0;
    }

    public void calculateRoute(String dest) {
        this.dest = Helper.getInstance().conte(graf, dest);
        if(opcio == 1) {
            while (!restants.buida()) {
                int closest = getClosest();
                System.out.println(closest);
                for(int i = 0; i < graf.size(); i++) {
                    if(i != closest) {
                        Object o = graf.getConnexio(closest, i);
                        if(!Graf.isElementIndefinit(o)) {

                        
                        Connexio c = (Connexio) graf.getConnexio(closest, i);
                        if (c != Graf.elementIndefinit) {
                            if (D[closest] + c.getDistance() < D[i]) {
                                D[i] = D[closest] + c.getDistance();
                                M[i] = M[closest] + c.getDuration();
                                P[i] = closest;
                            }
                        }
                    }
                }
            }
            System.out.println();
            System.out.println("Source: " + ((Ciutat) graf.getElement(src)).getName());
            System.out.println("Destination: " + ((Ciutat) graf.getElement(this.dest)).getName());
            System.out.println("Distance: " + D[this.dest]/1000);
            int[] time = Helper.getInstance().toTime(M[this.dest]);
            String timeString = String.format("%02d:%02d:%02d", time[0], time[1], time[2]);
            System.out.println("Time: " + timeString);
            System.out.println("Pathing: ");
            StringBuilder pathString = new StringBuilder();
            pathString.append(((Ciutat) graf.getElement(this.dest)).getName());
            int i = P[this.dest];
            while(P[i] != -1){
                pathString.insert(0, ((Ciutat) graf.getElement(i)).getName() + "  ->  ");
                i = P[i];
            }
            pathString.insert(0, ((Ciutat) graf.getElement(i)).getName() + "  ->  ");
            System.out.println("\t" + pathString);
        } else {
            while (!restants.buida()) {
                int closest = getClosest();
                for (int i = 0; i < graf.size(); i++) {
                    if (i != closest) {
                        Connexio c = (Connexio) graf.getConnexio(closest, i);
                        if (c != Graf.elementIndefinit) {
                            if (D[closest] + c.getDuration() < D[i]) {
                                D[i] = D[closest] + c.getDuration();
                                M[i] = M[closest] + c.getDistance();
                                P[i] = closest;
                            }
                        }
                    }
                }
            }
            System.out.println();
            System.out.println("Source: " + ((Ciutat) graf.getElement(src)).getName());
            System.out.println("Destination: " + ((Ciutat) graf.getElement(this.dest)).getName());
            System.out.println("Distance: " + M[this.dest]/1000);
            int[] time = Helper.getInstance().toTime(D[this.dest]);
            String timeString = String.format("%02d:%02d:%02d", time[0], time[1], time[2]);
            System.out.println("Time: " + timeString);
            System.out.println("Pathing: ");
            StringBuilder pathString = new StringBuilder();
            pathString.append(((Ciutat) graf.getElement(this.dest)).getName());
            int i = P[this.dest];
            while(P[i] != -1){
                pathString.insert(0, ((Ciutat) graf.getElement(i)).getName() + "  ->  ");
                i = P[i];
            }
            pathString.insert(0, ((Ciutat) graf.getElement(i)).getName() + "  ->  ");
            System.out.println("\t" + pathString);
        }
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
