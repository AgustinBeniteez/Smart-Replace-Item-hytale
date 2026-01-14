package com.agustheking.smartreplaceitem.listener;

import java.util.function.Consumer;
import com.hypixel.hytale.server.core.event.events.player.PlayerMouseButtonEvent;
import com.hypixel.hytale.protocol.MouseButtonState;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.agustheking.smartreplaceitem.SmartReplaceItem;

public class PlayerListener implements Consumer<PlayerMouseButtonEvent> {

    private final SmartReplaceItem plugin;

    public PlayerListener(SmartReplaceItem plugin) {
        this.plugin = plugin;
    }

    @Override
    public void accept(PlayerMouseButtonEvent event) {
        if (event.getMouseButton().state != MouseButtonState.Pressed) {
            return;
        }

        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        ItemStack itemInHand = inventory.getActiveHotbarItem();
        
        if (itemInHand != null && !itemInHand.isEmpty()) {
            ItemStack snapshot = itemInHand.withQuantity(itemInHand.getQuantity());
            plugin.lastHeldItems.put(player.getUuid(), snapshot);
        } else {
            plugin.lastHeldItems.remove(player.getUuid());
        }
    }
}
