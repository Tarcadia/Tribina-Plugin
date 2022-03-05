package net.tarcadia.tribina.plugin.mapregion.region.base;

import net.tarcadia.tribina.plugin.mapregion.posset.BasePosSet;
import net.tarcadia.tribina.plugin.mapregion.posset.PosSet;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface DisjointParentRegion extends DisjointRegion, ParentRegion {

    @Override
    void add(long x, long z);
    @Override
    void add(@NotNull Pair<Long, Long> pos);
    @Override
    void addAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void addAll(@NotNull PosSet pSet);

    @Override
    void sub(long x, long z);
    @Override
    void sub(@NotNull Pair<Long, Long> pos);
    @Override
    void subAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void subAll(@NotNull PosSet pSet);
    @Override
    void cross(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void cross(@NotNull PosSet pSet);

    @Override
    void addForce(long x, long z);
    @Override
    void addForce(@NotNull Pair<Long, Long> pos);
    @Override
    void addAllForce(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void addAllForce(@NotNull PosSet pSet);
    @Override
    void subForce(long x, long z);
    @Override
    void subForce(@NotNull Pair<Long, Long> pos);
    @Override
    void subAllForce(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void subAllForce(@NotNull PosSet pSet);

    @Override
    void subShrink(long x, long z);
    @Override
    void subShrink(@NotNull Pair<Long, Long> pos);
    @Override
    void subAllShrink(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void subAllShrink(@NotNull BasePosSet pSet);
    @Override
    void crossShrink(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void crossShrink(@NotNull BasePosSet pSet);

    void subShrinkForce(long x, long z);
    void subShrinkForce(@NotNull Pair<Long, Long> pos);
    void subAllShrinkForce(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void subAllShrinkForce(@NotNull BasePosSet pSet);

}
