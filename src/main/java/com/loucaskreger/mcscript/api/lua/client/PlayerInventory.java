package com.loucaskreger.mcscript.api.lua.client;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class PlayerInventory extends LuaTable {

    // playerEntity here cannot be null singe it is only instantiated once the player exists
    public PlayerInventory(ClientPlayerEntity playerEntity) {
        super();

//        playerEntity.getMainHandItem()
        this.set("selectedHotbarIndex", playerEntity.inventory.selected);
        this.set("getArmor", itemStackListToLuaTable(playerEntity.inventory.armor));
        this.set("getItems", itemStackListToLuaTable(playerEntity.inventory.items));
        this.set("getOffhand", itemStackListToLuaTable(playerEntity.inventory.offhand));
    }


    private LuaTable itemStackListToLuaTable(NonNullList<ItemStack> items) {
        LuaValue[] values = new LuaValue[items.size()];
        for (int i= 0; i < items.size(); i++) {
            values[i] = CoerceJavaToLua.coerce(items.get(i));
        }
        return LuaTable.listOf(values);
    }

}
