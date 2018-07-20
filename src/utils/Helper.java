package utils;

import Network.HttpRequest;
import Network.WSGoogleMaps;
import dataStructures.AVL;
import dataStructures.Graf;
import dataStructures.Hashtable;
import dataStructures.Llista;
import model.Ciutat;
import model.Connexio;

public class Helper {
    private Graf graf;
    private AVL arbre;
    private Hashtable hashtable;
    private static Helper ourInstance = new Helper();

    public static Helper getInstance() {
        return ourInstance;
    }

    private Helper() {
    }

    public int[] toTime(long seconds) {
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
                        graf.afegeixConnexio(propera, graf.mida() - 1, quina);
                        String aux = propera.getFrom();
                        propera.setFrom(propera.getTo());
                        propera.setTo(aux);
                        graf.afegeixConnexio(propera, quina, graf.mida() - 1);
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

    public int conte(String c) {
        for(int j = 0; j < graf.mida(); j++) {
            Ciutat ciutat = (Ciutat) graf.recuperaNode(j);
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
                return (int) arbre.getInfo(nomCiutat);
            default:
                return -1;
                //TODO Fer getter del hashmap
        }
    }

    public void setGraf(Graf graf) {
        this.graf = graf;
    }

    public void setArbre(AVL arbre) {
        this.arbre = arbre;
    }

    public void setHashtable(Hashtable hashtable) {
        this.hashtable = hashtable;
    }
}
