package net.tarcadia.tribina.plugin.mapregion;

import net.tarcadia.tribina.plugin.mapregion.region.AssetRegion;
import net.tarcadia.tribina.plugin.mapregion.region.PathRegion;
import net.tarcadia.tribina.plugin.mapregion.region.LandRegion;
import net.tarcadia.tribina.plugin.mapregion.region.TownRegion;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class MapRegions {

    public static final String MR = "[MR] ";

    public static final String KEY_MAPREGIONS_ENABLED = "enabled";
    public static final String KEY_MAPREGIONS_PATH_LIST = "paths";
    public static final String KEY_MAPREGIONS_TOWN_LIST = "towns";
    public static final String KEY_MAPREGIONS_LAND_LIST = "lands";
    public static final String KEY_MAPREGIONS_GLOBAL = "global";
    public static final String KEY_MAPREGIONS_GLOBAL_DISP_NAME = "global.disp.name";
    public static final String KEY_MAPREGIONS_GLOBAL_DISP_LORE = "global.disp.lore";
    public static final String KEY_MAPREGIONS_GLOBAL_AUTH = "global.auth";

    public static final String AUTH_MAPREGIONS_GLOBAL = "region.global";
    public static final String AUTH_MAPREGIONS_PATH_PUBLIC = "region.path.public";
    public static final String AUTH_MAPREGIONS_PATH_OPERATOR = "region.path.operator";
    public static final String AUTH_MAPREGIONS_LAND_PUBLIC = "region.land.public";
    public static final String AUTH_MAPREGIONS_TOWN_EXOTIC = "region.town.exotic";
    public static final String AUTH_MAPREGIONS_TOWN_CITIZEN = "region.town.citizen";
    public static final String AUTH_MAPREGIONS_TOWN_OPERATOR = "region.town.operator";
    public static final String AUTH_MAPREGIONS_ASSET_PUBLIC = "region.asset.public";
    public static final String AUTH_MAPREGIONS_ASSET_OWNER = "region.asset.owner";

    public static final String PATH_ROOT = "/MapRegions/";
    public static final String PATH_ROOT_PATHS = PATH_ROOT + "/Paths/";
    public static final String PATH_ROOT_LANDS = PATH_ROOT + "/Lands/";
    public static final String PATH_ROOT_TOWNS = PATH_ROOT + "/Towns/";
    public static final String PATH_FILE_CONFIG = PATH_ROOT + "/config.yml";

    public static final List<PathRegion> regionPaths = new LinkedList<>();
    public static final List<LandRegion> regionLands = new LinkedList<>();
    public static final List<TownRegion> regionTowns = new LinkedList<>();
    public static final List<AssetRegion> regionAssets = new LinkedList<>();

    private static Configuration config = null;

    public static void load() {
        Main.logger.info(MR + "Loading...");
        MapRegions.loadConfig();
        MapRegions.loadPaths();
        MapRegions.loadLands();
        MapRegions.loadTowns();
        Main.logger.info(MR + "Loaded.");
    }

    private static void loadConfig() {
        var defConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(Main.plugin.getResource(PATH_FILE_CONFIG))
        );

        MapRegions.config = Configuration.getConfiguration(new File(Main.dataPath + PATH_FILE_CONFIG));
        MapRegions.config.setDefaults(defConfig);

        Main.logger.info(MR + "Loaded config.");
    }

    private static void loadPaths() {
        if (MapRegions.config != null) {
            var configSec = MapRegions.config.getConfigurationSection(KEY_MAPREGIONS_PATH_LIST);
            if (configSec != null) for (var id : configSec.getKeys(false)) {
                var fileConfig = new File(Main.dataPath + PATH_ROOT_PATHS + id + ".yml");
                var fileBitmap = new File(Main.dataPath + PATH_ROOT_PATHS + id + ".bmp");
                var pathRegion = new PathRegion(id, fileConfig, fileBitmap);
                MapRegions.regionPaths.add(pathRegion);
                Main.logger.info(MR + "Loaded path " + pathRegion.id() + ".");
            }
        }
        Main.logger.info(MR + "Loaded paths.");
    }

    private static void loadLands() {
        if (MapRegions.config != null) {
            var configSec = MapRegions.config.getConfigurationSection(KEY_MAPREGIONS_LAND_LIST);
            if (configSec != null) for (var id : configSec.getKeys(false)) {
                var fileConfig = new File(Main.dataPath + PATH_ROOT_LANDS + id + ".yml");
                var fileBitmap = new File(Main.dataPath + PATH_ROOT_LANDS + id + ".bmp");
                var landRegion = new LandRegion(id, fileConfig, fileBitmap, MapRegions.regionLands);
                MapRegions.regionLands.add(landRegion);
                Main.logger.info(MR + "Loaded land " + landRegion.id() + ".");
            }
        }
        Main.logger.info(MR + "Loaded lands.");
    }

    private static void loadTowns() {
        if (MapRegions.config != null) {
            var configSec = MapRegions.config.getConfigurationSection(KEY_MAPREGIONS_TOWN_LIST);
            if (configSec != null) for (var id : configSec.getKeys(false)) {
                var subConfigSec = configSec.getConfigurationSection(id);
                var fileConfig = new File(Main.dataPath + PATH_ROOT_TOWNS + id + ".yml");
                var fileBitmap = new File(Main.dataPath + PATH_ROOT_TOWNS + id + ".bmp");
                var assets = new LinkedList<AssetRegion>();
                var townRegion = new TownRegion(id, fileConfig, fileBitmap, MapRegions.regionTowns, assets);
                MapRegions.regionTowns.add(townRegion);

                if (subConfigSec != null) for (var subId : subConfigSec.getKeys(false)) {
                    var subFileConfig = new File(Main.dataPath + PATH_ROOT_TOWNS + id + "/" + subId + ".yml");
                    var subFileBitmap = new File(Main.dataPath + PATH_ROOT_TOWNS + id + "/" + subId + ".bmp");
                    var assetRegion = new AssetRegion(subId, subFileConfig, subFileBitmap, assets, townRegion);
                    assets.add(assetRegion);
                    Main.logger.info(MR + "Loaded asset " + assetRegion.id() + "@" + townRegion.id() + ".");
                }
                MapRegions.regionAssets.addAll(assets);
                Main.logger.info(MR + "Loaded town " + townRegion.id() + ".");
            }
        }
        Main.logger.info(MR + "Loaded towns.");
    }

    public static Configuration config() {
        return MapRegions.config;
    }

    public static boolean enabled() {
        return MapRegions.config.getBoolean(KEY_MAPREGIONS_ENABLED);
    }

    public static void enable() {
        MapRegions.config.set(KEY_MAPREGIONS_ENABLED, true);
        Main.logger.info(MR + "Set enabled.");
    }

    public static void disable() {
        MapRegions.config.set(KEY_MAPREGIONS_ENABLED, false);
        Main.logger.info(MR + "Set disabled.");
    }

    @NotNull
    public static List<String> getAuthTags(@NotNull Player player) {
        List<String> authTags = new LinkedList<>(MapRegions.config.getStringList(KEY_MAPREGIONS_GLOBAL_AUTH));
        for (var region : regionPaths) if (region.inRegion(player)) authTags.addAll(region.getAuthTags(player));
        for (var region : regionLands) if (region.inRegion(player)) authTags.addAll(region.getAuthTags(player));
        for (var region : regionTowns) if (region.inRegion(player)) authTags.addAll(region.getAuthTags(player));
        for (var region : regionAssets) if (region.inRegion(player)) authTags.addAll(region.getAuthTags(player));
        return authTags;
    }

    @NotNull
    public static List<String> getRegionIdList() {
        List<String> ret = new LinkedList<>();
        for (var region : regionPaths) ret.add(region.id());
        for (var region : regionLands) ret.add(region.id());
        for (var region : regionTowns) ret.add(region.id());
        for (var region : regionAssets) ret.add(region.id());
        return ret;
    }

    @NotNull
    public static List<String> getRegionIdListAt(@NotNull Location loc) {
        List<String> ret = new LinkedList<>();
        for (var region : regionPaths) if (region.inRegion(loc)) ret.add(region.id());
        for (var region : regionLands) if (region.inRegion(loc)) ret.add(region.id());
        for (var region : regionTowns) if (region.inRegion(loc)) ret.add(region.id());
        for (var region : regionAssets) if (region.inRegion(loc)) ret.add(region.id());
        return ret;
    }

    @NotNull
    public static List<String> getRegionIdListAt(@NotNull Player player) {
        List<String> ret = new LinkedList<>();
        for (var region : regionPaths) if (region.inRegion(player)) ret.add(region.id());
        for (var region : regionLands) if (region.inRegion(player)) ret.add(region.id());
        for (var region : regionTowns) if (region.inRegion(player)) ret.add(region.id());
        for (var region : regionAssets) if (region.inRegion(player)) ret.add(region.id());
        return ret;
    }

    @Nullable
    public static String getRegionIdTopAt(@NotNull Location loc) {
        for (var region : regionAssets) if (region.inRegion(loc)) return region.id();
        for (var region : regionTowns) if (region.inRegion(loc)) return region.id();
        for (var region : regionPaths) if (region.inRegion(loc)) return region.id();
        for (var region : regionLands) if (region.inRegion(loc)) return region.id();
        return null;
    }

}
