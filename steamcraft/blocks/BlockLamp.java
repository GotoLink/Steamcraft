package steamcraft.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import steamcraft.Steamcraft;

public class BlockLamp extends Block
{
    public BlockLamp(int i, boolean flag)
    {
        super(i, Material.wood);
		isPowered = flag;
		setHardness(2.0F);
		setStepSound(Block.soundGlassFootstep);
		if(flag){
			setLightValue(1.0F);
		}
    }
    @Override
    public void onBlockAdded(World world, int i, int j, int k)
    {
    	if (!world.isRemote)
        {
            if (this.isPowered && !world.isBlockIndirectlyGettingPowered(i, j, k))
            {
                world.scheduleBlockUpdate(i, j, k, this.blockID, 4);
            }
            else if (!this.isPowered && world.isBlockIndirectlyGettingPowered(i, j, k))
            {
                world.setBlock(i, j, k, Steamcraft.lamp.blockID, 0, 2);
            }
        }
    }
    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
    	if (!world.isRemote && this.isPowered && !world.isBlockIndirectlyGettingPowered(i, j, k))
        {
            world.setBlock(i, j, k, Steamcraft.lampoff.blockID, 0, 2);
        }
	}
    @Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
    	onBlockAdded(world,i,j,k);
    }
    @Override
    public int idDropped(int i, Random random, int j)
    {
        return Steamcraft.lampoff.blockID;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return Steamcraft.lampoff.blockID;
    }
	
	private boolean isPowered;
}
