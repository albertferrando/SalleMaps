package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private boolean mapLoaded;
    private int opcio;
    private final int MAX_OPCIO = 4;
    private final int MIN_OPCIO = 1;
    private Scanner sc;

    private static Menu ourInstance = new Menu();

    public static Menu getInstance() {
        return ourInstance;
    }

    private Menu() {
        sc = new Scanner(System.in);
        opcio = 0;
        mapLoaded = false;
    }

    public int getOpcio(){
        return opcio;
    }

    public void mostraMenu() {
        System.out.println();
        System.out.println("1. Import map");
        System.out.println("2. Search city");
        System.out.println("3. Calculate route");
        System.out.println("4. Shut down");
        System.out.println();
    }

    public void demanaOpcio(){
        System.out.println("Choose an option:");
        try{
            opcio = sc.nextInt();
            if(opcio > MAX_OPCIO || opcio < MIN_OPCIO){
                System.out.println();
                System.out.println("Invalid option, try again:");
                System.out.println();
            }
        } catch(InputMismatchException e){
            System.out.println();
            System.out.println("Invalid option, try again:");
            System.out.println();
            sc.next();
            opcio = 0;
        }
    }

    public boolean isMapLoaded() {
        return mapLoaded;
    }

    public void setMapLoaded(boolean mapLoaded) {
        this.mapLoaded = mapLoaded;
    }

    public boolean opcioCorrecta(){
        return opcio <= MAX_OPCIO && opcio >= MIN_OPCIO;
    }

    public boolean sortir(){
        return opcio == MAX_OPCIO;
    }
}
