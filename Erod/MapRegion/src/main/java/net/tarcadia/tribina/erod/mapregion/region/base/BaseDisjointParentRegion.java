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

public class BaseDisjointParentRegion extends BaseRegion implements DisjointRegion, ParentRegion {

    private final List<? extends DisjointRegion> peers;
    private final List<? extends SubRegion> children;

    public BaseDisjointParentRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            @NotNull List<? extends DisjointRegion> peers,
            @NotNull List<? extends SubRegion> children
    ) {
        super(regionId, fileRoot);
        this.peers = peers;
        this.children = children;
    }

    public BaseDisjointParentRegion(
            @NotNull String regionId,
            @NotNull File fileConfig,
            @NotNull File fileBitmap,
            @NotNull List<? extends DisjointRegion> peers,
            @NotNull List<? extends SubRegion> children
    ) {
        super(regionId, fileConfig, fileBitmap);
        this.peers = peers;
        this.children = children;
    }

    @Override
    @NotNull
    public List<? extends DisjointRegion> peers() {
        return this.peers;
    }

    @Override
    @NotNull
    public List<? extends SubRegion> children() {
        return this.children;
    }

    @Override
    public void add(long x, long z) {
        if (!this.isNull()) {
            boolean flag = true;
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
            boolean flag = true;
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
            var set = new HashSet<Pos>(pSet);
            for (var peer : this.peers) set.removeAll(peer.getSet());
            Objects.requireNonNull(this.map()).addAll(set);
            this.saveMap();
        }
    }

    @Override
    public void addAll(@NotNull PosSet pSet) {
        if (!this.isNull()) {
            var set = new HashSet<>(pSet.getSet());
            for (var peer : this.peers) set.removeAll(peer.getSet());
            Objects.requireNonNull(this.map()).addAll(set);
            this.saveMap();
        }
    }

    @Override
    public void sub(long x, long z) {
        if (!this.isNull()) {
            boolean flag = true;
            for (var child : this.children) flag &= !child.contains(x, z);
            if (flag) {
                Objects.requireNonNull(this.map()).sub(x, z);
                this.saveMap();
            }
        }
    }

    @Override
    public void sub(@NotNull Pos pos) {
        if (!this.isNull()) {
            boolean flag = true;
            for (var child : this.children) flag &= !child.contains(pos);
            if (flag) {
                Objects.requireNonNull(this.map()).sub(pos);
                this.saveMap();
            }
        }
    }

    @Override
    public void subAll(@NotNull Collection<? extends Pos> pSet) {
        if (!this.isNull()) {
            var set = new HashSet<Pos>(pSet);
            for (var child : this.children) set.removeAll(child.getSet());
            Objects.requireNonNull(this.map()).subAll(set);
            this.saveMap();
        }
    }

    @Override
    public void subAll(@NotNull PosSet pSet) {
        if (!this.isNull()) {
            var set = new HashSet<>(pSet.getSet());
            for (var child : this.children) set.removeAll(child.getSet());
            Objects.requireNonNull(this.map()).subAll(set);
            this.saveMap();
        }
    }

    @Override
    public void cross(@NotNull Collection<? extends Pos> pSet) {
        if (!this.isNull()) {
            var set = new HashSet<Pos>(pSet);
            for (var child : this.children) set.addAll(child.getSet());
            Objects.requireNonNull(this.map()).subAll(set);
            this.saveMap();
        }
    }

    @Override
    public void cross(@NotNull PosSet pSet) {
        if (!this.isNull()) {
            var set = new HashSet<>(pSet.getSet());
            for (var child : this.children) set.addAll(child.getSet());
            Objects.requireNonNull(this.map()).subAll(set);
            this.saveMap();
        }
    }

    @Override
    public void addForce(long x, long z) {
        if (!this.isNull()) {
            for (var peer : this.peers) peer.subForce(x, z);
            this.add(x, z);
        }
    }

    @Override
    public void addForce(@NotNull Pos pos) {
        if (!this.isNull()) {
            for (var peer : this.peers) peer.subForce(pos);
            this.add(pos);
        }
    }

    @Override
    public void addAllForce(@NotNull Collection<? extends Pos> pSet) {
        if (!this.isNull()) {
            for (var peer : this.peers) peer.subAllForce(pSet);
            this.addAll(pSet);
        }
    }

    @Override
    public void addAllForce(@NotNull PosSet pSet) {
        if (!this.isNull()) {
            for (var peer : this.peers) peer.subAllForce(pSet);
            this.addAll(pSet);
        }
    }

    @Override
    public void subForce(long x, long z) {
        if (!this.isNull()) {
            for (var child : this.children) child.subForce(x, z);
            this.sub(x, z);
        }
    }

    @Override
    public void subForce(@NotNull Pos pos) {
        if (!this.isNull()) {
            for (var child : this.children) child.subForce(pos);
            this.sub(pos);
        }
    }

    @Override
    public void subAllForce(@NotNull Collection<? extends Pos> pSet) {
        if (!this.isNull()) {
            for (var child : this.children) child.subAllForce(pSet);
            this.subAll(pSet);
        }
    }

    @Override
    public void subAllForce(@NotNull PosSet pSet) {
        if (!this.isNull()) {
            for (var child : this.children) child.subAllForce(pSet);
            this.subAll(pSet);
        }
    }

    @Override
    public void crossForce(@NotNull Collection<? extends Pos> pSet) {
        if (!this.isNull()) {
            for (var child : this.children) child.crossForce(pSet);
            this.cross(pSet);
        }
    }

    @Override
    public void crossForce(@NotNull PosSet pSet) {
        if (!this.isNull()) {
            for (var child : this.children) child.crossForce(pSet);
            this.cross(pSet);
        }
    }

}
