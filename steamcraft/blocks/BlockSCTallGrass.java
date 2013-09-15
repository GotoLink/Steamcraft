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
    @Override
    public int idDropped(int i, Random random, int j)
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
    @Override
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
