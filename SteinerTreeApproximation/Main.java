import java.io.*;
import java.util.*;

public class Main {

    private static final int NO_PARENT = -1;

    public static void main(String[] args) {
    	// Test on 1 file
        //String filename = "C:/Users/toanh/OneDrive/Documents/Econometrics and Operations Research/Year 3/Allocations and Algorithms/Project/Instances/instance051.gr";
        //writeGraphToFile(filename, "instance051_Output.txt");
        
     // Test on instances
        File folder = new File("/home/anh/Desktop/Instances/");
        File[] listOfFiles = folder.listFiles();
        Arrays.sort(listOfFiles, (f1, f2) -> {
            return new Long(f1.length()).compareTo(new Long(f2.length()));
        });
        
        
        // Print list of files
         //for (File file : listOfFiles) {
         //if (!file.isHidden()) {
         //       if (!file.isDirectory()) {
         //          System.out.println("FILE\t" + " " + file.length() + " bytes\t\t" + file.getName());
         //       }
         //   }
         //}

        for (int i = 0; i < 2; i++) { //listOfFiles.length; change the file range accordingly
            System.out.println("i = " + i);
        	String fileName = "/home/anh/Desktop/Instances/" + listOfFiles[i].getName();
            System.out.println(fileName);
            String outputFileName = listOfFiles[i].getName().substring(0, 11) + "_Output(short).txt";
            System.out.println(outputFileName);
            writeGraphToFile(fileName, outputFileName);
            System.out.println("END!");
            System.out.println();
        }
        
        
        /*List<File> fileList = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
        	if (listOfFiles[i].getName().contains("short")) fileList.add(listOfFiles[i]);
        }
        
        List<Integer> id = new ArrayList<Integer>();
        List<Integer> V = new ArrayList<Integer>();
        List<Integer> SMTWeight = new ArrayList<Integer>();
        List<Integer> MSTWeight = new ArrayList<Integer>();
        List<Double> rho = new ArrayList<Double>();
        List<Double> t = new ArrayList<Double>();
        
        
        for (File file: fileList) {
        	id.add(Integer.parseInt(file.getName().substring(8,11)));
        	try {
        		String filename = file.getPath();
        		System.out.println(filename);
        		File f = new File(filename);
        		Scanner input = new Scanner(f);
        		input.next();input.next();input.next();input.next();input.next();
            	V.add(input.nextInt());
            	input.next();input.next();input.next();input.next();input.next();input.next();
            	SMTWeight.add(input.nextInt());
            	input.next();input.next();input.next();
            	t.add(input.nextDouble());
            	input.next();input.next();input.next();input.next();input.next();input.next();input.next();input.next();
            	MSTWeight.add(input.nextInt());
            	input.next();input.next();
            	rho.add(input.nextDouble());
            	input.close();
        	}
        	catch (FileNotFoundException e) {e.printStackTrace();}
        	
        }
        try {
        	FileWriter fileWriter = new FileWriter("Results.csv");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("id,V,SMTWeight,MSTWeight,rho,t");
            for (int i = 0; i < id.size(); i++) {
            	printWriter.print(id.get(i) + ",");
            	printWriter.print(V.get(i) + ",");
            	printWriter.print(SMTWeight.get(i) + ",");
            	printWriter.print(MSTWeight.get(i) + ",");
            	printWriter.print(rho.get(i) + ",");
            	printWriter.print(t.get(i));
            	printWriter.println();
            	
            }
            printWriter.close();
        }
        
        catch (IOException e) {e.printStackTrace();}*/
        
    	
    }

    // Method to output Steiner tree to a file
    public static void writeGraphToFile(String filename, String outputName) {
        long startTime = System.nanoTime();
        SteinerResult steiner = steiner(filename);
        long endTime = System.nanoTime();
        // Running time
        double difference = (endTime - startTime) / 1e6;

        // Write to a file
        try {
            FileWriter fileWriter = new FileWriter(outputName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("STEINER TREE");
            printWriter.println("Number of vertices: " + (steiner.getV()));
            printWriter.println("Number of edges: " + steiner.getE());
            printWriter.println("Tree weight: " + steiner.getTotalCost());
            printWriter.println("Running time (ms): " + difference);
            printWriter.println();
            printWriter.println("MST (in Minimum Steiner tree algorithm)");
            printWriter.println("Tree weight: " + steiner.getMSTCost());
            printWriter.println();
            printWriter.println("Steiner Ratio: " + ((double)steiner.getMSTCost()/(double)steiner.getTotalCost()));
            // Also output the graph here. Comment this section if you don't want to output the adjacency matrix
            // Of the Steiner tree itself
            /*printWriter.println();
            printWriter.println("Steiner tree graph:");
            printWriter.println();
            for (int i = 0; i < steiner.getSteiner().length; i++) {
                for (int j = 0; j < steiner.getSteiner()[0].length; j++) {
                    printWriter.print(steiner.getSteiner()[i][j] + " ");
                }
                printWriter.println();
            }*/
            printWriter.close();

        }
        catch (IOException e) {e.printStackTrace();}
    }

    // Steiner Tree
    public static SteinerResult steiner(String filename) {
        int[][] graph = createGraph(filename);
        List<Integer> terminals = terminalList(filename);
        MetricResult metricClosure = metricClosure(graph, terminals);
        MSTResult MST = MST(metricClosure.getMetricClosure());
        int MSTCost = MST.getTotalCost();

        // Steiner
        Set<Integer> V = new HashSet<>();
        List<List<Integer>> pathSubset = new ArrayList<>();
        for (int terminal: terminals) V.add(terminal); // Add all terminals first
        for (int i = 0; i < MST.getMST().length; i++) {
            for (int j = i+1; j < MST.getMST()[0].length; j++) {
                if (MST.getMST()[i][j] > 0) {
                    int row = terminals.get(i);
                    int column = terminals.get(j);
                    for (List<Integer> path: metricClosure.getPaths()) {
                        //System.out.println("Consider path " + path);
                        if (((path.get(0) == row) && (path.get(path.size()-1) == column)) ||
                                ((path.get(0) == column) && (path.get(path.size()-1) == row))) {
                            pathSubset.add(path);
                            for (int vertex: path) V.add(vertex);
                            break;
                        }
                    }
                }
            }
        }

        int[][] steiner = new int[V.size()][V.size()];
        List<Integer> VList = new ArrayList<>(V); // convert set V to a list
        for (List<Integer> path: pathSubset) {
            for (int i = 0; i < path.size()-1; i++) {
                steiner[VList.indexOf(path.get(i))][VList.indexOf(path.get(i+1))] = graph[path.get(i)][path.get(i+1)];
                steiner[VList.indexOf(path.get(i+1))][VList.indexOf(path.get(i))] = graph[path.get(i)][path.get(i+1)];
            }
        }
        return new SteinerResult(steiner, MSTCost);
    }

    // Metric closure
    public static MetricResult metricClosure(int[][] graph, List<Integer> terminals) {
        int[][] metricClosure = new int[terminals.size()][terminals.size()];
        List<List<Integer>> shortestPaths = new ArrayList<>();
        for (int i = 0; i < metricClosure.length; i++) {
            int start = terminals.get(i);
            DijkstraResult dijkstra = dijkstra(graph, start);
            for (int j = i+1; j < terminals.size(); j++) {
                int end = terminals.get(j);
                int distance = dijkstra.getShortestDistances()[end];
                metricClosure[i][j] = distance;
                metricClosure[j][i] = distance;
                shortestPaths.add(getPath(dijkstra,start,end));
            }
        }
        return new MetricResult(metricClosure,shortestPaths);
    }

    // Prim's Algorithm to find MST
    public static MSTResult MST(int[][] graph) {
        int[][] MST = new int[graph[0].length][graph[0].length];
        int startID = 0; // Start at node 0 of the (metric) graph
        Set<Integer> W = new HashSet<Integer>();
        W.add(startID);

        //int step = 1;
        while (W.size() != graph.length) {
            //System.out.println("W = " + W);
            int minCost = Integer.MAX_VALUE;
            int minNeighbor = -1;
            int nodeInW = -1;
            for (int i: W) {
                for (int j = 0; j < graph.length; j++) {
                    if (graph[i][j] > 0 && graph[i][j] < minCost && i!=j && !W.contains(j)) {
                        minCost = graph[i][j];
                        minNeighbor = j;
                        nodeInW = i;
                    }
                }
            }
            // Update W
            W.add(minNeighbor);
            MST[nodeInW][minNeighbor] = minCost;
            MST[minNeighbor][nodeInW] = minCost;
            //step++;
        }
        return new MSTResult(MST);
    }

    // Method to return shortest path
    public static List<Integer> getPath(DijkstraResult result, int source, int end) {
        List<Integer> path = new ArrayList<>();
        path.add(end);
        int current = new Integer(end);
        while (current != source) {
            path.add(result.getPreviousNode()[current]);
            current = new Integer(result.getPreviousNode()[current]);
        }
        return path;
    }

    // Dijkstra's Algorithm
    public static DijkstraResult dijkstra(int[][] adjacencyMatrix, int startVertex) {
        int vertexCount = adjacencyMatrix[0].length;

        // Array to hold shortest path lengths from source to vertex i
        int[] shortestDistances = new int[vertexCount];

        // added[i] is true if vertex i is included in shortest path,
        // or shortest distance from source to vertex i is confirmed
        boolean[] added = new boolean[vertexCount];

        // Initialize all distances as positive infinity, and all added[] as false
        for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Set distance from source to itself as 0
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest path tree
        int[] parents = new int[vertexCount];

        // Source vertex does not have a parent
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all vertices
        for (int i = 1; i < vertexCount; i++) {
            // Pick the minimum distance vertex from the set of vertices not yet processed.
            // nearesrVertex is always equal to startNode in the first iteration
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
                if(!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as processed
            added[nearestVertex] = true;

            // Update distances of the adjacent vertices of the picked vertex
            for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];
                if (edgeDistance > 0 && (shortestDistance + edgeDistance) < shortestDistances[vertexIndex]) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
        }

        return new DijkstraResult(shortestDistances, parents);
    }

    // Method to print adjacency matrix
    public static void printGraph(int[][] graph) {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                System.out.print(graph[i][j] + "\t");
            }
            System.out.println();
        }
    }

    // Method to return a list of terminal IDs
    public static List<Integer> terminalList(String filename) {
        try {
            List<Integer> terminals = new ArrayList<Integer>();
            File file = new File(filename);
            Scanner input = new Scanner(file);
            input.next();
            input.next();
            input.next();
            input.nextInt();
            input.next();
            input.nextInt();

            String nextWord = input.next();
            while (nextWord.equals("E")) {
                input.nextInt();
                input.nextInt();
                input.nextInt();
                nextWord = input.next();
            }
            input.next();
            input.next();
            input.next();
            input.nextInt();

            nextWord = input.next();
            while (nextWord.equals("T")) {
                terminals.add(input.nextInt() - 1);
                nextWord = input.next();
            }
            input.close();
            return terminals;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to read Graph into an adjacency matrix
    public static int[][] createGraph(String fileName) {
        try {
            File file = new File(fileName);
            Scanner input = new Scanner(file);

            input.next();
            input.next();
            input.next();
            // Number of vertices
            int vertexCount = input.nextInt();
            // Create empty adjacency matrix
            int[][] adjacencyMatrix = new int[vertexCount][vertexCount];

            input.next();
            // Number of edges
            int edgeCount = input.nextInt();

            //input.nextLine();
            String nextWord = input.next();
            while (nextWord.equals("E")) {
                int i = input.nextInt() - 1;
                int j = input.nextInt() - 1;
                int cost = input.nextInt();
                adjacencyMatrix[i][j] = cost;
                adjacencyMatrix[j][i] = cost;
                nextWord = input.next();
            }

            input.close();
            return adjacencyMatrix;
        }
        catch (FileNotFoundException e) { e.printStackTrace();}
        return null;
    }
}
