package utils;

public class Helper {
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
}
