package net.tarcadia.tribina.plugin.wasted.rangetalk;

import net.tarcadia.tribina.plugin.Main;
import net.tarcadia.tribina.plugin.wasted.rangetalk.event.EventTalkRange;
import net.tarcadia.tribina.plugin.util.data.configuration.Configuration;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class RangeTalks {

    public static final String RT = "[RT] ";

    public static final String KEY_RANGETALKS_ENABLED = "enabled";
    public static final String KEY_RANGETALKS_PLAYER = "players.";
    public static final String KEY_RANGETALKS_PLAYER_RANGE = ".range";
    public static final String KEY_RANGETALKS_DEFAULT_RANGE = "default.range";

    public static final String PATH_FILE_CONFIG = "RangeTalks.yml";

    private static final List<Listener> events = new LinkedList<>();

    private static Configuration config;

    public static void load() {
        loadConfig();
        loadEvents();
    }

    private static void loadConfig() {
        var defConfig = YamlConfiguration.loadConfiguration(
                new InputStreamReader(Main.plugin.getResource(PATH_FILE_CONFIG))
        );

        RangeTalks.config = Configuration.getConfiguration(new File(Main.dataPath + PATH_FILE_CONFIG));
        RangeTalks.config.setDefaults(defConfig);

        Main.logger.info(RT + "Loaded config.");
    }

    private static void loadEvents() {
        RangeTalks.events.add(new EventTalkRange());
        Main.logger.info(RT + "Loaded events.");
    }

    public static Configuration config() {
        return RangeTalks.config;
    }
    public static List<Listener> events() { return RangeTalks.events; }

    public static boolean enabled() {
        return RangeTalks.config.getBoolean(KEY_RANGETALKS_ENABLED);
    }

    public static void enable() {
        RangeTalks.config.set(KEY_RANGETALKS_ENABLED, true);
        Main.logger.info(RT + "Set enabled.");
    }

    public static void disable() {
        RangeTalks.config.set(KEY_RANGETALKS_ENABLED, false);
        Main.logger.info(RT + "Set disabled.");
    }

    private static Double range() {
        return RangeTalks.config.getDouble(KEY_RANGETALKS_DEFAULT_RANGE);
    }

    private static Double range(@NotNull Player player) {
        if (!RangeTalks.config.contains(KEY_RANGETALKS_PLAYER + player.getName())) {
            RangeTalks.config.set(
                    KEY_RANGETALKS_PLAYER + player.getName() + KEY_RANGETALKS_PLAYER_RANGE,
                    RangeTalks.config.getDouble(KEY_RANGETALKS_DEFAULT_RANGE)
            );
        }
        return RangeTalks.config.getDouble(KEY_RANGETALKS_PLAYER + player.getName() + KEY_RANGETALKS_PLAYER_RANGE);
    }

    public static boolean checkDist(@NotNull Location loc1, @NotNull Location loc2) {
        if (enabled()) {
            var sqrX = (loc1.getX() - loc2.getX()) * (loc1.getX() - loc2.getX());
            var sqrY = (loc1.getY() - loc2.getY()) * (loc1.getY() - loc2.getY());
            var sqrZ = (loc1.getZ() - loc2.getZ()) * (loc1.getZ() - loc2.getZ());
            var r = range();
            var sqrR = r * r;
            return (loc1.getWorld() == loc2.getWorld()) && (sqrX + sqrY + sqrZ < sqrR);
        } else {
            return true;
        }
    }

    public static boolean checkDist(@NotNull Player player1, @NotNull Player player2) {
        if (enabled()) {
            var loc1 = player1.getLocation();
            var loc2 = player2.getLocation();
            var sqrX = (loc1.getX() - loc2.getX()) * (loc1.getX() - loc2.getX());
            var sqrY = (loc1.getY() - loc2.getY()) * (loc1.getY() - loc2.getY());
            var sqrZ = (loc1.getZ() - loc2.getZ()) * (loc1.getZ() - loc2.getZ());
            var r = range(player1);
            var sqrR = r * r;
            return (loc1.getWorld() == loc2.getWorld()) && (sqrX + sqrY + sqrZ < sqrR);
        } else {
            return true;
        }
    }

}
