package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.region.base.BaseDisjointRegion;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;

public class TownRegion extends BaseDisjointRegion {

    public TownRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            @NotNull List<? extends AssetRegion> peers
    ) {
        super(regionId, fileRoot, peers);
    }

    // TODO: More implements that make town works.

}
