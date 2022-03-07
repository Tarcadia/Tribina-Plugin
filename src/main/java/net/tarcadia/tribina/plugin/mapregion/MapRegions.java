package net.tarcadia.tribina.plugin.mapregion;

import net.tarcadia.tribina.plugin.mapregion.region.AssetRegion;
import net.tarcadia.tribina.plugin.mapregion.region.PathRegion;
import net.tarcadia.tribina.plugin.mapregion.region.LandRegion;
import net.tarcadia.tribina.plugin.mapregion.region.TownRegion;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.wasted.mapregions.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class MapRegions {

    public static final String KEY_ENABLED = "enabled";

    public static final String KEY_REGION_PATH_LIST = "regions.path";
    public static final String KEY_REGION_TOWN_LIST = "regions.town";
    public static final String KEY_REGION_LAND_LIST = "regions.land";

    public static final String AUTH_REGION_GLOBAL = "region.global";
    public static final String AUTH_REGION_PATH_PUBLIC = "region.path.public";
    public static final String AUTH_REGION_PATH_OPERATOR = "region.path.operator";
    public static final String AUTH_REGION_LAND_PUBLIC = "region.land.public";
    public static final String AUTH_REGION_TOWN_EXOTIC = "region.town.exotic";
    public static final String AUTH_REGION_TOWN_CITIZEN = "region.town.citizen";
    public static final String AUTH_REGION_TOWN_OPERATOR = "region.town.operator";
    public static final String AUTH_REGION_ASSET_PUBLIC = "region.asset.public";
    public static final String AUTH_REGION_ASSET_OWNER = "region.asset.owner";

    public static final String PATH_MAPREGIONS = "/MapRegions/";
    public static final String PATH_FILE_CONFIG = PATH_MAPREGIONS + "/config.yml";
    public static final String PATH_REGION_PATHS = PATH_MAPREGIONS + "/Paths/";
    public static final String PATH_REGION_LANDS = PATH_MAPREGIONS + "/Lands/";
    public static final String PATH_REGION_TOWNS = PATH_MAPREGIONS + "/Towns/";

    public static final List<PathRegion> regionPaths = new LinkedList<>();
    public static final List<LandRegion> regionLands = new LinkedList<>();
    public static final List<TownRegion> regionTowns = new LinkedList<>();
    public static final List<AssetRegion> regionAssets = new LinkedList<>();

    private static Configuration config = null;

    public static void load() {
        Main.logger.info("[MR] Loading...");
        MapRegions.loadConfig();
        MapRegions.loadPaths();
        MapRegions.loadLands();
        MapRegions.loadTowns();
        Main.logger.info("[MR] Loaded.");
    }

    private static void loadConfig() {
        var defConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(Main.plugin.getResource(PATH_FILE_CONFIG))
        );

        MapRegions.config = new Configuration(
                new File(Main.dataPath + PATH_FILE_CONFIG),
                defConfig
        );

        Main.logger.info("[MR] Loaded config.");
    }

    private static void loadPaths() {
        if (MapRegions.config != null) {
            var configSec = MapRegions.config.getConfigurationSection(KEY_REGION_PATH_LIST);
            if (configSec != null) for (var id : configSec.getKeys(false)) {
                var fileConfig = new File(Main.dataPath + PATH_REGION_PATHS + id + ".yml");
                var fileBitmap = new File(Main.dataPath + PATH_REGION_PATHS + id + ".bmp");
                var pathRegion = new PathRegion(id, fileConfig, fileBitmap);
                MapRegions.regionPaths.add(pathRegion);
                Main.logger.info("[MR] Loaded path " + pathRegion.id() + ".");
            }
        }
        Main.logger.info("[MR] Loaded paths.");
    }

    private static void loadLands() {
        if (MapRegions.config != null) {
            var configSec = MapRegions.config.getConfigurationSection(KEY_REGION_LAND_LIST);
            if (configSec != null) for (var id : configSec.getKeys(false)) {
                var fileConfig = new File(Main.dataPath + PATH_REGION_LANDS + id + ".yml");
                var fileBitmap = new File(Main.dataPath + PATH_REGION_LANDS + id + ".bmp");
                var landRegion = new LandRegion(id, fileConfig, fileBitmap, MapRegions.regionLands);
                MapRegions.regionLands.add(landRegion);
                Main.logger.info("[MR] Loaded land " + landRegion.id() + ".");
            }
        }
        Main.logger.info("[MR] Loaded lands.");
    }

    private static void loadTowns() {
        if (MapRegions.config != null) {
            var configSec = MapRegions.config.getConfigurationSection(KEY_REGION_TOWN_LIST);
            if (configSec != null) for (var id : configSec.getKeys(false)) {
                var subConfigSec = configSec.getConfigurationSection(id);
                var fileConfig = new File(Main.dataPath + PATH_REGION_TOWNS + id + ".yml");
                var fileBitmap = new File(Main.dataPath + PATH_REGION_TOWNS + id + ".bmp");
                var assets = new LinkedList<AssetRegion>();
                var townRegion = new TownRegion(id, fileConfig, fileBitmap, MapRegions.regionTowns, assets);
                MapRegions.regionTowns.add(townRegion);

                if (subConfigSec != null) for (var subId : subConfigSec.getKeys(false)) {
                    var subFileConfig = new File(Main.dataPath + PATH_REGION_TOWNS + id + "/" + subId + ".yml");
                    var subFileBitmap = new File(Main.dataPath + PATH_REGION_TOWNS + id + "/" + subId + ".bmp");
                    var assetRegion = new AssetRegion(subId, subFileConfig, subFileBitmap, assets, townRegion);
                    assets.add(assetRegion);
                    Main.logger.info("[MR] Loaded asset " + assetRegion.id() + "@" + townRegion.id() + ".");
                }
                MapRegions.regionAssets.addAll(assets);
                Main.logger.info("[MR] Loaded town " + townRegion.id() + ".");
            }
        }
        Main.logger.info("[MR] Loaded towns.");
    }

    public static Configuration config() {
        return MapRegions.config;
    }

    public static boolean enabled() {
        return MapRegions.config.getBoolean(KEY_ENABLED);
    }

    public static void enable() {
        MapRegions.config.set(KEY_ENABLED, true);
        Main.logger.info("[MR] Set enabled.");
    }

    public static void disable() {
        MapRegions.config.set(KEY_ENABLED, false);
        Main.logger.info("[MR] Set disabled.");
    }


}
