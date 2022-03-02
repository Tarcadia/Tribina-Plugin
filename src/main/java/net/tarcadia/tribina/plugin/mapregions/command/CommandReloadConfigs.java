package net.tarcadia.tribina.plugin.mapregions.command;

import net.tarcadia.tribina.plugin.mapregions.Main;
import net.tarcadia.tribina.plugin.util.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class CommandReloadConfigs extends BaseCommand implements TabExecutor {
    public CommandReloadConfigs(String cmd) {
        super(cmd);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Main.plugin.reloadConfigs();
        sender.sendMessage("Reloaded configs.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
