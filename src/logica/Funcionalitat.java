package logica;

import utils.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Funcionalitat {
    private Graf graf;

    public void executaOpcio(int opcio) {
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

                    //TODO Carregar el mapa en un hash map.

                    //TODO Carregar el mapa en un arbre.

                    break;

                case 2:
                    if(Menu.getInstance().isMapLoaded()) {
                        System.out.println();
                        System.out.println("Indicate the city you want to search:");
                        String nomCiutat = sc.next();
                        int i = graf.conte(nomCiutat);
                        if(i != -1) {
                            graf.toString(i);
                        } else {
                            if(graf.addNewCity(nomCiutat)) {
                                graf.toString(-1);
                            }
                        }
                    }else {
                        System.out.println("You must import a map before executing option 2.");
                    }
                    break;

                case 3:
                    if(Menu.getInstance().isMapLoaded()) {
                        int valid = 0;
                        System.out.println();
                        System.out.println("Indicate the source city: ");
                        String src = sc.nextLine();
                        String dest;
                        if(graf.conte(src) != -1) {
                            valid = 1;
                            System.out.println();
                            System.out.println("Indicate the destination city: ");
                            dest = sc.nextLine();
                            if(graf.conte(dest) != -1) {
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
                                            Dijkstra d = new Dijkstra(graf, src, opcio);
                                            d.calculateRoute(dest);
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
    }
}
