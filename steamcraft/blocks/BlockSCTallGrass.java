package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import steamcraft.mod_Steamcraft;

public class BlockSCTallGrass extends BlockTallGrass
{
    public BlockSCTallGrass(int i)
    {
        super(i);
    }

    public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k)
    {
        int l = iblockaccess.getBlockMetadata(i, j, k);
        if(l == 0)
        {
            return 0xffffff;
        } else
        {
            long l1 = i * 0x2fc20f + k * 0x5d8875 + j;
            l1 = l1 * l1 * 0x285b825L + l1 * 11L;
            i = (int)((long)i + ((l1 >> 14 & 31L) - 16L));
            j = (int)((long)j + ((l1 >> 19 & 31L) - 16L));
            k = (int)((long)k + ((l1 >> 24 & 31L) - 16L));
            double d = iblockaccess.getWorldChunkManager().func_35554_b(i, k);
            double d1 = iblockaccess.getWorldChunkManager().func_35558_c(i, k);
            return ColorizerGrass.getGrassColor(d, d1);
        }
    }

    public int idDropped(int i, Random random)
    {
        if(random.nextInt(8) == 0)
        {
            return Item.seeds.itemID;
        } else if(random.nextInt(8) == 1)
        {
            return mod_Steamcraft.teaSeed.itemID;
        }
        {
            return -1;
        }
    }

    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {
        if(!world.isRemote && entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().itemID == Item.shears.itemID)
        {
            entityplayer.addStat(StatList.mineBlockStatArray[blockID], 1);
            dropBlockAsItem_do(world, i, j, k, new ItemStack(Block.tallGrass, 1, l));
        } else
        {
            super.harvestBlock(world, entityplayer, i, j, k, l);
        }
    }
}
