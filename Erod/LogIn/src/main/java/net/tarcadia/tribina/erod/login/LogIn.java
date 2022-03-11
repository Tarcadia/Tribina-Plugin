package net.tarcadia.tribina.erod.login;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;

public final class LogIn extends JavaPlugin implements InterfaceLogIn {

    public static LogIn plugin = null;
    public static FileConfiguration config = null;
    public static PluginDescriptionFile descrp = null;
    public static Logger logger = null;
    public static String dataPath = null;

    public static final String KEY_ENABLED = "enabled";
    public static final String KEY_WELCOME_FORE_REGIN = "welcome.fore-regin";
    public static final String KEY_WELCOME_FORE_LOGIN = "welcome.fore-login";
    public static final String KEY_WELCOME_POST_LOGIN = "welcome.post-login";
    public static final String KEY_PLAYERS = "players.";
    public static final String KEY_PLAYER_PASSWORDS = ".password";

    private MessageDigest md5;
    private final Set<String> playerLogged = new HashSet<>();
    private final Map<String, Long> playerLastTry = new HashMap<>();
    private final Map<String, Long> playerFails = new HashMap<>();

    @Override
    public void onLoad() {
        plugin = this;
        config = this.getConfig();
        descrp = this.getDescription();
        logger = this.getLogger();
        dataPath = this.getDataFolder().getPath() + "/";
        this.saveDefaultConfig();
        this.onInitEncoder();
        logger.info("Loaded " + descrp.getName() + " v" + descrp.getVersion() + ".");
    }

    private void onInitEncoder() {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            md5 = null;
            logger.severe("Encoder MD5 load failed.");
        }
    }

    @Override
    public void onEnable() {
        logger.info("Enabled " + descrp.getName() + " v" + descrp.getVersion() + ".");
    }

    @Override
    public void onDisable() {
        logger.info("Disabled " + descrp.getName() + " v" + descrp.getVersion() + ".");
    }



    @NotNull
    private String encodeMd5(@NotNull String str) {
        this.md5.update(str.getBytes(StandardCharsets.UTF_8));
        return new BigInteger(1, this.md5.digest()).toString(16);
    }

    @Override
    public boolean enabled() {
        return config.getBoolean(KEY_ENABLED);
    }

    @Override
    public void enable() {
        config.set(KEY_ENABLED, true);
        this.saveConfig();
    }

    @Override
    public void disable() {
        config.set(KEY_ENABLED, false);
        this.saveConfig();
    }

    @Override
    public long timeLogin(@NotNull Player player) {
        return (Objects.requireNonNullElse(playerLastTry.get(player.getName()), 0L)
                + Long.min(60000, 1000L << playerFails.get(player.getName()))
                - System.currentTimeMillis()
        );
    }

    @Override
    public @NotNull String getWelcome(@NotNull Player player) {
        if (!hasPlayer(player)) {
            return Objects.requireNonNullElse(config.getString(KEY_WELCOME_FORE_REGIN), "");
        } else if (!this.playerLogged.contains(player.getName())) {
            return Objects.requireNonNullElse(config.getString(KEY_WELCOME_FORE_LOGIN), "");
        } else {
            return Objects.requireNonNullElse(config.getString(KEY_WELCOME_POST_LOGIN), "");
        }
    }

    @Override
    public boolean hasPlayer(@NotNull Player player) {
        return config.getString(KEY_PLAYERS + player.getName() + KEY_PLAYER_PASSWORDS) != null;
    }

    @Override
    public boolean regPlayer(@NotNull Player player, @NotNull String password) {
        if (
                config.getString(KEY_PLAYERS + player.getName() + KEY_PLAYER_PASSWORDS) == null
        ) {
            config.set(
                    KEY_PLAYERS + player.getName() + KEY_PLAYER_PASSWORDS,
                    this.encodeMd5(password)
            );
            logger.info("Reg player " + player.getName() + " in accepted.");
            return true;
        } else {
            logger.warning("Reg player " + player.getName() + " in already exists.");
            return false;
        }
    }

    @Override
    public boolean loginPlayer(@NotNull Player player, @NotNull String password) {
        if (player.isOnline() && Objects.equals(
                this.encodeMd5(password),
                (config.getString(KEY_PLAYERS + player.getName() + KEY_PLAYER_PASSWORDS))
        )) {
            this.playerLogged.add(player.getName());
            this.playerFails.put(player.getName(), 0L);
            logger.info("Log player " + player.getName() + " in accepted.");
            return true;
        } else {
            this.playerLogged.remove(player.getName());
            this.playerLastTry.put(player.getName(), System.currentTimeMillis());
            this.playerFails.compute(player.getName(), (k, v) -> (v != null ? v + 1 : 1));
            logger.info("Log player " + player.getName() + " in denied.");
            return false;
        }
    }

    @Override
    public void logoutPlayer(@NotNull Player player) {
        this.playerLogged.remove(player.getName());
        logger.info("Log player " + player.getName() + " out.");
    }

}
