package steamcraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import steamcraft.HandlerRegistry;
import steamcraft.Steamcraft;
import steamcraft.TileEntitySteamFurnace;

import java.util.Random;

public class BlockSteamFurnace extends BlockMainFurnace {
	public BlockSteamFurnace(boolean flag) {
		super(flag, "steamfurnaceside", "steamfurnacetop", "steamfurnace");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntitySteamFurnace();
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(getIdle());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4) {
		return Item.getItemFromBlock(getIdle());
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		if (world.isRemote) {
			return true;
		}
		if (world.getTileEntity(i, j, k) instanceof TileEntitySteamFurnace) {
			entityplayer.openGui(Steamcraft.instance, 0, world, i, j, k);
		}
		return true;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (!isActive) {
			return;
		}
		TileEntitySteamFurnace tileentityfurnace = (TileEntitySteamFurnace) world.getTileEntity(i, j, k);
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
		if (tileentityfurnace.getWater() > 0) {
			world.spawnParticle("smoke", f, f1 + 1, f2, 0.0D, 0.1D, 0.0D);
			world.spawnParticle("smoke", f, f1 + 1, f2, 0.0D, 0.1D, 0.0D);
		}
	}

	public static Block getActive() {
		return HandlerRegistry.getBlock("steamcraft:steamFurnaceOn").get();
	}

	public static Block getIdle() {
		return HandlerRegistry.getBlock("steamcraft:steamFurnace").get();
	}

	public static void playSound(World world, int i, int j, int k, String s) {
		world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, s, 0.5F, 2.6F * 0.8F);
	}
}
