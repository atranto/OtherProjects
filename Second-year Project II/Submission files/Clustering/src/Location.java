public class Location {
    private String country;
    private String city;
    private double locationLatitude;
    private double locationLongitude;

    // Default contructor
    public Location(String country, String city, double locationLatitude, double locationLongitude) {
        this.country = country;
        this.city = city;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
    }

    // Default setters
    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public void setLocationLongitude(double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    // Default getters
    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }
}
