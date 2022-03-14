package net.tarcadia.tribina.erod.mapregion.region;

import net.tarcadia.tribina.erod.mapregion.region.base.BaseDisjointParentRegion;
import net.tarcadia.tribina.erod.mapregion.region.base.BaseRegion;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class TownRegion extends BaseDisjointParentRegion {

    public static void initAssetRegion(@NotNull String id, @NotNull File fileConfig, @NotNull File fileBitmap, @NotNull Location loc) {
        BaseRegion.initBaseRegion(id, fileConfig, fileBitmap, loc);
        // TODO: More init, when further implements finished.
    }

    public static TownRegion create(
            @NotNull String id,
            @NotNull File fileConfig,
            @NotNull File fileBitmap,
            @NotNull List<? extends TownRegion> peers,
            @NotNull Location loc
    ) {
        TownRegion.initAssetRegion(id, fileConfig, fileBitmap, loc);
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
