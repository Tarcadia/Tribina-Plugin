package net.tarcadia.tribina.erod.mapregion;

import net.tarcadia.tribina.erod.mapregion.util.data.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class MapRegion extends JavaPlugin {

    public static MapRegion plugin = null;
    public static Configuration config = null;
    public static PluginDescriptionFile descrp = null;
    public static Logger logger = null;
    public static String dataPath = null;

    public static final String PATH_CONFIG_DEFAULT = "config.yml";
    public static final String PATH_CONFIG = "Erod/MapRegion.yml";

    public static final String PATH_DATA_ROOT = "Erod/MapRegion/";
    public static final String PATH_DATA_PATHS_ROOT = "Erod/MapRegion/Paths/";
    public static final String PATH_DATA_LANDS_ROOT = "Erod/MapRegion/Lands/";
    public static final String PATH_DATA_TOWNS_ROOT = "Erod/MapRegion/Towns/";
    public static final String PATH_DATA_AUTH = "Erod/MapRegion/Auth.yml";

    public static final String PATH_RES_MAP_R1 = "default-map/default-r1.bmp";
    public static final String PATH_RES_MAP_R3 = "default-map/default-r3.bmp";
    public static final String PATH_RES_MAP_R5 = "default-map/default-r5.bmp";
    public static final String PATH_RES_MAP_R9 = "default-map/default-r9.bmp";

    public static final String KEY_ENABLED = "enabled";

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
        logger.info("Enabled " + descrp.getName() + " v" + descrp.getVersion() + ".");

    }

    @Override
    public void onDisable() {
        logger.info("Disabled " + descrp.getName() + " v" + descrp.getVersion() + ".");
    }
}
