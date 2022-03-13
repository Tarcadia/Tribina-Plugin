package net.tarcadia.tribina.erod.mapregion.util.type;

public record Pos(long x, long z) implements Cloneable {

    @Override
    public String toString() {
        return "(" + x + ", " + z + ")";
    }

    @Override
    public Pos clone() {
        return new Pos(this.x, this.z);
    }

}
