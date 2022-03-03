package net.tarcadia.tribina.plugin.mapregion;

import net.tarcadia.tribina.plugin.util.Configuration;
import org.jetbrains.annotations.NotNull;

public class Region {

    private final String filePath;
    private final Configuration config;

    public Region(@NotNull String name, @NotNull String fileRoot, int ttl) {
        this.filePath = fileRoot + "/" + name;
        this.config = new Configuration(this.filePath + ".yml", ttl);
    }

    public Region(@NotNull String name, @NotNull String fileRoot) {
        this.filePath = fileRoot + "/" + name;
        this.config = new Configuration(this.filePath + ".yml");
    }

}
