package net.tarcadia.tribina.plugin.wasted.stylename;

import org.jetbrains.annotations.NotNull;

public enum Tag {

    NullTag{
        @Override
        @NotNull
        public String tag() {
            return "";
        }
    },

    ;

    @NotNull
    public abstract String tag();

}
