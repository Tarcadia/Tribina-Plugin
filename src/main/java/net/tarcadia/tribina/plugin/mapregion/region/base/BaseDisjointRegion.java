package net.tarcadia.tribina.plugin.mapregion.region.base;

import net.tarcadia.tribina.plugin.mapregion.posset.PosSet;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class BaseDisjointRegion extends BaseRegion implements DisjointRegion {

    protected final List<? extends DisjointRegion> peers;

    public BaseDisjointRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            long ttl,
            @NotNull List<? extends DisjointRegion> peers
    ) {
        super(regionId, fileRoot, ttl);
        this.peers = peers;
    }

    public BaseDisjointRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            @NotNull List<? extends DisjointRegion> peers
    ) {
        super(regionId, fileRoot);
        this.peers = peers;
    }

    public BaseDisjointRegion(
            @NotNull String regionId,
            @NotNull File fileConfig,
            @NotNull File fileBitmap,
            long ttl,
            @NotNull List<? extends DisjointRegion> peers
    ) {
        super(regionId, fileConfig, fileBitmap, ttl);
        this.peers = peers;
    }

    public BaseDisjointRegion(
            @NotNull String regionId,
            @NotNull File fileConfig,
            @NotNull File fileBitmap,
            @NotNull List<? extends DisjointRegion> peers
    ) {
        super(regionId, fileConfig, fileBitmap);
        this.peers = peers;
    }

    @Override
    public List<? extends DisjointRegion> getPeers() {
        return this.peers;
    }

    @Override
    public void add(long x, long z) {
        if (!this.isNull) {
            boolean flag = true;
            for (var peer : this.peers) flag &= !peer.contains(x, z);
            if (flag) {
                this.map.add(x, z);
                this.saveMap();
            }
        }
    }

    @Override
    public void add(@NotNull Pair<Long, Long> pos) {
        if (!this.isNull) {
            boolean flag = true;
            for (var peer : this.peers) flag &= !peer.contains(pos);
            if (flag) {
                this.map.add(pos);
                this.saveMap();
            }
        }
    }

    @Override
    public void addAll(@NotNull Collection<? extends Pair<Long, Long>> pSet) {
        if (!this.isNull) {
            var set = new HashSet<Pair<Long, Long>>(pSet);
            for (var peer : this.peers) set.removeAll(peer.getSet());
            this.map.addAll(set);
            this.saveMap();
        }
    }

    @Override
    public void addAll(@NotNull PosSet pSet) {
        if (!this.isNull) {
            var set = new HashSet<>(pSet.getSet());
            for (var peer : this.peers) set.removeAll(peer.getSet());
            this.map.addAll(set);
            this.saveMap();
        }
    }

    @Override
    public void addOver(long x, long z) {
        if (!this.isNull) {
            for (var peer : this.peers) peer.sub(x, z);
            this.map.add(x, z);
            this.saveMap();
        }
    }

    @Override
    public void addOver(@NotNull Pair<Long, Long> pos) {
        if (!this.isNull) {
            for (var peer : this.peers) peer.sub(pos);
            this.map.add(pos);
            this.saveMap();
        }
    }

    @Override
    public void addAllOver(@NotNull Collection<? extends Pair<Long, Long>> pSet) {
        if (!this.isNull) {
            for (var peer : this.peers) peer.subAll(pSet);
            this.map.addAll(pSet);
            this.saveMap();
        }
    }

    @Override
    public void addAllOver(@NotNull PosSet pSet) {
        if (!this.isNull) {
            for (var peer : this.peers) peer.subAll(pSet);
            this.map.addAll(pSet);
            this.saveMap();
        }
    }

}
