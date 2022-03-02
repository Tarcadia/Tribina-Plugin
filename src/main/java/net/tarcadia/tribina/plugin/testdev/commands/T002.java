package net.tarcadia.tribina.plugin.testdev.commands;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.logging.Logger;

public class T002 implements TabExecutor
{

    private JavaPlugin plugin;
    private Logger logger;
    private PluginCommand command;

    public T002(@NonNull JavaPlugin plugin)
    {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.command = plugin.getCommand(this.getClass().getSimpleName());
        if (!(command == null))
        {
            command.setExecutor(this);
            command.setTabCompleter(this);
        }
        else
        {
            logger.warning("Unable to register command " + this.getClass().getSimpleName());
        }
        return;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        sender.sendMessage("Doing");
        if (sender.isOp() && (sender instanceof Player))
        {
            Player player = (Player) sender;
            World world = player.getWorld();
            Location location = player.getLocation();
            ChunkSnapshot chunk = world.getChunkAt(location).getChunkSnapshot();
            BlockData bd0 = chunk.getBlockData(0, 64, 0);
            BlockData bd1 = world.getChunkAt(location).getBlock(0, 64, 0).getBlockData();
            logger.info(bd0.getMaterial().name());
            logger.info(bd1.getMaterial().name());
            logger.info(bd0.getAsString());
            logger.info(bd1.getAsString());
            logger.info(bd0.toString());
            logger.info(bd1.toString());
            for (int x = 0; x < 16; ++x)
                for (int z = 0; z < 16; ++z)
                    for (int y = world.getMinHeight(); y < world.getMaxHeight(); ++y)
                    {
                        BlockData bd = chunk.getBlockData(x, y, z);
                        if (bd.matches(bd0))
                        {
                            logger.info("Find a match @[" + x + ", " + y + ", " + z + "]:");
                            logger.info(bd.getAsString());
                        }
                    }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
