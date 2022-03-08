package net.tarcadia.tribina.plugin.stylename;

import net.tarcadia.tribina.plugin.Main;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class StyleNames {

    public static final String KEY_STYLENAME_ENABLED = "enabled";
    public static final String KEY_STYLENAME_NAME = ".name";
    public static final String KEY_STYLENAME_TAG = ".tag";
    public static final String KEY_STYLENAME_TAG_LIST = ".tag-list";
    public static final String KEY_STYLENAME_TAG_VISIBLE = ".tag-visible";
    public static final String KEY_STYLENAME_STYLE = ".style";
    public static final String KEY_STYLENAME_STYLE_LIST = ".style-list";

    public static final String PATH_STYLENAMES = "/StyleNames/";
    public static final String PATH_FILE_CONFIG = PATH_STYLENAMES + "/config.yml";

    private static Configuration config;

    public static void load() {
        loadConfig();
    }

    private static void loadConfig() {
        StyleNames.config = Configuration.getConfiguration(new File(PATH_FILE_CONFIG));
    }

    public static String getDisplay(@NotNull Player player) {

        var style = StyleNames.config.getString(player.getName() + KEY_STYLENAME_STYLE, "");
        var name = StyleNames.config.getString(player.getName() + KEY_STYLENAME_NAME, player.getName());
        var tag = StyleNames.config.getString(player.getName() + KEY_STYLENAME_TAG);
        var tagVisible = StyleNames.config.getBoolean(player.getName() + KEY_STYLENAME_TAG_VISIBLE);

        var styleLst = StyleNames.config.getStringList(player.getName() + KEY_STYLENAME_STYLE_LIST);
        var tagLst = StyleNames.config.getStringList(player.getName() + KEY_STYLENAME_TAG_LIST);

        Style theStyle;
        Tag theTag;

        try {
            if (styleLst.contains(style)) {
                theStyle = Style.valueOf(style);
            } else {
                theStyle = Style.Normal;
            }
        } catch (IllegalArgumentException e) {
            Main.logger.warning("[SN] Style " + style + " load failed.");
            theStyle = Style.Normal;
        }

        try {
            if (tagLst.contains(tag)) {
                theTag = Tag.valueOf(tag);
            } else {
                theTag = Tag.NullTag;
            }
        } catch (IllegalArgumentException e) {
            Main.logger.warning("[SN] Tag " + tag + " load failed.");
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
        var styleLst = StyleNames.config.getStringList(player.getName() + KEY_STYLENAME_STYLE_LIST);
        styleLst.add(style);
        StyleNames.config.set(player.getName() + KEY_STYLENAME_STYLE_LIST, styleLst);
    }

    public static void addTag(@NotNull Player player, @NotNull String tag) {
        var tagLst = StyleNames.config.getStringList(player.getName() + KEY_STYLENAME_TAG_LIST);
        tagLst.add(tag);
        StyleNames.config.set(player.getName() + KEY_STYLENAME_STYLE_LIST, tagLst);
    }

    public static void setName(@NotNull Player player, @NotNull String name) {
        StyleNames.config.set(player.getName() + KEY_STYLENAME_NAME, name);
    }

    public static void setStyle(@NotNull Player player, @NotNull String style) {
        var styleLst = StyleNames.config.getStringList(player.getName() + KEY_STYLENAME_STYLE_LIST);
        if (styleLst.contains(style)) {
            StyleNames.config.set(player.getName() + KEY_STYLENAME_STYLE, style);
        }
    }

    public static void setTag(@NotNull Player player, @NotNull String tag) {
        var tagLst = StyleNames.config.getStringList(player.getName() + KEY_STYLENAME_TAG_LIST);
        if (tagLst.contains(tag)) {
            StyleNames.config.set(player.getName() + KEY_STYLENAME_TAG, tag);
        }
    }

    public static boolean checkStyle(@NotNull Player player, @NotNull String style) {
        var styleLst = StyleNames.config.getStringList(player.getName() + KEY_STYLENAME_STYLE_LIST);
        return styleLst.contains(style);
    }

    public static boolean checkTag(@NotNull Player player, @NotNull String tag) {
        var tagLst = StyleNames.config.getStringList(player.getName() + KEY_STYLENAME_TAG_LIST);
        return tagLst.contains(tag);
    }

}
