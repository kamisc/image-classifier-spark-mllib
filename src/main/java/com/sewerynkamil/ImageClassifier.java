package com.sewerynkamil;

import org.apache.spark.sql.SparkSession;

public class ImageClassifier {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Image classifier")
                .getOrCreate();

    }
}