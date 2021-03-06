package net.tarcadia.tribina.plugin.playauth;

import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Set;

public class PlayAuths {

    public static final String PA = "[PA] ";

    public static final String PATH_ROOT = "PlayAuths/";
    public static final String PATH_FILE_CONFIG = PATH_ROOT + "config.yml";
    public static final String PATH_FILE_AUTHS = PATH_ROOT + "auths.yml";

    private static Configuration config = null;
    private static Auth auth = null;

    public static void load() {
        Main.logger.info(PA + "Loading...");
        PlayAuths.loadConfig();
        PlayAuths.loadAuth();
        Main.logger.info(PA + "Loaded.");
    }

    private static void loadConfig() {
        var defConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(Main.plugin.getResource(PATH_FILE_CONFIG))
        );

        PlayAuths.config = Configuration.getConfiguration(new File(Main.dataPath + PATH_FILE_CONFIG));
        PlayAuths.config.setDefaults(defConfig);

        Main.logger.info(PA + "Loaded config.");
    }

    private static void loadAuth() {
        PlayAuths.auth = new Auth(new File(Main.dataPath + PATH_FILE_AUTHS));
        Main.logger.info(PA + "Loaded auths.");
    }

    public static Configuration config() {
        return PlayAuths.config;
    }

    @NotNull
    public Set<String> getPlaySet(@NotNull String authTag) {
        return PlayAuths.auth.getPlaySet(authTag);
    }

    @NotNull
    public static Set<String> getPlaySet(@NotNull Collection<String> authTags) {
        return PlayAuths.auth.getPlaySet(authTags);
    }

}
