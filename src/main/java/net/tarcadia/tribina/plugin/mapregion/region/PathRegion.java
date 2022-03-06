package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.region.base.BaseRegion;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class PathRegion extends BaseRegion {

    public PathRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot
    ) {
        super(regionId, fileRoot);
    }

    // TODO: More implements that make path do something

}
