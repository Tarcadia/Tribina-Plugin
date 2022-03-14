package net.tarcadia.tribina.erod.mapregion.region;

import net.tarcadia.tribina.erod.mapregion.region.base.BaseRegion;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

public class PathRegion extends BaseRegion {

    public static void initPathRegion(@NotNull String id, @NotNull File fileConfig, @NotNull File fileBitmap, @NotNull Location loc) {
        BaseRegion.initBaseRegion(id, fileConfig, fileBitmap, loc);
        // TODO: More init, when further implements finished.
    }

    public static PathRegion create(
            @NotNull String id,
            @NotNull File fileConfig,
            @NotNull File fileBitmap,
            @NotNull Location loc
    ) {
        PathRegion.initPathRegion(id, fileConfig, fileBitmap, loc);
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
