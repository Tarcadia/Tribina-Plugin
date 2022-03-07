package net.tarcadia.tribina.plugin;

import net.tarcadia.tribina.plugin.login.LogIns;
import net.tarcadia.tribina.plugin.mapregion.MapRegions;
import net.tarcadia.tribina.plugin.playauth.PlayAuths;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public static Main plugin = null;
    public static FileConfiguration config = null;
    public static PluginDescriptionFile descrp = null;
    public static Logger logger = null;
    public static String dataPath = null;

    @Override
    public void onLoad() {
        Main.plugin = this;
        Main.config = this.getConfig();
        Main.descrp = this.getDescription();
        Main.logger = this.getLogger();
        Main.dataPath = this.getDataFolder().getPath();
        Main.logger.info("Loaded " + Main.descrp.getName() + " v" + Main.descrp.getVersion() + ".");
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        LogIns.load();
        PlayAuths.load();
        MapRegions.load();
        Main.logger.info("Enabled " + Main.descrp.getName() + " v" + Main.descrp.getVersion() + ".");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Main.logger.info("Disabled " + Main.descrp.getName() + " v" + Main.descrp.getVersion() + ".");
    }
}
