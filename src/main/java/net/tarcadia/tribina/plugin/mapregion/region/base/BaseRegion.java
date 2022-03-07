package net.tarcadia.tribina.plugin.mapregion.region.base;

import net.tarcadia.tribina.plugin.Main;
import net.tarcadia.tribina.plugin.mapregion.posset.GlobalPosSet;
import net.tarcadia.tribina.plugin.mapregion.posset.PosSet;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.util.func.Bitmaps;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;

public class BaseRegion implements PosSet, Region {

    public static final String KEY_LOC_LOC = "loc.loc";
    public static final String KEY_LOC_OFFSET_X = "loc.offset.x";
    public static final String KEY_LOC_OFFSET_Z = "loc.offset.z";

    public static final String KEY_DISP_NAME = "disp.name";
    public static final String KEY_DISP_LORE = "disp.lore";
    public static final String KEY_AUTH = "auth";

    public static final long MAX_MAP_SCALE = 8192;

    private final String id;
    private final File fileConfig;
    private final File fileBitmap;
    private final Configuration config;
    private final GlobalPosSet map;
    private final boolean isNull;

    private Location loc;
    private long biasX;
    private long biasZ;

    public BaseRegion() {
        this.id = "";
        this.fileConfig = null;
        this.fileBitmap = null;
        this.config = null;
        this.map = null;
        this.loc = null;
        this.biasX = 0;
        this.biasZ = 0;
        this.isNull = true;
    }

    public BaseRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot
    ) {
        this.id = regionId;
        this.fileConfig = new File(fileRoot + "/" + regionId + ".yml");
        this.fileBitmap = new File(fileRoot + "/" + regionId + ".bmp");
        this.config = Configuration.getConfiguration(fileConfig);
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

    public BaseRegion(
            @NotNull String regionId,
            @NotNull File fileConfig,
            @NotNull File fileBitmap
    ) {
        this.id = regionId;
        this.fileConfig = fileConfig;
        this.fileBitmap = fileBitmap;
        this.config = Configuration.getConfiguration(fileConfig);
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
    @Nullable
    public String id() {
        return this.id;
    }

    @Override
    @Nullable
    public File fileConfig() {
        return this.fileConfig;
    }

    @Override
    @Nullable
    public File fileBitmap() {
        return this.fileBitmap;
    }

    @Override
    @Nullable
    public Configuration config() {
        return this.config;
    }

    @Override
    @Nullable
    public GlobalPosSet map() {
        return this.map;
    }

    @Override
    public boolean isNull() {
        return this.isNull;
    }

    @Override
    @Nullable
    public Location loc() {
        return this.loc;
    }

    @Override
    public long biasX() {
        return this.biasX;
    }

    @Override
    public long biasZ() {
        return this.biasZ;
    }

    @Override
    public void saveMap() {
        Main.logger.log(Level.INFO, "MR: Saving bitmap " + this.fileBitmap + ".");
        try{
            boolean flagTooLarge = false;
            var minX = this.minX();
            var minZ = this.minZ();
            if ((minX != null) && (minZ != null) && ((minX <= this.biasX) || (minZ <= this.biasZ)))
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
    public String getName(@NotNull Player player) {
        return this.getName();
    }

    @Override
    @NotNull
    public String getLore() {
        return this.config.getString(KEY_DISP_LORE, "");
    }

    @Override
    @NotNull
    public String getLore(@NotNull Player player) {
        return this.getLore();
    }

    @Override
    @NotNull
    public List<String> getAuth() {
        return this.config.getStringList(KEY_AUTH);
    }

    @Override
    @NotNull
    public List<String> getAuth(@NotNull Player player) {
        return this.getAuth();
    }

    @Override
    public void setName(@NotNull String name) {
        this.config.set(KEY_DISP_NAME, name);
    }

    @Override
    public void setLore(@NotNull String lore) {
        this.config.set(KEY_DISP_LORE, lore);
    }

    @Override
    public void addAuth(@NotNull String auth) {
        var lst = this.config.getStringList(KEY_AUTH);
        lst.add(auth);
        this.config.set(KEY_AUTH, lst);
    }

    @Override
    public void removeAuth(@NotNull String auth) {
        var lst = this.config.getStringList(KEY_AUTH);
        lst.remove(auth);
        this.config.set(KEY_AUTH, lst);
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
    public void addAll(@NotNull PosSet pSet) {
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
    public void subAll(@NotNull PosSet pSet) {
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
    public void cross(@NotNull PosSet pSet) {
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
