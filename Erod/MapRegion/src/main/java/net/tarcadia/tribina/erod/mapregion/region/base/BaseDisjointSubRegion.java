package net.tarcadia.tribina.erod.mapregion.region.base;

import net.tarcadia.tribina.erod.mapregion.util.type.Pos;
import net.tarcadia.tribina.erod.mapregion.util.type.posset.PosSet;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class BaseDisjointSubRegion extends BaseRegion implements DisjointRegion, SubRegion {

    private final List<? extends DisjointRegion> peers;
    private final ParentRegion parent;

    public BaseDisjointSubRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            @NotNull List<? extends DisjointRegion> peers,
            @NotNull ParentRegion parent
    ) {
        super(regionId, fileRoot);
        this.peers = peers;
        this.parent = parent;
    }

    public BaseDisjointSubRegion(
            @NotNull String regionId,
            @NotNull File fileConfig,
            @NotNull File fileBitmap,
            @NotNull List<? extends DisjointRegion> peers,
            @NotNull ParentRegion parent
    ) {
        super(regionId, fileConfig, fileBitmap);
        this.peers = peers;
        this.parent = parent;
    }

    @Override
    @NotNull
    public List<? extends DisjointRegion> peers() {
        return this.peers;
    }

    @Override
    @NotNull
    public ParentRegion parent() {
        return this.parent;
    }

    @Override
    public void add(long x, long z) {
        if (!this.isNull()) {
            boolean flag = this.parent.contains(x, z);
            for (var peer : this.peers) flag &= !peer.contains(x, z);
            if (flag) {
                Objects.requireNonNull(this.map()).add(x, z);
                this.saveMap();
            }
        }
    }

    @Override
    public void add(@NotNull Pos pos) {
        if (!this.isNull()) {
            boolean flag = this.parent.contains(pos);
            for (var peer : this.peers) flag &= !peer.contains(pos);
            if (flag) {
                Objects.requireNonNull(this.map()).add(pos);
                this.saveMap();
            }
        }
    }

    @Override
    public void addAll(@NotNull Collection<? extends Pos> pSet) {
        if (!this.isNull()) {
            var set = new HashSet<Pos>();
            for (var pos : pSet) if (this.parent.contains(pos)) set.add(pos);
            for (var peer : this.peers) set.removeAll(peer.getSet());
            Objects.requireNonNull(this.map()).addAll(set);
            this.saveMap();
        }
    }

    @Override
    public void addAll(@NotNull PosSet pSet) {
        if (!this.isNull()) {
            var set = new HashSet<Pos>();
            for (var pos : pSet.getSet()) if (this.parent.contains(pos)) set.add(pos);
            for (var peer : this.peers) set.removeAll(peer.getSet());
            Objects.requireNonNull(this.map()).addAll(set);
            this.saveMap();
        }
    }

    @Override
    public void addForce(long x, long z) {
        if (!this.isNull()) {
            this.parent.addForce(x, z);
            for (var peer : this.peers) peer.subForce(x, z);
            this.add(x, z);
        }
    }

    @Override
    public void addForce(@NotNull Pos pos) {
        if (!this.isNull()) {
            this.parent.addForce(pos);
            for (var peer : this.peers) peer.subForce(pos);
            this.add(pos);
        }
    }

    @Override
    public void addAllForce(@NotNull Collection<? extends Pos> pSet) {
        if (!this.isNull()) {
            this.parent.addAllForce(pSet);
            for (var peer : this.peers) peer.subAllForce(pSet);
            this.addAll(pSet);
        }
    }

    @Override
    public void addAllForce(@NotNull PosSet pSet) {
        if (!this.isNull()) {
            this.parent.addAllForce(pSet);
            for (var peer : this.peers) peer.subAllForce(pSet);
            this.addAll(pSet);
        }
    }

    @Override
    public void subForce(long x, long z) {
        if (!this.isNull()) {
            this.sub(x, z);
        }
    }

    @Override
    public void subForce(@NotNull Pos pos) {
        if (!this.isNull()) {
            this.sub(pos);
        }
    }

    @Override
    public void subAllForce(@NotNull Collection<? extends Pos> pSet) {
        if (!this.isNull()) {
            this.subAll(pSet);
        }
    }

    @Override
    public void subAllForce(@NotNull PosSet pSet) {
        if (!this.isNull()) {
            this.subAll(pSet);
        }
    }

    @Override
    public void crossForce(@NotNull Collection<? extends Pos> pSet) {
        if (!this.isNull()) {
            this.cross(pSet);
        }
    }

    @Override
    public void crossForce(@NotNull PosSet pSet) {
        if (!this.isNull()) {
            this.cross(pSet);
        }
    }

}
