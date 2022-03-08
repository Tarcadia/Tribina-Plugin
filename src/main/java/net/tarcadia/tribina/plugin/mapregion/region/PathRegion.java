package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.region.base.BaseRegion;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

public class PathRegion extends BaseRegion {

    public static PathRegion create(String id, File fileConfig, File fileBitmap) {
        // TODO: Add a new implementation for creating empty region in BaseRegion and call that for this method
        return new PathRegion(id, fileConfig, fileBitmap);
    }

    public PathRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot
    ) {
        super("path." + regionId, fileRoot);
    }

    public PathRegion(
            @NotNull String regionId,
            @NotNull File fileConfig,
            @NotNull File fileBitmap
    ) {
        super("path." + regionId, fileConfig, fileBitmap);
    }

    // TODO: More implements that make path do something

}
