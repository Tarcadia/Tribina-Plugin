package net.tarcadia.tribina.plugin.util.sys;

import net.tarcadia.tribina.plugin.Main;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class BaseCommand{

    protected final JavaPlugin plugin;
    protected final Logger logger;
    protected final PluginCommand command;
    protected final String cmd;

    public BaseCommand(String cmd)
    {
        this.plugin = Main.plugin;
        this.logger = Main.logger;
        this.command = Main.plugin.getCommand(cmd);
        this.cmd = cmd;
        this.register();
    }

    public BaseCommand(JavaPlugin plugin, String cmd)
    {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.command = plugin.getCommand(cmd);
        this.cmd = cmd;
        this.register();
    }

    private void register() {
        if (this.command != null)
        {
            if (this instanceof CommandExecutor) {
                this.command.setExecutor((CommandExecutor) this);
                this.logger.info(this.getClass().getSimpleName() + " registered CommandExecutor for " + cmd + ".");
            }
            if (this instanceof TabCompleter) {
                this.command.setTabCompleter((TabCompleter) this);
                this.logger.info(this.getClass().getSimpleName() + " registered TabCompleter for " + cmd + ".");
            }
            if (!(this instanceof CommandExecutor) && !(this instanceof TabCompleter))
            {
                this.logger.warning(this.getClass().getSimpleName() + " is not an implementation to a command.");
            }
        }
        else
        {
            this.logger.warning(this.getClass().getSimpleName() + " is unable to register command " + this.cmd + ".");
        }
    }

}
