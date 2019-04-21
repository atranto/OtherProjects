public class MSTResult {
    private final int[][] MST;
    private int totalCost;

    public MSTResult(int[][] MST) {
        this.MST = MST;
        // Get total cost
        for (int i = 0; i < MST.length; i++) {
            for (int j = i+1; j < MST.length; j++) {
                if (MST[i][j] > 0) {
                    totalCost += MST[i][j];
                }
            }
        }
    }

    public int[][] getMST() {
        return MST;
    }

    public int getTotalCost() {
        return totalCost;
    }
}
