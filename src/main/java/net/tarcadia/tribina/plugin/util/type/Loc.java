package net.tarcadia.tribina.plugin.util.type;

public record Loc(String world, long x, long z) implements Cloneable {

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
