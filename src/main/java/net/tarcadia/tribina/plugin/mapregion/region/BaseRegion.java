package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.posset.GlobalPosSet;
import net.tarcadia.tribina.plugin.util.configuration.Configuration;
import org.jetbrains.annotations.NotNull;

public class BaseRegion {

    private final String filePath;
    private final Configuration config;
    private final GlobalPosSet map;

    public BaseRegion(@NotNull String name, @NotNull String fileRoot, int ttl) {
        this.filePath = fileRoot + "/" + name;
        this.config = new Configuration(this.filePath + ".yml", ttl);
        this.map = new GlobalPosSet(this.filePath + ".bmp");
    }

    public BaseRegion(@NotNull String name, @NotNull String fileRoot) {
        this.filePath = fileRoot + "/" + name;
        this.config = new Configuration(this.filePath + ".yml");
        this.map = new GlobalPosSet(this.filePath + ".bmp");
    }

    // TODO: Finish the implementation

}
