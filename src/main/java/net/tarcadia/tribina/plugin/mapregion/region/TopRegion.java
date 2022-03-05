package net.tarcadia.tribina.plugin.mapregion.region;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;

public class TopRegion extends BaseRegion {

    private final String id;
    private final List<? extends BaseRegion> peers;

    public TopRegion(@NotNull String regionId, @NotNull Path fileRoot, List<? extends BaseRegion> peers) {
        super(regionId, fileRoot);
        this.id = regionId;
        this.peers = peers;
    }

}
