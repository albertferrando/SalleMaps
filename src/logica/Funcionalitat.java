package logica;

import utils.GestorJSON;
import utils.Graf;
import utils.Menu;

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
                            graf.addCity()
                        }
                    }else {
                        System.out.println("You must import a map before executing option 2.");
                    }
                    break;

                case 3:
                    if(Menu.getInstance().isMapLoaded()) {

                    }else {
                        System.out.println("You must import a map before executing option 3.");
                    }
                    break;
            }
        }
    }
}
