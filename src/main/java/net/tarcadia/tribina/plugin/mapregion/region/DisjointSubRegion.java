package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.posset.BasePosSet;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface DisjointSubRegion extends DisjointRegion, SubRegion{

    @Override
    ParentRegion getParent();
    @Override
    List<? extends DisjointRegion> getPeers();

    @Override
    void add(long x, long z);
    @Override
    void add(@NotNull Pair<Long, Long> pos);
    @Override
    void addAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void addAll(@NotNull BasePosSet pSet);

    @Override
    void addOver(long x, long z);
    @Override
    void addOver(@NotNull Pair<Long, Long> pos);
    @Override
    void addAllOver(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void addAllOver(@NotNull BasePosSet pSet);

    @Override
    void addExtend(long x, long z);
    @Override
    void addExtend(@NotNull Pair<Long, Long> pos);
    @Override
    void addAllExtend(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void addAllExtend(@NotNull BasePosSet pSet);

    void addExtendOver(long x, long z);
    void addExtendOver(@NotNull Pair<Long, Long> pos);
    void addAllExtendOver(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void addAllExtendOver(@NotNull BasePosSet pSet);

}
