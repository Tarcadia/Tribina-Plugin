package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.posset.BasePosSet;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface DisjointRegion {
    void add(long x, long z);
    void add(@NotNull Pair<Long, Long> pos);
    void addAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void addAll(@NotNull BasePosSet pSet);
    void addOver(long x, long z);
    void addOver(@NotNull Pair<Long, Long> pos);
    void addAllOver(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void addAllOver(@NotNull BasePosSet pSet);
}
