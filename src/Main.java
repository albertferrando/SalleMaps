import logica.Funcionalitat;
import utils.Menu;

public class Main {
    public static void main(String[] args) {
        Funcionalitat funcionalitat = new Funcionalitat();

        do {
            do {
                Menu.getInstance().mostraMenu();
                Menu.getInstance().demanaOpcio();
            } while (!Menu.getInstance().opcioCorrecta());
            funcionalitat.executaOpcio(Menu.getInstance().getOpcio());
        } while (!Menu.getInstance().sortir());
    }
}
