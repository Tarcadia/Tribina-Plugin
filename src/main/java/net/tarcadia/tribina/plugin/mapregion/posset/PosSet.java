package net.tarcadia.tribina.plugin.mapregion.posset;

import net.tarcadia.tribina.plugin.util.type.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PosSet {

    boolean contains(long x, long z);
    boolean contains(@NotNull Pair<Long, Long> pos);
    boolean containsAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);

    void add(long x, long z);
    void add(@NotNull Pair<Long, Long> pos);
    void addAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void addAll(@NotNull BasePosSet pSet);

    void sub(long x, long z);
    void sub(@NotNull Pair<Long, Long> pos);
    void subAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void subAll(@NotNull BasePosSet pSet);

    void cross(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    void cross(@NotNull BasePosSet pSet);

    Long minX();
    Long minZ();

    boolean isEmpty();
    List<Pair<Long, Long>> getList();
    Set<Pair<Long, Long>> getSet();

}
