package net.tarcadia.tribina.erod.mapregion.util.type;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Bitmaps {
    private static final int RGB_MASK = 0xffffff; // RGB mask
    private static final int IN_REGION = 0xffffff; // RGB(255, 255, 255) (white)

    /**
     * Save a set of pos of masked region into a BMP file.
     * @param pSet the region to mask in the bmp
     * @param file destination BMP file
     * @throws IOException If an input or output exception occurred
     * @throws IllegalArgumentException If the region is invalid
     * @throws Exception Other exceptions
     */
    public static void saveSetToBmp(@NonNull Set<Pair<Integer, Integer>> pSet, @NonNull File file) throws IOException, IllegalArgumentException, Exception {
        int width = 0;
        int height = 0;
        for (var pos : pSet) {
            int x = pos.x();
            int y = pos.y();
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("Invalid pos found: " + pos);
            }
            if (x + 1 > width) {
                width = x + 1;
            }
            if (y + 1 > height) {
                height = y + 1;
            }
        }
        var image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (var pos : pSet) {
            image.setRGB(pos.x(), pos.y(), Bitmaps.IN_REGION);
        }
        if (file.getParent() != null) new File(file.getParent()).mkdirs();
        if (!ImageIO.write(image, "BMP", file)) {
            throw new Exception("No appropriate writer is found");
        }
    }

    /**
     * Load a set of pos of masked region from a BMP file.
     * @param file source BMP file
     * @return a {@code Set<Pair<Integer, Integer>>} represents the masked region of the bmp
     * @throws IOException If an input or output exception occurred
     */
    public static Set<Pair<Integer, Integer>> loadBmpToSet(@NonNull File file) throws IOException {
        var pSet = new HashSet<Pair<Integer, Integer>>();
        var image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((image.getRGB(i, j) & Bitmaps.RGB_MASK) == Bitmaps.IN_REGION) {
                    pSet.add(new Pair<>(i, j));
                }
            }
        }
        return pSet;
    }

    /**
     * Load a set of pos of masked region from a BMP file.
     * @param input source BMP InputStream
     * @return a {@code Set<Pair<Integer, Integer>>} represents the masked region of the bmp
     * @throws IOException If an input or output exception occurred
     */
    public static Set<Pair<Integer, Integer>> loadBmpToSet(@NonNull InputStream input) throws IOException {
        var pSet = new HashSet<Pair<Integer, Integer>>();
        var image = ImageIO.read(input);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((image.getRGB(i, j) & Bitmaps.RGB_MASK) == Bitmaps.IN_REGION) {
                    pSet.add(new Pair<>(i, j));
                }
            }
        }
        return pSet;
    }

    /**
     * Save a list of pos of masked region into a BMP file.
     * @param pLst represents the region to mask in the bmp
     * @param file destination BMP file
     * @throws IOException If an input or output exception occurred
     * @throws IllegalArgumentException If the region is invalid
     * @throws Exception Other exceptions
     */
    public static void saveListToBmp(@NonNull List<Pair<Integer, Integer>> pLst, @NonNull File file) throws IOException, IllegalArgumentException, Exception {
        int width = 0;
        int height = 0;
        for (var pos : pLst) {
            int x = pos.x();
            int y = pos.y();
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("Invalid pos found: " + pos);
            }
            if (x + 1 > width) {
                width = x + 1;
            }
            if (y + 1 > height) {
                height = y + 1;
            }
        }
        var image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (var pos : pLst) {
            image.setRGB(pos.x(), pos.y(), Bitmaps.IN_REGION);
        }
        if (file.getParent() != null) new File(file.getParent()).mkdirs();
        if (!ImageIO.write(image, "BMP", file)) {
            throw new Exception("No appropriate writer is found");
        }
    }

    /**
     * Load a list of pos of masked region from a BMP file.
     * @param file source BMP file
     * @return a {@code List<Pair<Integer, Integer>>} represents the masked region of the bmp
     * @throws IOException If an input or output exception occurred
     */
    public static List<Pair<Integer, Integer>> loadBmpToList(@NonNull File file) throws IOException {
        var pLst = new LinkedList<Pair<Integer, Integer>>();
        var image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((image.getRGB(i, j) & Bitmaps.RGB_MASK) == Bitmaps.IN_REGION) {
                    pLst.add(new Pair<>(i, j));
                }
            }
        }
        return pLst;
    }

    /**
     * Load a list of pos of masked region from a BMP file.
     * @param input source BMP InputStream
     * @return a {@code List<Pair<Integer, Integer>>} represents the masked region of the bmp
     * @throws IOException If an input or output exception occurred
     */
    public static List<Pair<Integer, Integer>> loadBmpToList(@NonNull InputStream input) throws IOException {
        var pLst = new LinkedList<Pair<Integer, Integer>>();
        var image = ImageIO.read(input);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((image.getRGB(i, j) & Bitmaps.RGB_MASK) == Bitmaps.IN_REGION) {
                    pLst.add(new Pair<>(i, j));
                }
            }
        }
        return pLst;
    }

}
