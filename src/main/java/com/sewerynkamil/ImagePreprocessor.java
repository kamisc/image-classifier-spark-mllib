package com.sewerynkamil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    }

    private void loadImages() throws IOException {
        Files.walkFileTree(Paths.get(this.imageDirectory), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                images.add(ImageIO.read(file.toFile()));
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
