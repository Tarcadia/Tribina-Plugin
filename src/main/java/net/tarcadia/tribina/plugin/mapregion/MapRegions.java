package net.tarcadia.tribina.plugin.mapregion;

import net.tarcadia.tribina.plugin.mapregion.posset.PathRegion;
import net.tarcadia.tribina.plugin.mapregion.region.TopRegion;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.wasted.mapregions.Main;

import java.io.File;
import java.util.List;

public class MapRegions {

    public static Configuration config;
    public static List<? extends PathRegion> pathRegions;
    public static List<? extends TopRegion> topRegions;

    public void load()
    {
        MapRegions.config = new Configuration(new File(Main.dataPath + "/MapRegions.yml"));
        MapRegions.config.getStringList("paths");
    }

}
