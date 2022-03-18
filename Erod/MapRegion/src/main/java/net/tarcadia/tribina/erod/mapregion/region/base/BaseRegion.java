package net.tarcadia.tribina.erod.mapregion.region.base;

import net.tarcadia.tribina.erod.mapregion.MapRegion;
import net.tarcadia.tribina.erod.mapregion.util.data.Bitmaps;
import net.tarcadia.tribina.erod.mapregion.util.data.Configuration;
import net.tarcadia.tribina.erod.mapregion.util.type.Loc;
import net.tarcadia.tribina.erod.mapregion.util.type.Pos;
import net.tarcadia.tribina.erod.mapregion.util.type.posset.GlobalPosSet;
import net.tarcadia.tribina.erod.mapregion.util.type.posset.PosSet;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public class BaseRegion implements PosSet, Region {

    public static final String KEY_LOC_LOC_X = "loc.loc.x";
    public static final String KEY_LOC_LOC_Z = "loc.loc.z";
    public static final String KEY_LOC_LOC_WORLD = "loc.loc.world";
    public static final String KEY_LOC_OFFSET_X = "loc.offset.x";
    public static final String KEY_LOC_OFFSET_Z = "loc.offset.z";

    public static final String KEY_DISP_NAME = "disp.name";
    public static final String KEY_DISP_LORE = "disp.lore";
    public static final String KEY_AUTH = "auth";

    public static final String PATH_DEFAULT_MAP = MapRegion.PATH_RES_MAP_R3;

    public static final long MAX_MAP_SCALE = 8192;

    public static void initBaseRegion(@NotNull String id, @NotNull File fileConfig, @NotNull File fileBitmap, @NotNull Location loc) {
        try {
            var config = Configuration.getConfiguration(fileConfig);
            config.set(KEY_LOC_LOC_X, loc.getBlockX());
            config.set(KEY_LOC_LOC_Z, loc.getBlockZ());
            config.set(KEY_LOC_LOC_WORLD, Objects.requireNonNull(loc.getWorld()).getName());
            config.set(KEY_LOC_OFFSET_X, -3);
            config.set(KEY_LOC_OFFSET_Z, -3);
            config.set(KEY_DISP_NAME, "");
            config.set(KEY_DISP_LORE, "");
            config.set(KEY_AUTH, List.of());
            Bitmaps.saveListToBmp(Bitmaps.loadBmpToList(Objects.requireNonNull(MapRegion.plugin.getResource(PATH_DEFAULT_MAP))), fileBitmap);
        } catch (Exception e) {
            MapRegion.logger.severe("Init new region " + id + " failed.");
        }
    }

    private final String id;
    private final File fileConfig;
    private final File fileBitmap;
    private final Configuration config;
    private final GlobalPosSet map;
    private final boolean isNull;

    private Loc loc;
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
        if (
                this.config.isLong(KEY_LOC_LOC_X) &&
                this.config.isLong(KEY_LOC_LOC_Z) &&
                this.config.isString(KEY_LOC_LOC_WORLD) &&
                this.config.isLong(KEY_LOC_OFFSET_X) &&
                this.config.isLong(KEY_LOC_OFFSET_Z)
        ) {
            boolean isNull;
            try {
                long offsetX = this.config.getLong(KEY_LOC_OFFSET_X);
                long offsetZ = this.config.getLong(KEY_LOC_OFFSET_Z);
                long locX = this.config.getLong(KEY_LOC_LOC_X);
                long locZ = this.config.getLong(KEY_LOC_LOC_Z);
                String locWorld = Objects.requireNonNull(this.config.getString(KEY_LOC_LOC_WORLD));
                this.loc = new Loc(locWorld, locX, locZ);
                this.biasX = this.loc.x() + offsetX;
                this.biasZ = this.loc.z() + offsetZ;
                var posSet = Bitmaps.loadBmpToSet(fileBitmap);
                for (var pos : posSet) {
                    this.map.add(pos.x() + biasX, pos.z() + biasZ);
                }
                isNull = false;
            } catch (Exception e) {
                this.loc =null;
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
        if (
                this.config.isLong(KEY_LOC_LOC_X) &&
                this.config.isLong(KEY_LOC_LOC_Z) &&
                this.config.isString(KEY_LOC_LOC_WORLD) &&
                this.config.isLong(KEY_LOC_OFFSET_X) &&
                this.config.isLong(KEY_LOC_OFFSET_Z)
        ) {
            boolean isNull;
            try {
                long offsetX = this.config.getLong(KEY_LOC_OFFSET_X);
                long offsetZ = this.config.getLong(KEY_LOC_OFFSET_Z);
                long locX = this.config.getLong(KEY_LOC_LOC_X);
                long locZ = this.config.getLong(KEY_LOC_LOC_Z);
                String locWorld = Objects.requireNonNull(this.config.getString(KEY_LOC_LOC_WORLD));
                this.loc = new Loc(locWorld, locX, locZ);
                this.biasX = this.loc.x() + offsetX;
                this.biasZ = this.loc.z() + offsetZ;
                var posSet = Bitmaps.loadBmpToSet(fileBitmap);
                for (var pos : posSet) {
                    this.map.add(pos.x() + biasX, pos.z() + biasZ);
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
    @NotNull
    public Loc loc() {
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
        MapRegion.logger.info("Saving bitmap " + this.fileBitmap + ".");
        try{
            boolean flagTooLarge = false;
            var minX = this.minX();
            var minZ = this.minZ();
            if ((minX != null) && (minZ != null) && ((minX <= this.biasX) || (minZ <= this.biasZ)))
                this.reBias(minX, minZ);

            var set = new HashSet<Pos>();
            var lst = this.map.getList();
            for (var pos : lst) {
                long _x = pos.x() - this.biasX;
                long _z = pos.z() - this.biasZ;
                if ((_x >= 0) && (_z >= 0) && (_x < MAX_MAP_SCALE) && (_z < MAX_MAP_SCALE)) {
                    var p = new Pos(_x, _z);
                    set.add(p);
                } else {
                    flagTooLarge = true;
                }
            }
            if (flagTooLarge) MapRegion.logger.warning("Region " + this.id + " saving bitmap to large, some pos discarded.");
            Bitmaps.saveSetToBmp(set, this.fileBitmap);
        } catch (Exception e) {
            MapRegion.logger.warning("Region " + this.id + " saving bitmap failed.");
        }
    }

    @Override
    public boolean reLoc(@NotNull Location loc) {
        if (!this.isNull && (loc.getWorld() != null) && Objects.equals(loc.getWorld().getName(), this.loc.world())) {
            long offsetX = this.biasX - loc.getBlockX();
            long offsetZ = this.biasZ - loc.getBlockZ();
            this.config.set(KEY_LOC_OFFSET_X, offsetX);
            this.config.set(KEY_LOC_OFFSET_Z, offsetZ);
            this.config.set(KEY_LOC_LOC_X, loc.getBlockX());
            this.config.set(KEY_LOC_LOC_Z, loc.getBlockZ());
            this.config.set(KEY_LOC_LOC_WORLD, loc.getWorld().getName());
            MapRegion.logger.warning("Region " + this.id + " re-Location accessed.");
            return true;
        } else {
            MapRegion.logger.warning("Region " + this.id + " re-Location denied.");
            return false;
        }
    }

    @Override
    public boolean reBias(long x, long z) {
        if (!this.isNull) {
            long offsetX = x - this.loc.x();
            long offsetZ = z - this.loc.z();
            this.biasX = x;
            this.biasZ = z;
            this.config.set(KEY_LOC_OFFSET_X, offsetX);
            this.config.set(KEY_LOC_OFFSET_Z, offsetZ);
            MapRegion.logger.warning("Region " + this.id + " re-Bias accessed.");
            return true;
        } else {
            return false;
        }
    }

    @Override
    @NotNull
    public String getName() {
        if (!this.isNull) {
            return this.config.getString(KEY_DISP_NAME, "");
        } else {
            return "";
        }
    }

    @Override
    @NotNull
    public String getName(@NotNull Player player) {
        if (!this.isNull) {
            return this.getName();
        } else {
            return "";
        }
    }

    @Override
    @NotNull
    public String getLore() {
        if (!this.isNull) {
            return this.config.getString(KEY_DISP_LORE, "");
        } else {
            return "";
        }
    }

    @Override
    @NotNull
    public String getLore(@NotNull Player player) {
        if (!this.isNull) {
            return this.getLore();
        } else {
            return "";
        }
    }

    @Override
    @NotNull
    public List<String> getAuthTags() {
        if (!this.isNull) {
            return this.config.getStringList(KEY_AUTH);
        } else {
            return List.of();
        }
    }

    @Override
    @NotNull
    public List<String> getAuthTags(@NotNull Player player) {
        if (!this.isNull) {
            return this.getAuthTags();
        } else {
            return List.of();
        }
    }

    @Override
    public void setName(@NotNull String name) {
        if (!this.isNull) {
            this.config.set(KEY_DISP_NAME, name);
        }
    }

    @Override
    public void setLore(@NotNull String lore) {
        if (!this.isNull) {
            this.config.set(KEY_DISP_LORE, lore);
        }
    }

    @Override
    public void addAuthTag(@NotNull String auth) {
        if (!this.isNull) {
            this.config.addStringList(KEY_AUTH, auth);
        }
    }

    @Override
    public void removeAuthTag(@NotNull String auth) {
        if (!this.isNull) {
            this.config.addStringList(KEY_AUTH, auth);
        }
    }

    @Override
    public boolean inRegion(@NotNull Location loc) {
        if (!this.isNull && (loc.getWorld() != null)) {
            if (Objects.equals(loc.getWorld().getName(), this.loc.world())) {
                Pos pPos = new Pos(loc.getBlockX(), loc.getBlockZ());
                return this.contains(pPos);
            }
        }
        return false;
    }

    @Override
    public boolean inRegion(@NotNull Player player) {
        Location pLoc = player.getLocation();
        if (!this.isNull && (pLoc.getWorld() != null)) {
            if (Objects.equals(pLoc.getWorld().getName(), this.loc.world())) {
                Pos pPos = new Pos(pLoc.getBlockX(), pLoc.getBlockZ());
                return this.contains(pPos);
            }
        }
        return false;
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
    public boolean contains(@NotNull Pos pos) {
        if (this.isNull) {
            return false;
        } else {
            return this.map.contains(pos);
        }
    }

    @Override
    public boolean containsAll(@NotNull Collection<? extends Pos> pSet) {
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
    public void add(@NotNull Pos pos) {
        if (!this.isNull) {
            this.map.add(pos);
            this.saveMap();
        }
    }

    @Override
    public void addAll(@NotNull Collection<? extends Pos> pSet) {
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
    public void sub(@NotNull Pos pos) {
        if (!this.isNull) {
            this.map.sub(pos);
            this.saveMap();
        }
    }

    @Override
    public void subAll(@NotNull Collection<? extends Pos> pSet) {
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
    public void cross(@NotNull Collection<? extends Pos> pSet) {
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
    public List<Pos> getList() {
        if (!this.isNull) {
            return this.map.getList();
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    @NotNull
    public Set<Pos> getSet() {
        if (!this.isNull) {
            return this.map.getSet();
        } else {
            return new HashSet<>();
        }
    }

}
