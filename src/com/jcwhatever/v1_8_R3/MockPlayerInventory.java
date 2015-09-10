package com.jcwhatever.v1_8_R3;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Mock implementation of {@link org.bukkit.inventory.PlayerInventory}.
 */
public class MockPlayerInventory extends MockInventory implements PlayerInventory {

    private ItemStack[] _armor = new ItemStack[4];
    private int _heldItemSlot;

    /**
     * Constructor.
     *
     * @param holder  The inventory owner.
     * @param type    The inventory type.
     * @param size    The inventory size.
     */
    public MockPlayerInventory(Player holder, InventoryType type, int size) {
        super(holder, type, size);
    }

    @Override
    public ItemStack[] getArmorContents() {
        return _armor;
    }

    @Override
    public ItemStack getHelmet() {
        return _armor[0];
    }

    @Override
    public ItemStack getChestplate() {
        return _armor[1];
    }

    @Override
    public ItemStack getLeggings() {
        return _armor[2];
    }

    @Override
    public ItemStack getBoots() {
        return _armor[3];
    }

    @Override
    public void setArmorContents(ItemStack[] itemStacks) {
        assert itemStacks.length == 4;
        _armor = itemStacks;
    }

    @Override
    public void setHelmet(ItemStack itemStack) {
        _armor[0] = itemStack;
    }

    @Override
    public void setChestplate(ItemStack itemStack) {
        _armor[1] = itemStack;
    }

    @Override
    public void setLeggings(ItemStack itemStack) {
        _armor[2] = itemStack;
    }

    @Override
    public void setBoots(ItemStack itemStack) {
        _armor[3] = itemStack;
    }

    @Override
    public ItemStack getItemInHand() {
        return getItem(_heldItemSlot);
    }

    @Override
    public void setItemInHand(ItemStack itemStack) {
        setItem(_heldItemSlot, itemStack);
    }

    @Override
    public int getHeldItemSlot() {
        return _heldItemSlot;
    }

    @Override
    public void setHeldItemSlot(int i) {
        _heldItemSlot = i;
    }

    @Override
    public int clear(int id, int data) {
        int cleared = 0;
        for (int i=0; i < this.getSize(); i++) {
            ItemStack item = getItem(i);
            if (item == null)
                continue;

            if (id == -1 || data == -1) {
                setItem(i, null);
                cleared++;
            }
            else if (item.getType().getId() == id &&
                    item.getData().getData() == data) {
                setItem(i, null);
                cleared++;
            }
        }
        return cleared;
    }
}
