package io.themastergabriel.sheepnstuff.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTreeLog extends BlockTreeLogBase
{
    public static final PropertyEnum<BlockTreePlanks.EnumType> VARIANT = PropertyEnum.create("variant", BlockTreePlanks.EnumType.class, type -> type.getMetadata() < 8);

    public BlockTreeLog()
    {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockTreePlanks.EnumType.MANGO).withProperty(LEAVED, true));
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(LEAVED, meta >> 3 == 1).withProperty(VARIANT, BlockTreePlanks.EnumType.byMetadata(meta & 7));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(LEAVED) ? 1 : 0) << 3 | state.getValue(VARIANT).getMetadata() % 7;
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        for(PropertyBool bool : FACINGS)
        {
            IBlockState state1 = worldIn.getBlockState(pos.offset(EnumFacing.byName(bool.getName())));
            Block block1 = state1.getBlock();
            boolean flag = block1 instanceof BlockTreeLogBase && state1.getValue(VARIANT) == state.getValue(VARIANT) || (bool == DOWN && (block1 == Blocks.GRASS || block1 == Blocks.DIRT || block1 == Blocks.FARMLAND));
            state = state.withProperty(bool, flag);
        }

        return state;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState state = blockAccess.getBlockState(pos.offset(side));
        Block block = state.getBlock();
        return !(block instanceof BlockTreeLogBase) && (side != EnumFacing.DOWN || block != Blocks.GRASS || block != Blocks.DIRT || block != Blocks.FARMLAND);
    }

    /**
     * Get the MapColor for this Block and the given BlockState
     */
    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.getValue(VARIANT).getMapColor();
    }

    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(VARIANT).getMetadata());
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    public int damageDropped(IBlockState state)
    {
        return state.getValue(VARIANT).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer.Builder(this).add(VARIANT, LEAVED, NORTH, EAST, SOUTH, WEST, UP, DOWN).build();
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for(BlockTreePlanks.EnumType type : VARIANT.getAllowedValues())
        {
            items.add(new ItemStack(this, 1, type.getMetadata()));
        }
    }
}