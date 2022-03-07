package net.tarcadia.tribina.plugin.playmanage;

import net.tarcadia.tribina.plugin.playmanage.login.Logins;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import net.tarcadia.tribina.plugin.wasted.mapregions.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

public class PlayManages {

    public static final String KEY_LOGIN_ENABLED = "login.enabled";
    public static final String KEY_LOGIN_PASSWORDS = "login.passwords";
    public static final String KEY_LOGIN_NONLOGIN_AUTH = "login.non-login-auth";
    public static final String KEY_LOGIN_LOGIN_AUTH = "login.login-auth";

    public static final String KEY_CUSNAME_ENABLED = "login.enabled";
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

    private static Configuration configLogin = null;
    private static Configuration configCusName = null;
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
        PlayManages.configLogin = Configuration.getConfiguration(
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
        PlayManages.configCusName = Configuration.getConfiguration(
                new File(Main.dataPath + PATH_FILE_CUSNAME_CONFIG)
        );
        config.setDefaults(defConfig);
        Logins.load(config);
        Main.logger.info("[PM] Loaded custom name sub-system.");
    }

    public static boolean loginEnabled() {
        return PlayManages.configLogin.getBoolean(KEY_LOGIN_ENABLED);
    }

    public static void loginEnable() {
        PlayManages.configLogin.set(KEY_LOGIN_ENABLED, true);
        Main.logger.info("[MR] Set login sub-system enabled.");
    }

    public static void loginDisable() {
        PlayManages.configLogin.set(KEY_LOGIN_ENABLED, false);
        Main.logger.info("[MR] Set login sub-system disabled.");
    }

    public static List<String> loginAuth(@NotNull Player player) {
        if (!PlayManages.loginEnabled()) {
            return PlayManages.configLogin.getStringList(KEY_LOGIN_LOGIN_AUTH);
        } else if (Logins.checkPlayer(player)) {
            return PlayManages.configLogin.getStringList(KEY_LOGIN_LOGIN_AUTH);
        } else {
            return PlayManages.configLogin.getStringList(KEY_LOGIN_NONLOGIN_AUTH);
        }
    }

    public static boolean loginRegPlayer(Player player, String password) {
        if (!PlayManages.loginEnabled()) {
            return false;
        } else {
            return Logins.regPlayer(player, password);
        }
    }

    public static boolean loginLogPlayer(Player player, String password) {
        if (!PlayManages.loginEnabled()) {
            return false;
        } else {
            return Logins.loginPlayer(player, password);
        }
    }

    public static boolean loginCheckPlayer(Player player) {
        if (!PlayManages.loginEnabled()) {
            return true;
        } else {
            return Logins.checkPlayer(player);
        }
    }


}
