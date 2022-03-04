package net.tarcadia.tribina.plugin.mapregion.posset;

import net.tarcadia.tribina.plugin.util.func.Bitmaps;
import net.tarcadia.tribina.plugin.util.type.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class GlobalPosSet extends BasePosSet{

    static int CHUNK_SIZE_BITLEN = InChunkPosSet.CHUNK_SIZE_BITLEN;
    static int CHUNK_SIZE = InChunkPosSet.CHUNK_SIZE;
    static int FILE_SIZE_BITLEN = InFilePosSet.FILE_SIZE_BITLEN;
    static int FILE_SIZE = InFilePosSet.FILE_SIZE;
    static int FILE_CHUNK_SIZE = CHUNK_SIZE * FILE_SIZE;
    static int FILE_CHUNK_SIZE_BITLEN = FILE_SIZE_BITLEN + CHUNK_SIZE_BITLEN;

    private final Map<Pair<Long, Long>, InFilePosSet> setMap;

    public GlobalPosSet() {
        this.biasX = 0;
        this.biasZ = 0;
        this.setMap = new HashMap<>();
    }

    @Override
    public boolean contains(long x, long z) {
        long _cX = x >> FILE_CHUNK_SIZE_BITLEN;
        long _cZ = z >> FILE_CHUNK_SIZE_BITLEN;
        var pos = new Pair<>(_cX, _cZ);
        var sub = this.setMap.get(pos);
        if (sub != null) {
            return sub.contains(x, z);
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
        long _cX = x >> FILE_CHUNK_SIZE_BITLEN;
        long _cZ = z >> FILE_CHUNK_SIZE_BITLEN;
        var pos = new Pair<>(_cX, _cZ);
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
        long _cX = x >> FILE_CHUNK_SIZE_BITLEN;
        long _cZ = z >> FILE_CHUNK_SIZE_BITLEN;
        var pos = new Pair<>(_cX, _cZ);
        var sub = this.setMap.get(pos);
        if (sub != null) {
            sub.sub(x, z);
            if (sub.isEmpty()) {
                this.setMap.remove(pos);
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
        Map<Pair<Long, Long>, Set<Pair<Long, Long>>> c = new HashMap<>();
        for (var pos : pSet) {
            long _cX = pos.x() >> FILE_CHUNK_SIZE_BITLEN;
            long _cZ = pos.y() >> FILE_CHUNK_SIZE_BITLEN;
            var cPos = new Pair<>(_cX, _cZ);
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
    public void cross(@NotNull BasePosSet pSet) {
        this.cross(pSet.getSet());
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
    public List<Pair<Long, Long>> getList() {
        List<Pair<Long, Long>> ret = new LinkedList<>();
        for (var sub : this.setMap.values()) {
            ret.addAll(sub.getList());
        }
        return ret;
    }

    @Override
    public Set<Pair<Long, Long>> getSet() {
        Set<Pair<Long, Long>> ret = new HashSet<>();
        for (var sub : this.setMap.values()) {
            ret.addAll(sub.getSet());
        }
        return ret;
    }
}
