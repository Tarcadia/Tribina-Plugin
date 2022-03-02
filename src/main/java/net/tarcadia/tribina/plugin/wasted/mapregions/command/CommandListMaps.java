package net.tarcadia.tribina.plugin.wasted.mapregions.command;

import net.tarcadia.tribina.plugin.wasted.mapregions.Main;
import net.tarcadia.tribina.plugin.util.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class CommandListMaps extends BaseCommand implements TabExecutor {
    public CommandListMaps(String cmd) {
        super(cmd);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Loaded maps:");
        for (String mapId : Main.plugin.getMapList())
        {
            sender.sendMessage("  - " + mapId);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
