package net.tarcadia.tribina.plugin.wasted.testdev.commands;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.logging.Logger;

public class T001 implements TabExecutor
{

    private JavaPlugin plugin;
    private Logger logger;
    private PluginCommand command;

    public T001(@NonNull JavaPlugin plugin)
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
            int modified = 0;
            for (int dx = -2; dx <= 2; dx++)
            {
                Chunk chunkFrom = world.getChunkAt((location.getBlockX() >> 4) + dx, (location.getBlockZ() >> 4) + 1);
                Chunk chunkTo = world.getChunkAt((location.getBlockX() >> 4) + dx, (location.getBlockZ() >> 4));
                ChunkSnapshot chunkSnapFrom = chunkFrom.getChunkSnapshot();
                ChunkSnapshot chunkSnapTo = chunkTo.getChunkSnapshot();
                for (int i = 0; i < 16; i++)
                    for (int j = 0; j < 16; j++)
                        for (int k = world.getMinHeight(); k < world.getMaxHeight(); k++)
                        {
                            Material blockTypeFrom = chunkSnapFrom.getBlockType(i, k, j);
                            Material blockTypeTo = chunkSnapTo.getBlockType(i, k, j);
                            BlockData blockDataFrom = chunkSnapFrom.getBlockData(i, k, j);
                            BlockData blockDataTo = chunkSnapTo.getBlockData(i, k, j);
                            if (!(blockTypeFrom == blockTypeTo))
                            {
                                Block blockTo = chunkTo.getBlock(i, k, j);
                                blockTo.setType(blockTypeFrom, false);
                                blockTo.setBlockData(blockDataFrom.clone(), false);
                                modified += 1;
                            }
                            else if (!(blockDataFrom.matches(blockDataTo)))
                            {
                                Block blockTo = chunkTo.getBlock(i, k, j);
                                blockTo.setBlockData(blockDataFrom.clone(), false);
                                modified += 1;
                            }
                        }
            }
            this.logger.info("Modified " + modified + " blocks");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
