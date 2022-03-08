package net.tarcadia.tribina.plugin.mapregion.event;

import net.tarcadia.tribina.plugin.mapregion.MapRegions;
import net.tarcadia.tribina.plugin.util.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;
import java.util.Objects;

public class EventRegionName extends BaseListener {

    private Map<String, String> lastRegion;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        var to = event.getTo();
        var player = event.getPlayer();
        var lastId = lastRegion.get(player.getName());
        String toId;
        if (to != null) {
            toId = MapRegions.getRegionIdTopAt(to);
        } else {
            toId = null;
        }

        if (!Objects.equals(lastId, toId) && (toId != null)) {
            var regionName = MapRegions.getRegion(toId).getName(player);
            var regionLore = MapRegions.getRegion(toId).getLore(player);
            lastRegion.put(player.getName(), toId);
            player.sendTitle(regionName, regionLore, 5, 30, 5);
        }
    }

}
