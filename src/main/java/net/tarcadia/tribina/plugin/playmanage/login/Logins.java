package net.tarcadia.tribina.plugin.playmanage.login;

import net.tarcadia.tribina.plugin.Main;
import net.tarcadia.tribina.plugin.playmanage.PlayManages;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

public class Logins {

    public static final String KEY_LOGIN_PASSWORDS = PlayManages.KEY_LOGIN_PASSWORDS;

    private static Configuration config;
    private static MessageDigest md5;
    private static MessageDigest md;

    public static void load(@NotNull Configuration config) {
        Logins.config = config;
        try {
            Logins.md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Logins.md5 = null;
            Main.logger.log(Level.SEVERE, "[PM] Encoder MD5 load failed.");
        }
        Logins.md = Logins.md5;
    }

    public static void load(@NotNull Configuration config, @NotNull String algorithm) {
        Logins.config = config;
        try {
            Logins.md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Logins.md5 = null;
            Main.logger.log(Level.SEVERE, "[PM] Encoder MD5 load failed.");
        }
        try {
            Logins.md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            Main.logger.log(Level.SEVERE, "[PM] Encoder " + algorithm + " load failed.");
            Logins.md = Logins.md5;
        }
    }

    @NotNull
    public static String encode(@NotNull String str) {
        Logins.md.update(str.getBytes(StandardCharsets.UTF_8));
        return new BigInteger(1, Logins.md.digest()).toString(16);
    }

    @NotNull
    public static String encodeMd5(@NotNull String str) {
        Logins.md5.update(str.getBytes(StandardCharsets.UTF_8));
        return new BigInteger(1, Logins.md5.digest()).toString(16);
    }

    public static boolean hasPassword(@NotNull Player player) {
        return Logins.config.getString(KEY_LOGIN_PASSWORDS + "." + player.getName()) != null;
    }

    public static void setPassword(@NotNull Player player, @NotNull String password) {
        Logins.config.set(KEY_LOGIN_PASSWORDS + "." + player.getName(), Logins.encode(password));
    }

    public static boolean checkPassword(@NotNull Player player, @NotNull String password) {
        return Logins.encode(password).equals(Logins.config.getString(KEY_LOGIN_PASSWORDS + "." + player.getName()));
    }

}
