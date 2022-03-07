package net.tarcadia.tribina.plugin.playmanage.customname.style;

import org.jetbrains.annotations.NotNull;

public enum Style {

    Normal{
        @Override
        public String styled(@NotNull String name, @NotNull String tag) {
            return "";
        }

        @Override
        public String styled(@NotNull String name) {
            return null;
        }
    },

    Green{
        @Override
        public String styled(@NotNull String name, @NotNull String tag) {
            return "";
        }

        @Override
        public String styled(@NotNull String name) {
            return null;
        }
    };

    public abstract String styled(@NotNull String name, @NotNull String tag);
    public abstract String styled(@NotNull String name);

}
