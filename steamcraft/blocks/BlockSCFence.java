package steamcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSCFence extends BlockFence
{
    public BlockSCFence(int i, Material material, Block blockgate, boolean flag)
    {
        super(i, "steamcraft:castironblock", material);
		blockGate = blockgate;
		doesJoinBlocks = flag;
    }
    @Override
    public boolean canPlaceTorchOnTop(World world, int i, int j, int k)
    {
    	return true;
    }
	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j - 1, k) == blockID)
        {
            return true;
        }
        if(!world.getBlockMaterial(i, j - 1, k).isSolid())
        {
            return false;
        } else
        {
            return super.canPlaceBlockAt(world, i, j, k);
        }
    }
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, (float)j + 1.5F, k + 1);
    }
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }
	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	@Override
    public int getRenderType()
    {
        return 11;
    }
	@Override
    public boolean canConnectFenceTo(IBlockAccess iblockaccess, int i, int j, int k)
    {
        int l = iblockaccess.getBlockId(i, j, k);
		if(doesJoinBlocks && Block.blocksList[l] != null){
			return Block.blocksList[l].isOpaqueCube() || l == blockID || l == blockGate.blockID;
		}
        return l == blockID || l == blockGate.blockID;
    }
	public Block blockGate;
	public boolean doesJoinBlocks;
}
