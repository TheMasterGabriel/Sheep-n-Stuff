package io.themastergabriel.sheepnstuff.common.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ItemMagical extends Item
{
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    public static final EnumRarity MAGICAL = EnumHelperClient.addRarity("MAGICAL", TextFormatting.DARK_AQUA, "Magical");
    @SideOnly(Side.CLIENT) private static FontRenderer magicalFontRenderer;
    private final ModelResourceLocation internalLocation;

    public ItemMagical(ModelResourceLocation secondaryLocation)
    {
        super();
        this.internalLocation = secondaryLocation;
    }

    @SideOnly(Side.CLIENT)
    public static FontRenderer createMagicalFontRenderer()
    {
        if(magicalFontRenderer == null)
        {
            Minecraft mc = Minecraft.getMinecraft();
            magicalFontRenderer = new FontRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, false)
            {
                /**
                 * Draws the specified string with a shadow.
                 */
                @Override
                public int drawStringWithShadow(String text, float x, float y, int mask)
                {
                    int color = mask & 0xFFFFFF;
                    int opacity = mask >> 24;
                    return this.drawString(text, x, y, color == 0xFFFFFF ? 0x00AAAA | (opacity << 24) : mask, true);
                }
            };

            if(mc.gameSettings.language != null)
            {
                magicalFontRenderer.setUnicodeFlag(mc.isUnicode());
                magicalFontRenderer.setBidiFlag(mc.getLanguageManager().isCurrentLanguageBidirectional());
            }
        }

        return magicalFontRenderer;
    }

    /**
     * Returns the font renderer used to render tooltips and overlays for this item.
     * Returning null will use the standard font renderer.
     *
     * @param stack The current item stack
     * @return A instance of FontRenderer or null to use default
     */
    @SideOnly(Side.CLIENT)
    @Nullable
    @Override
    public final FontRenderer getFontRenderer(ItemStack stack)
    {
        return magicalFontRenderer;
    }

    /**
     * Returns true if this item has an enchantment glint. By default, this returns
     * <code>stack.isItemEnchanted()</code>, but other items can override it (for instance, written books always return
     * true).
     *
     * Note that if you override this method, you generally want to also call the super version (on {@link Item}) to get
     * the glint for enchanted items. Of course, that is unnecessary if the overwritten version always returns true.
     */
    @SideOnly(Side.CLIENT)
    public final boolean hasEffect(ItemStack stack)
    {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        IBakedModel model = renderItem.getItemModelMesher().getModelManager().getModel(this.internalLocation);

        this.doRenderLayer(renderItem.getItemModelWithOverrides(stack, null, null));
        this.renderModel(model.getOverrides().handleItemState(model, stack, null, null), -1);
        return false;
    }

    /**
     * Performs any rendering logical for the secondary magic layer. There is an OpenGL context available here.
     */
    @SideOnly(Side.CLIENT)
    protected abstract void doRenderLayer(IBakedModel model);

    @SideOnly(Side.CLIENT)
    protected final void renderEffect(IBakedModel model)
    {
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(514);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
        Minecraft.getMinecraft().getTextureManager().bindTexture(RES_ITEM_GLINT);
        GlStateManager.matrixMode(5890);
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0F, 8.0F, 8.0F);
        float f = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
        GlStateManager.translate(f, 0.0F, 0.0F);
        GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
        this.renderModel(model, -8372020);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0F, 8.0F, 8.0F);
        float f1 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F;
        GlStateManager.translate(-f1, 0.0F, 0.0F);
        GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
        this.renderModel(model, -8372020);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    }

    @SideOnly(Side.CLIENT)
    protected final void renderModel(IBakedModel model, int color)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

        for (EnumFacing enumfacing : EnumFacing.values())
        {
            List<BakedQuad> quads = model.getQuads(null, enumfacing, 0L);
            int i = 0;

            for(int j = quads.size(); i < j; ++i)
            {
                LightUtil.renderQuadColor(bufferbuilder, quads.get(i), color);
            }
        }

        List<BakedQuad>quads = model.getQuads(null, null, 0L);
        int i = 0;

        for (int j = quads.size(); i < j; ++i)
        {
            BakedQuad bakedquad = quads.get(i);
            LightUtil.renderQuadColor(bufferbuilder, bakedquad, color);
        }

        tessellator.draw();
    }

    /**
     * Return the internal model resource location, usually associated with metadata -1 and used for secondary render layer
     */
    @SideOnly(Side.CLIENT)
    public ModelResourceLocation getSecondaryResourceLocation()
    {
        return this.internalLocation;
    }

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack)
    {
        return MAGICAL;
    }
}