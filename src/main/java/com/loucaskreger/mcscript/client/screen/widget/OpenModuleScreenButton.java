package com.loucaskreger.mcscript.client.screen.widget;

import com.loucaskreger.mcscript.McScript;
import com.loucaskreger.mcscript.client.screen.ModuleScreen;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class OpenModuleScreenButton extends Button {

    private static final ResourceLocation BUTTON_LOCATION = new ResourceLocation(McScript.MOD_ID, "textures/gui/corner_button.png");
    private static final int WIDTH = 64;
    private static final int HEIGHT = 36;

    public OpenModuleScreenButton(Screen screen) {
        super(screen.width - WIDTH, screen.height - HEIGHT, WIDTH, HEIGHT, new TranslationTextComponent("module.menu"), button -> ModuleScreen.openScreen(screen));
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.font;
        minecraft.getTextureManager().bind(BUTTON_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.isHovered() ? 1 : 0;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(matrixStack, this.x, this.y, 0, i * this.height, this.width, this.height);
        ITextComponent message = this.getMessage();
        drawCenteredString(matrixStack, fontrenderer, message, this.x + ((this.width / 2) + (message.getString().length() / 2)), this.y + (this.height / 2), getFGColor() | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }
}
