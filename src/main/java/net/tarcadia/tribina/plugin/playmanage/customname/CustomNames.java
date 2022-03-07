package net.tarcadia.tribina.plugin.playmanage.customname;

import net.tarcadia.tribina.plugin.Main;
import net.tarcadia.tribina.plugin.playmanage.PlayManages;
import net.tarcadia.tribina.plugin.playmanage.customname.style.Style;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class CustomNames {

    public static final String KEY_CUSNAME_NAME = PlayManages.KEY_CUSNAME_NAME;
    public static final String KEY_CUSNAME_TAG = PlayManages.KEY_CUSNAME_TAG;
    public static final String KEY_CUSNAME_TAG_LIST = PlayManages.KEY_CUSNAME_TAG_LIST;
    public static final String KEY_CUSNAME_TAG_VISIBLE = PlayManages.KEY_CUSNAME_TAG_VISIBLE;
    public static final String KEY_CUSNAME_STYLE = PlayManages.KEY_CUSNAME_STYLE;
    public static final String KEY_CUSNAME_STYLE_LIST = PlayManages.KEY_CUSNAME_STYLE_LIST;

    private static Configuration config;

    public static void load(@NotNull Configuration config) {
        CustomNames.config = config;
    }

    public static String getCustomName(@NotNull Player player) {

        var style = CustomNames.config.getString(KEY_CUSNAME_STYLE + "." + player.getName(), "");
        var name = CustomNames.config.getString(KEY_CUSNAME_NAME + "." + player.getName(), player.getName());
        var tag = CustomNames.config.getString(KEY_CUSNAME_TAG + "." + player.getName());
        var tagVisible = CustomNames.config.getBoolean(KEY_CUSNAME_TAG_VISIBLE + "." + player.getName());

        var styleLst = CustomNames.config.getStringList(KEY_CUSNAME_STYLE_LIST + "." + player.getName());
        var tagLst = CustomNames.config.getStringList(KEY_CUSNAME_TAG_LIST + "." + player.getName());

        Style theStyle;

        try {
            if (styleLst.contains(style)) {
                theStyle = Style.valueOf(style);
            } else {
                theStyle = Style.Normal;
            }
        } catch (IllegalArgumentException e) {
            Main.logger.log(Level.WARNING, "[PM] Style " + style + " load failed.");
            theStyle = Style.Normal;
        }
        if (tagVisible && (tag != null) && (tagLst.contains(tag))) {
            return theStyle.styled(name, tag);
        } else {
            return theStyle.styled(name);
        }
    }

}
