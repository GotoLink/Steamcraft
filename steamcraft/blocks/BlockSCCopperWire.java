package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import steamcraft.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSCCopperWire extends BlockRedstoneWire
{
    public BlockSCCopperWire(int i)
    {
        super(i);
    }
    @Override
    public int getRenderType()
    {
        return ClientProxy.CopperModelID;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k)
    {
        return 0x800000;
    }
    
    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        int l = world.getBlockMetadata(i, j, k);
        if(l > 0)
        {
           /* double d = (double)i + 0.5D + ((double)random.nextFloat() - 0.5D) * 0.20000000000000001D;
            double d1 = (float)j + 0.0625F;
            double d2 = (double)k + 0.5D + ((double)random.nextFloat() - 0.5D) * 0.20000000000000001D;
            float f = (float)l / 15F;
            float f1 = f * 0.6F + 0.4F;
            if(l == 0)
            {
                f1 = 0.0F;
            }
            float f2 = f * f * 0.7F - 0.5F;
            float f3 = f * f * 0.6F - 0.7F;
            if(f2 < 0.0F)
            {
                f2 = 0.0F;
            }
            if(f3 < 0.0F)
            {
                f3 = 0.0F;
            }
            world.spawnParticle("reddust", d, d1, d2, f1, f2, f3);*/
			double d = (double)i + 0.5D + ((double)random.nextFloat() - 0.5D) * 0.20000000000000001D;
            double d1 = (float)j + 0.0625F;
            double d2 = (double)k + 0.5D + ((double)random.nextFloat() - 0.5D) * 0.20000000000000001D;
            float f = 1 / 15F;
            float f1 = -1;
            float f2 = f * f * 0.7F - 0.5F;
            float f3 = f * f * 0.6F - 0.7F;
            if(f2 < 0.0F)
            {
                f2 = 0.7F;
            }
            if(f3 < 0.0F)
            {
                f3 = 1.0F;
            }
            world.spawnParticle("reddust", d, d1, d2, f1, f2, f3);
        }
    }
}
