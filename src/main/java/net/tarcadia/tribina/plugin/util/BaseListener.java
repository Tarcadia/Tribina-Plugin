package net.tarcadia.tribina.plugin.util;

import net.tarcadia.tribina.plugin.Main;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.logging.Logger;

public class BaseListener implements Listener {

    protected final JavaPlugin plugin;
    protected final Logger logger;

    public BaseListener()
    {
        this.plugin = Main.plugin;
        this.logger = Main.logger;
        this.register();
    }

    public BaseListener(@NonNull JavaPlugin plugin)
    {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.register();
    }

    private void register() {
        if (this instanceof Listener) {
            this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
            this.logger.info(this.getClass().getSimpleName() + " registered Listener.");
        } else {
            this.logger.warning(this.getClass().getSimpleName() + " is not an implementation to an event listener.");
        }
    }

}
