package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.posset.BasePosSet;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class DisjointRegion extends BaseRegion {

    protected final String id;
    protected final List<? extends BaseRegion> peers;

    public DisjointRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            @NotNull List<? extends BaseRegion> peers
    ) {
        super(regionId, fileRoot);
        this.id = regionId;
        this.peers = peers;
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
    public void addAll(@NotNull BasePosSet pSet) {
        if (!this.isNull) {
            var set = new HashSet<>(pSet.getSet());
            for (var peer : this.peers) set.removeAll(peer.getSet());
            this.map.addAll(set);
            this.saveMap();
        }
    }

    public void addOver(long x, long z) {
        if (!this.isNull) {
            for (var peer : this.peers) peer.sub(x, z);
            this.map.add(x, z);
            this.saveMap();
        }
    }

    public void addOver(@NotNull Pair<Long, Long> pos) {
        if (!this.isNull) {
            for (var peer : this.peers) peer.sub(pos);
            this.map.add(pos);
            this.saveMap();
        }
    }

    public void addAllOver(@NotNull Collection<? extends Pair<Long, Long>> pSet) {
        if (!this.isNull) {
            for (var peer : this.peers) peer.subAll(pSet);
            this.map.addAll(pSet);
            this.saveMap();
        }
    }

    public void addAllOver(@NotNull BasePosSet pSet) {
        if (!this.isNull) {
            for (var peer : this.peers) peer.subAll(pSet);
            this.map.addAll(pSet);
            this.saveMap();
        }
    }

}
