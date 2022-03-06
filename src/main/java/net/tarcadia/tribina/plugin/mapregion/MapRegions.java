package net.tarcadia.tribina.plugin.mapregion;

import net.tarcadia.tribina.plugin.mapregion.region.AssetRegion;
import net.tarcadia.tribina.plugin.mapregion.region.PathRegion;
import net.tarcadia.tribina.plugin.mapregion.region.LandRegion;
import net.tarcadia.tribina.plugin.mapregion.region.TownRegion;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.wasted.mapregions.Main;

import java.io.File;
import java.util.List;

public class MapRegions {

    public static final String KEY_REGION_PATH_LIST = "regions.path";
    public static final String KEY_REGION_TOWN_LIST = "regions.town";
    public static final String KEY_REGION_LAND_LIST = "regions.land";

    public static Configuration config = null;
    public static List<PathRegion> regionPaths = null;
    public static List<LandRegion> regionLands = null;
    public static List<TownRegion> regionTowns = null;
    public static List<AssetRegion> regionAssets = null;

    public void load()
    {
        MapRegions.config = new Configuration(new File(Main.dataPath + "/MapRegions.yml"));
        MapRegions.config.getStringList("paths");
    }

}
