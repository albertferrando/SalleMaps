package utils;

public class GestorAPI {
    private static final String API_KEY = "&key=AIzaSyD3FSLnYCS3MxLg9MbWVVjOtxPmyhEG7OA";
    private static final String URL = "http://maps.googleapis.com/maps/api/distancematrix/json?units=metric";
    private static GestorAPI ourInstance = new GestorAPI();

    public static GestorAPI getInstance() {
        return ourInstance;
    }

    private GestorAPI() {
    }


}
