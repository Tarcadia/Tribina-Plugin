package net.tarcadia.tribina.plugin.mapregion;

import net.tarcadia.tribina.plugin.mapregion.region.PathRegion;
import net.tarcadia.tribina.plugin.mapregion.region.LandRegion;
import net.tarcadia.tribina.plugin.mapregion.region.TownRegion;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.wasted.mapregions.Main;

import java.io.File;
import java.util.List;

public class MapRegions {

    public static Configuration config;
    public static List<? extends PathRegion> paths;
    public static List<? extends TownRegion> towns;
    public static List<? extends LandRegion> lands;

    public void load()
    {
        MapRegions.config = new Configuration(new File(Main.dataPath + "/MapRegions.yml"));
        MapRegions.config.getStringList("paths");
    }

}
