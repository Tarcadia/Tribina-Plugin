package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.region.base.BaseDisjointRegion;
import net.tarcadia.tribina.plugin.mapregion.region.base.BaseRegion;
import net.tarcadia.tribina.plugin.mapregion.region.base.DisjointRegion;
import net.tarcadia.tribina.plugin.mapregion.region.base.DisjointSubRegion;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;

public class TownRegion extends BaseDisjointRegion implements DisjointRegion {
    public TownRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            @NotNull List<? extends DisjointRegion> peers
    ) {
        super(regionId, fileRoot, peers);
    }

    // TODO: More implements that make town works.

}
