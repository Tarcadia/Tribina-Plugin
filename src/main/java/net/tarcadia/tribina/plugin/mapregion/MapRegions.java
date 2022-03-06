package net.tarcadia.tribina.plugin.mapregion;

import net.tarcadia.tribina.plugin.mapregion.region.AssetRegion;
import net.tarcadia.tribina.plugin.mapregion.region.PathRegion;
import net.tarcadia.tribina.plugin.mapregion.region.LandRegion;
import net.tarcadia.tribina.plugin.mapregion.region.TownRegion;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.wasted.mapregions.Main;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MapRegions {

    public static final String KEY_REGION_PATH_LIST = "regions.path";
    public static final String KEY_REGION_TOWN_LIST = "regions.town";
    public static final String KEY_REGION_LAND_LIST = "regions.land";

    public static final String PATH_MAPREGIONS = "/MapRegions/";
    public static final String PATH_FILE_CONFIG = PATH_MAPREGIONS + "/config.yml";
    public static final String PATH_REGION_PATHS = PATH_MAPREGIONS + "/Paths/";
    public static final String PATH_REGION_LANDS = PATH_MAPREGIONS + "/Lands/";
    public static final String PATH_REGION_TOWNS = PATH_MAPREGIONS + "/Towns/";

    public static Configuration config = null;
    public static List<PathRegion> regionPaths = null;
    public static List<LandRegion> regionLands = null;
    public static List<TownRegion> regionTowns = null;
    public static List<AssetRegion> regionAssets = null;

    public void load() {
        this.loadConfig();
        this.loadPaths();
        this.loadLands();
        this.loadTowns();
    }

    public void loadConfig() {
        MapRegions.config = new Configuration(new File(Main.dataPath + PATH_FILE_CONFIG));
    }

    public void loadPaths() {
        if (MapRegions.config != null) {
            var configSec = MapRegions.config.getConfigurationSection(KEY_REGION_PATH_LIST);
            if (configSec != null) for (var id : configSec.getKeys(false)) {
                var fileConfig = new File(Main.dataPath + PATH_REGION_PATHS + id + ".yml");
                var fileBitmap = new File(Main.dataPath + PATH_REGION_PATHS + id + ".bmp");
                var pathRegion = new PathRegion(id, fileConfig, fileBitmap);
                MapRegions.regionPaths.add(pathRegion);
            }
        }
    }

    public void loadLands() {
        if (MapRegions.config != null) {
            var configSec = MapRegions.config.getConfigurationSection(KEY_REGION_LAND_LIST);
            if (configSec != null) for (var id : configSec.getKeys(false)) {
                var fileConfig = new File(Main.dataPath + PATH_REGION_LANDS + id + ".yml");
                var fileBitmap = new File(Main.dataPath + PATH_REGION_LANDS + id + ".bmp");
                var landRegion = new LandRegion(id, fileConfig, fileBitmap, MapRegions.regionLands);
                MapRegions.regionLands.add(landRegion);
            }
        }
    }

    public void loadTowns() {
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
                }
                MapRegions.regionAssets.addAll(assets);
            }
        }
    }

}
