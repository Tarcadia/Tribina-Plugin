package net.tarcadia.tribina.plugin.testdev;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.logging.Logger;


public class TestEvent implements Listener {

    private JavaPlugin plugin;
    private Logger logger;

    public TestEvent(@NonNull JavaPlugin plugin)
    {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public boolean onChunkLoad(ChunkLoadEvent event)
    {
        Chunk chunk = event.getChunk();
        String logstr = "Loaded chunk @" + chunk.getWorld().getName() + "[" + chunk.getX() + "," + chunk.getZ() + "].";
//        this.logger.info(logstr);
        return false;
    }

    @EventHandler
    public boolean onChunkUnload(ChunkUnloadEvent event)
    {
        Chunk chunk = event.getChunk();
        String logstr = "Unloaded chunk @" + chunk.getWorld().getName() + "[" + chunk.getX() + "," + chunk.getZ() + "].";
//        this.logger.info(logstr);
//        event.setSaveChunk(false);
        return false;
    }
}