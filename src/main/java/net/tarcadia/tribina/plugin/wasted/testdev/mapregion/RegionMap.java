package net.tarcadia.tribina.plugin.wasted.testdev.mapregion;

import org.bukkit.configuration.ConfigurationSection;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class RegionMap<T> {

    private String mapId;
    private ConfigurationSection config;
    private int x_offset;
    private int z_offset;
    private int x_length;
    private int z_length;
    private T[][] map;
    private Map<String, T> list;

    public RegionMap(@NonNull String mapId, ConfigurationSection config)
    {
        this.mapId = mapId;
        this.config = config;
        this.x_offset = this.config.getInt("position.x_offset");
        this.z_offset = this.config.getInt("position.z_offset");
        this.x_length = this.config.getInt("position.x_length");
        this.z_length = this.config.getInt("position.z_length");
        this.list = new HashMap<>();
        if (config != null)
        {
            for(String regionId : this.config.getConfigurationSection("regions").getKeys(false))
            {
                list.put(regionId, (T) new Region(mapId, regionId, config.getConfigurationSection("regions." + regionId)));
            }
        }
    }
}
