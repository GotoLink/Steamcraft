package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import steamcraft.mod_Steamcraft;

public class BlockSCTorch extends BlockTorch
{
    public BlockSCTorch(int i)
    {
        super(i);
    }

    private boolean canPlaceTop(World world, int i, int j, int k)
    {
        return (world.isBlockNormalCube(i, j, k)
		|| world.getBlockId(i, j, k) == mod_Steamcraft.railingCastIron.blockID);
    }
	
	private boolean canPlaceSide(World world, int i, int j, int k)
    {
        return (world.isBlockNormalCube(i, j, k) 
		|| world.getBlockId(i, j, k) == mod_Steamcraft.stairCompactStone.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.stairRoof.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.teslaReceiver.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.teslaReceiverActive.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.battery.blockID);
    }
}
