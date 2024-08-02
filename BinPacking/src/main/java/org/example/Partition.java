package org.example;

public class Partition implements Comparable<Partition> {
    private int id;
    private long lag;
    private double arrivalRate;

    public Partition(int id, long lag, double arrivalRate) {
        this.id = id;
        this.lag = lag;
        this.arrivalRate = arrivalRate;
    }

    public int getId() {
        return id;
    }

    public long getLag() {
        return lag;
    }

    public void setLag(long lag) {
        this.lag = lag;
    }

    public double getArrivalRate() {
        return arrivalRate;
    }

    public void setArrivalRate(double arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    @Override
    public int compareTo(Partition other) {
        return Long.compare(other.getLag(), this.lag);
    }
}
