package steamcraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMainFurnace extends BlockFurnace {
	protected IIcon furnaceIconTop;
	protected IIcon furnaceIconFront;
	protected final boolean isActive;
	private String[] names = new String[3];

	protected BlockMainFurnace(boolean par2, String side, String top, String front) {
		super(par2);
		this.isActive = par2;
		this.names[0] = side;
		this.names[1] = top;
		this.names[2] = front;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		return par1 == 1 ? this.furnaceIconTop : (par1 == 0 ? this.furnaceIconTop : (par1 != par2 ? this.blockIcon : this.furnaceIconFront));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("steamcraft:" + names[0]);
		this.furnaceIconFront = par1IconRegister.registerIcon("steamcraft:" + names[2] + (this.isActive ? "active" : "idle"));
		this.furnaceIconTop = par1IconRegister.registerIcon("steamcraft:" + names[1]);
	}

	public static void updateFurnaceBlockState(boolean flag, World world, int i, int j, int k, Block activeID, Block idleID, boolean sounds) {
		int l = world.getBlockMetadata(i, j, k);
		TileEntity tileentity = world.getTileEntity(i, j, k);
		setKeepInventory(true);
		if (flag) {
			if (sounds) {
				world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, "mob.ghast.fireball", 1.0F, 0.8F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F);
				world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, "mob.zombiepig.zpigdeath", 0.1F, 0.1F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.6F);
				world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, "fire.ignite", 1.5F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			}
			world.setBlock(i, j, k, activeID);
		} else {
			if (sounds)
				world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, "ambient.cave.cave", 0.1F, 0.1F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			world.setBlock(i, j, k, idleID);
		}
		setKeepInventory(false);
		world.setBlockMetadataWithNotify(i, j, k, l, 2);
		if (tileentity != null) {
			tileentity.validate();
			world.setTileEntity(i, j, k, tileentity);
		}
	}

	public static void setKeepInventory(boolean value) {
        field_149934_M = value;
	}
}
