package net.tarcadia.tribina.plugin.wasted.testdev.mapregion;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.logging.Logger;

public class Event_RegionLabel implements Listener {

    private JavaPlugin plugin;
    private Logger logger;

    public Event_RegionLabel(@NonNull JavaPlugin plugin)
    {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

}
