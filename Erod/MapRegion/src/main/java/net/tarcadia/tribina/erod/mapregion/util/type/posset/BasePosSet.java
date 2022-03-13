package net.tarcadia.tribina.erod.mapregion.util.type.posset;

import net.tarcadia.tribina.erod.mapregion.util.type.Pos;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class BasePosSet implements PosSet {

    @Override
    public abstract boolean contains(long x, long z);
    @Override
    public abstract boolean contains(@NotNull Pos pos);
    @Override
    public abstract boolean containsAll(@NotNull Collection<? extends Pos> pSet);

    @Override
    public abstract void add(long x, long z);
    @Override
    public abstract void add(@NotNull Pos pos);
    @Override
    public abstract void addAll(@NotNull Collection<? extends Pos> pSet);
    @Override
    public abstract void addAll(@NotNull PosSet pSet);

    @Override
    public abstract void sub(long x, long z);
    @Override
    public abstract void sub(@NotNull Pos pos);
    @Override
    public abstract void subAll(@NotNull Collection<? extends Pos> pSet);
    @Override
    public abstract void subAll(@NotNull PosSet pSet);

    @Override
    public abstract void cross(@NotNull Collection<? extends Pos> pSet);
    @Override
    public abstract void cross(@NotNull PosSet pSet);

    @Override
    public abstract Long minX();
    @Override
    public abstract Long minZ();

    @Override
    public abstract boolean isEmpty();
    @Override
    public abstract List<Pos> getList();
    @Override
    public abstract Set<Pos> getSet();

}
