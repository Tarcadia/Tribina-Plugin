package net.tarcadia.tribina.plugin.login.command;

import net.tarcadia.tribina.plugin.login.LogIns;
import net.tarcadia.tribina.plugin.util.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CommandReg extends BaseCommand implements TabExecutor {

    public CommandReg(@NotNull String cmd) {
        super(cmd);
    }

    public CommandReg(@NotNull JavaPlugin plugin, @NotNull String cmd) {
        super(plugin, cmd);
    }

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if ((sender instanceof Player) && (args.length == 2) && (args[0].equals(args[1]))) {
            if (LogIns.regPlayer((Player) sender, args[0])) {
                LogIns.loginPlayer((Player) sender, args[0]);
                sender.sendMessage("Reg in accessed.");
            } else {
                sender.sendMessage("Reg in denied. Already exists.");
            }
            return true;
        } else {
            sender.sendMessage("Login not proper.");
            return false;
        }
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }

}
