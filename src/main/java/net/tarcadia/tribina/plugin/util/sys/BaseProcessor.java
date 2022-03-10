package net.tarcadia.tribina.plugin.util.sys;

import net.tarcadia.tribina.plugin.Main;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class BaseProcessor {

    protected final JavaPlugin plugin;
    protected final Logger logger;
    protected final Map<String, PluginCommand> commands;
    protected final List<String> commandLines;

    public BaseProcessor(List<String> commandLines)
    {
        this.plugin = Main.plugin;
        this.logger = Main.logger;
        this.commands = new HashMap<>();
        for (var commandLine : commandLines) this.commands.put(commandLine, this.plugin.getCommand(commandLine));
        this.commandLines = commandLines;
        this.register();
    }

    public BaseProcessor(JavaPlugin plugin, List<String> commandLines)
    {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.commands = new HashMap<>();
        for (var commandLine : commandLines) this.commands.put(commandLine, this.plugin.getCommand(commandLine));
        this.commandLines = commandLines;
        this.register();
    }

    private void register() {
        for (var commandEntry : this.commands.entrySet()) {
            var command = commandEntry.getValue();
            var commandLine = commandEntry.getKey();
            if (command != null) {
                if (this instanceof CommandExecutor) {
                    command.setExecutor((CommandExecutor) this);
                    this.logger.info(this.getClass().getSimpleName() + " registered CommandExecutor for " + commandLine + ".");
                }
                if (this instanceof TabCompleter) {
                    command.setTabCompleter((TabCompleter) this);
                    this.logger.info(this.getClass().getSimpleName() + " registered TabCompleter for " + commandLine + ".");
                }
                if (this instanceof Listener) {
                    this.plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
                    this.logger.info(this.getClass().getSimpleName() + " registered Listener.");
                }
                if (!(this instanceof CommandExecutor) && !(this instanceof TabCompleter) && !(this instanceof Listener)) {
                    this.logger.warning(this.getClass().getSimpleName() + " is not an implementation to a processor.");
                }
            } else {
                this.logger.warning(this.getClass().getSimpleName() + " is unable to register command " + commandLine + ".");
            }
        }
    }

}
