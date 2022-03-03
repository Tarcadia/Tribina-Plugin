package net.tarcadia.tribina.plugin.mapregion.posset;

import net.tarcadia.tribina.plugin.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public abstract class BasePosSet {

    public abstract boolean contains(int x, int z);
    public abstract boolean contains(@NotNull Pair<Integer, Integer> pos);
    public abstract boolean containsAll(@NotNull Collection<? extends Pair<Integer, Integer>> poss);

    public abstract void add(int x, int z);
    public abstract void add(@NotNull Pair<Integer, Integer> pos);
    public abstract void addAll(@NotNull Collection<? extends Pair<Integer, Integer>> pSet);
    public abstract void addAll(@NotNull BasePosSet pSet);

    public abstract void sub(int x, int z);
    public abstract void sub(@NotNull Pair<Integer, Integer> pos);
    public abstract void subAll(@NotNull Collection<? extends Pair<Integer, Integer>> pSet);
    public abstract void subAll(@NotNull BasePosSet pSet);

    public abstract void cross(@NotNull Collection<? extends Pair<Integer, Integer>> pSet);
    public abstract void cross(@NotNull BasePosSet pSet);

    public abstract List<Pair<Integer, Integer>> getList();

}
