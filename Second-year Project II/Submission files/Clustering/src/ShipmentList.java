import java.util.*;

public class ShipmentList {
    private ArrayList<Shipment> shipmentList;
    // Metrics

    // Lists that store unique clusters
    private ArrayList<String> originClusters;
    private ArrayList<String> destClusters;
    // Lists that store total distances
    private ArrayList<Double> originDistances;
    private ArrayList<Double> destDistances;
    // Lists that store total weights
    private ArrayList<Double> originWeights;
    private ArrayList<Double> destWeights;
    // Lists that store total volumes
    private ArrayList<Double> originVolumes;
    private ArrayList<Double> destVolumes;
    // Lists that store total quantities
    private ArrayList<Integer> originQuantities;
    private ArrayList<Integer> destQuantities;

    // Default constructor
    public ShipmentList() {
        this.shipmentList = new ArrayList<>();
        this.originClusters = new ArrayList<>();
        this.destClusters = new ArrayList<>();
        this.originDistances = new ArrayList<>();
        this.destDistances = new ArrayList<>();
        this.originWeights = new ArrayList<>();
        this.destWeights = new ArrayList<>();
        this.originVolumes = new ArrayList<>();
        this.destVolumes = new ArrayList<>();
        this.originQuantities = new ArrayList<>();
        this.destQuantities = new ArrayList<>();
    }

    // Method to add a shipment to the list, and also updating the (total) metrics above
    // By incrementing them
    public void addShipment(Shipment x) {
        if (this.shipmentList.isEmpty()) {
            this.shipmentList.add(x);
            this.originClusters.add(x.getOriginCluster().getID());
            this.destClusters.add(x.getDestCluster().getID());
            this.originDistances.add(x.getOriginDistance());
            this.destDistances.add(x.getDestDistance());
            this.originWeights.add(x.getWeight());
            this.destWeights.add(x.getWeight());
            this.originVolumes.add(x.getVolume());
            this.destVolumes.add(x.getVolume());
            this.originQuantities.add(x.getQuantity());
            this.destQuantities.add(x.getQuantity());
        }
        else {
            this.shipmentList.add(x);
            if (this.originClusters.contains(x.getOriginCluster().getID())){
                int index = this.originClusters.indexOf(x.getOriginCluster().getID());
                // Update origin metrics
                this.originDistances.set(index,this.originDistances.get(index)+ x.getOriginDistance());
                this.originWeights.set(index,this.originWeights.get(index)+ x.getWeight());
                this.originVolumes.set(index,this.originVolumes.get(index)+ x.getVolume());
                this.originQuantities.set(index,this.originQuantities.get(index)+ x.getQuantity());
            }
            else {
                this.originClusters.add(x.getOriginCluster().getID());
                this.originDistances.add(x.getOriginDistance());
                this.originWeights.add(x.getWeight());
                this.originVolumes.add(x.getVolume());
                this.originQuantities.add(x.getQuantity());
            }
            if (this.destClusters.contains(x.getDestCluster().getID())) {
                int index = this.destClusters.indexOf(x.getDestCluster().getID());
                // Update destination metrics
                this.destDistances.set(index,this.destDistances.get(index)+ x.getDestDistance());
                this.destWeights.set(index,this.destWeights.get(index)+ x.getWeight());
                this.destVolumes.set(index,this.destVolumes.get(index)+ x.getVolume());
                this.destQuantities.set(index,this.destQuantities.get(index)+ x.getQuantity());
            }
            else {
                this.destClusters.add(x.getDestCluster().getID());
                this.destDistances.add(x.getDestDistance());
                this.destWeights.add(x.getWeight());
                this.destVolumes.add(x.getVolume());
                this.destQuantities.add(x.getQuantity());
            }
        }
    }

    // Method to remove a shipment to the list, and also updating the (total) metrics above
    // By decrementing them
    // This method is here for holistic reference
    public void removeShipment(int index) {
        Shipment x = this.shipmentList.get(index);
        // Update origin matrix
        int originClusterIndex = this.originClusters.indexOf(x.getOriginCluster().getID());
        this.originDistances.set(originClusterIndex,this.originDistances.get(originClusterIndex)- x.getOriginDistance());
        this.originWeights.set(originClusterIndex,this.originWeights.get(originClusterIndex)- x.getWeight());
        this.originVolumes.set(originClusterIndex,this.originVolumes.get(originClusterIndex)- x.getVolume());
        this.originQuantities.set(originClusterIndex,this.originQuantities.get(originClusterIndex)- x.getQuantity());

        // Update dest matrix
        int destClusterIndex = this.destClusters.indexOf(x.getDestCluster().getID());
        this.destDistances.set(destClusterIndex,this.destDistances.get(destClusterIndex)- x.getDestDistance());
        this.destWeights.set(destClusterIndex,this.destWeights.get(destClusterIndex)- x.getWeight());
        this.destVolumes.set(destClusterIndex,this.destVolumes.get(destClusterIndex)- x.getVolume());
        this.destQuantities.set(destClusterIndex,this.destQuantities.get(destClusterIndex)- x.getQuantity());

        this.shipmentList.remove(index);
    }

    // Method to update the metrics of the current clustering
    // Based on the clustering currently assigned to the shipments
    public void updateShipmentList() {
        if(!this.shipmentList.isEmpty()) {
            this.originClusters.clear();
            this.destClusters.clear();
            this.originDistances.clear();
            this.destDistances.clear();
            this.originWeights.clear();
            this.destWeights.clear();
            this.originVolumes.clear();
            this.destVolumes.clear();
            this.originQuantities.clear();
            this.destQuantities.clear();
            for (int i = 0; i < this.shipmentList.size(); i++) {
                Shipment x = this.shipmentList.get(i);
                if (x.getOriginCluster().checkIfNullAnywhere() == false) {
                    if (this.originClusters.contains(x.getOriginCluster().getID())){
                        int index = this.originClusters.indexOf(x.getOriginCluster().getID());
                        // Update origin metrics
                        this.originDistances.set(index,this.originDistances.get(index)+ x.getOriginDistance());
                        this.originWeights.set(index,this.originWeights.get(index)+ x.getWeight());
                        this.originVolumes.set(index,this.originVolumes.get(index)+ x.getVolume());
                        this.originQuantities.set(index,this.originQuantities.get(index)+ x.getQuantity());
                    }
                    else {
                        this.originClusters.add(x.getOriginCluster().getID());
                        this.originDistances.add(x.getOriginDistance());
                        this.originWeights.add(x.getWeight());
                        this.originVolumes.add(x.getVolume());
                        this.originQuantities.add(x.getQuantity());
                    }
                }
                if (x.getDestCluster().checkIfNullAnywhere() == false) {
                    if (this.destClusters.contains(x.getDestCluster().getID())) {
                        int index = this.destClusters.indexOf(x.getDestCluster().getID());
                        // Update destination metrics
                        this.destDistances.set(index,this.destDistances.get(index)+ x.getDestDistance());
                        this.destWeights.set(index,this.destWeights.get(index)+ x.getWeight());
                        this.destVolumes.set(index,this.destVolumes.get(index)+ x.getVolume());
                        this.destQuantities.set(index,this.destQuantities.get(index)+ x.getQuantity());
                    }
                    else {
                        this.destClusters.add(x.getDestCluster().getID());
                        this.destDistances.add(x.getDestDistance());
                        this.destWeights.add(x.getWeight());
                        this.destVolumes.add(x.getVolume());
                        this.destQuantities.add(x.getQuantity());
                    }
                }
            }
        }
    }

    // Method to calculate the mean of an Arraylist (double)
    public double listMeanDouble(ArrayList<Double> x) {
        double sum = 0;
        if(!x.isEmpty()) {
            for (double value : x) {
                sum += value;
            }
            return sum/x.size();
        }
        return sum;
    }

    // Method to calculate the mean of an Arraylist (int)
    public double listMeanInt(ArrayList<Integer> x) {
        int sum = 0;
        if(!x.isEmpty()) {
            for (double value : x) {
                sum += value;
            }
            return (double) sum /x.size();
        }
        return (double) sum;
    }

    // Method to return cluster ID containing value that is closest to the average value (double)
    public String averageCluster(double average, ArrayList<String> clusters, ArrayList<Double> metric) {
        if (!clusters.isEmpty()) {
            double distance = Math.abs(metric.get(0) - average);
            int idx = 0;
            for(int c = 1; c < metric.size(); c++){
                double cdistance = Math.abs(metric.get(c) - average);
                if(cdistance < distance){
                    idx = c;
                    distance = cdistance;
                }
            }
            return clusters.get(idx);
        }
        else return "No cluster available";
    }

    // Method to return cluster ID containing value that is closest to the average value (int)
    public String averageClusterInt(double average, ArrayList<String> clusters, ArrayList<Integer> metric) {
        if (!clusters.isEmpty()) {
            double distance = Math.abs((double) metric.get(0) - average);
            int idx = 0;
            for(int c = 1; c < metric.size(); c++){
                double cdistance = Math.abs((double) metric.get(c) - average);
                if(cdistance < distance){
                    idx = c;
                    distance = cdistance;
                }
            }
            return clusters.get(idx);
        }
        else return "No cluster available";
    }

    // Method to return top 3 clusters wrt a metric (double)
    public ArrayList<String> top3Clusters (ArrayList<String> clusters, ArrayList<Double> metric) {
        ArrayList<String> top3 = new ArrayList<>();
        double[] highestValues = new double[3];
        double first, second, third;

        third = first = second = Integer.MIN_VALUE;

        for (int i = 0; i < metric.size() ; i++) {
            /* If current element is smaller than first*/
            if (metric.get(i) > first) {
                third = second;
                second = first;
                first = metric.get(i);
            }

            /* If arr[i] is in between first and second then update second  */
            else if (metric.get(i) > second) {
                third = second;
                second = metric.get(i);
            }

            else if (metric.get(i) > third)
                third = metric.get(i);
        }

        highestValues[0] = first;
        highestValues[1] = second;
        highestValues[2] = third;

        ArrayList<Integer> index1 = indexOfAll(highestValues[0],metric);
        for (int i=0;i<index1.size();i++) {
            top3.add(clusters.get(index1.get(i)));
        }
        if (highestValues[0] != highestValues[1]) {
            ArrayList<Integer> index2 = indexOfAll(highestValues[1], metric);
            for (int i=0;i<index2.size();i++) {
                top3.add(clusters.get(index2.get(i)));
            }
        }
        if (highestValues[1] != highestValues[2]) {
            ArrayList<Integer> index3 = indexOfAll(highestValues[2], metric);
            for (int i=0;i<index3.size();i++) {
                top3.add(clusters.get(index3.get(i)));
            }
        }

        return top3;
    }

    // Method to return top 3 clusters wrt a metric (int)
    public ArrayList<String> top3ClustersInt (ArrayList<String> clusters, ArrayList<Integer> metric) {
        ArrayList<String> top3 = new ArrayList<>();
        int[] highestValues = new int[3];
        int first, second, third;

        third = first = second = Integer.MIN_VALUE;

        for (int i = 0; i < metric.size() ; i++) {
            /* If current element is smaller than first*/
            if (metric.get(i) > first) {
                third = second;
                second = first;
                first = metric.get(i);
            }

            /* If arr[i] is in between first and second then update second  */
            else if (metric.get(i) > second) {
                third = second;
                second = metric.get(i);
            }

            else if (metric.get(i) > third)
                third = metric.get(i);
        }

        highestValues[0] = first;
        highestValues[1] = second;
        highestValues[2] = third;

        ArrayList<Integer> index1 = indexOfAll(highestValues[0],metric);
        for (int i=0;i<index1.size();i++) {
            top3.add(clusters.get(index1.get(i)));
        }
        if (highestValues[0] != highestValues[1]) {
            ArrayList<Integer> index2 = indexOfAll(highestValues[1], metric);
            for (int i=0;i<index2.size();i++) {
                top3.add(clusters.get(index2.get(i)));
            }
        }
        if (highestValues[1] != highestValues[2]) {
            ArrayList<Integer> index3 = indexOfAll(highestValues[2], metric);
            for (int i=0;i<index3.size();i++) {
                top3.add(clusters.get(index3.get(i)));
            }
        }

        return top3;
    }

    // Method to return all the indexes of all the occurrences of an object in an arraylist
    static ArrayList<Integer> indexOfAll(Object obj, ArrayList list){
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++)
            if(obj.equals(list.get(i)))
                indexList.add(i);
        return indexList;
    }

    // Method to return bottom 3 clusters wrt a metric (double)
    public ArrayList<String> bottom3Clusters (ArrayList<String> clusters, ArrayList<Double> metric) {
        ArrayList<String> bottom3 = new ArrayList<String>();
        Double[] lowestValues = new Double[3];
        Arrays.fill(lowestValues, Double.MAX_VALUE);

        Set<Double> uniqueSet = new HashSet<Double>(metric);
        ArrayList<Double> uniqueList = new ArrayList<Double>(uniqueSet);

        for(int n = 0; n < uniqueList.size(); n++) {
            if(uniqueList.get(n) < lowestValues[2]) {
                lowestValues[2] = uniqueList.get(n);
                Arrays.sort(lowestValues);
            }
        }

        ArrayList<Integer> index1 = indexOfAll(lowestValues[0],metric);
        for (int i=0;i<index1.size();i++) {
            bottom3.add(clusters.get(index1.get(i)));
        }
        if (lowestValues[0] != lowestValues[1]) {
            ArrayList<Integer> index2 = indexOfAll(lowestValues[1], metric);
            for (int i=0;i<index2.size();i++) {
                bottom3.add(clusters.get(index2.get(i)));
            }
        }
        if (lowestValues[1] != lowestValues[2]) {
            ArrayList<Integer> index3 = indexOfAll(lowestValues[2], metric);
            for (int i=0;i<index3.size();i++) {
                bottom3.add(clusters.get(index3.get(i)));
            }
        }

        return bottom3;
    }

    // Method to return bottom 3 clusters wrt a metric (int)
    public ArrayList<String> bottom3ClustersInt (ArrayList<String> clusters, ArrayList<Integer> metric) {
        ArrayList<String> bottom3 = new ArrayList<String>();
        int[] lowestValues = new int[3];
        Arrays.fill(lowestValues, Integer.MAX_VALUE);

        Set<Integer> uniqueSet = new HashSet<Integer>(metric);
        ArrayList<Integer> uniqueList = new ArrayList<Integer>(uniqueSet);

        for(int n = 0; n < uniqueList.size(); n++) {
            if(uniqueList.get(n) < lowestValues[2]) {
                lowestValues[2] = uniqueList.get(n);
                Arrays.sort(lowestValues);
            }
        }

        ArrayList<Integer> index1 = indexOfAll(lowestValues[0],metric);
        for (int i=0;i<index1.size();i++) {
            bottom3.add(clusters.get(index1.get(i)));
        }
        if (lowestValues[0] != lowestValues[1]) {
            ArrayList<Integer> index2 = indexOfAll(lowestValues[1], metric);
            for (int i=0;i<index2.size();i++) {
                bottom3.add(clusters.get(index2.get(i)));
            }
        }
        if (lowestValues[1] != lowestValues[2]) {
            ArrayList<Integer> index3 = indexOfAll(lowestValues[2], metric);
            for (int i=0;i<index3.size();i++) {
                bottom3.add(clusters.get(index3.get(i)));
            }
        }

        return bottom3;
    }

    // Method to return the total distance from the origin location to the origin cluster, of the current clustering
    public double totalOriginDistance() {
        double sum = 0;
        for (int i = 0; i < this.shipmentList.size(); i++) {
            sum += this.shipmentList.get(i).getOriginDistance();
        }
        return sum;
    }

    // Method to return the total distance from the destination location to the destination cluster, of the current clustering
    public double totalDestDistance() {
        double sum = 0;
        for (int i = 0; i < this.shipmentList.size(); i++) {
            sum += this.shipmentList.get(i).getDestDistance();
        }
        return sum;
    }

    // Method that returns a summary of the metrics of the shipments
    public void summary() {
        double[] originAverages = new double[] {listMeanDouble(this.originDistances),listMeanDouble(this.originWeights),
                listMeanDouble(this.originVolumes),listMeanInt(this.originQuantities)};
        double[] destAverages = new double[] {listMeanDouble(this.destDistances),listMeanDouble(this.destWeights),
                listMeanDouble(this.destVolumes),listMeanInt(this.destQuantities)};

        // Origin
        System.out.print("Total distance from origin location to origin cluster: ");
        System.out.println(totalOriginDistance());
        System.out.println();

        System.out.print("Average cluster wrt total distance from origin location to origin cluster: ");
        System.out.println(averageCluster(originAverages[0],originClusters,originDistances));
        System.out.print("Value of average cluster: ");
        System.out.println(this.originDistances.get(this.originClusters.indexOf(averageCluster(originAverages[0],originClusters,originDistances))));
        System.out.print("Average value: ");
        System.out.println(originAverages[0]);
        System.out.println();

        System.out.print("Average cluster wrt total weight from origin location to origin cluster: ");
        System.out.println(averageCluster(originAverages[1],originClusters,originWeights));
        System.out.print("Value of average cluster: ");
        System.out.println(this.originWeights.get(this.originClusters.indexOf(averageCluster(originAverages[1],originClusters,originWeights))));
        System.out.print("Average value: ");
        System.out.println(originAverages[1]);
        System.out.println();

        System.out.print("Average cluster wrt total volume from origin location to origin cluster: ");
        System.out.println(averageCluster(originAverages[2],originClusters,originVolumes));
        System.out.print("Value of average cluster: ");
        System.out.println(this.originVolumes.get(this.originClusters.indexOf(averageCluster(originAverages[2],originClusters,originVolumes))));
        System.out.print("Average value: ");
        System.out.println(originAverages[2]);
        System.out.println();

        System.out.print("Average cluster wrt total quantity from destination location to destination cluster: ");
        System.out.println(averageClusterInt(originAverages[3],originClusters,originQuantities));
        System.out.print("Value of average cluster: ");
        System.out.println(this.originQuantities.get(this.originClusters.indexOf(averageClusterInt(originAverages[3],originClusters,originQuantities))));
        System.out.print("Average value: ");
        System.out.println(originAverages[3]);
        System.out.println();

        // Destination
        System.out.print("Total distance from destination location to destination cluster: ");
        System.out.println(totalDestDistance());
        System.out.println();

        System.out.print("Average cluster wrt total distance from destination location to destination cluster: ");
        System.out.println(averageCluster(destAverages[0],destClusters,destDistances));
        System.out.print("Value of average cluster: ");
        System.out.println(this.destDistances.get(this.destClusters.indexOf(averageCluster(destAverages[0],destClusters,destDistances))));
        System.out.print("Average value: ");
        System.out.println(destAverages[0]);
        System.out.println();

        System.out.print("Average cluster wrt total weight from destination location to destination cluster: ");
        System.out.println(averageCluster(destAverages[1],destClusters,destWeights));
        System.out.print("Value of average cluster: ");
        System.out.println(this.destWeights.get(this.destClusters.indexOf(averageCluster(destAverages[1],destClusters,destWeights))));
        System.out.print("Average value: ");
        System.out.println(destAverages[1]);
        System.out.println();

        System.out.print("Average cluster wrt total volume from destination location to destination cluster: ");
        System.out.println(averageCluster(destAverages[2],destClusters,destVolumes));
        System.out.print("Value of average cluster: ");
        System.out.println(this.destVolumes.get(this.destClusters.indexOf(averageCluster(destAverages[2],destClusters,destVolumes))));
        System.out.print("Average value: ");
        System.out.println(destAverages[2]);
        System.out.println();

        System.out.print("Average cluster wrt total quantity from destination location to destination cluster: ");
        System.out.println(averageClusterInt(destAverages[3],destClusters,destQuantities));
        System.out.print("Value of average cluster: ");
        System.out.println(this.destQuantities.get(this.destClusters.indexOf(averageClusterInt(destAverages[3],destClusters,destQuantities))));
        System.out.print("Average value: ");
        System.out.println(destAverages[3]);
        System.out.println();

        // Origin top 3
        System.out.println("Top 3 total origin distances: ");
        for (int i = 0; i < top3Clusters(originClusters,originDistances).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(top3Clusters(originClusters,originDistances).get(i));
            System.out.print(" (");
            System.out.print(originDistances.get(originClusters.indexOf(top3Clusters(originClusters,originDistances).get(i))));
            System.out.println(")");
        }
        System.out.println();
        System.out.println("Top 3 total origin weights: ");
        for (int i = 0; i < top3Clusters(originClusters,originWeights).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(top3Clusters(originClusters,originWeights).get(i));
            System.out.print(" (");
            System.out.print(originWeights.get(originClusters.indexOf(top3Clusters(originClusters,originWeights).get(i))));
            System.out.println(")");
        }
        System.out.println();
        System.out.println("Top 3 total origin volumes: ");
        for (int i = 0; i < top3Clusters(originClusters,originVolumes).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(top3Clusters(originClusters,originVolumes).get(i));
            System.out.print(" (");
            System.out.print(originVolumes.get(originClusters.indexOf(top3Clusters(originClusters,originVolumes).get(i))));
            System.out.println(")");
        }
        System.out.println();
        System.out.println("Top 3 total origin quantities: ");
        for (int i = 0; i < top3ClustersInt(originClusters,originQuantities).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(top3ClustersInt(originClusters,originQuantities).get(i));
            System.out.print(" (");
            System.out.print(originQuantities.get(originClusters.indexOf(top3ClustersInt(originClusters,originQuantities).get(i))));
            System.out.println(")");
        }
        System.out.println();

        // Destination top 3
        System.out.println("Top 3 total destination distances: ");
        for (int i = 0; i < top3Clusters(destClusters,destDistances).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(top3Clusters(destClusters,destDistances).get(i));
            System.out.print(" (");
            System.out.print(destDistances.get(destClusters.indexOf(top3Clusters(destClusters,destDistances).get(i))));
            System.out.println(")");
        }
        System.out.println();
        System.out.println("Top 3 total destination weights: ");
        for (int i = 0; i < top3Clusters(destClusters,destWeights).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(top3Clusters(destClusters,destWeights).get(i));
            System.out.print(" (");
            System.out.print(destWeights.get(destClusters.indexOf(top3Clusters(destClusters,destWeights).get(i))));
            System.out.println(")");
        }
        System.out.println();
        System.out.println("Top 3 total destination volumes: ");
        for (int i = 0; i < top3Clusters(destClusters,destVolumes).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(top3Clusters(destClusters,destVolumes).get(i));
            System.out.print(" (");
            System.out.print(destVolumes.get(destClusters.indexOf(top3Clusters(destClusters,destVolumes).get(i))));
            System.out.println(")");
        }
        System.out.println();
        System.out.println("Top 3 total destination quantities: ");
        for (int i = 0; i < top3ClustersInt(destClusters,destQuantities).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(top3ClustersInt(destClusters,destQuantities).get(i));
            System.out.print(" (");
            System.out.print(destQuantities.get(destClusters.indexOf(top3ClustersInt(destClusters,destQuantities).get(i))));
            System.out.println(")");
        }
        System.out.println();

        // Origin bottom 3
        System.out.println("Bottom 3 total origin distances: ");
        for (int i = 0; i < bottom3Clusters(originClusters,originDistances).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(bottom3Clusters(originClusters,originDistances).get(i));
            System.out.print(" (");
            System.out.print(originDistances.get(originClusters.indexOf(bottom3Clusters(originClusters,originDistances).get(i))));
            System.out.println(")");
        }
        System.out.println();

        System.out.println("Bottom 3 total origin weights: ");
        for (int i = 0; i < bottom3Clusters(originClusters,originWeights).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(bottom3Clusters(originClusters,originWeights).get(i));
            System.out.print(" (");
            System.out.print(originWeights.get(originClusters.indexOf(bottom3Clusters(originClusters,originWeights).get(i))));
            System.out.println(")");
        }
        System.out.println();

        System.out.println("Bottom 3 total origin volumes: ");
        for (int i = 0; i < bottom3Clusters(originClusters,originVolumes).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(bottom3Clusters(originClusters,originVolumes).get(i));
            System.out.print(" (");
            System.out.print(originVolumes.get(originClusters.indexOf(bottom3Clusters(originClusters,originVolumes).get(i))));
            System.out.println(")");
        }
        System.out.println();

        System.out.println("Bottom 3 total origin quantities: ");
        for (int i = 0; i < bottom3ClustersInt(originClusters,originQuantities).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(bottom3ClustersInt(originClusters,originQuantities).get(i));
            System.out.print(" (");
            System.out.print(originQuantities.get(originClusters.indexOf(bottom3ClustersInt(originClusters,originQuantities).get(i))));
            System.out.println(")");
        }
        System.out.println();

        // Destination bottom 3
        System.out.println("Bottom 3 total destination distances: ");
        for (int i = 0; i < bottom3Clusters(destClusters,destDistances).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(bottom3Clusters(destClusters,destDistances).get(i));
            System.out.print(" (");
            System.out.print(destDistances.get(destClusters.indexOf(bottom3Clusters(destClusters,destDistances).get(i))));
            System.out.println(")");
        }
        System.out.println();

        System.out.println("Bottom 3 total destination weights: ");
        for (int i = 0; i < bottom3Clusters(destClusters,destWeights).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(bottom3Clusters(destClusters,destWeights).get(i));
            System.out.print(" (");
            System.out.print(destWeights.get(destClusters.indexOf(bottom3Clusters(destClusters,destWeights).get(i))));
            System.out.println(")");
        }
        System.out.println();

        System.out.println("Bottom 3 total destination volumes: ");
        for (int i = 0; i < bottom3Clusters(destClusters,destVolumes).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(bottom3Clusters(destClusters,destVolumes).get(i));
            System.out.print(" (");
            System.out.print(destVolumes.get(destClusters.indexOf(bottom3Clusters(destClusters,destVolumes).get(i))));
            System.out.println(")");
        }
        System.out.println();

        System.out.println("Bottom 3 total destination quantities: ");
        for (int i = 0; i < bottom3ClustersInt(destClusters,destQuantities).size(); i++) {
            System.out.print(i+1); System.out.print(": ");
            System.out.print(bottom3ClustersInt(destClusters,destQuantities).get(i));
            System.out.print(" (");
            System.out.print(destQuantities.get(destClusters.indexOf(bottom3ClustersInt(destClusters,destQuantities).get(i))));
            System.out.println(")");
        }
        System.out.println();
    }

    // k-Means Clustering
    public void kMeans(int k) {
        // Step 1: Assign shipments to clusters (should be randomly, but here it's done sequentially)
        int itemsPerCluster = this.shipmentList.size()/k;
        int count = 0;
        int ID = 1;
        for (int i = 0; i < (itemsPerCluster*k); i++) {
            String name = "Cluster" + ID;
            Shipment x = new Shipment(this.shipmentList.get(i).getWeight(),
                    this.shipmentList.get(i).getQuantity(),
                    this.shipmentList.get(i).getVolume(),
                    new Cluster(null, Double.NaN, Double.NaN),
                    this.shipmentList.get(i).getDestCluster(),
                    this.shipmentList.get(i).getOriginLocation(),
                    this.shipmentList.get(i).getDestLocation());
            x.setOriginCluster(new Cluster(name, Double.NaN, Double.NaN));
            this.shipmentList.set(i,x);
            count++;
            if (count == itemsPerCluster) {
                count = 0;
                ID++;
            }
        }
        if (itemsPerCluster*k < this.shipmentList.size()) {
            for  (int i = itemsPerCluster*k; i < this.shipmentList.size(); i++) {
                String name = "Cluster" + k;
                Shipment x = new Shipment(this.shipmentList.get(i).getWeight(),
                        this.shipmentList.get(i).getQuantity(),
                        this.shipmentList.get(i).getVolume(),
                        new Cluster(null, Double.NaN, Double.NaN),
                        this.shipmentList.get(i).getDestCluster(),
                        this.shipmentList.get(i).getOriginLocation(),
                        this.shipmentList.get(i).getDestLocation());
                x.setOriginCluster(new Cluster(name, Double.NaN, Double.NaN));
                shipmentList.set(i,x);
            }
        }

        // Step 2: Iterate cluster assignment
        String[] currentAssignment = new String[this.shipmentList.size()];
        for (int p = 0; p < this.shipmentList.size(); p++){
            currentAssignment[p] = this.shipmentList.get(p).getOriginCluster().getID();
        }

        this.shipmentList = clusterAssign(this.shipmentList, k);
        updateShipmentList();

        String[] newAssignment = new String[this.shipmentList.size()];
        for (int p = 0; p < this.shipmentList.size(); p++){
            newAssignment[p] = this.shipmentList.get(p).getOriginCluster().getID();
        }

        // Recursion here
        int countKMeans = 1;
        while (!Arrays.equals(currentAssignment, newAssignment)) {
            currentAssignment = new String[this.shipmentList.size()];
            for (int p = 0; p < this.shipmentList.size(); p++){
                currentAssignment[p] = newAssignment[p];
            }

            this.shipmentList = clusterAssign(this.shipmentList, k);
            updateShipmentList();

            newAssignment = new String[this.shipmentList.size()];
            for (int p = 0; p < this.shipmentList.size(); p++){
                newAssignment[p] = this.shipmentList.get(p).getOriginCluster().getID();
            }

            countKMeans++;
        }

        // Output
        System.out.println("Performed k-means clustering of origin clusters with k = " + k);
        System.out.println("Number of iterations: " + countKMeans);
        System.out.println();
    }

    // Method to assign clusters, based on min location-centroid distance
    public ArrayList<Shipment> clusterAssign (ArrayList<Shipment> shipmentList, int k) {
        // Calculate the coordinates of the centroids
        Cluster[] centroids = new Cluster[k];
        for (int j = 0; j < k; j++) {
            String clusterName = "Cluster" + (j+1);
            centroids[j] = new Cluster(clusterName,Double.NaN,Double.NaN);
        }

        for (int j = 0; j < k; j++) {
            String clusterName = "Cluster" + (j+1);
            ArrayList<Shipment> temp = new ArrayList<Shipment>();
            for (int i = 0; i < shipmentList.size(); i++) {
                if (shipmentList.get(i).getOriginCluster().getID().equals(clusterName)) {
                    temp.add(shipmentList.get(i));
                }
            }

            // Calculate average latitude of cluster
            double sum = 0;
            int count = 0;
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getOriginLocation().getLocationLatitude() != Double.NaN) {
                    sum += temp.get(i).getOriginLocation().getLocationLatitude();
                    count++;
                }

            }
            double avgLat = sum / count;

            // Calculate average longitude of cluster
            sum = 0;
            count = 0;
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getOriginLocation().getLocationLongitude() != Double.NaN) {
                    sum += temp.get(i).getOriginLocation().getLocationLongitude();
                    count++;
                }
            }
            double avgLong = sum / count;

            centroids[j] = new Cluster(clusterName, avgLat, avgLong);
        }

        // Reassign the shipments to clusters based on distance to centroids
        for (int i = 0; i < shipmentList.size(); i++) {
            // Create an array that keeps track of distances between location and clusters
            double[] distArray = new double[k];

            // Compute those distances
            for (int m = 0; m < k; m++) {
                distArray[m] = HaversineDistance.distance(shipmentList.get(i).getOriginLocation().getLocationLatitude(),
                        shipmentList.get(i).getOriginLocation().getLocationLongitude(),
                        centroids[m].getClusterLatitude(), centroids[m].getClusterLongtitude());
            }

            // Select the cluster with the smallest distance
            double min = distArray[0];
            int index = 0;
            for (int j = 1; j < distArray.length; j++) {
                if (min > distArray[j]) {
                    min = distArray[j];
                    index = j;
                }
            }

            // Assign new cluster
            String randomID = centroids[index].getID();
            Shipment x = new Shipment(this.shipmentList.get(i).getWeight(),
                    this.shipmentList.get(i).getQuantity(),
                    this.shipmentList.get(i).getVolume(),
                    new Cluster(centroids[index].getID(), centroids[index].getClusterLatitude(),
                            centroids[index].getClusterLongtitude()),
                    this.shipmentList.get(i).getDestCluster(),
                    this.shipmentList.get(i).getOriginLocation(),
                    this.shipmentList.get(i).getDestLocation());
            shipmentList.set(i,x);
        }

        return shipmentList;
    }

    // Same k-Means algorithm, just with the destination clusters and locations instead
    public void kMeansDest(int k) {
        // Step 1: Assign shipments to clusters
        int itemsPerCluster = this.shipmentList.size()/k;
        int count = 0;
        int ID = 1;
        for (int i = 0; i < (itemsPerCluster*k); i++) {
            String name = "Cluster" + ID;
            Shipment x = new Shipment(this.shipmentList.get(i).getWeight(),
                    this.shipmentList.get(i).getQuantity(),
                    this.shipmentList.get(i).getVolume(),
                    this.shipmentList.get(i).getOriginCluster(),
                    new Cluster(name, Double.NaN, Double.NaN),
                    this.shipmentList.get(i).getOriginLocation(),
                    this.shipmentList.get(i).getDestLocation());
            this.shipmentList.set(i,x);
            count++;
            if (count == itemsPerCluster) {
                count = 0;
                ID++;
            }
        }
        if (itemsPerCluster*k < this.shipmentList.size()) {
            for  (int i = itemsPerCluster*k; i < this.shipmentList.size(); i++) {
                String name = "Cluster" + k;
                Shipment x = new Shipment(this.shipmentList.get(i).getWeight(),
                        this.shipmentList.get(i).getQuantity(),
                        this.shipmentList.get(i).getVolume(),
                        this.shipmentList.get(i).getOriginCluster(),
                        new Cluster(name, Double.NaN, Double.NaN),
                        this.shipmentList.get(i).getOriginLocation(),
                        this.shipmentList.get(i).getDestLocation());
                shipmentList.set(i,x);
            }
        }

        // Iterate cluster assignment
        String[] currentAssignment = new String[this.shipmentList.size()];
        for (int p = 0; p < this.shipmentList.size(); p++){
            currentAssignment[p] = this.shipmentList.get(p).getDestCluster().getID();
        }

        this.shipmentList = clusterAssignDest(this.shipmentList, k);
        updateShipmentList();

        String[] newAssignment = new String[this.shipmentList.size()];
        for (int p = 0; p < this.shipmentList.size(); p++){
            newAssignment[p] = this.shipmentList.get(p).getDestCluster().getID();
        }

        // Recursion here
        int countKMeans = 1;
        while (!Arrays.equals(currentAssignment, newAssignment)) {
            currentAssignment = new String[this.shipmentList.size()];
            for (int p = 0; p < this.shipmentList.size(); p++){
                currentAssignment[p] = newAssignment[p];
            }

            this.shipmentList = clusterAssignDest(this.shipmentList, k);
            updateShipmentList();

            newAssignment = new String[this.shipmentList.size()];
            for (int p = 0; p < this.shipmentList.size(); p++){
                newAssignment[p] = this.shipmentList.get(p).getDestCluster().getID();
            }

            countKMeans++;
        }
        System.out.println("Performed k-means clustering of destination clusters with k = " + k);
        System.out.println("Number of iterations: " + countKMeans);
        System.out.println();
    }

    public ArrayList<Shipment> clusterAssignDest (ArrayList<Shipment> shipmentList, int k) {
        // Step 2: Calculate the coordinates of the centroids
        Cluster[] centroids = new Cluster[k];
        for (int j = 0; j < k; j++) {
            String clusterName = "Cluster" + (j+1);
            centroids[j] = new Cluster(clusterName,Double.NaN,Double.NaN);
        }

        for (int j = 0; j < k; j++) {
            String clusterName = "Cluster" + (j+1);
            ArrayList<Shipment> temp = new ArrayList<Shipment>();
            for (int i = 0; i < shipmentList.size(); i++) {
                if (shipmentList.get(i).getDestCluster().getID().equals(clusterName)) {
                    temp.add(shipmentList.get(i));
                }
            }

            // Calculate average latitude of cluster
            double sum = 0;
            int count = 0;
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getDestLocation().getLocationLatitude() != Double.NaN) {
                    sum += temp.get(i).getDestLocation().getLocationLatitude();
                    count++;
                }

            }
            double avgLat = sum / count;

            // Calculate average longitude of cluster
            sum = 0;
            count = 0;
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getDestLocation().getLocationLongitude() != Double.NaN) {
                    sum += temp.get(i).getDestLocation().getLocationLongitude();
                    count++;
                }
            }
            double avgLong = sum / count;

            centroids[j] = new Cluster(clusterName, avgLat, avgLong);
        }

        // Step 3: Reassign the shipments to clusters based on distance to centroids
        for (int i = 0; i < shipmentList.size(); i++) {
            // Create an array that keeps track of distances between location and clusters
            double[] distArray = new double[k];

            // Compute those distances
            for (int m = 0; m < k; m++) {
                distArray[m] = HaversineDistance.distance(shipmentList.get(i).getDestLocation().getLocationLatitude(),
                        shipmentList.get(i).getDestLocation().getLocationLongitude(),
                        centroids[m].getClusterLatitude(), centroids[m].getClusterLongtitude());
            }

            // Select the cluster with the smallest distance
            double min = distArray[0];
            int index = 0;
            for (int j = 1; j < distArray.length; j++) {
                if (min > distArray[j]) {
                    min = distArray[j];
                    index = j;
                }
            }

            // Set assign new cluster
            String randomID = centroids[index].getID();
            Shipment x = new Shipment(this.shipmentList.get(i).getWeight(),
                    this.shipmentList.get(i).getQuantity(),
                    this.shipmentList.get(i).getVolume(),
                    this.shipmentList.get(i).getOriginCluster(),
                    new Cluster(centroids[index].getID(), centroids[index].getClusterLatitude(),
                            centroids[index].getClusterLongtitude()),
                    this.shipmentList.get(i).getOriginLocation(),
                    this.shipmentList.get(i).getDestLocation());
            shipmentList.set(i,x);
        }

        return shipmentList;
    }

    // Default getters
    public ArrayList<Shipment> getShipmentList() {
        return shipmentList;
    }

    public ArrayList<String> getOriginClusters() {
        return originClusters;
    }

    public ArrayList<String> getDestClusters() {
        return destClusters;
    }

    public ArrayList<Double> getOriginDistances() {
        return originDistances;
    }

    public ArrayList<Double> getDestDistances() {
        return destDistances;
    }

    public ArrayList<Double> getOriginWeights() {
        return originWeights;
    }

    public ArrayList<Double> getDestWeights() {
        return destWeights;
    }

    public ArrayList<Double> getOriginVolumes() {
        return originVolumes;
    }

    public ArrayList<Double> getDestVolumes() {
        return destVolumes;
    }

    public ArrayList<Integer> getOriginQuantities() {
        return originQuantities;
    }

    public ArrayList<Integer> getDestQuantities() {
        return destQuantities;
    }
}
