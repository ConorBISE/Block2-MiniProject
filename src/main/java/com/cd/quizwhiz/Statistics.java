package com.cd.quizwhiz;

import static java.util.Arrays.sort;

public class Statistics {

    // Calculate the mean of an array of scores.
    public static double Mean(double[] scores) {
        double sum = 0;

        // Loop through the array and calculate the sum of all scores.
        for (int i = 0; i < scores.length; i++) {
            double score = scores[i];
            sum += score;
        }

        // Calculate and return the mean by dividing the sum by the number of scores.
        return sum / scores.length;
    }

    // Calculate the median of an array of scores.
    public static double Median(double[] scores) {
        double median = 0;

        // Sort the scores in ascending order.
        sort(scores);

        // Check if the number of scores is even or odd to calculate the median accordingly.
        if (scores.length % 2 == 0) {
            // For an even number of scores, take the average of the two middle values.
            median = (scores[(scores.length / 2) - 1] + scores[scores.length / 2]) / 2.0;
        } else {
            // For an odd number of scores, simply take the middle value as the median.
            median = scores[scores.length / 2];
        }

        // Return the calculated median.
        return median;
    }
}
