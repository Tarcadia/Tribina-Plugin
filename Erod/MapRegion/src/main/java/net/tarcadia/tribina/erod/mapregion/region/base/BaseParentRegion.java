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

public class BaseParentRegion extends BaseRegion implements ParentRegion {

    private final List<? extends SubRegion> children;

    public BaseParentRegion(
            @NotNull String regionId,
            @NotNull Path fileRoot,
            @NotNull List<? extends SubRegion> children
    ) {
        super(regionId, fileRoot);
        this.children = children;
    }

    public BaseParentRegion(
            @NotNull String regionId,
            @NotNull File fileConfig,
            @NotNull File fileBitmap,
            @NotNull List<? extends SubRegion> children
    ) {
        super(regionId, fileConfig, fileBitmap);
        this.children = children;
    }

    @Override
    @NotNull
    public List<? extends SubRegion> children() {
        return this.children;
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
            this.add(x, z);
        }
    }

    @Override
    public void addForce(@NotNull Pos pos) {
        if (!this.isNull()) {
            this.add(pos);
        }
    }

    @Override
    public void addAllForce(@NotNull Collection<? extends Pos> pSet) {
        if (!this.isNull()) {
            this.addAll(pSet);
        }
    }

    @Override
    public void addAllForce(@NotNull PosSet pSet) {
        if (!this.isNull()) {
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
