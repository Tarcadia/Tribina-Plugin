package net.tarcadia.tribina.plugin.mapregion.posset;

import net.tarcadia.tribina.plugin.util.type.Pair;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class InChunkPosSet extends BasePosSet {

    static int CHUNK_SIZE_BITLEN = 4;
    static int CHUNK_SIZE = 1 << CHUNK_SIZE_BITLEN;
    static long CHUNK_SIZE_MASK = -1 << CHUNK_SIZE_BITLEN;

    private final long biasX;
    private final long biasZ;
    private final boolean[][] setMap;

    public InChunkPosSet(long biasX, long biasZ) {
        this.biasX = biasX & CHUNK_SIZE_MASK;
        this.biasZ = biasZ & CHUNK_SIZE_MASK;
        this.setMap = new boolean[CHUNK_SIZE][CHUNK_SIZE];
        for (int i = 0; i < CHUNK_SIZE; i++)
            for (int j = 0; j < CHUNK_SIZE; j++) {
                this.setMap[i][j] = false;
            }
    }

    @Override
    public boolean contains(long x, long z) {
        long _x = x - this.biasX;
        long _z = z - this.biasZ;
        if ((_x >= 0) && (_z >= 0) && (_x < CHUNK_SIZE) && (_z < CHUNK_SIZE)) {
            return this.setMap[(int) _x][(int) _z];
        } else {
            return false;
        }
    }

    private void set(long x, long z, boolean val) {
        long _x = x - this.biasX;
        long _z = z - this.biasZ;
        if ((_x >= 0) && (_z >= 0) && (_x < CHUNK_SIZE) && (_z < CHUNK_SIZE)) {
            this.setMap[(int) _x][(int) _z] = val;
        }
    }

    @Override
    public boolean contains(@NotNull Pair<Long, Long> pos) {
        return this.contains(pos.x(), pos.y());
    }

    @Override
    public boolean containsAll(@NotNull Collection<? extends Pair<Long, Long>> pSet) {
        boolean flag = true;
        for (var pos : pSet) {
            flag &= this.contains(pos);
        }
        return flag;
    }

    @Override
    public void add(long x, long z) {
        this.set(x, z, true);
    }

    @Override
    public void add(@NotNull Pair<Long, Long> pos) {
        this.add(pos.x(), pos.y());
    }

    @Override
    public void addAll(@NotNull Collection<? extends Pair<Long, Long>> pSet) {
        for (var pos : pSet) {
            this.add(pos);
        }
    }

    @Override
    public void addAll(@NotNull PosSet pSet) {
        this.addAll(pSet.getList());
    }

    @Override
    public void sub(long x, long z) {
        this.set(x, z, false);
    }

    @Override
    public void sub(@NotNull Pair<Long, Long> pos) {
        this.sub(pos.x(), pos.y());
    }

    @Override
    public void subAll(@NotNull Collection<? extends Pair<Long, Long>> pSet) {
        for (var pos : pSet) {
            this.sub(pos);
        }
    }

    @Override
    public void subAll(@NotNull PosSet pSet) {
        this.subAll(pSet.getList());
    }

    @Override
    public void cross(@NotNull Collection<? extends Pair<Long, Long>> pSet) {
        for (int i = 0; i < CHUNK_SIZE; i++)
            for (int j = 0; j < CHUNK_SIZE; j++) {
                var pos = new Pair<>(this.biasX + i, this.biasZ + j);
                if (!pSet.contains(pos)) {
                    this.setMap[i][j] = false;
                }
            }
    }

    @Override
    public void cross(@NotNull PosSet pSet) {
        this.cross(pSet.getSet());
    }

    @Override
    @Nullable
    public Long minX() {
        for (int i = 0; i < CHUNK_SIZE; i++)
            for (int j = 0; j < CHUNK_SIZE; j++) {
                if (this.setMap[i][j]) {
                    return this.biasX + i;
                }
            }
        return null;
    }

    @Override
    @Nullable
    public Long minZ() {
        for (int i = 0; i < CHUNK_SIZE; i++)
            for (int j = 0; j < CHUNK_SIZE; j++) {
                if (this.setMap[j][i]) {
                    return this.biasZ + i;
                }
            }
        return null;
    }

    @Override
    public boolean isEmpty() {
        boolean flag = false;
        for (int i = 0; i < CHUNK_SIZE; i++)
            for (int j = 0; j < CHUNK_SIZE; j++) {
                flag |= this.setMap[i][j];
            }
        return !flag;
    }

    @Override
    @NotNull
    public List<Pair<Long, Long>> getList() {
        List<Pair<Long, Long>> ret = new LinkedList<>();
        for (int i = 0; i < CHUNK_SIZE; i++)
            for (int j = 0; j < CHUNK_SIZE; j++) {
                var pos = new Pair<>(this.biasX + i, this.biasZ + j);
                ret.add(pos);
            }
        return ret;
    }

    @Override
    @NotNull
    public Set<Pair<Long, Long>> getSet() {
        Set<Pair<Long, Long>> ret = new HashSet<>();
        for (int i = 0; i < CHUNK_SIZE; i++)
            for (int j = 0; j < CHUNK_SIZE; j++) {
                var pos = new Pair<>(this.biasX + i, this.biasZ + j);
                ret.add(pos);
            }
        return ret;
    }
}
