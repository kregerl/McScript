package com.loucaskreger.mcscript.client.screen;

import com.loucaskreger.mcscript.McScript;
import com.loucaskreger.mcscript.util.LuaModule;
import com.loucaskreger.mcscript.util.ModuleManager;
import com.loucaskreger.mcscript.util.ModuleStatus;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class ModuleScreen extends Screen {
    // TODO: Add a search bar to the list.

    private static final int ROW_WIDTH = 220;
    private static final int HEADER_HEIGHT = 30;
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
        matrixStack.pushPose();
        renderBackground();
        matrixStack.popPose();
        this.list.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    private void renderBackground() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuilder();
        this.minecraft.getTextureManager().bind(BACKGROUND);
        builder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

        int x0 = Math.max(this.width / 3, ROW_WIDTH);

        addVertex(builder, new Vector3d(x0, HEADER_HEIGHT, 0.0d), 255);
        addVertex(builder, new Vector3d(this.width, HEADER_HEIGHT, 0.0d), 255);
        addVertex(builder, new Vector3d(this.width, 0, 0.0d), 255);
        addVertex(builder, new Vector3d(x0, 0, 0.0d), 255);

        addVertex(builder, new Vector3d(x0, this.height, 0.0d), 191);
        addVertex(builder, new Vector3d(this.width, this.height, 0.0d), 191);
        addVertex(builder, new Vector3d(this.width, HEADER_HEIGHT, 0.0d), 191);
        addVertex(builder, new Vector3d(x0, HEADER_HEIGHT, 0.0d), 191);

        tessellator.end();
    }

    private void addVertex(BufferBuilder builder, Vector3d vertex, int alpha) {
        builder.vertex(vertex.x, vertex.y, vertex.z).uv((float) vertex.x / 32.0F, (float) (vertex.y / 32.0F)).color(32, 32, 32, alpha).endVertex();
    }


    // (mc, width, height, y0, y1,itemHeight)
    class ModuleScreenList extends ExtendedList<ModuleScreenList.Entry> {

        public ModuleScreenList(Minecraft minecraft) {
            super(minecraft, Math.max(ModuleScreen.this.width / 3, ROW_WIDTH), ModuleScreen.this.height, HEADER_HEIGHT, ModuleScreen.this.height, 36);
            this.setRenderBackground(false);
            this.setRenderTopAndBottom(false);
            this.setRenderHeader(true, this.y0);

            for (LuaModule module : ModuleManager.getInstance().getModules().values()) {
                this.addEntry(new Entry(this.minecraft, module));
            }
        }

        @Override
        public void setSelected(@Nullable Entry p_241215_1_) {
            super.setSelected(p_241215_1_);
        }

        @Override
        protected void renderHeader(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, Tessellator tessellator) {
            BufferBuilder builder = tessellator.getBuilder();
            this.minecraft.getTextureManager().bind(BACKGROUND);
            builder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

            addVertex(builder, new Vector3d(this.x0, this.y0, 0.0d), 255);
            addVertex(builder, new Vector3d(this.x1, this.y0, 0.0d), 255);
            addVertex(builder, new Vector3d(this.x1, 0, 0.0d), 255);
            addVertex(builder, new Vector3d(this.x0, 0, 0.0d), 255);

            tessellator.end();
        }

        @Override
        protected void renderBackground(@Nonnull MatrixStack matrixStack) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder builder = tessellator.getBuilder();
            this.minecraft.getTextureManager().bind(BACKGROUND);
            builder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

            addVertex(builder, new Vector3d(this.x0, this.y1, 0.0d), 191);
            addVertex(builder, new Vector3d(this.x1, this.y1, 0.0d), 191);
            addVertex(builder, new Vector3d(this.x1, this.y0, 0.0d), 191);
            addVertex(builder, new Vector3d(this.x0, this.y0, 0.0d), 191);

            tessellator.end();
        }

        private void addVertex(BufferBuilder builder, Vector3d vertex, int alpha) {
            builder.vertex(vertex.x, vertex.y, vertex.z).uv((float) vertex.x / 32.0F, (float) (vertex.y + (int) this.getScrollAmount()) / 32.0F).color(32, 32, 32, alpha).endVertex();
        }

        class Entry extends ExtendedList.AbstractListEntry<ModuleScreen.ModuleScreenList.Entry> {

            private final Minecraft mc;
            private final LuaModule module;

            private Entry(Minecraft mc, LuaModule module) {
                this.mc = mc;
                this.module = module;
            }

            @Override
            public void render(@Nonnull MatrixStack matrixStack, int entryIndex, int yPos, int xPos, int p_230432_5_, int mouseX, int mouseY, int p_230432_8_, boolean p_230432_9_, float partialTicks) {
                this.mc.getTextureManager().bind(ModuleStatus.MODULE_STATUS_ICONS);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);

                RenderSystem.enableBlend();
                blit(matrixStack, xPos, yPos, 0, this.module.getStatus().getTextureYPos(), 32, 32);
                RenderSystem.disableBlend();

                drawString(matrixStack, ModuleScreen.this.font, this.module.getDisplayName(), xPos + 32, yPos, 16777215);
                matrixStack.pushPose();
                final float FONT_SCALE_FACTOR = 0.5f;
                matrixStack.scale(FONT_SCALE_FACTOR, FONT_SCALE_FACTOR, 0.f);
                final float INVERSE_FONT_SCALE_FACTOR = 1 / FONT_SCALE_FACTOR;
                final int AUTHOR_OFFSET = 10;
                drawString(matrixStack, ModuleScreen.this.font, String.format("By: %s", this.module.getAuthor()), (int) (INVERSE_FONT_SCALE_FACTOR * (xPos + 32)), (int) (INVERSE_FONT_SCALE_FACTOR * (yPos + AUTHOR_OFFSET)), 8421504);
                final int VERSION_OFFSET = AUTHOR_OFFSET + 6;
                drawString(matrixStack, ModuleScreen.this.font, String.format("Version: %s", this.module.getVersion()), (int) (INVERSE_FONT_SCALE_FACTOR * (xPos + 32)), (int) (INVERSE_FONT_SCALE_FACTOR * (yPos + VERSION_OFFSET)), 8421504);
                matrixStack.popPose();
            }


            @Override
            public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
                ModuleScreen.ModuleScreenList.this.setSelected(this);
                return true;
            }
        }
    }

}
