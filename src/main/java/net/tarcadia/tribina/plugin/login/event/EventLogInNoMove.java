package net.tarcadia.tribina.plugin.login.event;

import net.tarcadia.tribina.plugin.login.LogIns;
import net.tarcadia.tribina.plugin.util.sys.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EventLogInNoMove extends BaseListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        var player = event.getPlayer();
        if (!LogIns.checkPlayer(player)) {
            event.setCancelled(true);
        }
    }

}
