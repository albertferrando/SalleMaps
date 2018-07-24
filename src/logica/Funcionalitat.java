package logica;

import estructures.AVL;
import estructures.Graf;
import estructures.TaulaHash;
import model.Ciutat;
import utils.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Funcionalitat {
    private Graf graf;
    private AVL arbre;
    private TaulaHash taulaHash;

    public void executaOpcio(int opcio) {
        double timeStart = 0;
        double aux = 0;
        Scanner sc = new Scanner(System.in);
        if(opcio != 4) {
            switch (opcio) {
                case 1:
                    System.out.println();
                    System.out.println("Indicate the name of the json file from which you want to load the graph:");
                    String nomFitxer = sc.next();
                    graf = GestorJSON.getInstance().carregaGraf(nomFitxer);
                    if(graf != null) {
                        Menu.getInstance().setMapLoaded(true);
                    }
                    arbre = GestorJSON.getInstance().carregaArbre();
                    taulaHash = GestorJSON.getInstance().carregaHashtable();
                    Helper.getInstance().setArbre(arbre);
                    Helper.getInstance().setTaulaHash(taulaHash);
                    break;

                case 2:
                    if(Menu.getInstance().isMapLoaded()) {
                        do {
                            MenuOptimitzacio.getInstance().mostraMenu();
                            MenuOptimitzacio.getInstance().demanaOpcio();
                        } while (!MenuOptimitzacio.getInstance().opcioCorrecta());
                        int optimization = MenuOptimitzacio.getInstance().getOpcio();
                        System.out.println();
                        System.out.println("Indicate the city you want to search:");
                        String nomCiutat = sc.nextLine();
                        timeStart = System.nanoTime();
                        int i = Helper.getInstance().searchCity(optimization, nomCiutat);
                        if(i != -1) {
                            Ciutat c = (Ciutat) graf.recuperaNode(i);
                            c.toString(graf.recuperaConnexions(i), graf.recuperaNodes());
                        } else {
                            if(Helper.getInstance().addNewCity(optimization, nomCiutat)) {
                                arbre.afegeix(nomCiutat, graf.mida() - 1);
                                taulaHash.afegeix(nomCiutat, graf.mida() - 1);
                                Ciutat c = (Ciutat) graf.recuperaNode(graf.mida() - 1);
                                c.toString(graf.recuperaConnexions(graf.mida() - 1), graf.recuperaNodes());
                            }
                        }
                    }else {
                        System.out.println("You must import a map before executing option 2.");
                    }
                    break;

                case 3:
                    if(Menu.getInstance().isMapLoaded()) {
                        do {
                            MenuOptimitzacio.getInstance().mostraMenu();
                            MenuOptimitzacio.getInstance().demanaOpcio();
                        } while (!MenuOptimitzacio.getInstance().opcioCorrecta());
                        int optimization = MenuOptimitzacio.getInstance().getOpcio();
                        int valid = 0;
                        System.out.println();
                        System.out.println("Indicate the source city: ");
                        String src = sc.nextLine();
                        aux = System.nanoTime();
                        int j = Helper.getInstance().searchCity(optimization, src);
                        aux = System.nanoTime() - aux;
                        if(j != -1) {
                            valid = 1;
                            System.out.println();
                            System.out.println("Indicate the destination city: ");
                            String dest = sc.nextLine();
                            timeStart = System.nanoTime();
                            int i = Helper.getInstance().searchCity(optimization, dest);
                            if(i != -1) {
                                valid = 2;
                                System.out.println(System.getProperty("line.separator") + "1. Shortest route" +
                                        System.getProperty("line.separator") + "2. Fastest route" + System.getProperty("line.separator"));
                                System.out.println("Choose an option: ");
                                do {
                                    try {
                                        opcio = sc.nextInt();
                                        if (opcio != 1 && opcio != 2) {
                                            System.out.println();
                                            System.out.println("Invalid option, try again:");
                                            System.out.println();
                                        } else {
                                            Dijkstra d = new Dijkstra(graf, j, opcio);
                                            d.calculateRoute(i);
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println();
                                        System.out.println("Invalid option, try again:");
                                        System.out.println();
                                        sc.next();
                                        opcio = -1;
                                    }
                                } while (opcio != 1 && opcio != 2);
                            }
                        }
                        if(valid == 1) {
                            System.out.println("The destination city indicated is not registered in the system.");
                        } else if(valid == 0) {
                            System.out.println("The source city indicated is not registered in the system.");
                        }
                    }else {
                        System.out.println("You must import a map before executing option 3.");
                    }
                    break;
            }
        }
        if(opcio > 1) {
            double timeFinal = System.nanoTime();
            System.out.println();
            System.out.println("Process time: " + (timeFinal + aux - timeStart) / 1000000 + " milliseconds.");
        }
    }
}
