package utils;

import Network.HttpRequest;
import Network.WSGoogleMaps;
import estructures.AVL;
import estructures.Graf;
import estructures.TaulaHash;
import estructures.Llista;
import model.Ciutat;
import model.Connexio;

public class Helper {
    private Graf graf;
    private AVL arbre;
    private TaulaHash taulaHash;
    private static Helper ourInstance = new Helper();

    public static Helper getInstance() {
        return ourInstance;
    }

    private Helper() {
    }

    int[] toTime(long seconds) {
        int hours = (int) seconds / 3600;
        long remainder = seconds - hours * 3600;
        int mins = (int) (remainder / 60);
        remainder = remainder - mins * 60;
        int secs = (int) remainder;
        return new int[]{hours , mins , secs};
    }

    private void connectCity() {
        Llista nodes = graf.recuperaNodes();
        Connexio propera = new Connexio();
        Ciutat src = (Ciutat) nodes.recuperar(nodes.mida() - 1);
        double latitudes[] = new double[nodes.mida()];
        double longitudes[] = new double[nodes.mida()];

        for(int i = 0; i < nodes.mida() - 1; i++) {
            latitudes[i] = ((Ciutat) nodes.recuperar(i)).getLatitude();
            longitudes[i] = ((Ciutat) nodes.recuperar(i)).getLongitude();
        }
        WSGoogleMaps.getInstance().distance(src.getLatitude(), src.getLongitude(), latitudes, longitudes,
                new HttpRequest.HttpReply() {
                    @Override
                    public void onSuccess(String s) {
                        int quina = 0;
                        long distancia = GestorJSON.getInstance().getDistance(s,0);
                        for(int i = 1; i < nodes.mida() - 1; i++) {
                            long newDistance = GestorJSON.getInstance().getDistance(s, i);
                            if(newDistance < distancia) {
                                distancia = newDistance;
                                quina = i;
                            }
                        }
                        propera.setDistance(distancia);
                        propera.setDuration(GestorJSON.getInstance().getDuration(s, quina));
                        propera.setFrom(src.getName());
                        propera.setTo(((Ciutat) nodes.recuperar(quina)).getName());
                        graf.afegeixConnexio(propera, graf.mida() - 1);
                        String aux = propera.getFrom();
                        propera.setFrom(propera.getTo());
                        propera.setTo(aux);
                        graf.afegeixConnexio(propera, quina);
                    }

                    @Override
                    public void onError(String s) {
                        System.out.println();
                        System.out.println("Error while doing the HTTP request.");
                    }
                });
    }

    public boolean addNewCity(int optimization, String nomCiutat) {
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
                        if(Helper.getInstance().searchCity(optimization, nomCiutat) == -1) {
                            graf.afegeixNode(c);
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

    private int conte(String c) {
        for(int j = 0; j < graf.mida(); j++) {
            Ciutat ciutat = (Ciutat) graf.recuperaElement(j);
            if(ciutat.getName().equals(c)) {
                return j;
            }
        }
        return -1;
    }

    public int searchCity(int optimization, String nomCiutat) {
        switch (optimization) {
            case 1:
                return conte(nomCiutat);
            case 2:
                return (int) arbre.recupera(nomCiutat);
            case 3:
                return (int) taulaHash.recupera(nomCiutat);
            default:
                System.out.println("Fail");
                return -1;
        }
    }

    void setGraf(Graf graf) {
        this.graf = graf;
    }

    void setArbre(AVL arbre) {
        this.arbre = arbre;
    }

    void setTaulaHash(TaulaHash taulaHash) {
        this.taulaHash = taulaHash;
    }

    public Graf getGraf() {
        return graf;
    }

    public AVL getArbre() {
        return arbre;
    }

    public TaulaHash getTaulaHash() {
        return taulaHash;
    }

    public void toString(int i, int optimitzation) {
        Graf.Node node = graf.recuperaNode(i);
        Ciutat c = ((Ciutat) node.getElement());
        Llista connexions = node.getConnexions();
        System.out.println("Name: " + c.getName());
        System.out.println("Country: " + c.getCountry());
        System.out.println("Latitude: " + c.getLatitude());
        System.out.println("Longitude: " + c.getLongitude());
        System.out.println();
        System.out.println("Close cities: ");
        for(int j = 0; j < connexions.mida(); j++) {
            Connexio connexio = (Connexio) connexions.recuperar(j);
            Ciutat city = (Ciutat) graf.recuperaElement(searchCity(optimitzation, connexio.getTo()));
            System.out.println();
            System.out.println("\tName: " + city.getName());
            System.out.println("\tCountry: " + city.getCountry());
            System.out.println("\tLatitude: " + city.getLatitude());
            System.out.println("\tLongitude: " + city.getLongitude());
            int[] time = Helper.getInstance().toTime(connexio.getDuration());
            String timeString = String.format("%02d:%02d:%02d", time[0], time[1], time[2]);
            System.out.println("\tTime: " + timeString);
            System.out.println("\tDistance: " + connexio.getDistance()/1000);
        }
    }
}
