package net.tarcadia.tribina.plugin.mapregion.region;

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

    void addOver(long x, long z);
    void addOver(@NotNull Pair<Long, Long> pos);
    void addAllOver(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void addAllOver(@NotNull PosSet pSet);
}
