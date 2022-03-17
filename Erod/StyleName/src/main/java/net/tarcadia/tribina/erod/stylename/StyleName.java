package net.tarcadia.tribina.erod.stylename;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
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
import java.nio.charset.StandardCharsets;
import java.util.*;
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
    public static final String KEY_PLAYERS_SKIN = ".skin";
    public static final String KEY_PLAYERS_SKIN_LIST = ".skin-list";
    public static final String KEY_PLAYERS_TAG = ".tag";
    public static final String KEY_PLAYERS_TAG_LIST = ".tag-list";
    public static final String KEY_PLAYERS_TAG_VISIBLE = ".tag-visible";
    public static final String KEY_PLAYERS_STYLE = ".style";
    public static final String KEY_PLAYERS_STYLE_LIST = ".style-list";

    public static final String KEY_TEXT_FUNCTION_ENABLE = "texts.function-enable";
    public static final String KEY_TEXT_FUNCTION_DISABLE = "texts.function-disable";
    public static final String KEY_TEXT_FUNCTION_FAIL = "texts.function-fail";
    public static final String KEY_TEXT_LIST_FAIL = "texts.list-fail";
    public static final String KEY_TEXT_SET_NAME = "texts.set-name";
    public static final String KEY_TEXT_SET_NAME_FAIL = "texts.set-name-fail";
    public static final String KEY_TEXT_ADD_SKIN = "texts.add-skin";
    public static final String KEY_TEXT_ADD_SKIN_FAIL = "texts.add-skin-fail";
    public static final String KEY_TEXT_SET_SKIN = "texts.set-skin";
    public static final String KEY_TEXT_SET_SKIN_FAIL = "texts.set-skin-fail";
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
    public static final String CMD_SN_ARG_ADD_SKIN = "add-skin";
    public static final String CMD_SN_ARG_SET_SKIN = "set-skin";
    public static final String CMD_SN_ARG_ADD_TAG = "add-tag";
    public static final String CMD_SN_ARG_SET_TAG = "set-tag";
    public static final String CMD_SN_ARG_SET_TAG_VISIBLE = "set-tag-visible";
    public static final String CMD_SN_ARG_SET_TAG_INVISIBLE = "set-tag-invisible";
    public static final String CMD_SN_ARG_ADD_STYLE = "add-style";
    public static final String CMD_SN_ARG_SET_STYLE = "set-style";

    private ProtocolManager pm;

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
        pm = ProtocolLibrary.getProtocolManager();
        var commandSN = this.getCommand(CMD_SN);
        if (commandSN != null) {
            commandSN.setExecutor(this);
            commandSN.setTabCompleter(this);
        }
        this.getServer().getPluginManager().registerEvents(this, this);
        logger.info("Enabled " + descrp.getName() + " v" + descrp.getVersion() + ".");

    }

    @Override
    public void onDisable() {
        logger.info("Disabled " + descrp.getName() + " v" + descrp.getVersion() + ".");
    }

    public boolean setPlayerName(@NotNull Player player, @NotNull String name) {
        if (!name.contains("§") && name.getBytes(StandardCharsets.UTF_8).length <= 16) {
            config.set(KEY_PLAYERS + player.getName() + KEY_PLAYERS_NAME, name);
            return true;
        } else {
            return false;
        }
    }

    @NotNull
    public String getPlayerName(@NotNull Player player) {
        return config.getString(KEY_PLAYERS + player.getName() + KEY_PLAYERS_NAME, "");
    }

    public void addPlayerTag(@NotNull Player player, @NotNull String tag) {
        config.addStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG_LIST, tag);
    }

    public boolean setPlayerTag(@NotNull Player player, @NotNull String tag) {
        var tagLst = config.getStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG_LIST);
        if (tagLst.contains(tag)) {
            config.set(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG, tag);
            return true;
        } else {
            return false;
        }
    }

    @NotNull
    public String getPlayerTag(@NotNull Player player) {
        var tag = config.getString(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG, "");
        var ret = "";
        try {
            var theTag = Tag.valueOf(tag);
            ret = theTag.tag();
        } catch (IllegalArgumentException e) {
            ret = tag + " (NOT SUPPORTED)";
        }
        return ret;
    }

    @NotNull
    public List<String> getPlayerTagList(@NotNull Player player) {
        var lst = config.getStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG_LIST);
        var ret = new LinkedList<String>();
        for (var tag : lst) {
            try {
                var theTag = Tag.valueOf(tag);
                ret.add(theTag.tag());
            } catch (IllegalArgumentException e) {
                ret.add(tag + " (NOT SUPPORTED)");
            }
        }
        return ret;
    }

    public void setPlayerTagVisibility(@NotNull Player player, boolean visibility) {
        config.set(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG_VISIBLE, visibility);
    }

    public void addPlayerStyle(@NotNull Player player, @NotNull String style) {
        config.addStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE_LIST, style);
    }

    public boolean setPlayerStyle(@NotNull Player player, @NotNull String style) {
        var styleLst = config.getStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE_LIST);
        if (styleLst.contains(style)) {
            config.set(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE, style);
            return true;
        } else {
            return false;
        }
    }

    @NotNull
    public String getPlayerStyle(@NotNull Player player) {
        var style = config.getString(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE, "");
        var ret = "";
        try {
            var theStyle = Style.valueOf(style);
            ret = theStyle.styled(style);
        } catch (IllegalArgumentException e) {
            ret = style + " (NOT SUPPORTED)";
        }
        return ret;
    }

    @NotNull
    public List<String> getPlayerStyleList(@NotNull Player player) {
        var lst = config.getStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE_LIST);
        var ret = new LinkedList<String>();
        for (var style : lst) {
            try {
                var theStyle = Style.valueOf(style);
                ret.add(theStyle.styled(style));
            } catch (IllegalArgumentException e) {
                ret.add(style + " (NOT SUPPORTED)");
            }
        }
        return ret;
    }

    @NotNull
    public String getPlayerDisplay(@NotNull Player player) {

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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();
        player.setDisplayName(this.getPlayerDisplay(player));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equals(CMD_SN)) {
            if ((args.length == 1) && (args[0].equals(CMD_SN_ARG_ENABLE))) {
                if (sender.isOp()) {
                    this.functionEnable();
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_FUNCTION_ENABLE), ""));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_FUNCTION_FAIL), ""));
                }
                return true;
            } else if ((args.length == 1) && (args[0].equals(CMD_SN_ARG_DISABLE))) {
                if (sender.isOp()) {
                    this.functionDisable();
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_FUNCTION_DISABLE), ""));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_FUNCTION_FAIL), ""));
                }
                return true;
            } else if ((args.length == 2) && (args[0].equals(CMD_SN_ARG_LIST))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null)) {
                    sender.sendMessage(player.getName() + ":\n");
                    sender.sendMessage("name: " + this.getPlayerName(player) + '\n');
                    sender.sendMessage("tag: "+ this.getPlayerTag(player) + '\n');
                    sender.sendMessage("style: " + this.getPlayerStyle(player) + '\n');
                    sender.sendMessage("tag list:\n");
                    for (var s : this.getPlayerTagList(player)) sender.sendMessage("  - " + s + '\n');
                    sender.sendMessage("style list:\n");
                    for (var s : this.getPlayerStyleList(player)) sender.sendMessage("  - " + s + '\n');
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_LIST_FAIL), ""));
                }
                return true;
            } else if ((args.length == 3) && (args[0].equals(CMD_SN_ARG_SET_NAME))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null) && this.setPlayerName(player, args[2])) {
                    player.setDisplayName(this.getPlayerDisplay(player));
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_NAME), "").replace("$player$", player.getName()).replace("$name$", args[2]));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_NAME_FAIL), ""));
                }
                return true;
            } else if ((args.length == 3) && (args[0].equals(CMD_SN_ARG_ADD_TAG))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null)) {
                    this.addPlayerTag(player, args[2]);
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_ADD_TAG), "").replace("$player$", player.getName()).replace("$tag$", args[2]));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_ADD_TAG_FAIL), ""));
                }
                return true;
            } else if ((args.length == 3) && (args[0].equals(CMD_SN_ARG_SET_TAG))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null) && this.setPlayerTag(player, args[2])) {
                    player.setDisplayName(this.getPlayerDisplay(player));
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_TAG), "").replace("$player$", player.getName()).replace("$tag$", args[2]));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_TAG_FAIL), ""));
                }
                return true;
            } else if ((args.length == 2) && (args[0].equals(CMD_SN_ARG_SET_TAG_VISIBLE))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null)) {
                    this.setPlayerTagVisibility(player, true);
                    player.setDisplayName(this.getPlayerDisplay(player));
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_TAG_VISIBLE), "").replace("$player$", player.getName()));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_TAG_VISIBILITY_FAIL), ""));
                }
                return true;
            } else if ((args.length == 2) && (args[0].equals(CMD_SN_ARG_SET_TAG_INVISIBLE))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null)) {
                    this.setPlayerTagVisibility(player, false);
                    player.setDisplayName(this.getPlayerDisplay(player));
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_TAG_INVISIBLE), "").replace("$player$", player.getName()));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_SET_TAG_VISIBILITY_FAIL), ""));
                }
                return true;
            } else if ((args.length == 3) && (args[0].equals(CMD_SN_ARG_ADD_STYLE))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null)) {
                    this.addPlayerStyle(player, args[2]);
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_ADD_STYLE), "").replace("$player$", player.getName()).replace("$style$", args[2]));
                } else {
                    sender.sendMessage(Objects.requireNonNullElse(config.getString(KEY_TEXT_ADD_STYLE_FAIL), ""));
                }
                return true;
            } else if ((args.length == 3) && (args[0].equals(CMD_SN_ARG_SET_STYLE))) {
                var player = this.getServer().getPlayer(args[1]);
                if (sender.isOp() && (player != null) && this.setPlayerStyle(player, args[2])) {
                    player.setDisplayName(this.getPlayerDisplay(player));
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
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_LIST);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_SET_NAME);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_ADD_TAG);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_SET_TAG);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_SET_TAG_VISIBLE);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_SET_TAG_INVISIBLE);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_ADD_STYLE);
            if ((args.length == 1) && sender.isOp()) ret.add(CMD_SN_ARG_SET_STYLE);
            if ((args.length == 2) && (args[0].equals(CMD_SN_ARG_LIST)) && sender.isOp()) {
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
