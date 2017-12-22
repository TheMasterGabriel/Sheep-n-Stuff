package io.themastergabriel.sheepnstuff.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public abstract class BlockTreeLogBase extends Block
{
    public static final PropertyBool LEAVED = PropertyBool.create("leaved");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    public static final PropertyBool[] FACINGS = { DOWN, UP, NORTH, EAST, SOUTH, WEST, };

    protected BlockTreeLogBase()
    {
        super(Material.WOOD);
        this.setHardness(2.0F);
        this.setLightOpacity(1);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override
    public Material getMaterial(IBlockState state)
    {
        return state.getValue(LEAVED) ? Material.LEAVES : Material.WOOD;
    }

    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity)
    {
        return state.getValue(LEAVED) ? SoundType.PLANT : SoundType.WOOD;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = state.getActualState(source, pos);

        if(!state.getValue(LEAVED))
        {
            double f1 = state.getValue(WEST) ? 0.0D : 0.25D;
            double f2 = state.getValue(DOWN) ? 0.0D : 0.25D;
            double f3 = state.getValue(NORTH) ? 0.0D : 0.25D;
            double f4 = state.getValue(EAST) ? 1.0D : 0.75D;
            double f5 = state.getValue(UP) ? 1.0D : 0.75D;
            double f6 = state.getValue(SOUTH) ? 1.0D : 0.75D;
            return new AxisAlignedBB(f1, f2, f3, f4, f5, f6);
        }

        return super.getBoundingBox(state, source, pos);
    }

    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        if (!p_185477_7_) state = state.getActualState(worldIn, pos);

        if(!state.getValue(LEAVED))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D));
            if(state.getValue(WEST)) addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.0D, 0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D));
            if(state.getValue(EAST)) addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.8125D, 0.1875D, 0.1875D, 1.0D, 0.8125D, 0.8125D));
            if(state.getValue(UP)) addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.8125D, 0.1875D, 0.8125D, 1.0D, 0.8125D));
            if(state.getValue(DOWN)) addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.1875D, 0.8125D));
            if(state.getValue(NORTH)) addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.0D, 0.8125D, 0.8125D, 0.1875D));
            if(state.getValue(SOUTH)) addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D, 1.0D));
        }
        else
        {
            super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, p_185477_7_);
        }
    }

    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        if(state.getValue(LEAVED) && !player.capabilities.isCreativeMode)
        {
            world.setBlockState(pos, state.withProperty(LEAVED, false), world.isRemote ? 10 : 2);
            return false;
        }

        return world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
    }

    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return blockState.getValue(LEAVED) ? 0.2F : super.getBlockHardness(blockState, worldIn, pos);
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.HARVESTED_FRUIT_LOG);
    }

    /**
     * Called when a user uses the creative pick block button on this block
     *
     * @param target The full target the player is looking at
     * @return A ItemStack to add to the player's inventory, empty itemstack if nothing should be added.
     */
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(ModBlocks.HARVESTED_FRUIT_LOG), 1, this.damageDropped(state));
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}