package com.loucaskreger.mcscript.util;

import com.loucaskreger.mcscript.McScript;
import net.minecraft.util.ResourceLocation;

public enum ModuleStatus {
    ACTIVE,
    ERROR,
    DISABLED;

    public static final ResourceLocation MODULE_STATUS_ICONS = new ResourceLocation(McScript.MOD_ID, "textures/gui/module_status.png");

    public int getTextureYPos() {
        return this.ordinal() * 32;
    }
}
