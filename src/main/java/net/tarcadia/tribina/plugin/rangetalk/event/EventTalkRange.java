package net.tarcadia.tribina.plugin.rangetalk.event;

import net.tarcadia.tribina.plugin.rangetalk.RangeTalks;
import net.tarcadia.tribina.plugin.util.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EventTalkRange extends BaseListener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        var player = event.getPlayer();
        var recipients = event.getRecipients();
        recipients.removeIf(p -> !RangeTalks.checkDist(player, p));
    }

}
