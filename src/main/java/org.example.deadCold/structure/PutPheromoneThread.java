package org.example.deadCold.structure;

import java.util.List;
import java.util.concurrent.Callable;

public class PutPheromoneThread implements Callable<Object> {
    private int[] antWay;
    private double wayDistance;
    private double shortestWay;
    private Graph graph;

    public PutPheromoneThread(int[] antWay, double wayDistance, Graph graph, double shortestWay) {
        this.antWay = antWay;
        this.wayDistance = wayDistance;
        this.graph = graph;
        this.shortestWay = shortestWay;
    }

    @Override
    public Object call() {
        double PHEROMONE_FACTOR = 10000;
        double pheromone = PHEROMONE_FACTOR / (Math.E/Math.exp(shortestWay/wayDistance));
        for (int j = 1; j < antWay.length; j++) {
            synchronized (graph.getMatrix().get(antWay[j - 1]).get(antWay[j])) {
                graph.getMatrix().get(antWay[j - 1]).get(antWay[j]).pheromone += pheromone;
                }
            synchronized (graph.getMatrix().get(antWay[j]).get(antWay[j - 1])) {
                graph.getMatrix().get(antWay[j]).get(antWay[j - 1]).pheromone += pheromone;
                }
        }
        return null;
    }
}
