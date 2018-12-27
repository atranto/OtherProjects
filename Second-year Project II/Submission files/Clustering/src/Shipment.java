public class Shipment {
    private double weight;
    private int quantity;
    private double volume;

    private Cluster originCluster;
    private Cluster destCluster;

    private Location originLocation;
    private Location destLocation;

    private double originDistance;
    private double destDistance;

    // Default constructor
    public Shipment(double weight, int quantity, double volume, Cluster originCluster, Cluster destCluster,
                    Location originLocation, Location destLocation) {
        this.weight = weight;
        this.quantity = quantity;
        this.volume = volume;
        this.originCluster = originCluster;
        this.destCluster = destCluster;
        this.originLocation = originLocation;
        this.destLocation = destLocation;

        // Calculate Haversine distance between origin location & origin cluster
        if ((this.originCluster.getClusterLatitude() != Double.NaN) && (this.originCluster.getClusterLongtitude() != Double.NaN)) {
            this.originDistance = HaversineDistance.distance(this.originLocation.getLocationLatitude(),
                    this.originLocation.getLocationLongitude(),
                    this.originCluster.getClusterLatitude(),
                    this.originCluster.getClusterLongtitude());
        }
        else this.originDistance = Double.NaN;

        // Calculate Haversine distance between destination location & destination cluster
        if ((this.destCluster.getClusterLatitude() != Double.NaN) && (this.destCluster.getClusterLongtitude() != Double.NaN)) {
            this.destDistance = HaversineDistance.distance(this.destLocation.getLocationLatitude(),
                    this.destLocation.getLocationLongitude(),
                    this.destCluster.getClusterLatitude(),
                    this.destCluster.getClusterLongtitude());
        }
        else this.destDistance = Double.NaN;
    }

    // Default setters
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setOriginCluster(Cluster originCluster) {
        this.originCluster = originCluster;
    }

    public void setDestCluster(Cluster destCluster) {
        this.destCluster = destCluster;
    }

    public void setOriginLocation(Location originLocation) {
        this.originLocation = originLocation;
    }

    public void setDestLocation(Location destLocation) {
        this.destLocation = destLocation;
    }

    public void setOriginDistance(double originDistance) {
        this.originDistance = originDistance;
    }

    public void setDestDistance(double destDistance) {
        this.destDistance = destDistance;
    }

    // Default getters
    public double getWeight() {
        return weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getVolume() {
        return volume;
    }

    public Cluster getOriginCluster() {
        return originCluster;
    }

    public Cluster getDestCluster() {
        return destCluster;
    }

    public Location getOriginLocation() {
        return originLocation;
    }

    public Location getDestLocation() {
        return destLocation;
    }

    public double getOriginDistance() {
        return originDistance;
    }

    public double getDestDistance() {
        return destDistance;
    }
}
