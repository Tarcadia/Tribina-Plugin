package net.tarcadia.tribina.plugin.mapregion.posset;

import net.tarcadia.tribina.plugin.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GlobalPosSet extends BasePosSet{

    static int CHUNK_SIZE_BITLEN = InChunkPosSet.CHUNK_SIZE_BITLEN;
    static int CHUNK_SIZE = InChunkPosSet.CHUNK_SIZE;
    static int FILE_SIZE_BITLEN = InFilePosSet.FILE_SIZE_BITLEN;
    static int FILE_SIZE = InFilePosSet.FILE_SIZE;
    static int FILE_CHUNK_SIZE = CHUNK_SIZE * FILE_SIZE;

    private final Map<Pair<Long, Long>, InFilePosSet> setMap;

    public GlobalPosSet() {
        this.biasX = 0;
        this.biasZ = 0;
        this.setMap = new HashMap<>();
    }

    public GlobalPosSet(String filePath) {
        this.biasX = 0;
        this.biasZ = 0;
        this.setMap = new HashMap<>();
        // TODO: Load from file;
    }

    // TODO: Finish the implementation;

    @Override
    public boolean contains(long x, long z) {
        return false;
    }

    @Override
    public boolean contains(@NotNull Pair<Long, Long> pos) {
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<? extends Pair<Long, Long>> pSet) {
        return false;
    }

    @Override
    public void add(long x, long z) {

    }

    @Override
    public void add(@NotNull Pair<Long, Long> pos) {

    }

    @Override
    public void addAll(@NotNull Collection<? extends Pair<Long, Long>> pSet) {

    }

    @Override
    public void addAll(@NotNull BasePosSet pSet) {

    }

    @Override
    public void sub(long x, long z) {

    }

    @Override
    public void sub(@NotNull Pair<Long, Long> pos) {

    }

    @Override
    public void subAll(@NotNull Collection<? extends Pair<Long, Long>> pSet) {

    }

    @Override
    public void subAll(@NotNull BasePosSet pSet) {

    }

    @Override
    public void cross(@NotNull Collection<? extends Pair<Long, Long>> pSet) {

    }

    @Override
    public void cross(@NotNull BasePosSet pSet) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public List<Pair<Long, Long>> getList() {
        return null;
    }

    @Override
    public Set<Pair<Long, Long>> getSet() {
        return null;
    }
}
