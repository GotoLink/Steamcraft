package steamcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBrassLog extends Block {
	private IIcon blockSide;

	public BlockBrassLog() {
		super(Material.field_151573_f);
	}

	@Override
	public IIcon func_149673_e(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if (l == 1 || l == 0) {
			return field_149761_L;
		} else {
			return blockSide;
		}
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister par1IconRegister) {
        field_149761_L = par1IconRegister.registerIcon(func_149641_N() + "top");
		blockSide = par1IconRegister.registerIcon(func_149641_N() + "side");
	}
}
