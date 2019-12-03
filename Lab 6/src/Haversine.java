public class Haversine {

    public static double distance(double startLat, double startLong, double endLat, double endLong) {
        int r = 6371; //Promień ziemi
        double dLat = Math.toRadians(endLat - startLat);
        double dLong = Math.toRadians(endLong - startLong);
        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);
        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        return r * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}