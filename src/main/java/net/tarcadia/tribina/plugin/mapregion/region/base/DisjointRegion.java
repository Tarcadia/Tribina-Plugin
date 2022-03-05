package net.tarcadia.tribina.plugin.mapregion.region.base;

import net.tarcadia.tribina.plugin.mapregion.posset.PosSet;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface DisjointRegion extends Region {

    List<? extends DisjointRegion> getPeers();

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

    void addForce(long x, long z);
    void addForce(@NotNull Pair<Long, Long> pos);
    void addAllForce(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void addAllForce(@NotNull PosSet pSet);
    void subForce(long x, long z);
    void subForce(@NotNull Pair<Long, Long> pos);
    void subAllForce(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void subAllForce(@NotNull PosSet pSet);

}
