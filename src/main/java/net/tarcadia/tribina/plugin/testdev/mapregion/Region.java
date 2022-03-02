package net.tarcadia.tribina.plugin.testdev.mapregion;

import org.bukkit.configuration.ConfigurationSection;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Set;

public class Region {

    private String mapId;
    private String regionId;
    private String name;
    private String description;
    private Set<Integer[]> pos;

    public Region(@NonNull String mapId, @NonNull String regionId, ConfigurationSection config)
    {
        this.mapId = mapId;
        this.regionId = regionId;
        if (config != null)
        {
            this.name = config.getString("name");
            this.description = config.getString("description");
        }
        else
        {
            this.name = "";
            this.description = "";
        }
    }
}
