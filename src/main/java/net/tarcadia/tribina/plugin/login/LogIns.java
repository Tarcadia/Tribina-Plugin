package net.tarcadia.tribina.plugin.login;

import net.tarcadia.tribina.plugin.Main;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public class LogIns {

    public static final String KEY_LOGIN_ENABLED = "login.enabled";
    public static final String KEY_LOGIN_PASSWORDS = "login.passwords";
    public static final String KEY_LOGIN_NONLOGIN_AUTH = "login.non-login-auth";
    public static final String KEY_LOGIN_LOGIN_AUTH = "login.login-auth";

    public static final String AUTH_LOGIN_NONLOGIN = "login.non-login";
    public static final String AUTH_LOGIN_LOGIN = "login.login";

    public static final String PATH_LOGINS = "/Logins/";
    public static final String PATH_FILE_CONFIG = PATH_LOGINS + "/config.yml";

    private static Configuration config;
    private static MessageDigest md5;
    private static MessageDigest md;

    private static final Set<String> loggedPlayers = new HashSet<>();

    public static void load() {
        LogIns.config = Configuration.getConfiguration(new File(PATH_FILE_CONFIG));
        try {
            LogIns.md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LogIns.md5 = null;
            Main.logger.severe("[LI] Encoder MD5 load failed.");
        }
        LogIns.md = LogIns.md5;
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
        return LogIns.config.getString(KEY_LOGIN_PASSWORDS + "." + player.getName()) != null;
    }

    private static void setPassword(@NotNull Player player, @NotNull String password) {
        LogIns.config.set(KEY_LOGIN_PASSWORDS + "." + player.getName(), LogIns.encode(password));
    }

    private static boolean checkPassword(@NotNull Player player, @NotNull String password) {
        return LogIns.encode(password).equals(LogIns.config.getString(KEY_LOGIN_PASSWORDS + "." + player.getName()));
    }

    public static boolean regPlayer(Player player, String password) {
        if (LogIns.hasPassword(player)) {
            Main.logger.warning("[LI] Login reg player " + player.getName() + " already exists.");
            return false;
        } else {
            LogIns.setPassword(player, password);
            Main.logger.info("[LI] Login reg player " + player.getName() + " accepted.");
            return true;
        }
    }

    public static boolean loginPlayer(Player player, String password) {
        if (player.isOnline() && checkPassword(player, password)) {
            LogIns.loggedPlayers.add(player.getName());
            Main.logger.info("[LI] Login log player " + player.getName() + " accepted.");
            return true;
        } else {
            LogIns.loggedPlayers.remove(player.getName());
            Main.logger.info("[LI] Login log player " + player.getName() + " denied.");
            return false;
        }
    }

    public static boolean checkPlayer(Player player) {
        if (player.isOnline()) {
            return LogIns.loggedPlayers.contains(player.getName());
        } else {
            LogIns.loggedPlayers.remove(player.getName());
            return false;
        }
    }

}