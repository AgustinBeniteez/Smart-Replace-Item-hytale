package com.agustheking.smartreplaceitem.listener;

import java.util.function.Consumer;
import com.hypixel.hytale.server.core.event.events.entity.LivingEntityInventoryChangeEvent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.inventory.transaction.Transaction;
import com.hypixel.hytale.server.core.Message;
import com.agustheking.smartreplaceitem.SmartReplaceItem;

public class InventoryListener implements Consumer<LivingEntityInventoryChangeEvent> {

    private final SmartReplaceItem plugin;

    public InventoryListener(SmartReplaceItem plugin) {
        this.plugin = plugin;
    }

    @Override
    public void accept(LivingEntityInventoryChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        Inventory inventory = player.getInventory();
        byte hotbarSlot = inventory.getActiveHotbarSlot();
        ItemStack currentItem = inventory.getActiveHotbarItem();

        plugin.getLogger().at(java.util.logging.Level.INFO).log(
            "[SmartReplace] InventoryChange: hotbarSlot=" + hotbarSlot +
            " currentItem=" + currentItem
        );

        ItemStack lastItem = plugin.lastHeldItems.get(player.getUuid());

        plugin.getLogger().at(java.util.logging.Level.INFO).log(
            "[SmartReplace] InventoryChange: lastItem=" + lastItem
        );

        if (lastItem == null || lastItem.isEmpty()) {
            return;
        }

        if (currentItem != null && !currentItem.isEmpty()) {
            if (currentItem.isEquivalentType(lastItem)) {
                ItemStack snapshot = currentItem.withQuantity(currentItem.getQuantity());
                plugin.lastHeldItems.put(player.getUuid(), snapshot);
            } else {
                ItemStack snapshot = currentItem.withQuantity(currentItem.getQuantity());
                plugin.lastHeldItems.put(player.getUuid(), snapshot);
            }
            return;
        }

        refillItem(player, inventory, hotbarSlot, lastItem);
    }

    private void refillItem(Player player, Inventory inventory, byte hotbarSlot, ItemStack targetItem) {
        ItemContainer storage = inventory.getStorage();
        int storageSize = storage.getCapacity();
        
        for (int i = 0; i < storageSize; i++) {
            ItemStack candidate = storage.getItemStack((short)i);
            
            if (candidate != null && !candidate.isEmpty() && candidate.isEquivalentType(targetItem)) {
                inventory.moveItem(
                    Inventory.STORAGE_SECTION_ID, 
                    i, 
                    Inventory.HOTBAR_SECTION_ID, 
                    hotbarSlot, 
                    candidate.getQuantity()
                );
                
                player.sendMessage(Message.parse("§a[SmartReplace] Refilled!"));

                plugin.getLogger().at(java.util.logging.Level.INFO).log(
                    "[SmartReplace] Refilled slot " + hotbarSlot + " with " + candidate.toString()
                );
                break;
            }
        }
    }
}
