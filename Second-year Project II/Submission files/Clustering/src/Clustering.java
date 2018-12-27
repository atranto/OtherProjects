import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Clustering {
    public static void main(String[] args) throws FileNotFoundException {
        // Create a new lsit of shipments
        ShipmentList data = new ShipmentList();
        // Read the data into Java
        readData(data,
                "C:/Users/toanh/OneDrive/Documents/Econometrics and Operations Research/Year 2/Courses/Second-year Project II/Aggregated data (for Q4-5).csv",
                "All");


        // Question 4
        System.out.println("Summary of original clustering:");
        System.out.println();
        data.summary();

        // Question 5: k-Means Clustering
        // k = 5
        data.kMeans(5);
        data.kMeansDest(5);
        data.updateShipmentList();
        System.out.println("Summary of k-means clustering:");
        System.out.println();
        data.summary();

        // k = 100
        data.kMeans(100);
        data.kMeansDest(100);
        data.updateShipmentList();
        System.out.println("Summary of k-means clustering:");
        System.out.println();
        data.summary();

        // k = 1027
        data.kMeans(1027);
        data.kMeansDest(1027);
        data.updateShipmentList();
        System.out.println("Summary of k-means clustering:");
        System.out.println();
        data.summary();

        // k = 2000
        data.kMeans(2000);
        data.kMeansDest(2000);
        data.updateShipmentList();
        System.out.println("Summary of k-means clustering:");
        System.out.println();
        data.summary();

        // Question 6: Segmented data
        // Shipments whose origin and destination are in the same country:

        // 1. Germany
        ShipmentList dataGermany = new ShipmentList();
        readData(dataGermany,
                "C:/Users/toanh/OneDrive/Documents/Econometrics and Operations Research/Year 2/Courses/Second-year Project II/Aggregated data (for Q4-5).csv",
                "GERMANY");
        System.out.println("Number of shipments for Germany: " + dataGermany.getShipmentList().size());
        // k = 240
        dataGermany.kMeans(240);
        dataGermany.kMeansDest(240);
        dataGermany.updateShipmentList();
        System.out.println("Summary of k-means clustering:");
        System.out.println();
        dataGermany.summary();

        // 2. France
        ShipmentList dataFrance = new ShipmentList();
        readData(dataFrance,
                "C:/Users/toanh/OneDrive/Documents/Econometrics and Operations Research/Year 2/Courses/Second-year Project II/Aggregated data (for Q4-5).csv",
                "FRANCE");
        System.out.println("Number of shipments for France: " + dataFrance.getShipmentList().size());
        // k = 270
        dataFrance.kMeans(270);
        dataFrance.kMeansDest(270);
        dataFrance.updateShipmentList();
        System.out.println("Summary of k-means clustering:");
        System.out.println();
        dataFrance.summary();

        // 3. Spain
        ShipmentList dataSpain = new ShipmentList();
        readData(dataSpain,
                "C:/Users/toanh/OneDrive/Documents/Econometrics and Operations Research/Year 2/Courses/Second-year Project II/Aggregated data (for Q4-5).csv",
                "SPAIN");
        System.out.println("Number of shipments for Spain: " + dataSpain.getShipmentList().size());
        // k = 106
        dataSpain.kMeans(106);
        dataSpain.kMeansDest(106);
        dataSpain.updateShipmentList();
        System.out.println("Summary of k-means clustering:");
        System.out.println();
        dataSpain.summary();

        // 4. UK
        ShipmentList dataUK = new ShipmentList();
        readData(dataUK,
                "C:/Users/toanh/OneDrive/Documents/Econometrics and Operations Research/Year 2/Courses/Second-year Project II/Aggregated data (for Q4-5).csv",
                "UNITED KINGDOM");
        System.out.println("Number of shipments for UK: " + dataUK.getShipmentList().size());
        // k = 15
        dataUK.kMeans(15);
        dataUK.kMeansDest(15);
        dataUK.updateShipmentList();
        System.out.println("Summary of k-means clustering:");
        System.out.println();
        dataUK.summary();

        // 5. Other shipments (i.e those that have different origin & destination countries)
        ShipmentList dataOthers = new ShipmentList();
        readData(dataOthers,
                "C:/Users/toanh/OneDrive/Documents/Econometrics and Operations Research/Year 2/Courses/Second-year Project II/Aggregated data (for Q4-5).csv",
                "Different origin and destination");
        System.out.println("Number of other shipments: " + dataOthers.getShipmentList().size());
        // k = 396
        dataOthers.kMeans(396);
        dataOthers.kMeansDest(396);
        dataOthers.updateShipmentList();
        System.out.println("Summary of k-means clustering:");
        System.out.println();
        dataOthers.summary();


    }

    // Method to read shipment data into Java
    // condition: All, Different origin and destination, or a country name
    // Different conditions for different way to segment the data
    // with 'all' being no segmentation
    public static void readData(ShipmentList shipmentList, String filePath, String condition) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = null;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                String OriginCountry = words[0];
                String OriginCity = words[1];
                String OriginCluster = words[2];
                double OriginClusterLat = Double.parseDouble(words[3]);
                double OriginClusterLong = Double.parseDouble(words[4]);
                double OriginLat = Double.parseDouble(words[5]);
                double OriginLong = Double.parseDouble(words[6]);
                String DestCountry = words[7];
                String DestCity = words[8];
                String DestinationCluster = words[9];
                double DestinationClusterLat = Double.parseDouble(words[10]);
                double DestinationClusterLong = Double.parseDouble(words[11]);
                double DestLat = Double.parseDouble(words[12]);
                double DestLong = Double.parseDouble(words[13]);
                double weight = Double.parseDouble(words[14]);
                int quantity = Integer.parseInt(words[15]);
                double volume = Double.parseDouble(words[16]);

                Shipment newShipment = new Shipment(weight, quantity, volume, new Cluster(OriginCluster, OriginClusterLat, OriginClusterLong),
                        new Cluster(DestinationCluster, DestinationClusterLat, DestinationClusterLong),
                        new Location(OriginCountry,OriginCity,OriginLat,OriginLong), new Location(DestCountry,DestCity,DestLat,DestLong));

                if (condition.equals("All")) shipmentList.addShipment(newShipment);
                else if (condition.equals("Different origin and destination")) {
                    if (!OriginCountry.equals(DestCountry)) shipmentList.addShipment(newShipment);
                }
                else {
                    if ((OriginCountry.equals(DestCountry)) && (OriginCountry.equals(condition))) shipmentList.addShipment(newShipment);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
