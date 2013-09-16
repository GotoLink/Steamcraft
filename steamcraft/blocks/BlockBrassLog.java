package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import steamcraft.mod_Steamcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBrassLog extends Block
{
	private Icon blockSide;
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
        if(l == 1 || l == 0)
        {
            return blockIcon;
        }
        else
        {
            return blockSide;
        }
	}
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
		blockIcon = par1IconRegister.registerIcon(getTextureName()+"top");
        blockSide = par1IconRegister.registerIcon(getTextureName()+"side");
    }
}
