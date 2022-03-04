package net.tarcadia.tribina.plugin.util.func;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.util.Set;
import java.util.HashSet;
import java.io.File;

import java.io.IOException;

import net.tarcadia.tribina.plugin.util.type.Pair;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Bitmaps {
    private static final int RGB_MASK = 0xffffff; // RGB mask
    private static final int IN_REGION = 0xffffff; // RGB(255, 255, 255) (white)

    /**
     * Save a region into a BMP file.
     * @param region the region to be saved
     * @param file destination BMP file
     * @throws IOException If an input or output exception occurred
     * @throws IllegalArgumentException If the region is invalid
     * @throws Exception Other exceptions
     */
    public static void saveSetToBmp(@NonNull Set<Pair<Integer, Integer>> region, @NonNull File file) throws IOException, IllegalArgumentException, Exception {
        int width = 0;
        int height = 0;
        for (var pair : region) {
            int x = pair.x();
            int y = pair.y();
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("Invalid pos found: " + pair);
            }
            if (x + 1 > width) {
                width = x + 1;
            }
            if (y + 1 > height) {
                height = y + 1;
            }
        }
        var image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (var pair : region) {
            image.setRGB(pair.x(), pair.y(), Bitmaps.IN_REGION);
        }
        if (!ImageIO.write(image, "BMP", file)) {
            throw new Exception("No appropriate writer is found");
        }
    }

    /**
     * Load a region from a BMP file.
     * @param file source BMP file
     * @return a {@code Set<Pair<Integer, Integer>>} represents the region
     * @throws IOException If an input or output exception occurred
     */
    public static Set<Pair<Integer, Integer>> loadBmpToSet(@NonNull File file) throws IOException {
        var region = new HashSet<Pair<Integer, Integer>>();
        var image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((image.getRGB(i, j) & Bitmaps.RGB_MASK) == Bitmaps.IN_REGION) {
                    region.add(new Pair<>(i, j));
                }
            }
        }
        return region;
    }
}
