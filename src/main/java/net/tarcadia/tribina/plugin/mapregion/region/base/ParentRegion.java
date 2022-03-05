package net.tarcadia.tribina.plugin.mapregion.region.base;

import net.tarcadia.tribina.plugin.mapregion.posset.BasePosSet;
import net.tarcadia.tribina.plugin.mapregion.posset.PosSet;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface ParentRegion extends Region {

    List<? extends SubRegion> children();

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

    void addForce(long x, long z);
    void addForce(@NotNull Pair<Long, Long> pos);
    void addAllForce(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void addAllForce(@NotNull PosSet pSet);
    void subForce(long x, long z);
    void subForce(@NotNull Pair<Long, Long> pos);
    void subAllForce(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void subAllForce(@NotNull PosSet pSet);
    void crossForce(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void crossForce(@NotNull PosSet pSet);
}
