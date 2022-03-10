package net.tarcadia.tribina.plugin.login;

import net.tarcadia.tribina.plugin.Main;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogIns {

    public static final String LI = "[LI] ";

    public static final String KEY_LOGINS_ENABLED = "enabled";
    public static final String KEY_LOGINS_PLAYERS = "players.";
    public static final String KEY_LOGINS_PLAYER_PASSWORDS = ".password";
    public static final String KEY_LOGINS_AUTH_NONLOGIN = "auth.non-login";
    public static final String KEY_LOGINS_AUTH_LOGIN = "auth.login";

    public static final String AUTH_LOGINS_NONLOGIN = "login.non-login";
    public static final String AUTH_LOGINS_LOGIN = "login.login";

    public static final String PATH_FILE_CONFIG = "/LogIns.yml";

    private static Configuration config;
    private static MessageDigest md5;
    private static MessageDigest md;

    private static final Set<String> loggedPlayers = new HashSet<>();

    public static void load() {
        loadConfig();
        loadEncoder();
    }

    private static void loadConfig() {
        var defConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(Main.plugin.getResource(PATH_FILE_CONFIG))
        );
        LogIns.config = Configuration.getConfiguration(new File(PATH_FILE_CONFIG));
        LogIns.config.setDefaults(defConfig);
    }

    private static void loadEncoder() {
        try {
            LogIns.md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LogIns.md5 = null;
            Main.logger.severe(LI + "Encoder MD5 load failed.");
        }
        LogIns.md = LogIns.md5;
    }

    public static Configuration config() {
        return LogIns.config;
    }

    public static boolean enabled() {
        return LogIns.config.getBoolean(KEY_LOGINS_ENABLED);
    }

    public static void enable() {
        LogIns.config.set(KEY_LOGINS_ENABLED, true);
        Main.logger.info(LI + "Set enabled.");
    }

    public static void disable() {
        LogIns.config.set(KEY_LOGINS_ENABLED, false);
        Main.logger.info(LI + "Set disabled.");
    }

    @NotNull
    private static String encode(@NotNull String str) {
        LogIns.md.update(str.getBytes(StandardCharsets.UTF_8));
        return new BigInteger(1, LogIns.md.digest()).toString(16);
    }

    @NotNull
    private static String encodeMd5(@NotNull String str) {
        LogIns.md5.update(str.getBytes(StandardCharsets.UTF_8));
        return new BigInteger(1, LogIns.md5.digest()).toString(16);
    }

    private static boolean hasPassword(@NotNull Player player) {
        return LogIns.config.getString(KEY_LOGINS_PLAYERS + player.getName() + KEY_LOGINS_PLAYER_PASSWORDS) != null;
    }

    private static void setPassword(@NotNull Player player, @NotNull String password) {
        LogIns.config.set(KEY_LOGINS_PLAYERS + player.getName() + KEY_LOGINS_PLAYER_PASSWORDS, LogIns.encode(password));
    }

    private static boolean checkPassword(@NotNull Player player, @NotNull String password) {
        return LogIns.encode(password).equals(LogIns.config.getString(KEY_LOGINS_PLAYERS + player.getName() + KEY_LOGINS_PLAYER_PASSWORDS));
    }

    public static boolean regPlayer(@NotNull Player player, @NotNull String password) {
        if (LogIns.hasPassword(player)) {
            Main.logger.warning(LI + "Login reg player " + player.getName() + " already exists.");
            return false;
        } else {
            LogIns.setPassword(player, password);
            Main.logger.info(LI + "Login reg player " + player.getName() + " accepted.");
            return true;
        }
    }

    public static boolean loginPlayer(@NotNull Player player, @NotNull String password) {
        if (player.isOnline() && checkPassword(player, password)) {
            LogIns.loggedPlayers.add(player.getName());
            Main.logger.info(LI + "Login log player " + player.getName() + " accepted.");
            return true;
        } else {
            LogIns.loggedPlayers.remove(player.getName());
            Main.logger.info(LI + "Login log player " + player.getName() + " denied.");
            return false;
        }
    }

    public static boolean checkPlayer(@NotNull Player player) {
        if (player.isOnline()) {
            return LogIns.loggedPlayers.contains(player.getName());
        } else {
            LogIns.loggedPlayers.remove(player.getName());
            return false;
        }
    }

    public static List<String> getAuthTags(@NotNull Player player) {
        if (checkPlayer(player)) {
            return LogIns.config.getStringList(KEY_LOGINS_AUTH_LOGIN);
        } else {
            return LogIns.config.getStringList(KEY_LOGINS_AUTH_NONLOGIN);
        }
    }

}
