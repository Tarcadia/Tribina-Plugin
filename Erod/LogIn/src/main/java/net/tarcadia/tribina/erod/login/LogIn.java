package net.tarcadia.tribina.erod.login;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;

public final class LogIn extends JavaPlugin implements TabExecutor, Listener {

    public static LogIn plugin = null;
    public static Configuration config = null;
    public static PluginDescriptionFile descrp = null;
    public static Logger logger = null;
    public static String dataPath = null;

    public static final String PATH_CONFIG_DEFAULT = "config.yml";
    public static final String PATH_CONFIG = "Erod/LogIn.yml";

    public static final String KEY_ENABLED = "enabled";
    public static final String KEY_ALLOW_VISIT = "allow-visit";
    public static final String KEY_WELCOME_FORE_REGIN = "texts.welcome.fore-regin";
    public static final String KEY_WELCOME_FORE_LOGIN = "texts.welcome.fore-login";
    public static final String KEY_WELCOME_POST_LOGIN = "texts.welcome.post-login";
    public static final String KEY_TEXT_FUNCTION_ENABLE = "texts.function-enable";
    public static final String KEY_TEXT_FUNCTION_DISABLE = "texts.function-disable";
    public static final String KEY_TEXT_FUNCTION_FAILED = "texts.function-failed";
    public static final String KEY_TEXT_VISIT_ALLOW = "texts.visit-allow";
    public static final String KEY_TEXT_VISIT_DENY = "texts.visit-deny";
    public static final String KEY_TEXT_VISIT_FAILED = "texts.visit-failed";
    public static final String KEY_TEXT_REG_ACCEPT = "texts.reg-accept";
    public static final String KEY_TEXT_REG_DENIED = "texts.reg-denied";
    public static final String KEY_TEXT_LOGIN_ACCEPT = "texts.login-accept";
    public static final String KEY_TEXT_LOGIN_DENIED = "texts.login-denied";
    public static final String KEY_TEXT_LOGIN_ERROR = "texts.login-error";
    public static final String KEY_TEXT_LOGIN_WAIT = "texts.login-wait";
    public static final String KEY_PLAYERS = "players.";
    public static final String KEY_PLAYER_PASSWORDS = ".password";

    public static final String CMD_LI = "erodlogin";
    public static final String CMD_LI_ARG_REG = "reg";
    public static final String CMD_LI_ARG_ENABLE = "enable";
    public static final String CMD_LI_ARG_DISABLE = "disable";
    public static final String CMD_LI_ARG_VISIT_ALLOW = "visit-allow";
    public static final String CMD_LI_ARG_VISIT_DENY = "visit-deny";

    private MessageDigest md5;
    private final Set<String> playerLogged = new HashSet<>();
    private final Map<String, Long> playerLastTry = new HashMap<>();
    private final Map<String, Long> playerFails = new HashMap<>();
    private final Map<String, Location> playerLoginLoc = new HashMap<>();
    private final Map<String, GameMode> playerLoginMode = new HashMap<>();

    public boolean isFunctionEnabled() {
        return config.getBoolean(KEY_ENABLED);
    }

    public void functionEnable() {
        config.set(KEY_ENABLED, true);
        logger.info("Plugin functional enabled.");
    }

    public void functionDisable() {
        config.set(KEY_ENABLED, false);
        logger.info("Plugin functional disabled.");
    }

    @Override
    public void onLoad() {
        plugin = this;
        config = Configuration.getConfiguration(new File(PATH_CONFIG));
        config.setDefaults(YamlConfiguration.loadConfiguration(Objects.requireNonNull(this.getTextResource(PATH_CONFIG_DEFAULT))));
        descrp = this.getDescription();
        logger = this.getLogger();
        dataPath = this.getDataFolder().getPath() + "/";
        this.initEncoder();
        logger.info("Loaded " + descrp.getName() + " v" + descrp.getVersion() + ".");
    }

    private void initEncoder() {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            md5 = null;
            logger.severe("Encoder MD5 load failed.");
        }
    }

    @Override
    public void onEnable() {
        var commandLI = this.getCommand(CMD_LI);
        if (commandLI != null) {
            commandLI.setExecutor(this);
            commandLI.setTabCompleter(this);
        }
        this.getServer().getPluginManager().registerEvents(this, this);
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
                + Long.min(60000, 1000L << Objects.requireNonNullElse(playerFails.get(player.getName()), 0L))
                - System.currentTimeMillis()
        );
    }

    public boolean canVisit() {
        return config.getBoolean(KEY_ALLOW_VISIT, false);
    }

    public void setVisit(boolean canVisit) {
        config.set(KEY_ALLOW_VISIT, canVisit);
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
            logger.info("Reg player " + player.getName() + " in accepted.");
            return true;
        } else {
            logger.warning("Reg player " + player.getName() + " in already exists.");
            return false;
        }
    }

    public boolean loggedPlayer(@NotNull Player player) {
        return this.playerLogged.contains(player.getName());
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
    public void onPlayerMove(PlayerMoveEvent e) {
        if (this.isFunctionEnabled() && !this.loggedPlayer(e.getPlayer()) && !this.canVisit()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.playerLastTry.putIfAbsent(event.getPlayer().getName(), 0L);
        this.playerFails.putIfAbsent(event.getPlayer().getName(), 0L);
        this.playerLoginLoc.put(event.getPlayer().getName(), event.getPlayer().getLocation());
        this.playerLoginMode.put(event.getPlayer().getName(), event.getPlayer().getGameMode());
        if (this.isFunctionEnabled()) {
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
            if (!hasPlayer(event.getPlayer())) {
                event.getPlayer().sendMessage(Objects.requireNonNullElse(config.getString(KEY_WELCOME_FORE_REGIN), ""));
            } else if (hasPlayer(event.getPlayer())) {
                event.getPlayer().sendMessage(Objects.requireNonNullElse(config.getString(KEY_WELCOME_FORE_LOGIN), ""));
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        var player = event.getPlayer();
        if (!this.loggedPlayer(player)) {
            var gm = this.playerLoginMode.get(player.getName());
            var loc = this.playerLoginLoc.get(player.getName());
            if (gm != null) player.setGameMode(gm);
            if (loc != null) player.teleport(loc);
        }
        this.logoutPlayer(event.getPlayer());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equals(CMD_LI)) {
            if ((args.length == 1) && (args[0].equals(CMD_LI_ARG_ENABLE))) {
                if (sender.isOp()) {
                    this.functionEnable();
                    sender.sendMessage(config.getString(KEY_TEXT_FUNCTION_ENABLE, ""));
                } else {
                    sender.sendMessage(config.getString(KEY_TEXT_FUNCTION_FAILED, ""));
                }
                return true;
            } else if ((args.length == 1) && (args[0].equals(CMD_LI_ARG_DISABLE))) {
                if (sender.isOp() && ((sender instanceof ConsoleCommandSender) || ((sender instanceof Player) && this.loggedPlayer((Player) sender)))) {
                    this.functionDisable();
                    sender.sendMessage(config.getString(KEY_TEXT_FUNCTION_DISABLE, ""));
                } else {
                    sender.sendMessage(config.getString(KEY_TEXT_FUNCTION_FAILED, ""));
                }
                return true;
            } else if ((args.length == 1) && (args[0].equals(CMD_LI_ARG_VISIT_ALLOW))) {
                if (sender.isOp() && ((sender instanceof ConsoleCommandSender) || ((sender instanceof Player) && this.loggedPlayer((Player) sender)))) {
                    this.setVisit(true);
                    sender.sendMessage(config.getString(KEY_TEXT_VISIT_ALLOW, ""));
                } else {
                    sender.sendMessage(config.getString(KEY_TEXT_VISIT_FAILED, ""));
                }
                return true;
            } else if ((args.length == 1) && (args[0].equals(CMD_LI_ARG_VISIT_DENY))) {
                if (sender.isOp() && ((sender instanceof ConsoleCommandSender) || ((sender instanceof Player) && this.loggedPlayer((Player) sender)))) {
                    this.setVisit(false);
                    sender.sendMessage(config.getString(KEY_TEXT_VISIT_DENY, ""));
                } else {
                    sender.sendMessage(config.getString(KEY_TEXT_VISIT_FAILED, ""));
                }
                return true;
            } else if ((args.length == 3) && (args[0].equals(CMD_LI_ARG_REG)) && Objects.equals(args[1], args[2])) {
                if ((sender instanceof Player) && this.regPlayer((Player) sender, args[1])) {
                    sender.sendMessage(config.getString(KEY_TEXT_REG_ACCEPT, ""));
                    this.loginPlayer((Player) sender, args[1]);
                    var gm = this.playerLoginMode.get(sender.getName());
                    var loc = this.playerLoginLoc.get(sender.getName());
                    if (gm != null) ((Player) sender).setGameMode(gm);
                    if (loc != null) ((Player) sender).teleport(loc);
                    sender.sendMessage(config.getString(KEY_WELCOME_POST_LOGIN, ""));
                } else {
                    sender.sendMessage(config.getString(KEY_TEXT_REG_DENIED, ""));
                }
                return true;
            } else if ((args.length == 1)) {
                if (this.isFunctionEnabled() && (sender instanceof Player) && !this.loggedPlayer((Player) sender)) {
                    var t = timeLogin((Player) sender);
                    if (t <= 0) {
                        if (loginPlayer((Player) sender, args[0])) {
                            sender.sendMessage(config.getString(KEY_TEXT_LOGIN_ACCEPT, ""));
                            var gm = this.playerLoginMode.get(sender.getName());
                            var loc = this.playerLoginLoc.get(sender.getName());
                            if (gm != null) ((Player) sender).setGameMode(gm);
                            if (loc != null) ((Player) sender).teleport(loc);
                            sender.sendMessage(config.getString(KEY_WELCOME_POST_LOGIN, ""));
                        } else {
                            sender.sendMessage(config.getString(KEY_TEXT_LOGIN_DENIED, ""));
                        }
                    } else {
                        sender.sendMessage(config.getString(KEY_TEXT_LOGIN_WAIT, "").replace("$time$", Long.toString(t)));
                    }
                    return true;
                } else {
                    sender.sendMessage(config.getString(KEY_TEXT_LOGIN_ERROR, ""));
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equals(CMD_LI)) {
            List<String> ret = new LinkedList<>();
            if ((args.length <= 1) && sender.isOp() && !this.isFunctionEnabled()) ret.add(CMD_LI_ARG_ENABLE);
            if ((args.length <= 1) && sender.isOp() && this.isFunctionEnabled()) ret.add(CMD_LI_ARG_DISABLE);
            if ((args.length <= 1) && sender.isOp() && this.canVisit()) ret.add(CMD_LI_ARG_VISIT_DENY);
            if ((args.length <= 1) && sender.isOp() && !this.canVisit()) ret.add(CMD_LI_ARG_VISIT_ALLOW);
            if ((args.length <= 1) && (sender instanceof Player) && this.isFunctionEnabled() && !hasPlayer((Player) sender) && !this.loggedPlayer((Player) sender))
                ret.add(CMD_LI_ARG_REG);
            if ((args.length <= 1) && (sender instanceof Player) && this.isFunctionEnabled() && hasPlayer((Player) sender) && !this.loggedPlayer((Player) sender))
                ret.add("<password>");
            return ret;
        } else {
            return null;
        }
    }

}
