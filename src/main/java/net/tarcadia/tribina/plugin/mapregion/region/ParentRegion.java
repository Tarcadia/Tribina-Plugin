package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.posset.BasePosSet;
import net.tarcadia.tribina.plugin.mapregion.posset.PosSet;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface ParentRegion extends Region {

    List<? extends SubRegion> getChildren();

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

    void subShrink(long x, long z);
    void subShrink(@NotNull Pair<Long, Long> pos);
    void subAllShrink(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void subAllShrink(@NotNull BasePosSet pSet);
    void crossShrink(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void crossShrink(@NotNull BasePosSet pSet);
}
