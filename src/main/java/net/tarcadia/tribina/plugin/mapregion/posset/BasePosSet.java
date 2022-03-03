package net.tarcadia.tribina.plugin.mapregion.posset;

import net.tarcadia.tribina.plugin.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public abstract class BasePosSet {

    protected long biasX;
    protected long biasZ;

    public abstract boolean contains(long x, long z);
    public abstract boolean contains(@NotNull Pair<Long, Long> pos);
    public abstract boolean containsAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);

    public abstract void add(long x, long z);
    public abstract void add(@NotNull Pair<Long, Long> pos);
    public abstract void addAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    public abstract void addAll(@NotNull BasePosSet pSet);

    public abstract void sub(long x, long z);
    public abstract void sub(@NotNull Pair<Long, Long> pos);
    public abstract void subAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    public abstract void subAll(@NotNull BasePosSet pSet);

    public abstract void cross(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    public abstract void cross(@NotNull BasePosSet pSet);

    public abstract boolean isEmpty();
    public abstract List<Pair<Long, Long>> getList();

}
