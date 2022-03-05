package net.tarcadia.tribina.plugin.mapregion.region;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;

public class LandRegion extends BaseDisjointRegion {

    public LandRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            @NotNull List<? extends DisjointRegion> peers
    ) {
        super(regionId, fileRoot, peers);
    }

}
