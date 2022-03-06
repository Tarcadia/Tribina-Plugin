package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.region.base.BaseDisjointSubRegion;
import net.tarcadia.tribina.plugin.mapregion.region.base.BaseSubRegion;
import net.tarcadia.tribina.plugin.mapregion.region.base.DisjointRegion;
import net.tarcadia.tribina.plugin.mapregion.region.base.ParentRegion;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;

public class AssetRegion extends BaseDisjointSubRegion {

    public AssetRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            @NotNull List<? extends DisjointRegion> peers,
            @NotNull ParentRegion parent
    ) {
        super(regionId, fileRoot, peers, parent);
    }

    // TODO: More implements that make asset a region that is a region

}
