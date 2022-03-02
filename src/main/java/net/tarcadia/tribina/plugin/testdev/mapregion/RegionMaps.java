package net.tarcadia.tribina.plugin.testdev.mapregion;

import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class RegionMaps<T> {

    private String configFileName;
    private YamlConfiguration config;
    private List<RegionMap<T>> maps;

    public RegionMaps(@NonNull String configFileName)
    {
        this.configFileName = configFileName;
        this.config = YamlConfiguration.loadConfiguration(new File(this.configFileName));
        this.maps = new LinkedList<>();
        for(String mapId : this.config.getConfigurationSection("maps").getKeys(false))
        {
            maps.add(new RegionMap<T>(mapId, this.config.getConfigurationSection("maps." + mapId)));
        }
    }
}
