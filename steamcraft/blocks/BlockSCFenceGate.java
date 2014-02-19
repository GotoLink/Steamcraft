package steamcraft.blocks;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSCFenceGate extends BlockFenceGate {
	public BlockSCFenceGate() {
		super();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon func_149691_a(int par1, int par2) {
		return field_149761_L;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister par1IconRegister) {
		this.field_149761_L = par1IconRegister.registerIcon(func_149641_N());
	}
}
