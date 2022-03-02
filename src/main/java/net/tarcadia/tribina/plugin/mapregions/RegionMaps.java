package net.tarcadia.tribina.plugin.mapregions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

public class RegionMaps {

    private final String pathRegionMaps;
    private final ConfigurationSection config;
    private final ConfigurationSection configGlobal;
    private final List<String> mapsList;
    private final Map<String, RegionMap> maps;

    public RegionMaps(@NonNull ConfigurationSection config, @NonNull String pathRegionMaps) {
        this.pathRegionMaps = pathRegionMaps;
        this.config = config;
        this.configGlobal = Objects.requireNonNullElseGet(
                config.getConfigurationSection("global"),
                () -> this.config.createSection("global")
        );
        Main.logger.info("Loaded global region.");
        this.mapsList = this.config.getStringList("maps");
        this.maps = new HashMap<>();
        for (String mapId : this.mapsList) {
            this.loadMap(mapId);
            Main.logger.info("Loaded " + mapId + " region.");
        }
    }

    public RegionMaps(@NonNull String pathConfig, @NonNull String pathRegionMaps) {
        this.pathRegionMaps = pathRegionMaps;
        this.config = YamlConfiguration.loadConfiguration(new File(pathConfig));
        this.configGlobal = Objects.requireNonNullElseGet(
                config.getConfigurationSection("global"),
                () -> config.createSection("global")
        );
        Main.logger.info("Loaded global region.");
        this.mapsList = this.config.getStringList("maps");
        this.maps = new HashMap<>();
        for (String mapId : this.mapsList) {
            this.loadMap(mapId);
            Main.logger.info("Loaded " + mapId + " region.");
        }
    }

    public void loadMap(String mapId) {
        this.maps.putIfAbsent(mapId, new RegionMap(
                this.pathRegionMaps + "/" + mapId + ".yml",
                this.pathRegionMaps + "/" + mapId
        ));
    }

    public void reloadMap(String mapId) {
        this.maps.put(mapId, new RegionMap(
                this.pathRegionMaps + "/" + mapId + ".yml",
                this.pathRegionMaps + "/" + mapId
        ));
    }

    public void save() {
        for (String mapId : this.mapsList) {
            try {
                this.maps.get(mapId).save();
            } catch (Exception e) {
                Main.logger.log(
                        Level.SEVERE,
                        "Cannot save region map " + mapId,
                        e
                );
            }
        }
    }

    public boolean inMapList(String mapId) {
        return this.mapsList.contains(mapId);
    }

    public List<String> getMapList() {
        return List.copyOf(this.mapsList);
    }

    public boolean inRegionList(String mapId, String regionId) {
        return this.inMapList(mapId) && this.maps.get(mapId).inRegionList(regionId);
    }

    public List<String> getRegionList(String mapId) {
        if (this.inMapList(mapId)) {
            return List.copyOf(this.maps.get(mapId).getRegionList());
        } else {
            return new ArrayList<>();
        }
    }

}
