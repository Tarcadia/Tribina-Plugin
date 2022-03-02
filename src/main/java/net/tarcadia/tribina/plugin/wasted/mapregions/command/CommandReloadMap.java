package net.tarcadia.tribina.plugin.wasted.mapregions.command;

import net.tarcadia.tribina.plugin.wasted.mapregions.Main;
import net.tarcadia.tribina.plugin.util.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class CommandReloadMap extends BaseCommand implements TabExecutor {
    public CommandReloadMap(String cmd) {
        super(cmd);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        } else {
            for (var arg : args) {
                Main.plugin.reloadMap(arg);
            }
            sender.sendMessage("Reloaded maps.");
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Main.plugin.getMapList();
    }
}
