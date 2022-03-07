package net.tarcadia.tribina.plugin.playmanage;

import net.tarcadia.tribina.plugin.playmanage.login.Logins;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.wasted.mapregions.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;

public class PlayManages {

    public static final String KEY_LOGIN_PASSWORDS = "login.passwords";
    public static final String KEY_CUSNAME_NAME = "custom-name.names";
    public static final String KEY_CUSNAME_TAG = "custom-name.tag";
    public static final String KEY_CUSNAME_TAG_LIST = "custom-name.tag-list";
    public static final String KEY_CUSNAME_TAG_VISIBLE = "custom-name.tag-visible";
    public static final String KEY_CUSNAME_STYLE = "custom-name.style";
    public static final String KEY_CUSNAME_STYLE_LIST = "custom-name.style-list";

    public static final String PATH_PLAYMANAGES = "/PlayManages/";
    public static final String PATH_FILE_CONFIG = PATH_PLAYMANAGES + "/config.yml";
    public static final String PATH_FILE_LOGIN_CONFIG = PATH_PLAYMANAGES + "/login.yml";
    public static final String PATH_FILE_CUSNAME_CONFIG = PATH_PLAYMANAGES + "/custom-name.yml";

    private static Configuration config = null;

    public static void load() {
        Main.logger.info("[PM] Loading...");
        PlayManages.loadConfig();
        PlayManages.loadLogin();
        PlayManages.loadCusName();
        Main.logger.info("[PM] Loaded.");
    }

    private static void loadConfig() {
        var defConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(Main.plugin.getResource(PATH_FILE_CONFIG))
        );
        PlayManages.config = Configuration.getConfiguration(
                new File(Main.dataPath + PATH_FILE_CONFIG)
        );
        PlayManages.config.setDefaults(defConfig);
        Main.logger.info("[PM] Loaded config.");
    }

    private static void loadLogin() {
        var defConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(Main.plugin.getResource(PATH_FILE_LOGIN_CONFIG))
        );
        var config = Configuration.getConfiguration(
                new File(Main.dataPath + PATH_FILE_LOGIN_CONFIG)
        );
        config.setDefaults(defConfig);
        Logins.load(config);
        Main.logger.info("[PM] Loaded login sub-system.");
    }

    private static void loadCusName() {
        var defConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(Main.plugin.getResource(PATH_FILE_CUSNAME_CONFIG))
        );
        var config = Configuration.getConfiguration(
                new File(Main.dataPath + PATH_FILE_CUSNAME_CONFIG)
        );
        config.setDefaults(defConfig);
        Logins.load(config);
        Main.logger.info("[PM] Loaded custom name sub-system.");
    }

}
