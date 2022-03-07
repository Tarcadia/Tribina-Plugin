package net.tarcadia.tribina.plugin.playauth;

import net.tarcadia.tribina.plugin.mapregion.MapRegions;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.wasted.mapregions.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;

public class PlayAuths {

    public static final String PATH_PLAYAUTHS = "/PlayAuths/";
    public static final String PATH_FILE_CONFIG = PATH_PLAYAUTHS + "/config.yml";
    public static final String PATH_FILE_AUTHS = PATH_PLAYAUTHS + "/auths.yml";

    public static Configuration config = null;

    public void load() {
        this.loadConfig();
    }

    private void loadConfig() {
        var defConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(Main.plugin.getResource(PATH_FILE_CONFIG))
        );

        PlayAuths.config =  Configuration.getConfiguration(new File(Main.dataPath + PATH_FILE_CONFIG));
        PlayAuths.config.setDefaults(defConfig);
    }

}
