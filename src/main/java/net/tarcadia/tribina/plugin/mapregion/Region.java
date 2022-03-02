package net.tarcadia.tribina.plugin.mapregion;

import net.tarcadia.tribina.plugin.Main;
import net.tarcadia.tribina.plugin.util.Configuration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Region {

    private final String name;
    private final String filePath;
    private final Region parent;
    private final Map<String, Region> peers;
    private final Map<String, Region> children;



    private final Configuration config;
    private final int ttl;

    public Region(@NotNull String name, @NotNull String fileRoot, @NotNull org.bukkit.configuration.Configuration def, int ttl) {
        this.name = name;
        this.filePath = fileRoot + "/" + this.name;
        this.parent = null;
        this.peers = new HashMap<>();
        this.children = new HashMap<>();

        this.config = new Configuration(this.filePath + ".yml", def, ttl);
        this.ttl = ttl;
    }

    public Region(@NotNull String name, @NotNull Region parent) {
        this.name = name;
        this.filePath = parent.filePath + "/" + this.name;
        this.parent = parent;
        this.peers = parent.children;
        this.children = new HashMap<>();
        this.peers.put(this.name, this);

        this.config = new Configuration(this.filePath + ".yml", parent.config, parent.ttl);
        this.ttl = parent.ttl;
    }



}
