package net.tarcadia.tribina.erod.mapregion.region;

import net.tarcadia.tribina.erod.mapregion.region.base.BaseDisjointRegion;
import net.tarcadia.tribina.erod.mapregion.region.base.BaseRegion;
import net.tarcadia.tribina.erod.mapregion.region.base.DisjointRegion;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class LandRegion extends BaseDisjointRegion {

    public static void initLandRegion(@NotNull String id, @NotNull File fileConfig, @NotNull File fileBitmap, @NotNull Location loc) {
        BaseRegion.initBaseRegion(id, fileConfig, fileBitmap, loc);
        // TODO: More init, when further implements finished.
    }

    public static LandRegion create(
            @NotNull String id,
            @NotNull File fileConfig,
            @NotNull File fileBitmap,
            @NotNull List<? extends LandRegion> peers,
            @NotNull Location loc
    ) {
        LandRegion.initLandRegion(id, fileConfig, fileBitmap, loc);
        return new LandRegion(id, fileConfig, fileBitmap, peers);
    }

    public LandRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            @NotNull List<? extends DisjointRegion> peers
    ) {
        super("land." + regionId, fileRoot, peers);
    }

    public LandRegion(
            @NotNull String regionId,
            @NotNull File fileConfig,
            @NotNull File fileBitmap,
            @NotNull List<? extends DisjointRegion> peers
    ) {
        super("land." + regionId, fileConfig, fileBitmap, peers);
    }

    // TODO: More implements that make land a region that looks interesting

}
