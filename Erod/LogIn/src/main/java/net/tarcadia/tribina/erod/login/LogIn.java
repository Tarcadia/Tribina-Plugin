package net.tarcadia.tribina.erod.login;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;

public final class LogIn extends JavaPlugin implements TabExecutor, Listener {

    public static LogIn plugin = null;
    public static FileConfiguration config = null;
    public static PluginDescriptionFile descrp = null;
    public static Logger logger = null;
    public static String dataPath = null;

    public static final String KEY_ENABLED = "enabled";
    public static final String KEY_WELCOME_FORE_REGIN = "texts.welcome.fore-regin";
    public static final String KEY_WELCOME_FORE_LOGIN = "texts.welcome.fore-login";
    public static final String KEY_WELCOME_POST_LOGIN = "texts.welcome.post-login";
    public static final String KEY_TEXT_FUNCTION_ENABLE = "texts.function-enable";
    public static final String KEY_TEXT_FUNCTION_DISABLE = "texts.function-disable";
    public static final String KEY_TEXT_CONFIG_SAVE = "texts.config-save";
    public static final String KEY_TEXT_CONFIG_RELOAD = "texts.config-reload";
    public static final String KEY_TEXT_REG_ACCEPT = "texts.reg-accept";
    public static final String KEY_TEXT_REG_DENIED = "texts.reg-denied";
    public static final String KEY_TEXT_LOGIN_ACCEPT = "texts.login-accept";
    public static final String KEY_TEXT_LOGIN_DENIED = "texts.login-denied";
    public static final String KEY_TEXT_LOGIN_ERROR = "texts.login-error";
    public static final String KEY_TEXT_LOGIN_WAIT = "texts.login-wait";
    public static final String KEY_PLAYERS = "players.";
    public static final String KEY_PLAYER_PASSWORDS = ".password";

    public static final String CMD_REG = "reg";
    public static final String CMD_ENABLE = "enable";
    public static final String CMD_DISABLE = "disable";
    public static final String CMD_SAVE_CONFIG = "save-config";
    public static final String CMD_RELOAD_CONFIG = "reload-config";

    private MessageDigest md5;
    private final Set<String> playerLogged = new HashSet<>();
    private final Map<String, Long> playerLastTry = new HashMap<>();
    private final Map<String, Long> playerFails = new HashMap<>();

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        config = this.getConfig();
        logger.info("Config reloaded.");
    }

    @Override
    public void saveConfig() {
        super.saveConfig();
        logger.info("Config saved.");
    }

    public boolean isFunctionEnabled() {
        return config.getBoolean(KEY_ENABLED);
    }

    public void functionEnable() {
        config.set(KEY_ENABLED, true);
        this.saveConfig();
        logger.info("Plugin functional enabled.");
    }

    public void functionDisable() {
        config.set(KEY_ENABLED, false);
        this.saveConfig();
        logger.info("Plugin functional disabled.");
    }

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

    public long timeLogin(@NotNull Player player) {
        return (Objects.requireNonNullElse(playerLastTry.get(player.getName()), 0L)
                + Long.min(60000, 1000L << playerFails.get(player.getName()))
                - System.currentTimeMillis()
        );
    }

    public boolean hasPlayer(@NotNull Player player) {
        return config.getString(KEY_PLAYERS + player.getName() + KEY_PLAYER_PASSWORDS) != null;
    }

    public boolean regPlayer(@NotNull Player player, @NotNull String password) {
        if (
                config.getString(KEY_PLAYERS + player.getName() + KEY_PLAYER_PASSWORDS) == null
        ) {
            config.set(
                    KEY_PLAYERS + player.getName() + KEY_PLAYER_PASSWORDS,
                    this.encodeMd5(player.getName() + password)
            );
            this.saveConfig();
            logger.info("Reg player " + player.getName() + " in accepted.");
            return true;
        } else {
            logger.warning("Reg player " + player.getName() + " in already exists.");
            return false;
        }
    }

    public boolean loginPlayer(@NotNull Player player, @NotNull String password) {
        if (player.isOnline() && Objects.equals(
                this.encodeMd5(player.getName() + password),
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

    public void logoutPlayer(@NotNull Player player) {
        this.playerLogged.remove(player.getName());
        logger.info("Log player " + player.getName() + " out.");
    }

    @EventHandler
    public void onPlayerAction(PlayerEvent event) {
        if (this.isFunctionEnabled()) {
            if (event instanceof PlayerJoinEvent && !hasPlayer(event.getPlayer())) {
                event.getPlayer().sendMessage(Objects.requireNonNullElse(config.getString(KEY_WELCOME_FORE_REGIN), ""));
            } else if (event instanceof PlayerJoinEvent && hasPlayer(event.getPlayer())) {
                event.getPlayer().sendMessage(Objects.requireNonNullElse(config.getString(KEY_WELCOME_FORE_LOGIN), ""));
            } else if (event instanceof PlayerQuitEvent) {
                this.logoutPlayer(event.getPlayer());
            } else if (this.playerLogged.contains(event.getPlayer().getName()) && event instanceof Cancellable) {
                ((Cancellable) event).setCancelled(true);
            }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if ((args.length == 1) && (args[0].equals(CMD_ENABLE))) {
            this.functionEnable();
            sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_FUNCTION_ENABLE), ""));
            return true;
        } else if ((args.length == 1) && (args[0].equals(CMD_DISABLE))) {
            this.functionDisable();
            sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_FUNCTION_DISABLE), ""));
            return true;
        } else if ((args.length == 1) && (args[0].equals(CMD_SAVE_CONFIG))) {
            this.saveConfig();
            sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_CONFIG_SAVE), ""));
            return true;
        } else if ((args.length == 1) && (args[0].equals(CMD_RELOAD_CONFIG))) {
            this.reloadConfig();
            sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_CONFIG_RELOAD), ""));
            return true;
        } else if ((args.length == 3) && (args[0].equals(CMD_REG)) && Objects.equals(args[1], args[2])) {
            if ((sender instanceof Player) && this.regPlayer((Player) sender, args[1])) {
                sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_REG_ACCEPT), ""));
                this.loginPlayer((Player) sender, args[1]);
                sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_WELCOME_POST_LOGIN), ""));
                return true;
            } else {
                sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_REG_DENIED), ""));
                return false;
            }
        } else if ((args.length == 1)) {
            if ((sender instanceof Player)) {
                var t = timeLogin((Player) sender);
                if (t <= 0) {
                    if (loginPlayer((Player) sender, args[0])) {
                        sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_LOGIN_ACCEPT), ""));
                        sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_WELCOME_POST_LOGIN), ""));
                    } else {
                        sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_LOGIN_DENIED), ""));
                    }
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_LOGIN_WAIT), "").replace("$time$", Long.toString(t)));
                }
                return true;
            } else {
                sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_LOGIN_ERROR), ""));
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> ret = new LinkedList<>();
        if ((args.length == 0) && sender.isOp() && this.isFunctionEnabled()) ret.add(CMD_DISABLE);
        if ((args.length == 0) && sender.isOp() && !this.isFunctionEnabled()) ret.add(CMD_ENABLE);
        if ((args.length == 0) && sender.isOp()) ret.add(CMD_SAVE_CONFIG);
        if ((args.length == 0) && sender.isOp()) ret.add(CMD_RELOAD_CONFIG);
        if ((sender instanceof Player) && (args.length == 0) && !hasPlayer((Player) sender)) ret.add(CMD_REG);
        if ((sender instanceof Player) && (args.length == 0) && hasPlayer((Player) sender)) ret.add("<password>");
        return ret;
    }

}