package net.tarcadia.tribina.plugin.wasted.testdev.commands;

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.logging.Logger;

public class T000 implements TabExecutor
{

    private JavaPlugin plugin;
    private Logger logger;
    private PluginCommand command;

    public T000(@NonNull JavaPlugin plugin)
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
        sender.sendMessage("Hello world!");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
