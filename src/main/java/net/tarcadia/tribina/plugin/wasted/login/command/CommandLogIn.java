package net.tarcadia.tribina.plugin.wasted.login.command;

import net.tarcadia.tribina.plugin.wasted.login.LogIns;
import net.tarcadia.tribina.plugin.util.sys.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CommandLogIn extends BaseCommand implements TabExecutor {

    private Map<String, Long> playerLastTry = new HashMap<>();
    private Map<String, Long> playerFails = new HashMap<>();

    public CommandLogIn(@NotNull String cmd) {
        super(cmd);
    }

    public CommandLogIn(@NotNull JavaPlugin plugin, @NotNull String cmd) {
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
        if ((sender instanceof Player) && (args.length == 1)) {
            playerFails.putIfAbsent(sender.getName(), 0L);
            if (
                    System.currentTimeMillis() - Objects.requireNonNullElse(playerLastTry.get(sender.getName()), 0L) >=
                    Long.min(60000, 1L << playerFails.get(sender.getName()))
            ) {
                if (LogIns.loginPlayer((Player) sender, args[0])) {
                    sender.sendMessage("Login accessed.");
                    playerLastTry.put(sender.getName(), System.currentTimeMillis());
                    playerFails.put(sender.getName(), 0L);
                } else {
                    playerLastTry.put(sender.getName(), System.currentTimeMillis());
                    playerFails.compute(sender.getName(), (k, v) -> v + 1);
                    sender.sendMessage("Login denied.");
                }
            } else {
                sender.sendMessage("Login too fast.");
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
