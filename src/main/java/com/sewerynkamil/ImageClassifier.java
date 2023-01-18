package com.sewerynkamil;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;

public class ImageClassifier {
    public static void main(String[] args) throws IOException {
        SparkSession spark = SparkSession
                .builder()
                .appName("Image classifier")
                .getOrCreate();

        preProcessImage();

        Dataset<Row> train = spark.read().format("libsvm").load("data/train.txt");
        Dataset<Row> test = spark.read().format("libsvm").load("data/test.txt");
    }

    public static void preProcessImage() throws IOException {
        ImagePreprocessor trainPositive =  new ImagePreprocessor(
                "data/train/hotdog",
                1,
                "data/train.txt"
        );
        trainPositive = null;

        ImagePreprocessor trainNegative =  new ImagePreprocessor(
                "data/train/nothotdog",
                0,
                "data/train.txt"
        );
        trainNegative = null;

        ImagePreprocessor testPositive =  new ImagePreprocessor(
                "data/test/hotdog",
                1,
                "data/test.txt"
        );
        testPositive = null;

        ImagePreprocessor testNegative =  new ImagePreprocessor(
                "data/test/nothotdog",
                0,
                "data/test.txt"
        );
        testNegative = null;
    }
}