package net.tarcadia.tribina.erod.stylename;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class StyleName extends JavaPlugin implements TabExecutor, Listener {

    public static StyleName plugin = null;
    public static Configuration config = null;
    public static PluginDescriptionFile descrp = null;
    public static Logger logger = null;
    public static String dataPath = null;

    public static final String PATH_CONFIG_DEFAULT = "config.yml";
    public static final String PATH_CONFIG = "Erod/StyleName.yml";

    public static final String KEY_ENABLED = "enabled";
    public static final String KEY_PLAYERS = "players.";
    public static final String KEY_PLAYERS_NAME = ".name";
    public static final String KEY_PLAYERS_TAG = ".tag";
    public static final String KEY_PLAYERS_TAG_LIST = ".tag-list";
    public static final String KEY_PLAYERS_TAG_VISIBLE = ".tag-visible";
    public static final String KEY_PLAYERS_STYLE = ".style";
    public static final String KEY_PLAYERS_STYLE_LIST = ".style-list";

    public static final String KEY_TEXT_FUNCTION_ENABLE = "texts.function-enable";
    public static final String KEY_TEXT_FUNCTION_DISABLE = "texts.function-disable";
    public static final String KEY_TEXT_SET_NAME = "texts.set-name";
    public static final String KEY_TEXT_SET_NAME_FAIL = "texts.set-name-fail";
    public static final String KEY_TEXT_ADD_TAG = "texts.add-tag";
    public static final String KEY_TEXT_ADD_TAG_FAIL = "texts.add-tag-fail";
    public static final String KEY_TEXT_SET_TAG = "texts.set-tag";
    public static final String KEY_TEXT_SET_TAG_FAIL = "texts.set-tag-fail";
    public static final String KEY_TEXT_SET_TAG_VISIBLE = "texts.set-tag-visible";
    public static final String KEY_TEXT_SET_TAG_INVISIBLE = "texts.set-tag-invisible";
    public static final String KEY_TEXT_SET_TAG_VISIBILITY_FAIL = "texts.set-tag-visibility-fail";
    public static final String KEY_TEXT_ADD_STYLE = "texts.add-style";
    public static final String KEY_TEXT_ADD_STYLE_FAIL = "texts.add-style-fail";
    public static final String KEY_TEXT_SET_STYLE = "texts.set-style";
    public static final String KEY_TEXT_SET_STYLE_FAIL = "texts.set-style-fail";

    public static final String CMD_SN = "erodstylename";
    public static final String CMD_SN_ARG_ENABLE = "enable";
    public static final String CMD_SN_ARG_DISABLE = "disable";
    public static final String CMD_SN_ARG_LIST = "list";
    public static final String CMD_SN_ARG_SET_NAME = "set-name";
    public static final String CMD_SN_ARG_ADD_TAG = "add-tag";
    public static final String CMD_SN_ARG_SET_TAG = "set-tag";
    public static final String CMD_SN_ARG_SET_TAG_VISIBLE = "set-tag-visible";
    public static final String CMD_SN_ARG_SET_TAG_INVISIBLE = "set-tag-invisible";
    public static final String CMD_SN_ARG_ADD_STYLE = "add-style";
    public static final String CMD_SN_ARG_SET_STYLE = "set-style";

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
        logger.info("Loaded " + descrp.getName() + " v" + descrp.getVersion() + ".");
    }

    @Override
    public void onEnable() {
        var commandSN = this.getCommand(CMD_SN);
        if (commandSN != null) {
            commandSN.setExecutor(this);
            commandSN.setTabCompleter(this);
        }
//        this.getServer().getPluginManager().registerEvents(this, this);
        logger.info("Enabled " + descrp.getName() + " v" + descrp.getVersion() + ".");

    }

    @Override
    public void onDisable() {
        logger.info("Disabled " + descrp.getName() + " v" + descrp.getVersion() + ".");
    }

    public boolean setName(@NotNull Player player, @NotNull String name) {
        boolean canName = true;
        // TODO: Check if the name contains some char that we dont want.
        config.set(KEY_PLAYERS + player.getName() + KEY_PLAYERS_NAME, name);
        return canName;
    }

    public void addTag(@NotNull Player player, @NotNull String tag) {
        config.addStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG_LIST, tag);
    }

    public boolean setTag(@NotNull Player player, @NotNull String tag) {
        var tagLst = config.getStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG_LIST);
        if (tagLst.contains(tag)) {
            config.set(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG, tag);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkTagVisibility(@NotNull Player player) {
        return config.getBoolean(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG_VISIBLE);
    }

    public void setTagVisibility(@NotNull Player player, boolean visibility) {
        config.set(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG_VISIBLE, visibility);
    }

    public void addStyle(@NotNull Player player, @NotNull String style) {
        config.addStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE_LIST, style);
    }

    public boolean setStyle(@NotNull Player player, @NotNull String style) {
        var styleLst = config.getStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE_LIST);
        if (styleLst.contains(style)) {
            config.set(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE, style);
            return true;
        } else {
            return false;
        }
    }

    @NotNull
    public String getDisplay(@NotNull Player player) {

        var name = config.getString(KEY_PLAYERS + player.getName() + KEY_PLAYERS_NAME, player.getName());
        var style = config.getString(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE, "Normal");
        var tag = config.getString(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG, "NullTag");
        var tagVisible = config.getBoolean(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG_VISIBLE);

        var styleLst = config.getStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE_LIST);
        var tagLst = config.getStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG_LIST);

        Style theStyle;
        Tag theTag;

        try {
            if (styleLst.contains(style)) {
                theStyle = Style.valueOf(style);
            } else {
                theStyle = Style.Normal;
            }
        } catch (IllegalArgumentException e) {
            logger.warning("Style " + style + " load failed.");
            theStyle = Style.Normal;
        }

        try {
            if (tagLst.contains(tag)) {
                theTag = Tag.valueOf(tag);
            } else {
                theTag = Tag.NullTag;
            }
        } catch (IllegalArgumentException e) {
            logger.warning("Tag " + tag + " load failed.");
            theTag = Tag.NullTag;
        }


        if (tagVisible && (theTag != Tag.NullTag)) {
            return theTag.tag() + theStyle.styled(name);
        } else {
            return theStyle.styled(name);
        }
    }



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equals(CMD_SN)) {
            if ((args.length == 1) && (args[0].equals(CMD_SN_ARG_ENABLE))) {
                if (sender.isOp()) {
                    this.functionEnable();
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_FUNCTION_ENABLE), ""));
                }
                return true;
            } else if ((args.length == 1) && (args[0].equals(CMD_SN_ARG_DISABLE))) {
                if (sender.isOp()) {
                    this.functionDisable();
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_FUNCTION_DISABLE), ""));
                }
                return true;
            } else if ((args.length == 2) && (args[0].equals(CMD_SN_ARG_LIST))) {
                var player = this.getServer().getPlayer(args[1]);
                if ((player != null)) {
                    sender.sendMessage(player.getName() + ":\n");
                    sender.sendMessage("name: " + Objects.requireNonNullElse(config.getString(KEY_PLAYERS + player.getName() + KEY_PLAYERS_NAME), "") + '\n');
                    sender.sendMessage("tags:\n");
                    for (var s : config.getStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE)) sender.sendMessage(" -" + s + '\n');
                    sender.sendMessage("styles:\n");
                    for (var s : config.getStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE)) sender.sendMessage(" -" + s + '\n');
                }
                return true;
            } else if ((args.length == 3) && (args[0].equals(CMD_SN_ARG_SET_NAME))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null) && this.setName(player, args[2])) {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_NAME), "").replace("$player$", player.getName()).replace("$name$", args[2]));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_NAME_FAIL), ""));
                }
                return true;
            } else if ((args.length == 3) && (args[0].equals(CMD_SN_ARG_ADD_TAG))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null)) {
                    this.addTag(player, args[2]);
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_ADD_TAG), "").replace("$player$", player.getName()).replace("$tag$", args[2]));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_ADD_TAG_FAIL), ""));
                }
                return true;
            } else if ((args.length == 3) && (args[0].equals(CMD_SN_ARG_SET_TAG))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null) && this.setTag(player, args[2])) {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_TAG), "").replace("$player$", player.getName()).replace("$tag$", args[2]));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_TAG_FAIL), ""));
                }
                return true;
            } else if ((args.length == 2) && (args[0].equals(CMD_SN_ARG_SET_TAG_VISIBLE))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null)) {
                    this.setTagVisibility(player, true);
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_TAG_VISIBLE), "").replace("$player$", player.getName()));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_TAG_VISIBILITY_FAIL), ""));
                }
                return true;
            } else if ((args.length == 2) && (args[0].equals(CMD_SN_ARG_SET_TAG_INVISIBLE))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null)) {
                    this.setTagVisibility(player, false);
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_TAG_INVISIBLE), "").replace("$player$", player.getName()));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_TAG_VISIBILITY_FAIL), ""));
                }
                return true;
            } else if ((args.length == 3) && (args[0].equals(CMD_SN_ARG_ADD_STYLE))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null)) {
                    this.addStyle(player, args[2]);
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_ADD_STYLE), "").replace("$player$", player.getName()).replace("$style$", args[2]));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_ADD_STYLE_FAIL), ""));
                }
                return true;
            } else if ((args.length == 3) && (args[0].equals(CMD_SN_ARG_SET_STYLE))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null) && this.setStyle(player, args[2])) {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_STYLE), "").replace("$player$", player.getName()).replace("$style$", args[2]));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_STYLE_FAIL), ""));
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equals(CMD_SN)) {
            List<String> ret = new LinkedList<>();
            if ((args.length == 1) && sender.isOp() && !this.isFunctionEnabled()) ret.add(CMD_SN_ARG_ENABLE);
            if ((args.length == 1) && sender.isOp() && this.isFunctionEnabled()) ret.add(CMD_SN_ARG_DISABLE);
            if ((args.length == 1)) ret.add(CMD_SN_ARG_LIST);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_SET_NAME);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_ADD_TAG);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_SET_TAG);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_SET_TAG_VISIBLE);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_SET_TAG_INVISIBLE);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_ADD_STYLE);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_SET_STYLE);
            if ((args.length == 2) && (args[0].equals(CMD_SN_ARG_LIST))) {
                List<String> playerLst = new LinkedList<>();
                for (var p : this.getServer().getOnlinePlayers()) playerLst.add(p.getName());
                ret.addAll(playerLst);
            }
            if ((args.length == 2) && (args[0].equals(CMD_SN_ARG_SET_NAME)) && sender.isOp()) {
                List<String> playerLst = new LinkedList<>();
                for (var p : this.getServer().getOnlinePlayers()) playerLst.add(p.getName());
                ret.addAll(playerLst);
            }
            if ((args.length == 2) && (args[0].equals(CMD_SN_ARG_ADD_TAG)) && sender.isOp()) {
                List<String> playerLst = new LinkedList<>();
                for (var p : this.getServer().getOnlinePlayers()) playerLst.add(p.getName());
                ret.addAll(playerLst);
            }
            if ((args.length == 2) && (args[0].equals(CMD_SN_ARG_SET_TAG)) && sender.isOp()) {
                List<String> playerLst = new LinkedList<>();
                for (var p : this.getServer().getOnlinePlayers()) playerLst.add(p.getName());
                ret.addAll(playerLst);
            }
            if ((args.length == 2) && (args[0].equals(CMD_SN_ARG_ADD_STYLE)) && sender.isOp()) {
                List<String> playerLst = new LinkedList<>();
                for (var p : this.getServer().getOnlinePlayers()) playerLst.add(p.getName());
                ret.addAll(playerLst);
            }
            if ((args.length == 2) && (args[0].equals(CMD_SN_ARG_SET_STYLE)) && sender.isOp()) {
                List<String> playerLst = new LinkedList<>();
                for (var p : this.getServer().getOnlinePlayers()) playerLst.add(p.getName());
                ret.addAll(playerLst);
            }
            if ((args.length == 3) && sender.isOp() && (args[0].equals(CMD_SN_ARG_SET_NAME))) ret.add("<name>");
            if ((args.length == 3) && sender.isOp() && (args[0].equals(CMD_SN_ARG_ADD_TAG))) ret.add("<tag>");
            if ((args.length == 3) && sender.isOp() && (args[0].equals(CMD_SN_ARG_SET_TAG))) ret.add("<tag>");
            if ((args.length == 3) && sender.isOp() && (args[0].equals(CMD_SN_ARG_ADD_STYLE))) ret.add("<style>");
            if ((args.length == 3) && sender.isOp() && (args[0].equals(CMD_SN_ARG_SET_STYLE))) ret.add("<style>");
            return ret;
        } else {
            return null;
        }
    }

}
