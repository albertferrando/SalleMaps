package utils;

import com.google.gson.*;
import estructures.AVL;
import estructures.Graf;
import estructures.TaulaHash;
import model.Ciutat;
import model.Connexio;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GestorJSON {
    private static GestorJSON ourInstance = new GestorJSON();
    private static final String FILEPATH = "data" + System.getProperty("file.separator");
    private JsonObject jO;
    private JsonArray cities;
    private JsonArray connections;

    public static GestorJSON getInstance() {
        return ourInstance;
    }

    private GestorJSON() {
    }

    public void carregaEstructures(String nomFitxer) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement;
        try {
            jsonElement = parser.parse(new FileReader(FILEPATH + nomFitxer));
            if(!jsonElement.toString().equals("null")) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                cities = jsonObject.get("cities").getAsJsonArray();
                connections = jsonObject.get("connections").getAsJsonArray();
            }
            Graf graf = new Graf();
            AVL arbre = new AVL();
            TaulaHash taulaHash = new TaulaHash(cities.size() * 60 / 100);
            Helper.getInstance().setGraf(graf);
            Helper.getInstance().setArbre(arbre);
            Helper.getInstance().setTaulaHash(taulaHash);
            Gson gson = new Gson();
            for (int i = 0; i < cities.size(); i++) {
                Ciutat c = gson.fromJson(cities.get(i), Ciutat.class);
                graf.afegeixNode(c);
                arbre.afegeix(c.getName(), i);
                taulaHash.afegeix(c.getName(), i);
            }
            for(int i = 0; i < connections.size(); i++) {
                Connexio c = gson.fromJson(connections.get(i), Connexio.class);
                graf.afegeixConnexio(c, Helper.getInstance().searchCity(3, c.getFrom()));
            }
            Menu.getInstance().setMapLoaded(true);
        } catch (FileNotFoundException e1) {
            System.out.println("Invalid name of json file. Make sure that the json file is in the data folder.");
        }
    }

    public Boolean checkStatus(String s) {
        JsonParser parser = new JsonParser();
        jO = (JsonObject) parser.parse(s);
        return jO.get("status").getAsString().equals("OK");
    }

    public int checkIfCity() {
        JsonArray results = jO.get("results").getAsJsonArray();
        for(int i = 0; i < results.size(); i++) {
            JsonArray types = results.get(i).getAsJsonObject().get("types").getAsJsonArray();
            for(int j = 0; j < types.size(); j++) {
                if(types.get(j).getAsString().equals("locality")) {
                    return i;
                }
            }
        }
        return -1;
    }

    public Ciutat parseCity(int isCity) {
        Ciutat ciutat = new Ciutat();

        JsonObject city = jO.get("results").getAsJsonArray().get(isCity).getAsJsonObject();
        JsonArray jsonArray = city.get("address_components").getAsJsonArray();
        for(int i = 0; i < jsonArray.size(); i++) {
            JsonArray types = jsonArray.get(i).getAsJsonObject().get("types").getAsJsonArray();
            for(int j = 0; j < types.size(); j++) {
                if(types.get(j).getAsString().equals("locality")) {
                    ciutat.setName(jsonArray.get(i).getAsJsonObject().get("long_name").getAsString());
                }
                if(types.get(j).getAsString().equals("country")) {
                    ciutat.setCountry(jsonArray.get(i).getAsJsonObject().get("short_name").getAsString());
                }
            }
        }
        ciutat.setAddress(city.get("formatted_address").getAsString());
        JsonObject location = city.get("geometry").getAsJsonObject().get("location").getAsJsonObject();
        ciutat.setLatitude(location.get("lat").getAsDouble());
        ciutat.setLongitude(location.get("lng").getAsDouble());
        return ciutat;
    }

    public long getDistance(String s, int i) {
        JsonParser parser = new JsonParser();
        jO = (JsonObject) parser.parse(s);
        JsonArray jsonArray = jO.get("rows").getAsJsonArray().get(0).getAsJsonObject().get("elements").getAsJsonArray();
        return jsonArray.get(i).getAsJsonObject().get("distance").getAsJsonObject().get("value").getAsLong();
    }

    public long getDuration(String s, int i) {
        JsonParser parser = new JsonParser();
        jO = (JsonObject) parser.parse(s);
        JsonArray jsonArray = jO.get("rows").getAsJsonArray().get(0).getAsJsonObject().get("elements").getAsJsonArray();
        return jsonArray.get(i).getAsJsonObject().get("duration").getAsJsonObject().get("value").getAsLong();
    }
}
