package steamcraft.blocks;

import java.util.Random;

import steamcraft.mod_Steamcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBrassLog extends Block
{
	public BlockBrassLog(int i)
    {
        super(i, Material.iron);
    }
	@Override
    public int idDropped(int i, Random random, int j)
    {
        return mod_Steamcraft.woodBrass.blockID;
    }
	@Override
    public Icon getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if(l == 1)
        {
            return mod_Steamcraft.BrassLogTopTex;
        }
        if(l == 0)
        {
            return mod_Steamcraft.BrassLogTopTex;
        }else
        {
            return mod_Steamcraft.BrassLogSideTex;
        }
	}
}
