package net.tarcadia.tribina.erod.mapregion.util.data;

import net.tarcadia.tribina.erod.mapregion.util.type.Pos;
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
    public static void saveSetToBmp(@NonNull Set<Pos> pSet, @NonNull File file) throws IOException, IllegalArgumentException, Exception {
        int width = 0;
        int height = 0;
        for (var pos : pSet) {
            long x = pos.x();
            long z = pos.z();
            if (x < 0 || z < 0 || x > Integer.MAX_VALUE || z > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("Invalid pos found: " + pos);
            }
            if (x + 1 > width) {
                width = (int) x + 1;
            }
            if (z + 1 > height) {
                height = (int) z + 1;
            }
        }
        var image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (var pos : pSet) {
            image.setRGB((int) pos.x(), (int) pos.z(), Bitmaps.IN_REGION);
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
    public static Set<Pos> loadBmpToSet(@NonNull File file) throws IOException {
        var pSet = new HashSet<Pos>();
        var image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((image.getRGB(i, j) & Bitmaps.RGB_MASK) == Bitmaps.IN_REGION) {
                    pSet.add(new Pos(i, j));
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
    public static Set<Pos> loadBmpToSet(@NonNull InputStream input) throws IOException {
        var pSet = new HashSet<Pos>();
        var image = ImageIO.read(input);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((image.getRGB(i, j) & Bitmaps.RGB_MASK) == Bitmaps.IN_REGION) {
                    pSet.add(new Pos(i, j));
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
    public static void saveListToBmp(@NonNull List<Pos> pLst, @NonNull File file) throws IOException, IllegalArgumentException, Exception {
        int width = 0;
        int height = 0;
        for (var pos : pLst) {
            long x = pos.x();
            long z = pos.z();
            if (x < 0 || z < 0 || x > Integer.MAX_VALUE || z > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("Invalid pos found: " + pos);
            }
            if (x + 1 > width) {
                width = (int) x + 1;
            }
            if (z + 1 > height) {
                height = (int) z + 1;
            }
        }
        var image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (var pos : pLst) {
            image.setRGB((int) pos.x(), (int) pos.z(), Bitmaps.IN_REGION);
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
    public static List<Pos> loadBmpToList(@NonNull File file) throws IOException {
        var pLst = new LinkedList<Pos>();
        var image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((image.getRGB(i, j) & Bitmaps.RGB_MASK) == Bitmaps.IN_REGION) {
                    pLst.add(new Pos(i, j));
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
    public static List<Pos> loadBmpToList(@NonNull InputStream input) throws IOException {
        var pLst = new LinkedList<Pos>();
        var image = ImageIO.read(input);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((image.getRGB(i, j) & Bitmaps.RGB_MASK) == Bitmaps.IN_REGION) {
                    pLst.add(new Pos(i, j));
                }
            }
        }
        return pLst;
    }

}
