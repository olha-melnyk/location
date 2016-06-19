package ws.bilka.location;

public class Location {

    private double mLongitude;
    private double mLatitude;

    public Location() {
    }

    public Location(double longitude, double latitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

}
