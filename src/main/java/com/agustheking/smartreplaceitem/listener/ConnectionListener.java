package com.agustheking.smartreplaceitem.listener;

import java.util.function.Consumer;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.agustheking.smartreplaceitem.SmartReplaceItem;

public class ConnectionListener implements Consumer<PlayerDisconnectEvent> {

    private final SmartReplaceItem plugin;

    public ConnectionListener(SmartReplaceItem plugin) {
        this.plugin = plugin;
    }

    @Override
    public void accept(PlayerDisconnectEvent event) {
        plugin.lastHeldItems.remove(event.getPlayerRef().getUuid());
    }
}
