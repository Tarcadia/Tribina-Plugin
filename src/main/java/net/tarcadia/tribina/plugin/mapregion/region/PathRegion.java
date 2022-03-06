package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.region.base.BaseRegion;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

public class PathRegion extends BaseRegion {

    public PathRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot
    ) {
        super(regionId, fileRoot);
    }

    public PathRegion(
            @NotNull String regionId,
            @NotNull File fileConfig,
            @NotNull File fileBitmap
    ) {
        super(regionId, fileConfig, fileBitmap);
    }

    // TODO: More implements that make path do something

}
