package steamcraft.blocks;

import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import steamcraft.HandlerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTeaPlant extends BlockCrops {
	public static int modelID;
	private IIcon[] icons;

	public BlockTeaPlant() {
		super();
	}

	@Override
	public void func_149863_m(World world, int i, int j, int k) {
		world.setBlockMetadataWithNotify(i, j, k, 7, 2);
	}

	@Override
    @SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		if (j < 1) {
			return icons[0];
		} else if (j < 7) {
			return icons[1];
		} else {
			return icons[2];
		}
	}

	@Override
	public int getRenderType() {
		return modelID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.icons = new IIcon[3];
		for (int i = 0; i < this.icons.length; ++i) {
			this.icons[i] = par1IconRegister.registerIcon(this.getTextureName() + "_stage_" + i);
		}
	}

	@Override
	protected Item func_149865_P() {
		return HandlerRegistry.getItem("steamcraft:teaLeaf").get();
	}

	@Override
	protected Item func_149866_i() {
		return HandlerRegistry.getItem("steamcraft:teaSeeds").get();
	}
}
