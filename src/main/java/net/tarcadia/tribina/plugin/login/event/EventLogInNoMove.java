package net.tarcadia.tribina.plugin.login.event;

import net.tarcadia.tribina.plugin.login.LogIns;
import net.tarcadia.tribina.plugin.util.sys.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventLogInNoMove extends BaseListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();
        player.sendMessage("/regin to register, /login to login");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        var player = event.getPlayer();
        if (!LogIns.checkPlayer(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        var player = event.getPlayer();
        LogIns.logoutPlayer(player);
    }

}
