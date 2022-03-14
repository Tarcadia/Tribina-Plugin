package net.tarcadia.tribina.erod.mapregion.util.type.posset;

import net.tarcadia.tribina.erod.mapregion.util.type.Pos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class InFilePosSet extends BasePosSet {

    static final int CHUNK_SIZE_BITLEN = InChunkPosSet.CHUNK_SIZE_BITLEN;
    static final int CHUNK_SIZE = InChunkPosSet.CHUNK_SIZE;
    static final int FILE_SIZE_BITLEN = 5;
    static final int FILE_SIZE = 1 << FILE_SIZE_BITLEN;
    static final long FILE_SIZE_MASK = -1L << FILE_SIZE_BITLEN << CHUNK_SIZE_BITLEN;
    static final int FILE_CHUNK_SIZE = CHUNK_SIZE * FILE_SIZE;

    private final long biasX;
    private final long biasZ;
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
        if ((_x >= 0) && (_z >= 0) && (_x < FILE_CHUNK_SIZE) && (_z < FILE_CHUNK_SIZE)) {
            int _cX = (int) (_x >> CHUNK_SIZE_BITLEN);
            int _cZ = (int) (_z >> CHUNK_SIZE_BITLEN);
            var sub = this.setMap[_cX][_cZ];
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
    public boolean contains(@NotNull Pos pos) {
        return this.contains(pos.x(), pos.z());
    }

    @Override
    public boolean containsAll(@NotNull Collection<? extends Pos> pSet) {
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
        if ((_x >= 0) && (_z >= 0) && (_x < FILE_CHUNK_SIZE) && (_z < FILE_CHUNK_SIZE)) {
            int _cX = (int) (_x >> CHUNK_SIZE_BITLEN);
            int _cZ = (int) (_z >> CHUNK_SIZE_BITLEN);
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
    public void add(@NotNull Pos pos) {
        this.add(pos.x(), pos.z());
    }

    @Override
    public void addAll(@NotNull Collection<? extends Pos> pSet) {
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
        long _x = x - this.biasX;
        long _z = z - this.biasZ;
        if ((_x >= 0) && (_z >= 0) && (_x < FILE_CHUNK_SIZE) && (_z < FILE_CHUNK_SIZE)) {
            int _cX = (int) (_x >> CHUNK_SIZE_BITLEN);
            int _cZ = (int) (_z >> CHUNK_SIZE_BITLEN);
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
    public void sub(@NotNull Pos pos) {
        this.sub(pos.x(), pos.z());
    }

    @Override
    public void subAll(@NotNull Collection<? extends Pos> pSet) {
        for (var pos : pSet) {
            this.sub(pos);
        }
    }

    @Override
    public void subAll(@NotNull PosSet pSet) {
        this.subAll(pSet.getList());
    }

    @Override
    public void cross(@NotNull Collection<? extends Pos> pSet) {
        Set<Pos>[][] c = new Set[FILE_SIZE][FILE_SIZE];
        for (int i = 0; i < FILE_SIZE; i++)
            for (int j = 0; j < FILE_SIZE; j++) {
                c[i][j] = new HashSet<>();
            }
        for (var pos : pSet) {
            long _x = pos.x() - this.biasX;
            long _z = pos.z() - this.biasZ;
            if ((_x >= 0) && (_z >= 0) && (_x < FILE_CHUNK_SIZE) && (_z < FILE_CHUNK_SIZE)) {
                int _cX = (int) (_x >> CHUNK_SIZE_BITLEN);
                int _cZ = (int) (_z >> CHUNK_SIZE_BITLEN);
                c[_cX][_cZ].add(pos);
            }
        }
        for (int i = 0; i < FILE_SIZE; i++)
            for (int j = 0; j < FILE_SIZE; j++) {
                this.setMap[i][j].cross(c[i][j]);
            }
    }

    @Override
    public void cross(@NotNull PosSet pSet) {
        this.cross(pSet.getSet());
    }

    @Override
    @Nullable
    public Long minX() {
        for (int i = 0; i < FILE_SIZE; i++) {
            Long min = null;
            for (int j = 0; j < FILE_SIZE; j++) {
                var minSub = this.setMap[i][j].minX();
                if ((minSub != null) && (min == null)) {
                    min = minSub;
                } else if ((minSub != null) && (min > minSub)) {
                    min = minSub;
                }
            }
            if (min != null) {
                return min;
            }
        }
        return null;
    }

    @Override
    @Nullable
    public Long minZ() {
        for (int i = 0; i < FILE_SIZE; i++) {
            Long min = null;
            for (int j = 0; j < FILE_SIZE; j++) {
                var minSub = this.setMap[j][i].minZ();
                if ((minSub != null) && (min == null)) {
                    min = minSub;
                } else if ((minSub != null) && (min > minSub)) {
                    min = minSub;
                }
            }
            if (min != null) {
                return min;
            }
        }
        return null;
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
    @NotNull
    public List<Pos> getList() {
        List<Pos> ret = new LinkedList<>();
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
    @NotNull
    public Set<Pos> getSet() {
        Set<Pos> ret = new HashSet<>();
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
