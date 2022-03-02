package net.tarcadia.tribina.plugin.util;

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

public class BaseCommand{

    private final PluginCommand command;
    public BaseCommand(JavaPlugin plugin, String cmd)
    {
        this.command = plugin.getCommand(cmd);
        if (command != null)
        {
            if (this instanceof CommandExecutor) {
                command.setExecutor((CommandExecutor) this);
            }
            if (this instanceof TabCompleter) {
                command.setTabCompleter((TabCompleter) this);
            }
            if (!(this instanceof CommandExecutor) && !(this instanceof TabCompleter))
            {
                plugin.getLogger().warning("Not a command implementation: " + this.getClass().getSimpleName() + ".");
            }
        }
        else
        {
            plugin.getLogger().warning("Unable to register command: " + this.getClass().getSimpleName() + ".");
        }
    }

}
