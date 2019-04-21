public class SteinerResult {
    private final int[][] steiner;
    private int totalCost = 0;
    private final int MSTCost;
    private final int V;
    private int E = 0;

    public SteinerResult(int[][] steiner, int MSTCost) {
        this.steiner = steiner;
        this.MSTCost = MSTCost;
        this.V = steiner.length;

        // Get total cost
        for (int i = 0; i < steiner.length; i++) {
            for (int j = i+1; j < steiner.length; j++) {
                if (steiner[i][j] > 0) {
                    totalCost += steiner[i][j];
                    E++;
                }
            }
        }
    }

    public int[][] getSteiner() {
        return steiner;
    }

    public int getMSTCost() {
        return MSTCost;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getV() {
        return V;
    }

    public int getE() {
        return E;
    }
}
