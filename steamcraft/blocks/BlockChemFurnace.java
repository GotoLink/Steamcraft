package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import steamcraft.HandlerRegistry;
import steamcraft.Steamcraft;
import steamcraft.TileEntityChemFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChemFurnace extends BlockMainFurnace {
	public BlockChemFurnace(boolean flag) {
		super(flag, "chemfurnaceside", "castironblock", "chemfurnace");
	}

	@Override
	public TileEntity func_149915_a(World world, int i) {
		return new TileEntityChemFurnace();
	}

	@Override
	public Item func_149650_a(int i, Random random, int j) {
		return Item.func_150898_a(getIdle());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item func_149694_d(World par1World, int par2, int par3, int par4) {
		return Item.func_150898_a(getIdle());
	}

	@Override
	public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		if (world.isRemote) {
			return true;
		}
		if (world.func_147438_o(i, j, k) instanceof TileEntityChemFurnace) {
			entityplayer.openGui(Steamcraft.instance, 1, world, i, j, k);
		}
		return true;
	}

	@Override
	public void func_149734_b(World world, int i, int j, int k, Random random) {
		if (!isActive) {
			return;
		}
		int l = world.getBlockMetadata(i, j, k);
		float f = i + 0.5F;
		float f1 = j + 0.0F + (random.nextFloat() * 6F) / 16F;
		float f2 = k + 0.5F;
		float f3 = 0.52F;
		float f4 = random.nextFloat() * 0.6F - 0.3F;
		if (l == 4) {
			world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
		} else if (l == 5) {
			world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
		} else if (l == 2) {
			world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
		} else if (l == 3) {
			world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
		}
	}

	public static Block getActive() {
		return HandlerRegistry.getBlock("steamcraft:chemFurnaceOn").get();
	}

	public static Block getIdle() {
		return HandlerRegistry.getBlock("steamcraft:chemFurnace").get();
	}
}
