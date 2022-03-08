package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.region.base.BaseDisjointParentRegion;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class TownRegion extends BaseDisjointParentRegion {

    private final List<? extends AssetRegion> assets;

    public TownRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            @NotNull List<? extends TownRegion> peers,
            @NotNull List<? extends AssetRegion> assets
    ) {
        super("town." + regionId, fileRoot, peers, assets);
        this.assets = assets;
    }

    public TownRegion(
            @NotNull String regionId,
            @NotNull File fileConfig,
            @NotNull File fileBitmap,
            @NotNull List<? extends TownRegion> peers,
            @NotNull List<? extends AssetRegion> assets
    ) {
        super("town." + regionId, fileConfig, fileBitmap, peers, assets);
        this.assets = assets;
    }

    // TODO: More implements that make town works.

}
