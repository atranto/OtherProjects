public class DijkstraResult {
    private final int[] shortestDistances;
    private final int[] previousNode;

    public DijkstraResult(int[] shortestDistances, int[] previousNode) {
        super();
        this.shortestDistances = shortestDistances;
        this.previousNode = previousNode;
    }

    public int[] getShortestDistances() {
        return shortestDistances;
    }

    public int[] getPreviousNode() {
        return previousNode;
    }

    public void printShortestDistances() {
        System.out.print("Shortest distances: ");
        for (int i=0; i < shortestDistances.length; i++) System.out.print(shortestDistances[i] + " ");
    }

    public void printParents() {
        System.out.print("Previous nodes: ");
        for (int i=0; i < previousNode.length; i++) System.out.print(previousNode[i] + " ");
    }
}
