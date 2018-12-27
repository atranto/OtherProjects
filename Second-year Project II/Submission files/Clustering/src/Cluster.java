public class Cluster {
    // ID of cluster
    private String ID;
    // Cluster latitude
    private double clusterLatitude;
    // CLuster longitude
    private double clusterLongtitude;

    // Default constructor
    public Cluster(String ID, double clusterLatitude, double clusterLongtitude) {
        this.ID = ID;
        this.clusterLatitude = clusterLatitude;
        this.clusterLongtitude = clusterLongtitude;
    }

    // Default setters
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setClusterLatitude(double clusterLatitude) {
        this.clusterLatitude = clusterLatitude;
    }

    public void setClusterLongtitude(double clusterLongtitude) {
        this.clusterLongtitude = clusterLongtitude;
    }

    // Method to check if any component of a cluster is empty
    public boolean checkIfNullAnywhere() {
        if ((this.ID == null) || (this.clusterLatitude == Double.NaN) || (this.clusterLongtitude == Double.NaN)) {
            return true;
        }
        return false;
    }

    // Default getters
    public String getID() {
        return ID;
    }

    public double getClusterLatitude() {
        return clusterLatitude;
    }

    public double getClusterLongtitude() {
        return clusterLongtitude;
    }
}