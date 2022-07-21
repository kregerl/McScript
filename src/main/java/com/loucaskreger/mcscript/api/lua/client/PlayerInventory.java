package com.loucaskreger.mcscript.api.lua.client;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import org.luaj.vm2.LuaTable;

public class PlayerInventory extends LuaTable {

    // playerEntity here cannot be null singe it is only instantiated once the player exists
    public PlayerInventory(ClientPlayerEntity playerEntity) {
        super();

//        playerEntity.getMainHandItem()
        this.set("selectedHotbarIndex", playerEntity.inventory.selected);
    }

}
