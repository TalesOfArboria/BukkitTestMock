package com.jcwhatever.bukkit.v1_8_R1;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

/**
 * Mock implementation of {@link org.bukkit.inventory.InventoryView}.
 */
public class MockInventoryView extends InventoryView {

    private HumanEntity _player;
    private Inventory _topInventory;
    private Inventory _bottomInventory;
    private ItemStack _cursor;

    public MockInventoryView(Player player) {
        _player = player;
        _topInventory = new MockInventory(player, InventoryType.CHEST, 6 * 9);
        _bottomInventory = player.getInventory();
    }

    public MockInventoryView(Player player, Inventory topInventory) {
        _player = player;
        _topInventory = topInventory;
        _bottomInventory = player.getInventory();
    }

    /**
     * Simulate a click on a slot in the inventory view.
     *
     * @param type          The slot type.
     * @param rawSlotIndex  The raw slot index of the click.
     * @param clickType     The click type.
     * @param action        The inventory action.
     */
    public void click(SlotType type, int rawSlotIndex, ClickType clickType, InventoryAction action) {

        InventoryClickEvent event = new InventoryClickEvent(this, type, rawSlotIndex, clickType, action);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;

        switch (event.getAction()) {
            case PICKUP_ALL: {
                _cursor = getItem(rawSlotIndex);
                setItem(rawSlotIndex, null);
                break;
            }
            case PICKUP_SOME: {
                break;
            }
            case PICKUP_HALF: {
                ItemStack slot = getItem(rawSlotIndex);
                if (slot != null) {
                    _cursor = slot.clone();
                    _cursor.setAmount(slot.getAmount() / 2);
                }
                break;
            }
            case PICKUP_ONE: {
                ItemStack slot = getItem(rawSlotIndex);
                if (slot != null) {
                    _cursor = slot.clone();
                    _cursor.setAmount(1);
                }
                break;
            }
            case PLACE_ALL: {
                setItem(rawSlotIndex, _cursor);
                _cursor = null;
                break;
            }
            case PLACE_SOME: {
                break;
            }
            case PLACE_ONE: {
                if (_cursor != null && _cursor.getAmount() > 0) {
                    _cursor.setAmount(_cursor.getAmount() - 1);

                    ItemStack current = getItem(rawSlotIndex);
                    if (current != null && current.getType() == _cursor.getType()) {
                        current.setAmount(current.getAmount() + 1);
                    }
                    else {
                        current = _cursor.clone();
                        current.setAmount(1);
                        setItem(rawSlotIndex, current);
                    }
                }
                break;
            }
            case SWAP_WITH_CURSOR: {
                ItemStack current = getItem(rawSlotIndex);
                setItem(rawSlotIndex, _cursor);
                _cursor = current;
                break;
            }
            case DROP_ALL_CURSOR: {
                _cursor = null;
                break;
            }
            case DROP_ONE_CURSOR: {
                _cursor.setAmount(_cursor.getAmount() - 1);
                break;
            }
            case DROP_ALL_SLOT: {
                setItem(rawSlotIndex, null);
                break;
            }
            case DROP_ONE_SLOT: {
                ItemStack current = getItem(rawSlotIndex);
                current.setAmount(current.getAmount() - 1);
                break;
            }
            case MOVE_TO_OTHER_INVENTORY: {
                ItemStack current = getItem(rawSlotIndex);
                setItem(rawSlotIndex, null);
                _topInventory.addItem(current);
                break;
            }
            case HOTBAR_MOVE_AND_READD:
                break;
            case HOTBAR_SWAP:
                break;
            case CLONE_STACK:
                _cursor = getItem(rawSlotIndex);
                if (_cursor != null) {
                    _cursor = _cursor.clone();
                    _cursor.setAmount(_cursor.getType().getMaxStackSize());
                }
                break;
            case COLLECT_TO_CURSOR:
                break;
        }
    }

    @Override
    public Inventory getTopInventory() {
        return _topInventory;
    }

    @Override
    public Inventory getBottomInventory() {
        return _bottomInventory;
    }

    @Override
    public HumanEntity getPlayer() {
        return _player;
    }

    @Override
    public InventoryType getType() {
        return _topInventory.getType();
    }
}
