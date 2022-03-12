package net.tarcadia.tribina.erod.rangetalk;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class RangeTalk extends JavaPlugin implements Listener {

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

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (this.isFunctionEnabled()) {
            var player = event.getPlayer();
            var recipients = event.getRecipients();
            recipients.removeIf((p) -> !this.checkRange(player, p));
        }
    }

}
