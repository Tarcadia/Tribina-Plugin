package net.tarcadia.tribina.erod.mapregion.util.type.posset;

import net.tarcadia.tribina.erod.mapregion.util.type.Pos;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PosSet {

    boolean contains(long x, long z);
    boolean contains(@NotNull Pos pos);
    boolean containsAll(@NotNull Collection<? extends Pos> pSet);

    void add(long x, long z);
    void add(@NotNull Pos pos);
    void addAll(@NotNull Collection<? extends Pos> pSet);
    void addAll(@NotNull PosSet pSet);

    void sub(long x, long z);
    void sub(@NotNull Pos pos);
    void subAll(@NotNull Collection<? extends Pos> pSet);
    void subAll(@NotNull PosSet pSet);

    void cross(@NotNull Collection<? extends Pos> pSet);
    void cross(@NotNull PosSet pSet);

    Long minX();
    Long minZ();

    boolean isEmpty();
    List<Pos> getList();
    Set<Pos> getSet();

}
