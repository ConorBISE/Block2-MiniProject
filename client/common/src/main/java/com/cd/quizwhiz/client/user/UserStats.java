package com.cd.quizwhiz.client.user;

public class UserStats {
    public boolean hasStats;
    public double mean;
    public double median;
    public double deviation;
    
    public UserStats(boolean hasStats, double mean, double median, double deviation) {
        this.hasStats = hasStats;
        this.mean = mean;
        this.median = median;
        this.deviation = deviation;
    }
}
