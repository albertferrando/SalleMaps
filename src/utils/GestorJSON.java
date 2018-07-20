package utils;

import com.google.gson.*;
import dataStructures.AVL;
import dataStructures.Graf;
import model.Ciutat;
import model.Connexio;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GestorJSON {
    private static GestorJSON ourInstance = new GestorJSON();
    private static final String FILEPATH = "data" + System.getProperty("file.separator");
    private JsonObject jO;

    public static GestorJSON getInstance() {
        return ourInstance;
    }

    private GestorJSON() {
    }

    public Graf carregaGraf(String nomFitxer) {
        Graf graf = new Graf();
        Helper.getInstance().setGraf(graf);
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(FILEPATH + nomFitxer));
            if(!jsonElement.toString().equals("null")) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray ciutats = jsonObject.get("cities").getAsJsonArray();
                for (int i = 0; i < ciutats.size(); i++) {
                    Ciutat c = gson.fromJson(ciutats.get(i), Ciutat.class);
                    graf.afegeixNode(c);
                }
                JsonArray connexions = jsonObject.get("connections").getAsJsonArray();
                for(int i = 0; i < connexions.size(); i++) {
                    Connexio c = gson.fromJson(connexions.get(i), Connexio.class);
                    graf.afegeixConnexio(c, Helper.getInstance().conte(c.getFrom()),
                            Helper.getInstance().conte(c.getTo()));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Invalid name of json file. Make sure that the json file is in the data folder.");
            return null;
        }
        return graf;
    }

    public AVL carregaArbre(String nomFitxer) {
        AVL arbre = new AVL();
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(FILEPATH + nomFitxer));
            if (!jsonElement.toString().equals("null")) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray ciutats = jsonObject.get("cities").getAsJsonArray();
                for (int i = 0; i < ciutats.size(); i++) {
                    Ciutat c = gson.fromJson(ciutats.get(i), Ciutat.class);
                    arbre.insert(c.getName(), i);
                }
            }
        } catch (FileNotFoundException e) {}
        return arbre;
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
