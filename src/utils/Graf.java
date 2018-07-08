package utils;

import Network.HttpRequest;
import Network.WSGoogleMaps;
import model.Ciutat;
import model.Connexio;

public class Graf {
    private Llista ciutats;
    private Llista connexions;

    Graf() {
        ciutats = new Llista();
        connexions = new Llista();
    }

    int size() {
        return ciutats.mida();
    }

    Object getElement(int i) {
        return ciutats.recuperar(i);
    }

    void addCity(Ciutat c) {
        for(int i = 0; i < ciutats.mida(); i++) {
            Llista l = (Llista) connexions.recuperar(i);
            l.afegeix(Connexio.elementIndefinit);
        }
        ciutats.afegeix(c);
        connexions.afegeix(new Llista());
        Llista l = (Llista) connexions.recuperar(ciutats.mida());
        for(int i = 0; i < ciutats.mida(); i++) {
            l.afegeix(Connexio.elementIndefinit);
        }
    }

    void addConnection(Connexio c) {
        int from = getIndex(c.getFrom());
        int to = getIndex(c.getTo());
        ((Llista)connexions.recuperar(from)).posarAlIndex(to, c);
    }

    private int getIndex(String city) {
        for(int i = 0; i < ciutats.mida(); i++) {
            Ciutat c = (Ciutat) ciutats.recuperar(i);
            if(c.getName().equals(city)) {
                return i;
            }
        }
        return -1;
    }

    public int conte(String nomCiutat) {
        for(int i = 0; i < ciutats.mida(); i++) {
            if(((Ciutat)ciutats.recuperar(i)).getName().equals(nomCiutat)) {
                return i;
            }
        }
        return -1;
    }

    public void toString(int i) {
        if(i == -1) {
            i = ciutats.mida() - 1;
        }
        System.out.println("Name: " + ((Ciutat)ciutats.recuperar(i)).getName());
        System.out.println("Country: " + ((Ciutat)ciutats.recuperar(i)).getCountry());
        System.out.println("Latitude: " + ((Ciutat)ciutats.recuperar(i)).getLatitude());
        System.out.println("Longitude: " + ((Ciutat)ciutats.recuperar(i)).getLongitude());
        System.out.println();
        System.out.println("Close cities: ");
        Llista l = (Llista) connexions.recuperar(i);
        for(int j = 0; j < ciutats.mida(); j++) {
            Connexio c = (Connexio) l.recuperar(j);
            if(!c.isElementIndefinit()) {
                Ciutat city = (Ciutat) ciutats.recuperar(j);
                System.out.println();
                System.out.println("\tName: " + city.getName());
                System.out.println("\tCountry: " + city.getCountry());
                System.out.println("\tLatitude: " + city.getLatitude());
                System.out.println("\tLongitude: " + city.getLongitude());
                int[] time = Helper.getInstance().toTime(c.getDuration());
                String timeString = String.format("%02d:%02d:%02d", time[0], time[1], time[2]);
                System.out.println("\tTime: " + timeString);
                System.out.println("\tDistance: " + c.getDistance()/1000);
            }
        }
    }

    public boolean addNewCity(String nomCiutat) {
        final Boolean[] addded = {false};

        WSGoogleMaps.getInstance().geolocate(nomCiutat, new HttpRequest.HttpReply() {
            @Override
            public void onSuccess(String s) {
                Boolean isOK = GestorJSON.getInstance().checkStatus(s);
                int isCity = -1;
                if(isOK) {
                    isCity = GestorJSON.getInstance().checkIfCity();
                    if (isCity != -1) {
                        Ciutat c = GestorJSON.getInstance().parseCity(isCity);
                        if(conte(c.getName()) == -1) {
                            addCity(c);
                            System.out.println();
                            System.out.println("NEW CITY ADDED");
                            System.out.println();
                            addded[0] = true;
                            connectCity();
                        } else {
                            System.out.println("The indicated city is already in the system.");
                            System.out.println();
                        }
                    }
                }

                if(!isOK || isCity == -1) {
                    System.out.println();
                    System.out.println("Invalid city name. Make sure to write a city name.");
                }
            }

            @Override
            public void onError(String s) {
                System.out.println();
                System.out.println("Error while doing the HTTP request.");
            }
        });
        return addded[0];
    }

    private void connectCity() {
        Connexio propera = new Connexio();
        Ciutat src = (Ciutat) ciutats.recuperar(ciutats.mida() - 1);
        double latitudes[] = new double[ciutats.mida()];
        double longitudes[] = new double[ciutats.mida()];

        for(int i = 0; i < ciutats.mida() - 1; i++) {
            latitudes[i] = ((Ciutat) ciutats.recuperar(i)).getLatitude();
            longitudes[i] = ((Ciutat) ciutats.recuperar(i)).getLongitude();
        }

        WSGoogleMaps.getInstance().distance(src.getLatitude(), src.getLongitude(), latitudes, longitudes,
                new HttpRequest.HttpReply() {
                    @Override
                    public void onSuccess(String s) {
                        int quina = 0;
                        long distancia = GestorJSON.getInstance().getDistance(s,0);
                        for(int i = 1; i < ciutats.mida() - 1; i++) {
                            long newDistance = GestorJSON.getInstance().getDistance(s, i);
                            if(newDistance < distancia) {
                                distancia = newDistance;
                                quina = i;
                            }
                        }
                        propera.setDistance(distancia);
                        propera.setDuration(GestorJSON.getInstance().getDuration(s, quina));
                        propera.setFrom(src.getName());
                        propera.setTo(((Ciutat) ciutats.recuperar(quina)).getName());
                        addConnection(propera);
                        String aux = propera.getFrom();
                        propera.setFrom(propera.getTo());
                        propera.setTo(aux);
                        addConnection(propera);
                    }

                    @Override
                    public void onError(String s) {
                        System.out.println();
                        System.out.println("Error while doing the HTTP request.");
                    }
                });
    }

    public Connexio getConnexio(int closest, int i) {
        return (Connexio) ((Llista) connexions.recuperar(closest)).recuperar(i);
    }
}