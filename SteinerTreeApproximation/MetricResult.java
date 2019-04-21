import java.util.List;

public class MetricResult {
    private final int[][] metricClosure;
    private final List<List<Integer>> paths;

    public MetricResult(int[][] metricClosure, List<List<Integer>> paths) {
        this.metricClosure = metricClosure;
        this.paths = paths;
    }

    public int[][] getMetricClosure() {
        return metricClosure;
    }

    public List<List<Integer>> getPaths() {
        return paths;
    }
}
