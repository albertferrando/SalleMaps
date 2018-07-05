package utils;

import com.google.gson.*;
import model.Ciutat;
import model.Connexio;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GestorJSON {
    private static GestorJSON ourInstance = new GestorJSON();
    private static final String FILEPATH = "data" + System.getProperty("file.separator");

    public static GestorJSON getInstance() {
        return ourInstance;
    }

    private GestorJSON() {
    }

    public Graf carregaGraf(String nomFitxer) {
        Graf graf = new Graf();
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(FILEPATH + nomFitxer));
            if(!jsonElement.toString().equals("null")) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray ciutats = jsonObject.get("cities").getAsJsonArray();
                for (int i = 0; i < ciutats.size(); i++) {
                    Ciutat c = gson.fromJson(ciutats.get(i), Ciutat.class);
                    graf.addCity(c);
                }
                graf.inicialitzaConnexions();
                JsonArray connexions = jsonObject.get("connections").getAsJsonArray();
                for(int i = 0; i < connexions.size(); i++) {
                    Connexio c = gson.fromJson(connexions.get(i), Connexio.class);
                    graf.addConnection(c);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Invalid name of json file. Make sure that the json file is in the data folder.");
            return null;
        }
        return graf;
    }
}
