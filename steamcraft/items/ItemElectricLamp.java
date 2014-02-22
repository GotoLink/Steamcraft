package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemElectricLamp extends Item {
	private Block spawnID;

	public ItemElectricLamp(Block spawn) {
		super();
		spawnID = spawn;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
		if (world.getBlock(i, j, k) != Blocks.snow) {
			if (l == 0) {
				j--;
			}
			if (l == 1) {
				j++;
			}
			if (l == 2) {
				k--;
			}
			if (l == 3) {
				k++;
			}
			if (l == 4) {
				i--;
			}
			if (l == 5) {
				i++;
			}
			if (!world.isAirBlock(i, j, k)) {
				return false;
			}
		}
		if (!entityplayer.canPlayerEdit(i, j, k, l, itemstack)) {
			return false;
		}
		if (itemstack.stackSize == 0) {
			return false;
		}
		if (world.canPlaceEntityOnSide(spawnID, i, j, k, false, l, null, itemstack)) {
            int meta = spawnID.onBlockPlaced(world, i, j, k, l, par8, par9, par10, 0);
			if (world.setBlock(i, j, k, spawnID, meta, 3)) {
				if (world.getBlock(i, j, k) == spawnID) {
					spawnID.onBlockPlacedBy(world, i, j, k, entityplayer, itemstack);
                    spawnID.onPostBlockPlaced(world, i, j, k, meta);
				}
				world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, spawnID.stepSound.func_150496_b(), (spawnID.stepSound.getVolume() + 1.0F) / 2.0F, spawnID.stepSound.getPitch() * 0.8F);
				itemstack.stackSize--;
			}
		}
		return true;
	}
}
