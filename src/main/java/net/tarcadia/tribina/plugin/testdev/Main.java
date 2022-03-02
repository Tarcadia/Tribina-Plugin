package net.tarcadia.tribina.plugin.testdev;

import net.tarcadia.tribina.plugins.testdev.commands.T000;
import net.tarcadia.tribina.plugins.testdev.commands.T001;
import net.tarcadia.tribina.plugins.testdev.commands.T002;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    private Server server;
    private Logger logger;
    private FileConfiguration config;
    private PluginDescriptionFile description;
    private String descriptionPluginName;
    private String descriptionPluginVersion;
    private TestEvent testEvent;
    private T000 commandT000;
    private T001 commandT001;
    private T002 commandT002;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.server = this.getServer();
        this.logger = this.getLogger();
        this.config = this.getConfig();
        this.description = this.getDescription();
        this.descriptionPluginName = this.description.getName();
        this.descriptionPluginVersion = this.description.getVersion();
        this.logger.info("Enabled " + this.descriptionPluginName + " v" + this.descriptionPluginVersion);

        this.testEvent = new TestEvent(this);
        this.commandT000 = new T000(this);
        this.commandT001 = new T001(this);
        this.commandT002 = new T002(this);
        this.logger.info("Initialized " + this.descriptionPluginName + " v" + this.descriptionPluginVersion);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.logger.info("Disabled " + this.descriptionPluginName + " v" + this.descriptionPluginVersion);
    }
}
