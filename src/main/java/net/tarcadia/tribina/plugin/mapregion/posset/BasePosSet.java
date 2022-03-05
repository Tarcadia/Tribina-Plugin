package net.tarcadia.tribina.plugin.mapregion.posset;

import net.tarcadia.tribina.plugin.util.type.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class BasePosSet implements PosSet{

    @Override
    public abstract boolean contains(long x, long z);
    @Override
    public abstract boolean contains(@NotNull Pair<Long, Long> pos);
    @Override
    public abstract boolean containsAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);

    @Override
    public abstract void add(long x, long z);
    @Override
    public abstract void add(@NotNull Pair<Long, Long> pos);
    @Override
    public abstract void addAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    public abstract void addAll(@NotNull BasePosSet pSet);

    @Override
    public abstract void sub(long x, long z);
    @Override
    public abstract void sub(@NotNull Pair<Long, Long> pos);
    @Override
    public abstract void subAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    public abstract void subAll(@NotNull BasePosSet pSet);

    @Override
    public abstract void cross(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    public abstract void cross(@NotNull BasePosSet pSet);

    @Override
    public abstract Long minX();
    @Override
    public abstract Long minZ();

    @Override
    public abstract boolean isEmpty();
    @Override
    public abstract List<Pair<Long, Long>> getList();
    @Override
    public abstract Set<Pair<Long, Long>> getSet();

}
