package com.loucaskreger.mcscript.client.screen;

import com.loucaskreger.mcscript.McScript;
import com.loucaskreger.mcscript.util.LuaModule;
import com.loucaskreger.mcscript.util.ModuleManager;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;
import java.util.Map;

@SuppressWarnings("deprecation")
public class ModuleScreen extends Screen {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(McScript.MOD_ID, "textures/gui/background.png");
    private final Screen previousScreen;
    private ModuleScreenList list;

    protected ModuleScreen(Screen previousScreen) {
        super(new StringTextComponent("Loaded Modules"));
        this.previousScreen = previousScreen;
    }

    public static void openScreen(Screen previousScreen) {
        Minecraft.getInstance().setScreen(new ModuleScreen(previousScreen));
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(this.previousScreen);
    }

    @Override
    protected void init() {
        this.list = new ModuleScreenList(this.minecraft);
        this.children.add(this.list);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.list.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    class ModuleScreenList extends ExtendedList<ModuleScreenList.Entry> {

        public ModuleScreenList(Minecraft minecraft) {
            //      mc,         width,                  height,                 y0,                 y1,                     itemHeight
            super(minecraft, ModuleScreen.this.width, ModuleScreen.this.height, 30, ModuleScreen.this.height, 36);
            this.setRenderBackground(false);
            this.setRenderTopAndBottom(false);
            this.setRenderHeader(true, this.y0);

            for (Map.Entry<String, LuaModule> entry : ModuleManager.getInstance().getModules().entrySet()) {
                this.addEntry(new Entry(entry));
            }
        }

        @Override
        protected void renderHeader(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, Tessellator tessellator) {
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            this.minecraft.getTextureManager().bind(BACKGROUND);
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

            bufferbuilder.vertex(this.x0, this.y0, 0.0D).uv((float) this.x0 / 32.0F, (float) (this.y0 + (int) this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            bufferbuilder.vertex(this.x1, this.y0, 0.0D).uv((float) this.x1 / 32.0F, (float) (this.y0 + (int) this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            bufferbuilder.vertex(this.x1, 0, 0.0D).uv((float) this.x1 / 32.0F, (float) (this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            bufferbuilder.vertex(this.x0, 0, 0.0D).uv((float) this.x0 / 32.0F, (float) (this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();

            tessellator.end();
        }

        @Override
        protected void renderBackground(@Nonnull MatrixStack matrixStack) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            this.minecraft.getTextureManager().bind(BACKGROUND);
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

            bufferbuilder.vertex(this.x0, this.y1, 0.0D).uv((float) this.x0 / 32.0F, (float) (this.y1 + (int) this.getScrollAmount()) / 32.0F).color(32, 32, 32, 191).endVertex();
            bufferbuilder.vertex(this.x1, this.y1, 0.0D).uv((float) this.x1 / 32.0F, (float) (this.y1 + (int) this.getScrollAmount()) / 32.0F).color(32, 32, 32, 191).endVertex();
            bufferbuilder.vertex(this.x1, this.y0, 0.0D).uv((float) this.x1 / 32.0F, (float) (this.y0 + (int) this.getScrollAmount()) / 32.0F).color(32, 32, 32, 191).endVertex();
            bufferbuilder.vertex(this.x0, this.y0, 0.0D).uv((float) this.x0 / 32.0F, (float) (this.y0 + (int) this.getScrollAmount()) / 32.0F).color(32, 32, 32, 191).endVertex();

            tessellator.end();
        }

        class Entry extends ExtendedList.AbstractListEntry<ModuleScreen.ModuleScreenList.Entry> {

            private final ITextComponent name;

            private Entry(Map.Entry<String, LuaModule> entry) {
                this.name = new StringTextComponent(entry.getKey());
            }

            @Override
            public void render(@Nonnull MatrixStack matrixStack, int entryIndex, int yPos, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float partialTicks) {
                McScript.LOGGER.info("HERE2");
                McScript.LOGGER.info("p_230432_4_: " + p_230432_4_);
                McScript.LOGGER.info("p_230432_5_: " + p_230432_5_);
                McScript.LOGGER.info("p_230432_6_: " + p_230432_6_);
                McScript.LOGGER.info("p_230432_7_: " + p_230432_7_);
                McScript.LOGGER.info("p_230432_8_: " + p_230432_8_);
                McScript.LOGGER.info("p_230432_9_: " + p_230432_9_);
                McScript.LOGGER.info("partialTicks: " + partialTicks);


                drawString(matrixStack, ModuleScreen.this.font, name, 0, yPos, 16777215);
            }
        }
    }

}
