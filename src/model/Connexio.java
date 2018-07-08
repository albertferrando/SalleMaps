package model;

public class Connexio {
    private String from;
    private String to;
    //distance en metres
    private long distance;
    //duracio en segons
    private long duration;
    public static final Connexio elementIndefinit = elementIndefinit();

    public boolean isElementIndefinit() {
        return this.getDistance() == -1;
    }

    private static Connexio elementIndefinit() {
        Connexio c = new Connexio();
        c.setDistance(-1);
        return c;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
