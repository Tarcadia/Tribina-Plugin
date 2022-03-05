package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.Main;
import net.tarcadia.tribina.plugin.mapregion.posset.BasePosSet;
import net.tarcadia.tribina.plugin.mapregion.posset.GlobalPosSet;
import net.tarcadia.tribina.plugin.mapregion.posset.PosSet;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.util.func.Bitmaps;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;

public class BaseRegion implements PosSet, Region {

    static final String KEY_LOC_LOC = "loc.loc";
    static final String KEY_LOC_OFFSET_X = "loc.offset.x";
    static final String KEY_LOC_OFFSET_Z = "loc.offset.z";

    static final String KEY_DISP_NAME = "disp.name";
    static final String KEY_DISP_LORE = "disp.lore";
    static final String KEY_AUTH = "auth";

    static final long MAX_MAP_SCALE = 8192;

    protected final File fileConfig;
    protected final File fileBitmap;
    protected final Configuration config;
    protected final GlobalPosSet map;

    protected Location loc;
    protected long biasX;
    protected long biasZ;

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
            boolean isNull;
            try {
                long offsetX = this.config.getLong(KEY_LOC_OFFSET_X);
                long offsetZ = this.config.getLong(KEY_LOC_OFFSET_Z);
                this.loc = this.config.getLocation(KEY_LOC_LOC);
                this.biasX = loc.getBlockX() + offsetX;
                this.biasZ = loc.getBlockZ() + offsetZ;
                var posSet = Bitmaps.loadBmpToSet(fileBitmap);
                for (var pos : posSet) {
                    this.map.add(pos.x() + biasX, pos.y() + biasZ);
                }
                isNull = false;
            } catch (Exception e) {
                this.loc = null;
                this.biasX = 0;
                this.biasZ = 0;
                isNull = true;
            }
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
            boolean isNull;
            try {
                long offsetX = this.config.getLong(KEY_LOC_OFFSET_X);
                long offsetZ = this.config.getLong(KEY_LOC_OFFSET_Z);
                this.loc = this.config.getLocation(KEY_LOC_LOC);
                this.biasX = loc.getBlockX() + offsetX;
                this.biasZ = loc.getBlockZ() + offsetZ;
                var posSet = Bitmaps.loadBmpToSet(fileBitmap);
                for (var pos : posSet) {
                    this.map.add(pos.x() + biasX, pos.y() + biasZ);
                }
                isNull = false;
            } catch (Exception e) {
                this.loc = null;
                this.biasX = 0;
                this.biasZ = 0;
                isNull = true;
            }
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
            boolean isNull;
            try {
                long offsetX = this.config.getLong(KEY_LOC_OFFSET_X);
                long offsetZ = this.config.getLong(KEY_LOC_OFFSET_Z);
                this.loc = this.config.getLocation(KEY_LOC_LOC);
                this.biasX = loc.getBlockX() + offsetX;
                this.biasZ = loc.getBlockZ() + offsetZ;
                var posSet = Bitmaps.loadBmpToSet(fileBitmap);
                for (var pos : posSet) {
                    this.map.add(pos.x() + biasX, pos.y() + biasZ);
                }
                isNull = false;
            } catch (Exception e) {
                this.loc = null;
                this.biasX = 0;
                this.biasZ = 0;
                isNull = true;
            }
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
            boolean isNull;
            try {
                long offsetX = this.config.getLong(KEY_LOC_OFFSET_X);
                long offsetZ = this.config.getLong(KEY_LOC_OFFSET_Z);
                this.loc = this.config.getLocation(KEY_LOC_LOC);
                this.biasX = loc.getBlockX() + offsetX;
                this.biasZ = loc.getBlockZ() + offsetZ;
                var posSet = Bitmaps.loadBmpToSet(fileBitmap);
                for (var pos : posSet) {
                    this.map.add(pos.x() + biasX, pos.y() + biasZ);
                }
                isNull = false;
            } catch (Exception e) {
                this.loc = null;
                this.biasX = 0;
                this.biasZ = 0;
                isNull = true;
            }
            this.isNull = isNull;
        } else {
            this.loc = null;
            this.biasX = 0;
            this.biasZ = 0;
            this.isNull = true;
        }
    }

    @Override
    public void saveMap() {
        Main.logger.log(Level.INFO, "MR: Saving bitmap " + this.fileBitmap + ".");
        try{
            boolean flagTooLarge = false;
            var minX = this.minX();
            var minZ = this.minZ();
            if ((minX != null) && (minZ != null))
                this.reBias(minX, minZ);

            var set = new HashSet<Pair<Integer, Integer>>();
            var lst = this.map.getList();
            for (var pos : lst) {
                long _x = pos.x() - this.biasX;
                long _z = pos.y() - this.biasZ;
                if ((_x >= 0) && (_z >= 0) && (_x < MAX_MAP_SCALE) && (_z < MAX_MAP_SCALE)) {
                    var p = new Pair<>((int) _x, (int) _z);
                    set.add(p);
                } else {
                    flagTooLarge = true;
                }
            }
            if (flagTooLarge) Main.logger.log(Level.WARNING, "MR: Saving bitmap to large, some pos discarded.");
            Bitmaps.saveSetToBmp(set, this.fileBitmap);
        } catch (Exception e) {
            Main.logger.log(Level.WARNING, "MR: Saving bitmap failed.", e);
        }
    }

    @Override
    public boolean reLoc(@NotNull Location loc) {
        if (loc.getWorld() == this.loc.getWorld()) {
            long offsetX = this.biasX - loc.getBlockX();
            long offsetZ = this.biasZ - loc.getBlockZ();
            this.config.set(KEY_LOC_OFFSET_X, offsetX);
            this.config.set(KEY_LOC_OFFSET_Z, offsetZ);
            this.config.set(KEY_LOC_LOC, loc);
            Main.logger.log(Level.WARNING, "MR: Re-Location accessed.");
            return true;
        } else {
            Main.logger.log(Level.WARNING, "MR: Re-Location denied.");
            return false;
        }
    }

    @Override
    public boolean reBias(long x, long z) {
        long offsetX = x - this.loc.getBlockX();
        long offsetZ = z - this.loc.getBlockZ();
        this.biasX = x;
        this.biasZ = z;
        this.config.set(KEY_LOC_OFFSET_X, offsetX);
        this.config.set(KEY_LOC_OFFSET_Z, offsetZ);
        Main.logger.log(Level.WARNING, "MR: Re-Bias accessed.");
        return true;
    }

    @Override
    @NotNull
    public String getName() {
        return this.config.getString(KEY_DISP_NAME, "");
    }

    @Override
    @NotNull
    public String getLore() {
        return this.config.getString(KEY_DISP_LORE, "");
    }

    @Override
    @NotNull
    public List<String> getAuth() {
        return this.config.getStringList(KEY_AUTH);
    }

    @Override
    public boolean contains(long x, long z) {
        if (this.isNull) {
            return false;
        } else {
            return this.map.contains(x, z);
        }
    }

    @Override
    public boolean contains(@NotNull Pair<Long, Long> pos) {
        if (this.isNull) {
            return false;
        } else {
            return this.map.contains(pos);
        }
    }

    @Override
    public boolean containsAll(@NotNull Collection<? extends Pair<Long, Long>> pSet) {
        if (this.isNull) {
            return false;
        } else {
            return this.map.containsAll(pSet);
        }
    }

    @Override
    public void add(long x, long z) {
        if (!this.isNull) {
            this.map.add(x, z);
            this.saveMap();
        }
    }

    @Override
    public void add(@NotNull Pair<Long, Long> pos) {
        if (!this.isNull) {
            this.map.add(pos);
            this.saveMap();
        }
    }

    @Override
    public void addAll(@NotNull Collection<? extends Pair<Long, Long>> pSet) {
        if (!this.isNull) {
            this.map.addAll(pSet);
            this.saveMap();
        }
    }

    @Override
    public void addAll(@NotNull BasePosSet pSet) {
        if (!this.isNull) {
            this.map.addAll(pSet);
            this.saveMap();
        }
    }

    @Override
    public void sub(long x, long z) {
        if (!this.isNull) {
            this.map.sub(x, z);
            this.saveMap();
        }
    }

    @Override
    public void sub(@NotNull Pair<Long, Long> pos) {
        if (!this.isNull) {
            this.map.sub(pos);
            this.saveMap();
        }
    }

    @Override
    public void subAll(@NotNull Collection<? extends Pair<Long, Long>> pSet) {
        if (!this.isNull) {
            this.map.subAll(pSet);
            this.saveMap();
        }
    }

    @Override
    public void subAll(@NotNull BasePosSet pSet) {
        if (!this.isNull) {
            this.map.subAll(pSet);
            this.saveMap();
        }
    }

    @Override
    public void cross(@NotNull Collection<? extends Pair<Long, Long>> pSet) {
        if (!this.isNull) {
            this.map.cross(pSet);
            this.saveMap();
        }
    }

    @Override
    public void cross(@NotNull BasePosSet pSet) {
        if (!this.isNull) {
            this.map.cross(pSet);
            this.saveMap();
        }
    }

    @Override
    @Nullable
    public Long minX() {
        if (this.isNull) {
            return null;
        } else {
            return this.map.minX();
        }
    }

    @Override
    @Nullable
    public Long minZ() {
        if (this.isNull) {
            return null;
        } else {
            return this.map.minZ();
        }
    }

    @Override
    public boolean isEmpty() {
        if (this.isNull) {
            return true;
        } else {
            return this.map.isEmpty();
        }
    }

    @Override
    @NotNull
    public List<Pair<Long, Long>> getList() {
        if (!this.isNull) {
            return this.map.getList();
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    @NotNull
    public Set<Pair<Long, Long>> getSet() {
        if (!this.isNull) {
            return this.map.getSet();
        } else {
            return new HashSet<>();
        }
    }

}
