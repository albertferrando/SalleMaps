package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuOptimitzacio {
    private int opcio;
    private final int MAX_OPCIO = 3;
    private final int MIN_OPCIO = 1;
    private Scanner sc;
    private static MenuOptimitzacio ourInstance = new MenuOptimitzacio();

    public static MenuOptimitzacio getInstance() {
        return ourInstance;
    }

    private MenuOptimitzacio() {
        sc = new Scanner(System.in);
        opcio = 0;
    }

    public void mostraMenu() {
        System.out.println();
        System.out.println("1. Without optimization");
        System.out.println("2. Tree optimization");
        System.out.println("3. TaulaHash optimization");
        System.out.println();
    }

    public void demanaOpcio(){
        System.out.println("Choose an option:");
        try{
            opcio = sc.nextInt();
        } catch(InputMismatchException e){
            sc.next();
            opcio = 0;
        }
    }

    public int getOpcio(){
        return opcio;
    }

    public boolean opcioCorrecta(){
        return opcio <= MAX_OPCIO && opcio >= MIN_OPCIO;
    }
}
