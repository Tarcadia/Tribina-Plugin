package net.tarcadia.tribina.plugin.mapregion.region;

import net.tarcadia.tribina.plugin.mapregion.posset.BasePosSet;
import net.tarcadia.tribina.plugin.mapregion.posset.PosSet;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface Region extends PosSet {

    void saveMap();
    boolean reLoc(@NotNull Location loc);
    boolean reBias(long x, long z);
    String getName();
    String getLore();
    List<String> getAuth();
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

    @Nullable
    Long minX();
    @Nullable
    Long minZ();

    boolean isEmpty();

    @NotNull
    List<Pair<Long, Long>> getList();
    @NotNull
    Set<Pair<Long, Long>> getSet();

}
