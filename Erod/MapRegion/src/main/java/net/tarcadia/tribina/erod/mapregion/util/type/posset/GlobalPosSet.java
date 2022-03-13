package net.tarcadia.tribina.erod.mapregion.util.type.posset;

import net.tarcadia.tribina.erod.mapregion.util.type.Pos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GlobalPosSet extends BasePosSet {

    static final int CHUNK_SIZE_BITLEN = InChunkPosSet.CHUNK_SIZE_BITLEN;
    static final int CHUNK_SIZE = InChunkPosSet.CHUNK_SIZE;
    static final int FILE_SIZE_BITLEN = InFilePosSet.FILE_SIZE_BITLEN;
    static final int FILE_SIZE = InFilePosSet.FILE_SIZE;
    static final int FILE_CHUNK_SIZE = InFilePosSet.FILE_CHUNK_SIZE;
    static final int FILE_CHUNK_SIZE_BITLEN = FILE_SIZE_BITLEN + CHUNK_SIZE_BITLEN;

    private final Map<Pos, InFilePosSet> setMap;

    public GlobalPosSet() {
        this.setMap = new HashMap<>();
    }

    @Override
    public boolean contains(long x, long z) {
        long _cX = x >> FILE_CHUNK_SIZE_BITLEN;
        long _cZ = z >> FILE_CHUNK_SIZE_BITLEN;
        var pos = new Pos(_cX, _cZ);
        var sub = this.setMap.get(pos);
        if (sub != null) {
            return sub.contains(x, z);
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
        long _cX = x >> FILE_CHUNK_SIZE_BITLEN;
        long _cZ = z >> FILE_CHUNK_SIZE_BITLEN;
        var pos = new Pos(_cX, _cZ);
        var sub = this.setMap.computeIfAbsent(
                pos,
                k -> new InFilePosSet(
                    _cX << FILE_CHUNK_SIZE_BITLEN,
                    _cZ << FILE_CHUNK_SIZE_BITLEN
                )
        );
        sub.add(x, z);
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
        long _cX = x >> FILE_CHUNK_SIZE_BITLEN;
        long _cZ = z >> FILE_CHUNK_SIZE_BITLEN;
        var pos = new Pos(_cX, _cZ);
        var sub = this.setMap.get(pos);
        if (sub != null) {
            sub.sub(x, z);
            if (sub.isEmpty()) {
                this.setMap.remove(pos);
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
        Map<Pos, Set<Pos>> c = new HashMap<>();
        for (var pos : pSet) {
            long _cX = pos.x() >> FILE_CHUNK_SIZE_BITLEN;
            long _cZ = pos.z() >> FILE_CHUNK_SIZE_BITLEN;
            var cPos = new Pos(_cX, _cZ);
            var sub = c.computeIfAbsent(
                    cPos,
                    k -> new HashSet<>()
            );
            sub.add(pos);
        }
        for (var entry : c.entrySet()) {
            var pos = entry.getKey();
            var val = entry.getValue();
            this.setMap.get(pos).cross(val);
        }
    }

    @Override
    public void cross(@NotNull PosSet pSet) {
        this.cross(pSet.getSet());
    }

    @Override
    @Nullable
    public Long minX() {
        Long min = null;
        for (var sub : this.setMap.values()) {
            var minSub = sub.minX();
            if ((minSub != null) && (min == null)) {
                min = minSub;
            } else if ((minSub != null) && (min > minSub)) {
                min = minSub;
            }
        }
        return min;
    }

    @Override
    @Nullable
    public Long minZ() {
        Long min = null;
        for (var sub : this.setMap.values()) {
            var minSub = sub.minZ();
            if ((minSub != null) && (min == null)) {
                min = minSub;
            } else if ((minSub != null) && (min > minSub)) {
                min = minSub;
            }
        }
        return min;
    }

    @Override
    public boolean isEmpty() {
        boolean flag = false;
        for (var sub : this.setMap.values()) {
            flag |= !sub.isEmpty();
        }
        return !flag;
    }

    @Override
    @NotNull
    public List<Pos> getList() {
        List<Pos> ret = new LinkedList<>();
        for (var sub : this.setMap.values()) {
            ret.addAll(sub.getList());
        }
        return ret;
    }

    @Override
    @NotNull
    public Set<Pos> getSet() {
        Set<Pos> ret = new HashSet<>();
        for (var sub : this.setMap.values()) {
            ret.addAll(sub.getSet());
        }
        return ret;
    }
}
