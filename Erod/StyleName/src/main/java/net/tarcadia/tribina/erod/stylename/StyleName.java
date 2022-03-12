package net.tarcadia.tribina.erod.stylename;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class StyleName extends JavaPlugin {

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
    public static final String KEY_TEXT_SET_TAG = "texts.set-tag";
    public static final String KEY_TEXT_SET_TAG_FAIL = "texts.set-tag-fail";
    public static final String KEY_TEXT_SET_TAG_VISIBLE = "texts.set-tag-visible";
    public static final String KEY_TEXT_SET_TAG_INVISIBLE = "texts.set-tag-invisible";
    public static final String KEY_TEXT_ADD_STYLE = "texts.add-style";
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
//        var commandRT = this.getCommand(CMD_RT);
//        var commandRTShout = this.getCommand(CMD_RT_SHOUT);
//        if (commandRT != null) {
//            commandRT.setExecutor(this);
//            commandRT.setTabCompleter(this);
//        }
//        if (commandRTShout != null) {
//            commandRTShout.setExecutor(this);
//            commandRTShout.setTabCompleter(this);
//        }
//        this.getServer().getPluginManager().registerEvents(this, this);
        logger.info("Enabled " + descrp.getName() + " v" + descrp.getVersion() + ".");

    }

    @Override
    public void onDisable() {
        logger.info("Disabled " + descrp.getName() + " v" + descrp.getVersion() + ".");
    }

    public boolean checkStyle(@NotNull Player player, @NotNull String style) {
        var styleLst = config.getStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE_LIST);
        return styleLst.contains(style);
    }

    public boolean checkTag(@NotNull Player player, @NotNull String tag) {
        var tagLst = config.getStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG_LIST);
        return tagLst.contains(tag);
    }

    public void addStyle(@NotNull Player player, @NotNull String style) {
        config.addStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_STYLE_LIST, style);
    }

    public void addTag(@NotNull Player player, @NotNull String tag) {
        config.addStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG_LIST, tag);
    }

    public boolean setName(@NotNull Player player, @NotNull String name) {
        boolean canName = true;
        // TODO: Check if the name contains some char that we dont want.
        config.set(KEY_PLAYERS + player.getName() + KEY_PLAYERS_NAME, name);
        return canName;
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

    public boolean setTag(@NotNull Player player, @NotNull String tag) {
        var tagLst = config.getStringList(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG_LIST);
        if (tagLst.contains(tag)) {
            config.set(KEY_PLAYERS + player.getName() + KEY_PLAYERS_TAG, tag);
            return true;
        } else {
            return false;
        }
    }

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

}
