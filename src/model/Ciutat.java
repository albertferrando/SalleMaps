package model;

import utils.Graf;
import utils.Helper;
import utils.Llista;

public class Ciutat {
    private String name;
    private String address;
    private String country;
    private double latitude;
    private double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void toString(Llista connexions, Llista nodes) {
        System.out.println("Name: " + name);
        System.out.println("Country: " + country);
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);
        System.out.println();
        System.out.println("Close cities: ");
        for(int j = 0; j < connexions.mida(); j++) {
            Object o = connexions.recuperar(j);
            if(!Graf.isElementIndefinit(o)) {
                Connexio c = (Connexio) o;
                Ciutat city = (Ciutat) nodes.recuperar(j);
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
}
