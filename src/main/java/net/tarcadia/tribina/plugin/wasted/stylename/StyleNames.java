package net.tarcadia.tribina.plugin.wasted.stylename;

import net.tarcadia.tribina.plugin.Main;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;

public class StyleNames {

    public static final String SN = "[SN] ";

    public static final String KEY_STYLENAMES_ENABLED = "enabled";
    public static final String KEY_STYLENAMES_PLAYERS = "players.";
    public static final String KEY_STYLENAMES_NAME = ".name";
    public static final String KEY_STYLENAMES_TAG = ".tag";
    public static final String KEY_STYLENAMES_TAG_LIST = ".tag-list";
    public static final String KEY_STYLENAMES_TAG_VISIBLE = ".tag-visible";
    public static final String KEY_STYLENAMES_STYLE = ".style";
    public static final String KEY_STYLENAMES_STYLE_LIST = ".style-list";

    public static final String PATH_ROOT = "StyleNames/";
    public static final String PATH_FILE_CONFIG = PATH_ROOT + "config.yml";

    private static Configuration config;

    public static void load() {
        loadConfig();
    }

    private static void loadConfig() {
        var defConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(Main.plugin.getResource(PATH_FILE_CONFIG))
        );
        StyleNames.config = Configuration.getConfiguration(new File(PATH_FILE_CONFIG));
        StyleNames.config.setDefaults(defConfig);
        Main.logger.info(SN + "Loaded config.");
    }

    public static Configuration config() {
        return StyleNames.config;
    }

    public static boolean enabled() {
        return StyleNames.config.getBoolean(KEY_STYLENAMES_ENABLED);
    }

    public static void enable() {
        StyleNames.config.set(KEY_STYLENAMES_ENABLED, true);
        Main.logger.info(SN + "Set enabled.");
    }

    public static void disable() {
        StyleNames.config.set(KEY_STYLENAMES_ENABLED, false);
        Main.logger.info(SN + "Set disabled.");
    }

    public static String getDisplay(@NotNull Player player) {

        var style = StyleNames.config.getString(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_STYLE, "");
        var name = StyleNames.config.getString(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_NAME, player.getName());
        var tag = StyleNames.config.getString(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_TAG);
        var tagVisible = StyleNames.config.getBoolean(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_TAG_VISIBLE);

        var styleLst = StyleNames.config.getStringList(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_STYLE_LIST);
        var tagLst = StyleNames.config.getStringList(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_TAG_LIST);

        Style theStyle;
        Tag theTag;

        try {
            if (styleLst.contains(style)) {
                theStyle = Style.valueOf(style);
            } else {
                theStyle = Style.Normal;
            }
        } catch (IllegalArgumentException e) {
            Main.logger.warning(SN + "Style " + style + " load failed.");
            theStyle = Style.Normal;
        }

        try {
            if (tagLst.contains(tag)) {
                theTag = Tag.valueOf(tag);
            } else {
                theTag = Tag.NullTag;
            }
        } catch (IllegalArgumentException e) {
            Main.logger.warning(SN + "Tag " + tag + " load failed.");
            theTag = Tag.NullTag;
        }


        if (tagVisible && (theTag != Tag.NullTag)) {
            return "{\"text\": \"\", \"extra\": [" +
                    theTag.tag() +
                    ", " +
                    theStyle.styled(name.replace("\"", "")) +
                    "]}";
        } else {
            return "{\"text\": \"\", \"extra\": [" +
                    theStyle.styled(name.replace("\"", "")) +
                    "]}";
        }
    }

    public static void addStyle(@NotNull Player player, @NotNull String style) {
        StyleNames.config.addStringList(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_STYLE_LIST, style);
    }

    public static void addTag(@NotNull Player player, @NotNull String tag) {
        StyleNames.config.addStringList(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_TAG_LIST, tag);
    }

    public static void setName(@NotNull Player player, @NotNull String name) {
        StyleNames.config.set(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_NAME, name);
    }

    public static void setStyle(@NotNull Player player, @NotNull String style) {
        var styleLst = StyleNames.config.getStringList(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_STYLE_LIST);
        if (styleLst.contains(style)) {
            StyleNames.config.set(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_STYLE, style);
        }
    }

    public static void setTag(@NotNull Player player, @NotNull String tag) {
        var tagLst = StyleNames.config.getStringList(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_TAG_LIST);
        if (tagLst.contains(tag)) {
            StyleNames.config.set(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_TAG, tag);
        }
    }

    public static boolean checkStyle(@NotNull Player player, @NotNull String style) {
        var styleLst = StyleNames.config.getStringList(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_STYLE_LIST);
        return styleLst.contains(style);
    }

    public static boolean checkTag(@NotNull Player player, @NotNull String tag) {
        var tagLst = StyleNames.config.getStringList(KEY_STYLENAMES_PLAYERS + player.getName() + KEY_STYLENAMES_TAG_LIST);
        return tagLst.contains(tag);
    }

}
