package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.posset.GlobalPosSet;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.util.func.Bitmaps;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.Set;

public class BaseRegion {

    static String KEY_LOC_LOC = "loc.loc";
    static String KEY_LOC_OFFSET_X = "loc.offset.x";
    static String KEY_LOC_OFFSET_Z = "loc.offset.z";

    protected final File fileConfig;
    protected final File fileBitmap;
    protected final Configuration config;
    protected final GlobalPosSet map;

    protected final Location loc;
    protected final long biasX;
    protected final long biasZ;

    protected final boolean isNull;

    public BaseRegion() {
        this.fileConfig = null;
        this.fileBitmap = null;
        this.config = null;
        this.map = null;
        this.loc = null;
        this.biasX = 0;
        this.biasZ = 0;
        this.isNull = true;
    }

    public BaseRegion(@NotNull String name, @NotNull Path fileRoot, int ttl) {
        this.fileConfig = new File(fileRoot + "/" + name + ".yml");
        this.fileBitmap = new File(fileRoot + "/" + name + ".bmp");
        this.config = new Configuration(fileConfig, ttl);
        this.map = new GlobalPosSet();
        if (this.config.isLocation(KEY_LOC_LOC) &&
                this.config.isLong(KEY_LOC_OFFSET_X) &&
                this.config.isLong(KEY_LOC_OFFSET_Z)
        ) {
            Location loc = null;
            long biasX = 0;
            long biasZ = 0;
            boolean isNull = true;
            try {
                long offsetX = this.config.getLong(KEY_LOC_OFFSET_X);
                long offsetZ = this.config.getLong(KEY_LOC_OFFSET_Z);
                loc = this.config.getLocation(KEY_LOC_LOC);
                biasX = loc.getBlockX() + offsetX;
                biasZ = loc.getBlockZ() + offsetZ;
                var posSet = Bitmaps.loadBmpToSet(fileBitmap);
                for (var pos : posSet) {
                    this.map.add(pos.x() + biasX, pos.y() + biasZ);
                }
                isNull = false;
            } catch (Exception e) {
                loc = null;
                biasX = 0;
                biasZ = 0;
                isNull = true;
            }
            this.loc = loc;
            this.biasX = biasX;
            this.biasZ = biasZ;
            this.isNull = isNull;
        } else {
            this.loc = null;
            this.biasX = 0;
            this.biasZ = 0;
            this.isNull = true;
        }
    }

    public BaseRegion(@NotNull String name, @NotNull Path fileRoot) {
        this.fileConfig = new File(fileRoot + "/" + name + ".yml");
        this.fileBitmap = new File(fileRoot + "/" + name + ".bmp");
        this.config = new Configuration(fileConfig);
        this.map = new GlobalPosSet();
        if (this.config.isLocation(KEY_LOC_LOC) &&
                this.config.isLong(KEY_LOC_OFFSET_X) &&
                this.config.isLong(KEY_LOC_OFFSET_Z)
        ) {
            Location loc = null;
            long biasX = 0;
            long biasZ = 0;
            boolean isNull = true;
            try {
                long offsetX = this.config.getLong(KEY_LOC_OFFSET_X);
                long offsetZ = this.config.getLong(KEY_LOC_OFFSET_Z);
                loc = this.config.getLocation(KEY_LOC_LOC);
                biasX = loc.getBlockX() + offsetX;
                biasZ = loc.getBlockZ() + offsetZ;
                var posSet = Bitmaps.loadBmpToSet(fileBitmap);
                for (var pos : posSet) {
                    this.map.add(pos.x() + biasX, pos.y() + biasZ);
                }
                isNull = false;
            } catch (Exception e) {
                loc = null;
                biasX = 0;
                biasZ = 0;
                isNull = true;
            }
            this.loc = loc;
            this.biasX = biasX;
            this.biasZ = biasZ;
            this.isNull = isNull;
        } else {
            this.loc = null;
            this.biasX = 0;
            this.biasZ = 0;
            this.isNull = true;
        }
    }

    public BaseRegion(@NotNull File fileConfig, @NotNull File fileBitmap, int ttl) {
        this.fileConfig = fileConfig;
        this.fileBitmap = fileBitmap;
        this.config = new Configuration(fileConfig, ttl);
        this.map = new GlobalPosSet();
        if (this.config.isLocation(KEY_LOC_LOC) &&
                this.config.isLong(KEY_LOC_OFFSET_X) &&
                this.config.isLong(KEY_LOC_OFFSET_Z)
        ) {
            Location loc = null;
            long biasX = 0;
            long biasZ = 0;
            boolean isNull = true;
            try {
                long offsetX = this.config.getLong(KEY_LOC_OFFSET_X);
                long offsetZ = this.config.getLong(KEY_LOC_OFFSET_Z);
                loc = this.config.getLocation(KEY_LOC_LOC);
                biasX = loc.getBlockX() + offsetX;
                biasZ = loc.getBlockZ() + offsetZ;
                var posSet = Bitmaps.loadBmpToSet(fileBitmap);
                for (var pos : posSet) {
                    this.map.add(pos.x() + biasX, pos.y() + biasZ);
                }
                isNull = false;
            } catch (Exception e) {
                loc = null;
                biasX = 0;
                biasZ = 0;
                isNull = true;
            }
            this.loc = loc;
            this.biasX = biasX;
            this.biasZ = biasZ;
            this.isNull = isNull;
        } else {
            this.loc = null;
            this.biasX = 0;
            this.biasZ = 0;
            this.isNull = true;
        }
    }

    public BaseRegion(@NotNull File fileConfig, @NotNull File fileBitmap) {
        this.fileConfig = fileConfig;
        this.fileBitmap = fileBitmap;
        this.config = new Configuration(fileConfig);
        this.map = new GlobalPosSet();
        if (this.config.isLocation(KEY_LOC_LOC) &&
                this.config.isLong(KEY_LOC_OFFSET_X) &&
                this.config.isLong(KEY_LOC_OFFSET_Z)
        ) {
            Location loc = null;
            long biasX = 0;
            long biasZ = 0;
            boolean isNull = true;
            try {
                long offsetX = this.config.getLong(KEY_LOC_OFFSET_X);
                long offsetZ = this.config.getLong(KEY_LOC_OFFSET_Z);
                loc = this.config.getLocation(KEY_LOC_LOC);
                biasX = loc.getBlockX() + offsetX;
                biasZ = loc.getBlockZ() + offsetZ;
                var posSet = Bitmaps.loadBmpToSet(fileBitmap);
                for (var pos : posSet) {
                    this.map.add(pos.x() + biasX, pos.y() + biasZ);
                }
                isNull = false;
            } catch (Exception e) {
                loc = null;
                biasX = 0;
                biasZ = 0;
                isNull = true;
            }
            this.loc = loc;
            this.biasX = biasX;
            this.biasZ = biasZ;
            this.isNull = isNull;
        } else {
            this.loc = null;
            this.biasX = 0;
            this.biasZ = 0;
            this.isNull = true;
        }
    }

    // TODO: Finish the implementation

}
