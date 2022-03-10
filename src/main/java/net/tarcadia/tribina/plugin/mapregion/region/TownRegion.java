package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.region.base.BaseDisjointParentRegion;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class TownRegion extends BaseDisjointParentRegion {

    public static TownRegion create(String id, File fileConfig, File fileBitmap, List<? extends TownRegion> peers) {
        // TODO: Add a new implementation for creating empty region in BaseRegion and call that for this method
        return new TownRegion(id, fileConfig, fileBitmap, peers, new LinkedList<>());
    }

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

    public List<? extends AssetRegion> assets() {
        return this.assets;
    }

    // TODO: More implements that make town works.

}
