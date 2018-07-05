package utils;

import model.Ciutat;
import model.Connexio;

public class Graf {
    private List ciutats;
    private List connexions;

    Graf() {
        ciutats = new List(new Ciutat());
        connexions = new List(new List(new Connexio()));
    }

    private Connexio elementIndefinit() {
        Connexio c = new Connexio();
        c.setDistance(-1);
        return c;
    }

    private boolean isElementIndefinit(Connexio c) {
        return c.getDistance() == -1;
    }

    void addCity(Ciutat c) {
        ciutats.add(c);
    }

    void addConnection(Connexio c) {
        int from = getIndex(c.getFrom());
        int to = getIndex(c.getTo());
        ((List)connexions.get(from)).setAtIndex(to, c);
    }

    private int getIndex(String city) {
        for(int i = 1; i < ciutats.getSize() + 1; i++) {
            Ciutat c = (Ciutat) ciutats.get(i);
            if(c.getName().equals(city)) {
                return i;
            }
        }
        return -1;
    }

    void inicialitzaConnexions() {
        int mida = ciutats.getSize() + 1;
        for(int i = 1; i < mida;i++) {
            connexions.add(new List(new Connexio()));
            List l = (List)connexions.get(i);
            for(int j = 1; j < mida; j++) {
                l.add(elementIndefinit());
            }
        }
    }

    public int conte(String nomCiutat) {
        for(int i = 1; i < ciutats.getSize() + 1; i++) {
            if(((Ciutat)ciutats.get(i)).getName().equals(nomCiutat)) {
                return i;
            }
        }
        return -1;
    }

    public void toString(int i) {
        System.out.println("Name: " + ((Ciutat)ciutats.get(i)).getName());
        System.out.println("Country: " + ((Ciutat)ciutats.get(i)).getCountry());
        System.out.println("Latitude: " + ((Ciutat)ciutats.get(i)).getLatitude());
        System.out.println("Longitude: " + ((Ciutat)ciutats.get(i)).getLongitude());
        System.out.println();
        System.out.println("Close cities: ");
        List l = (List) connexions.get(i);
        for(int j = 1; j < ciutats.getSize() + 1; j++) {
            Connexio c = (Connexio) l.get(j);
            if(!isElementIndefinit(c)) {
                Ciutat city = (Ciutat) ciutats.get(j);
                System.out.println();
                System.out.println("\tName: " + city.getName());
                System.out.println("\tCountry: " + city.getCountry());
                System.out.println("\tLatitude: " + city.getLatitude());
                System.out.println("\tLongitude: " + city.getLongitude());
                int[] time = toTime(c.getDuration());
                String timeString = String.format("%02d:%02d:%02d", time[0], time[1], time[2]);
                System.out.println("\tTime: " + timeString);
                System.out.println("\tDistance: " + c.getDistance()/1000);
            }
        }
    }

    private int[] toTime(long seconds) {
        int hours = (int) seconds / 3600;
        long remainder = seconds - hours * 3600;
        int mins = (int) (remainder / 60);
        remainder = remainder - mins * 60;
        int secs = (int) remainder;
        return new int[]{hours , mins , secs};
    }
}