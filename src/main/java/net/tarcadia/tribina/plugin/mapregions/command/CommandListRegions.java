package net.tarcadia.tribina.plugin.mapregions.command;

import net.tarcadia.tribina.plugin.mapregions.Main;
import net.tarcadia.tribina.plugin.util.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class CommandListRegions extends BaseCommand implements TabExecutor {
    public CommandListRegions(String cmd) {
        super(cmd);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            return false;
        } else {
            sender.sendMessage("Loaded regions in " + args[0] + ":");
            for (String mapId : Main.plugin.getRegionList(args[0]))
            {
                sender.sendMessage("  - " + mapId);
            }
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 1) {
            return Main.plugin.getMapList();
        } else {
            return null;
        }
    }
}
