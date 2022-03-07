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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class Logins {

    public static final String KEY_LOGIN_PASSWORDS = PlayManages.KEY_LOGIN_PASSWORDS;

    private static Configuration config;
    private static MessageDigest md5;
    private static MessageDigest md;

    private static final Set<String> loggedPlayers = new HashSet<>();

    public static void load(@NotNull Configuration config) {
        Logins.config = config;
        try {
            Logins.md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Logins.md5 = null;
            Main.logger.severe("[PM] Encoder MD5 load failed.");
        }
        Logins.md = Logins.md5;
    }

    public static void load(@NotNull Configuration config, @NotNull String algorithm) {
        Logins.config = config;
        try {
            Logins.md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Logins.md5 = null;
            Main.logger.severe("[PM] Encoder MD5 load failed.");
        }
        try {
            Logins.md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            Main.logger.severe("[PM] Encoder " + algorithm + " load failed.");
            Logins.md = Logins.md5;
        }
    }

    @NotNull
    private static String encode(@NotNull String str) {
        Logins.md.update(str.getBytes(StandardCharsets.UTF_8));
        return new BigInteger(1, Logins.md.digest()).toString(16);
    }

    @NotNull
    private static String encodeMd5(@NotNull String str) {
        Logins.md5.update(str.getBytes(StandardCharsets.UTF_8));
        return new BigInteger(1, Logins.md5.digest()).toString(16);
    }

    private static boolean hasPassword(@NotNull Player player) {
        return Logins.config.getString(KEY_LOGIN_PASSWORDS + "." + player.getName()) != null;
    }

    private static void setPassword(@NotNull Player player, @NotNull String password) {
        Logins.config.set(KEY_LOGIN_PASSWORDS + "." + player.getName(), Logins.encode(password));
    }

    private static boolean checkPassword(@NotNull Player player, @NotNull String password) {
        return Logins.encode(password).equals(Logins.config.getString(KEY_LOGIN_PASSWORDS + "." + player.getName()));
    }

    public static boolean regPlayer(Player player, String password) {
        if (Logins.hasPassword(player)) {
            Main.logger.warning("[PM] Login reg player " + player.getName() + " already exists.");
            return false;
        } else {
            Logins.setPassword(player, password);
            Main.logger.info("[PM] Login reg player " + player.getName() + " accepted.");
            return true;
        }
    }

    public static boolean loginPlayer(Player player, String password) {
        if (player.isOnline() && checkPassword(player, password)) {
            Logins.loggedPlayers.add(player.getName());
            Main.logger.info("[PM] Login log player " + player.getName() + " accepted.");
            return true;
        } else {
            Logins.loggedPlayers.remove(player.getName());
            Main.logger.info("[PM] Login log player " + player.getName() + " denied.");
            return false;
        }
    }

    public static boolean checkPlayer(Player player) {
        if (player.isOnline()) {
            return Logins.loggedPlayers.contains(player.getName());
        } else {
            Logins.loggedPlayers.remove(player.getName());
            return false;
        }
    }

}
