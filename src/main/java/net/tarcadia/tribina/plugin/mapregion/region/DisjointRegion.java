package net.tarcadia.tribina.plugin.mapregion.region;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;

public class DisjointRegion extends BaseRegion {

    protected final String id;
    protected final List<? extends BaseRegion> peers;

    public DisjointRegion(@NotNull String regionId, @NotNull Path fileRoot, @NotNull List<? extends BaseRegion> peers) {
        super(regionId, fileRoot);
        this.id = regionId;
        this.peers = peers;
    }

}
