package net.tarcadia.tribina.plugin.stylename;

import org.jetbrains.annotations.NotNull;

public enum Style {

    Normal{
        @Override
        @NotNull
        public String styled(@NotNull String name) {
            return "\"" + name + "\"";
        }
    },

    GreenName{
        @Override
        @NotNull
        public String styled(@NotNull String name) {
            return "";
        }
    },

    YellowName{
        @Override
        @NotNull
        public String styled(@NotNull String name) {
            return "{\"text\": " +
                    "\"" + name + "\", " +
                    "\"color\": \"yellow\", " +
                    "}";
        }
    },

    BlueName{
        @Override
        @NotNull
        public String styled(@NotNull String name) {
            return "{\"text\": " +
                    "\"" + name + "\", " +
                    "\"color\": \"blue\", " +
                    "}";
        }
    },

    GoldName{
        @Override
        @NotNull
        public String styled(@NotNull String name) {
            return "{\"text\": " +
                    "\"" + name + "\", " +
                    "\"color\": \"gold\", " +
                    "}";
        }
    },

    LightCyanName{
        @Override
        @NotNull
        public String styled(@NotNull String name) {
            return "{\"text\": " +
                    "\"" + name + "\", " +
                    "\"italic\": true, " +
                    "\"color\": \"aqua\", " +
                    "}";
        }
    },

    LightPinkName{
        @Override
        @NotNull
        public String styled(@NotNull String name) {
            return "{\"text\": " +
                    "\"" + name + "\", " +
                    "\"italic\": true, " +
                    "\"color\": \"light_purple\", " +
                    "}";
        }
    },

    CyanName{
        @Override
        @NotNull
        public String styled(@NotNull String name) {
            return "{\"text\": " +
                    "\"" + name + "\", " +
                    "\"color\": \"aqua\", " +
                    "}";
        }
    },

    PinkName{
        @Override
        @NotNull
        public String styled(@NotNull String name) {
            return "{\"text\": " +
                    "\"" + name + "\", " +
                    "\"color\": \"light_purple\", " +
                    "}";
        }
    },

    HeavyCyanName{
        @Override
        @NotNull
        public String styled(@NotNull String name) {
            return "{\"text\": " +
                    "\"" + name + "\", " +
                    "\"bold\": true, " +
                    "\"color\": \"aqua\", " +
                    "}";
        }
    },

    HeavyPinkName{
        @Override
        @NotNull
        public String styled(@NotNull String name) {
            return "{\"text\": " +
                    "\"" + name + "\", " +
                    "\"bold\": true, " +
                    "\"color\": \"light_purple\", " +
                    "}";
        }
    },

    ;

    @NotNull
    public abstract String styled(@NotNull String name);

}
