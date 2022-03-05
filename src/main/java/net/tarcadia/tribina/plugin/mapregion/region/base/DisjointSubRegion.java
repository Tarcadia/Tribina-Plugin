package net.tarcadia.tribina.plugin.mapregion.region.base;

import net.tarcadia.tribina.plugin.mapregion.posset.PosSet;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface DisjointSubRegion extends DisjointRegion, SubRegion {

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
    void addExtend(long x, long z);
    @Override
    void addExtend(@NotNull Pair<Long, Long> pos);
    @Override
    void addAllExtend(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void addAllExtend(@NotNull PosSet pSet);

    void addExtendForce(long x, long z);
    void addExtendForce(@NotNull Pair<Long, Long> pos);
    void addAllExtendForce(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void addAllExtendForce(@NotNull PosSet pSet);

}
