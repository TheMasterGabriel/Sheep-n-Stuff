package io.themastergabriel.sheepnstuff.common.items;

import io.themastergabriel.sheepnstuff.SheepNStuff;
import io.themastergabriel.sheepnstuff.common.blocks.BlockTreeLogBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLivingRod extends ItemMagical
{
    public ItemLivingRod()
    {
        super(new ModelResourceLocation(new ResourceLocation(SheepNStuff.MODID, "living_rod_leaves"), "inventory"));
        this.setMaxStackSize(1);
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState state = worldIn.getBlockState(pos);

        if(state.getBlock() instanceof BlockTreeLogBase)
        {
            worldIn.setBlockState(pos, state.cycleProperty(BlockTreeLogBase.LEAVED));

            if(worldIn.isRemote)
            {
                for (int i = 0; i < 15; i++)
                {
                    worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)((float)pos.getX() + itemRand.nextFloat()), (double)pos.getY() + (double)itemRand.nextFloat() * state.getBoundingBox(worldIn, pos).maxY, (double)((float)pos.getZ() + itemRand.nextFloat()), itemRand.nextGaussian() * 0.02D, itemRand.nextGaussian() * 0.02D, itemRand.nextGaussian() * 0.02D);
                }
            }

            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
    }

    /**
     * Performs any rendering logical for the secondary magic layer. There is an OpenGL context available here.
     */
    @Override
    @SideOnly(Side.CLIENT)
    protected void doRenderLayer(IBakedModel model)
    {
        this.renderEffect(model);
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add("Uses " + TextFormatting.GREEN + "\u2698plant\u2E19" + TextFormatting.GRAY + " magic to sprout or wither leaves from a fruit log");
        tooltip.add(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? TextFormatting.ITALIC + "Hold LSHIFT for a secret..." : TextFormatting.AQUA + "All the magic is in the stick");
    }
}