package net.tarcadia.tribina.plugin.mapregion.region.base;

import net.tarcadia.tribina.plugin.mapregion.posset.PosSet;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface SubRegion extends Region {

    ParentRegion parent();

    @Override
    void add(long x, long z);
    @Override
    void add(@NotNull Pair<Long, Long> pos);
    @Override
    void addAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void addAll(@NotNull PosSet pSet);

    void addExtend(long x, long z);
    void addExtend(@NotNull Pair<Long, Long> pos);
    void addAllExtend(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void addAllExtend(@NotNull PosSet pSet);
}
