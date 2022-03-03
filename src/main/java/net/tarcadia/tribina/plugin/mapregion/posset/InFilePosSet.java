package net.tarcadia.tribina.plugin.mapregion.posset;

import net.tarcadia.tribina.plugin.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class InFilePosSet extends BasePosSet{

    static int CHUNK_SIZE_BITLEN = InChunkPosSet.CHUNK_SIZE_BITLEN;
    static int CHUNK_SIZE = InChunkPosSet.CHUNK_SIZE;
    static int FILE_SIZE_BITLEN = 5;
    static int FILE_SIZE = 1 << FILE_SIZE_BITLEN;
    static long FILE_SIZE_MASK = - (1L << FILE_SIZE_BITLEN << CHUNK_SIZE_BITLEN - 1);

    private final InChunkPosSet[][] setMap;

    public InFilePosSet(long biasX, long biasZ) {
        this.biasX = biasX & FILE_SIZE_MASK;
        this.biasZ = biasZ & FILE_SIZE_MASK;
        this.setMap = new InChunkPosSet[FILE_SIZE][FILE_SIZE];
        for (int i = 0; i < FILE_SIZE; i++)
            for (int j = 0; j < FILE_SIZE; j++) {
                this.setMap[i][j] = null;
            }
    }

    @Override
    public boolean contains(long x, long z) {
        long _x = x - this.biasX;
        long _z = z - this.biasZ;
        if ((_x >= 0) && (_z >= 0) && (_x < (long) FILE_SIZE * CHUNK_SIZE) && (_z < (long) FILE_SIZE * CHUNK_SIZE)) {
            var sub = this.setMap[(int) _x][(int) _z];
            if (sub != null) {
                return sub.contains(x, z);
            } else {
                return false;
            }
        } else {
            return false;
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
        long _x = x - this.biasX;
        long _z = z - this.biasZ;
        if ((_x >= 0) && (_z >= 0) && (_x < (long) FILE_SIZE * CHUNK_SIZE) && (_z < (long) FILE_SIZE * CHUNK_SIZE)) {
            int _cX = (int) (_x / CHUNK_SIZE);
            int _cZ = (int) (_z / CHUNK_SIZE);
            var sub = Objects.requireNonNullElseGet(
                    this.setMap[_cX][_cZ],
                    () -> {
                        var _sub = new InChunkPosSet(
                                this.biasX + (long) _cX * CHUNK_SIZE,
                                this.biasZ + (long) _cZ * CHUNK_SIZE
                        );
                        this.setMap[_cX][_cZ] = _sub;
                        return _sub;
                    }
            );
            sub.add(x, z);
        }
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
    public void addAll(@NotNull BasePosSet pSet) {
        this.addAll(pSet.getList());
    }

    @Override
    public void sub(long x, long z) {
        long _x = x - this.biasX;
        long _z = z - this.biasZ;
        if ((_x >= 0) && (_z >= 0) && (_x < (long) FILE_SIZE * CHUNK_SIZE) && (_z < (long) FILE_SIZE * CHUNK_SIZE)) {
            int _cX = (int) (_x / CHUNK_SIZE);
            int _cZ = (int) (_z / CHUNK_SIZE);
            var sub = this.setMap[_cX][_cZ];
            if (sub != null) {
                sub.sub(x, z);
                if (sub.isEmpty()) {
                    this.setMap[_cX][_cZ] = null;
                }
            }
        }
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
    public void subAll(@NotNull BasePosSet pSet) {
        this.subAll(pSet.getList());
    }

    @Override
    public void cross(@NotNull Collection<? extends Pair<Long, Long>> pSet) {
        Set<Pair<Long, Long>>[][] c = new Set[FILE_SIZE][FILE_SIZE];
        for (int i = 0; i < FILE_SIZE; i++)
            for (int j = 0; j < FILE_SIZE; j++) {
                c[i][j] = new HashSet<>();
            }
        for (var pos : pSet) {
            long _x = pos.x() - this.biasX;
            long _z = pos.y() - this.biasZ;
            if ((_x >= 0) && (_z >= 0) && (_x < (long) FILE_SIZE * CHUNK_SIZE) && (_z < (long) FILE_SIZE * CHUNK_SIZE)) {
                int _cX = (int) (_x / CHUNK_SIZE);
                int _cZ = (int) (_z / CHUNK_SIZE);
                c[_cX][_cZ].add(pos);
            }
        }
        for (int i = 0; i < FILE_SIZE; i++)
            for (int j = 0; j < FILE_SIZE; j++) {
                this.setMap[i][j].cross(c[i][j]);
            }
    }

    @Override
    public void cross(@NotNull BasePosSet pSet) {
        this.cross(pSet.getSet());
    }

    @Override
    public boolean isEmpty() {
        boolean flag = false;
        for (int i = 0; i < FILE_SIZE; i++)
            for (int j = 0; j < FILE_SIZE; j++) {
                var sub = this.setMap[i][j];
                if (sub != null) {
                    flag |= !sub.isEmpty();
                }
            }
        return !flag;
    }

    @Override
    public List<Pair<Long, Long>> getList() {
        List<Pair<Long, Long>> ret = new LinkedList<>();
        for (int i = 0; i < FILE_SIZE; i++)
            for (int j = 0; j < FILE_SIZE; j++) {
                var sub = this.setMap[i][j];
                if (sub != null) {
                    ret.addAll(sub.getList());
                }
            }
        return ret;
    }

    @Override
    public Set<Pair<Long, Long>> getSet() {
        Set<Pair<Long, Long>> ret = new HashSet<>();
        for (int i = 0; i < FILE_SIZE; i++)
            for (int j = 0; j < FILE_SIZE; j++) {
                var sub = this.setMap[i][j];
                if (sub != null) {
                    ret.addAll(sub.getSet());
                }
            }
        return ret;
    }
}
