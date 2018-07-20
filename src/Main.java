import Network.WSGoogleMaps;
import logica.Funcionalitat;
import utils.Helper;
import utils.Menu;

public class Main {
    public static void main(String[] args) {
        Funcionalitat funcionalitat = new Funcionalitat();
        WSGoogleMaps.getInstance().setApiKey("AIzaSyD3FSLnYCS3MxLg9MbWVVjOtxPmyhEG7OA");
        do {
            do {
                Menu.getInstance().mostraMenu();
                Menu.getInstance().demanaOpcio();
            } while (!Menu.getInstance().opcioCorrecta());
            funcionalitat.executaOpcio(Menu.getInstance().getOpcio());
        } while (!Menu.getInstance().sortir());
    }
}
