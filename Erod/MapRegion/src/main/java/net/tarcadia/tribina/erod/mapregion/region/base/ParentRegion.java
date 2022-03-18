package net.tarcadia.tribina.erod.mapregion.region.base;

import net.tarcadia.tribina.erod.mapregion.util.type.Pos;
import net.tarcadia.tribina.erod.mapregion.util.type.posset.PosSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface ParentRegion extends Region {

    @NotNull
    List<? extends SubRegion> children();

    @Override
    void sub(long x, long z);
    @Override
    void sub(@NotNull Pos pos);
    @Override
    void subAll(@NotNull Collection<? extends Pos> pSet);
    @Override
    void subAll(@NotNull PosSet pSet);
    @Override
    void cross(@NotNull Collection<? extends Pos> pSet);
    @Override
    void cross(@NotNull PosSet pSet);

    void addForce(long x, long z);
    void addForce(@NotNull Pos pos);
    void addAllForce(@NotNull Collection<? extends Pos> pSet);
    void addAllForce(@NotNull PosSet pSet);
    void subForce(long x, long z);
    void subForce(@NotNull Pos pos);
    void subAllForce(@NotNull Collection<? extends Pos> pSet);
    void subAllForce(@NotNull PosSet pSet);
    void crossForce(@NotNull Collection<? extends Pos> pSet);
    void crossForce(@NotNull PosSet pSet);
}
