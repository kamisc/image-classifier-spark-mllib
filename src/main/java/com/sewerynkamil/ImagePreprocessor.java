package com.sewerynkamil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class ImagePreprocessor {
    private final String imageDirectory;
    private final int label; // 0 - nonhotdog ; 1 - hotdog
    private final String outputFilename;
    private ArrayList<BufferedImage> images = new ArrayList<>();

    public ImagePreprocessor(String imageDirectory, int label, String outputFilename) throws IOException {
        this.imageDirectory = imageDirectory;
        this.label = label;
        this.outputFilename = outputFilename;

        this.loadImages();
        this.transformImages();
    }

    private void loadImages() throws IOException {
        Files.walkFileTree(Paths.get(this.imageDirectory), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                images.add(ImageIO.read(file.toFile()));
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void transformImages() throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.outputFilename, true)));

        for (BufferedImage image : images) {
            // make a grayscale image
            toGrayScale(image);
            // write the pixel to a libsvm file
            Raster raster = image.getData();
            int width = image.getWidth();
            int height = image.getHeight();
            out.print(this.label);
            int pixelCount = 1;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    float normalizedPixel = raster.getSample(i, j, 0) / 255.0f;
                    out.print(" " + pixelCount + ":" + normalizedPixel);
                    pixelCount++;
                }
            }
            out.print("");
        }
        out.close();
    }

    public static void toGrayScale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color c = new Color(image.getRGB(i, j));
                int r = (int) (c.getRed() * 0.21);
                int g = (int) (c.getGreen() * 0.72);
                int b = (int) (c.getBlue() * 0.07);
                int sum = r + g + b;
                Color grayPixel = new Color(sum, sum, sum);
                image.setRGB(i, j, grayPixel.getRGB());
            }
        }
    }
}
