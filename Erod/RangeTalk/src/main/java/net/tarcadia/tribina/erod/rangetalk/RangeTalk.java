package net.tarcadia.tribina.erod.rangetalk;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class RangeTalk extends JavaPlugin implements TabExecutor, Listener {

    public static RangeTalk plugin = null;
    public static Configuration config = null;
    public static PluginDescriptionFile descrp = null;
    public static Logger logger = null;
    public static String dataPath = null;

    public static final String KEY_ENABLED = "enabled";
    public static final String KEY_DEFAULT_RANGE = "default-range";
    public static final String KEY_PLAYERS = "players.";
    public static final String KEY_PLAYERS_CAN_SHOUT = ".can-shout";
    public static final String KEY_PLAYERS_RANGE = ".range";

    public static final String KEY_TEXT_FUNCTION_ENABLE = "texts.function-enable";
    public static final String KEY_TEXT_FUNCTION_DISABLE = "texts.function-disable";
    public static final String KEY_TEXT_SET_RANGE_ACCEPT = "texts.set-range-accept";
    public static final String KEY_TEXT_SET_RANGE_FAILED = "texts.set-range-failed";
    public static final String KEY_TEXT_SET_CAN_SHOUT_TRUE = "texts.set-can-shout-true";
    public static final String KEY_TEXT_SET_CAN_SHOUT_FALSE = "texts.set-can-shout-false";
    public static final String KEY_TEXT_SET_CAN_SHOUT_FAILED = "texts.set-can-shout-failed";
    public static final String KEY_TEXT_SHOUT_FAILED = "texts.shout-failed";

    public static final String CMD_RT = "erodrangetalk";
    public static final String CMD_RT_ARG_ENABLE = "enable";
    public static final String CMD_RT_ARG_DISABLE = "disable";
    public static final String CMD_RT_ARG_SET = "set";
    public static final String CMD_RT_ARG_SET_RANGE = "range";
    public static final String CMD_RT_ARG_SET_CAN_SHOUT = "can-shout";

    public static final String CMD_RT_SHOUT = "erodrangetalk-shout";

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
        this.saveDefaultConfig();
        plugin = this;
        config = Configuration.getConfiguration(new File(this.getConfig().getCurrentPath()));
        descrp = this.getDescription();
        logger = this.getLogger();
        dataPath = this.getDataFolder().getPath() + "/";
        this.saveDefaultConfig();
        logger.info("Loaded " + descrp.getName() + " v" + descrp.getVersion() + ".");
    }

    @Override
    public void onEnable() {
        var commandRT = this.getCommand(CMD_RT);
        var commandRTShout = this.getCommand(CMD_RT_SHOUT);
        if (commandRT != null) {
            commandRT.setExecutor(this);
            commandRT.setTabCompleter(this);
        }
        if (commandRTShout != null) {
            commandRTShout.setExecutor(this);
            commandRTShout.setTabCompleter(this);
        }
        this.getServer().getPluginManager().registerEvents(this, this);
        logger.info("Enabled " + descrp.getName() + " v" + descrp.getVersion() + ".");

    }

    @Override
    public void onDisable() {
        logger.info("Disabled " + descrp.getName() + " v" + descrp.getVersion() + ".");
    }

    public double getRange(@NotNull Player player) {
        if (!config.contains(KEY_PLAYERS + player.getName() + KEY_PLAYERS_RANGE)) {
            config.set(KEY_PLAYERS + player.getName() + KEY_PLAYERS_RANGE, config.getDouble(KEY_DEFAULT_RANGE));
        }
        return config.getDouble(KEY_PLAYERS + player.getName() + KEY_PLAYERS_RANGE, config.getDouble(KEY_DEFAULT_RANGE));
    }

    public void setRange(@NotNull Player player, double range) {
        config.set(KEY_PLAYERS + player.getName() + KEY_PLAYERS_RANGE, range);
    }

    public boolean checkRange(@NotNull Player player1, @NotNull Player player2) {
        var r = this.getRange(player1);
        var loc1 = player1.getLocation();
        var loc2 = player2.getLocation();
        var sqrX = (loc1.getX() - loc2.getX()) * (loc1.getX() - loc2.getX());
        var sqrY = (loc1.getY() - loc2.getY()) * (loc1.getY() - loc2.getY());
        var sqrZ = (loc1.getZ() - loc2.getZ()) * (loc1.getZ() - loc2.getZ());
        var sqrR = r * r;
        return Objects.equals(loc1.getWorld(), loc2.getWorld()) && (sqrX + sqrY + sqrZ <= sqrR);
    }

    public void setShout(@NotNull Player player, boolean canShout) {
        config.set(KEY_PLAYERS + player.getName() + KEY_PLAYERS_CAN_SHOUT, canShout);
    }

    public boolean checkShout(@NotNull Player player) {
        return config.getBoolean(KEY_PLAYERS + player.getName() + KEY_PLAYERS_CAN_SHOUT, false);
    }

    public void doShout(@NotNull Player player, String shout) {
        if (isFunctionEnabled() && checkShout(player)) {
            var name = (!player.getDisplayName().equals("") ? player.getDisplayName() : "\"" + player.getName() + "\"");
            player.getServer().broadcastMessage("{\"text\": \"\", \"extra\": [" +
                    name + ", " +
                    "{\"text\": \"" + shout + "\", \"color\": \"gold\", \"bold\": true}" +
                    "]}"
            );
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (this.isFunctionEnabled()) {
            var player = event.getPlayer();
            var recipients = event.getRecipients();
            recipients.removeIf((p) -> !this.checkRange(player, p));
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equals(CMD_RT)) {
            if ((args.length == 1) && (args[0].equals(CMD_RT_ARG_ENABLE))) {
                if (sender.isOp()) {
                    this.functionEnable();
                    sender.sendMessage(config.getString(KEY_TEXT_FUNCTION_ENABLE, ""));
                }
                return true;
            } else if ((args.length == 1) && (args[0].equals(CMD_RT_ARG_DISABLE))) {
                if (sender.isOp()) {
                    this.functionDisable();
                    sender.sendMessage(config.getString(KEY_TEXT_FUNCTION_DISABLE, ""));
                }
                return true;
            } else if ((args.length == 4) && (args[0].equals(CMD_RT_ARG_SET)) && (args[2].equals(CMD_RT_ARG_SET_RANGE))) {
                var player = this.getServer().getPlayer(args[1]);
                var range = Double.parseDouble(args[3]);
                if (sender.isOp() && (player != null) && (Double.isNaN(range))) {
                    this.setRange(player, range);
                    sender.sendMessage(config.getString(KEY_TEXT_SET_RANGE_ACCEPT, "").replace("$player$", player.getName()).replace("$range$", Double.toString(range)));
                } else {
                    sender.sendMessage(config.getString(KEY_TEXT_SET_RANGE_FAILED, ""));
                }
                return true;
            } else if ((args.length == 4) && (args[0].equals(CMD_RT_ARG_SET)) && (args[2].equals(CMD_RT_ARG_SET_CAN_SHOUT))) {
                var player = this.getServer().getPlayer(args[1]);
                var canShout = Boolean.parseBoolean(args[3]);
                System.out.println(canShout);
                if (sender.isOp() && (player != null)) {
                    this.setShout(player, canShout);
                    if (canShout)
                        sender.sendMessage(config.getString(KEY_TEXT_SET_CAN_SHOUT_TRUE, "").replace("$player$", player.getName()));
                    else
                        sender.sendMessage(config.getString(KEY_TEXT_SET_CAN_SHOUT_FALSE, "").replace("$player$", player.getName()));
                } else {
                    sender.sendMessage(config.getString(KEY_TEXT_SET_CAN_SHOUT_FAILED, ""));
                }
                return true;
            } else {
                return false;
            }
        } else if (command.getName().equals(CMD_RT_SHOUT)) {
            if ((args.length >= 1) && (sender instanceof Player) && this.checkShout((Player) sender)) {
                this.doShout((Player) sender, String.join(" ", args));
                return true;
            } else {
                sender.sendMessage(config.getString(KEY_TEXT_SHOUT_FAILED, ""));
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equals(CMD_RT)) {
            List<String> ret = new LinkedList<>();
            if ((args.length == 1) && sender.isOp() && !this.isFunctionEnabled()) ret.add(CMD_RT_ARG_ENABLE);
            if ((args.length == 1) && sender.isOp() && this.isFunctionEnabled()) ret.add(CMD_RT_ARG_DISABLE);
            if ((args.length == 2) && (args[0].equals(CMD_RT_ARG_SET)) && sender.isOp()) {
                List<String> playerLst = new LinkedList<>();
                for (var p : this.getServer().getOnlinePlayers()) playerLst.add(p.getName());
                ret.addAll(playerLst);
            }
            if ((args.length == 3) && sender.isOp()) ret.add(CMD_RT_ARG_SET_RANGE);
            if ((args.length == 3) && sender.isOp()) ret.add(CMD_RT_ARG_SET_CAN_SHOUT);
            if ((args.length == 4) && (args[0].equals(CMD_RT_ARG_SET)) && (args[2].equals(CMD_RT_ARG_SET_RANGE)) && sender.isOp())
                ret.add("<number>");
            if ((args.length == 4) && (args[0].equals(CMD_RT_ARG_SET)) && (args[2].equals(CMD_RT_ARG_SET_CAN_SHOUT)) && sender.isOp())
                ret.add("<true|false>");
            return ret;
        } else if (command.getName().equals(CMD_RT_SHOUT)) {
            List<String> ret = new LinkedList<>();
            if ((args.length >= 1) && (sender instanceof Player) && this.checkShout((Player) sender)) ret.add("<message>");
            return ret;
        } else {
            return null;
        }
    }

}
