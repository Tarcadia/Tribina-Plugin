package net.tarcadia.tribina.plugin.mapregion.region.base;

import net.tarcadia.tribina.plugin.mapregion.posset.GlobalPosSet;
import net.tarcadia.tribina.plugin.mapregion.posset.PosSet;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

public interface Region extends PosSet {

    @Nullable
    String id();
    @Nullable
    File fileConfig();
    @Nullable
    File fileBitmap();
    @Nullable
    Configuration config();
    @Nullable
    GlobalPosSet map();
    boolean isNull();

    @Nullable
    Location loc();
    long biasX();
    long biasZ();

    void saveMap();
    boolean reLoc(@NotNull Location loc);
    boolean reBias(long x, long z);

    @NotNull
    String getName();
    @NotNull
    String getName(@NotNull Player player);
    @NotNull
    String getLore();
    @NotNull
    String getLore(@NotNull Player player);
    @NotNull
    List<String> getAuthTags();
    @NotNull
    List<String> getAuthTags(@NotNull Player player);

    void setName(@NotNull String name);
    void setLore(@NotNull String lore);
    void addAuthTag(@NotNull String auth);
    void removeAuthTag(@NotNull String auth);

    boolean inRegion(@NotNull Location loc);
    boolean inRegion(@NotNull Player player);

    @Override
    boolean contains(long x, long z);
    @Override
    boolean contains(@NotNull Pair<Long, Long> pos);
    @Override
    boolean containsAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void add(long x, long z);
    @Override
    void add(@NotNull Pair<Long, Long> pos);
    @Override
    void addAll(@NotNull Collection<? extends Pair<Long, Long>> pSet);
    @Override
    void addAll(@NotNull PosSet pSet);
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

    @Override
    @Nullable
    Long minX();
    @Override
    @Nullable
    Long minZ();

    @Override
    boolean isEmpty();

    @Override
    @NotNull
    List<Pair<Long, Long>> getList();
    @Override
    @NotNull
    Set<Pair<Long, Long>> getSet();

}
