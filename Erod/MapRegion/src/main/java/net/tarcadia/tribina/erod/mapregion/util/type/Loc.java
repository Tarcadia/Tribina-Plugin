package net.tarcadia.tribina.erod.mapregion.util.type;

import org.jetbrains.annotations.NotNull;

public record Loc(@NotNull String world, long x, long z) implements Cloneable {

    public Pos pos() {
        return new Pos(this.x, this.z);
    }

    @Override
    public String toString() {
        return "Loc{" +
                "(" + x + ", " + z + ")" +
                "@" + world +
                '}';
    }

    @Override
    public Loc clone() {
        return new Loc(this.world, this.x, this.z);
    }

}
